package model.gameObjects;

import model.World;
import model.enums.BulletType;
import model.enums.EnemyState;
import javafx.scene.media.AudioClip;
import java.awt.Point;
import javafx.scene.image.Image;
import java.util.Random;

public class Enemy extends Tank {

    EnemyState state;
    final AudioClip AUDIO_ENEMYSHOT = new AudioClip(getClass().getResource("/Media/enemyshot.mp3").toString());
    int fireNum = 60;

    /**
     * Initializes a new Enemy using parameters.
     * 
     * @param position
     * @param direction
     * @param height
     * @param width
     * @param health
     * @param speed
     * @param turretDirection
     * @param pastPositions
     */
    public Enemy(Image image, Point position, int direction, int height, int width, int health, int speed,
            int turretDirection, int reloadTime, int reloadStatus, Point lastPosition, EnemyState state) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.height = (int) (image.getHeight() + 0.5);
        this.width = (int) (image.getWidth() + 0.5);
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.reloadTime = reloadTime;
        this.reloadStatus = reloadStatus;
        this.lastPosition = lastPosition;
        this.state = state;
    }

    /**
     * Initializes a new enemy using random variables
     * 
     * @param useRandom - used as a way to specify weather to use random values or
     *                  null values, as with Enemy().
     */
    public Enemy(boolean useRandom) {
        if (useRandom) {
            Random random = new Random();
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    if (World.instance().getCurrentWave() > 7) {
                        image = new Image("/Images/drone.gif");
                        health = 1;
                        speed = 8;
                        break;
                    }
                case 1:
                    if (World.instance().getCurrentWave() > 3) {
                        image = new Image("/Images/heavytank.gif");
                        health = 4;
                        speed = 2;
                        break;
                    }
                case 2:
                    image = new Image("/Images/bluetankv1wider.gif");
                    health = 2;
                    speed = 4;
                    break;
            }

            position = new Point(random.nextInt(1010) + 320, random.nextInt(900) - 400);
            direction = random.nextInt(360);
            height = (int) (image.getHeight() + 0.5);
            width = (int) (image.getWidth() + 0.5);
            turretDirection = direction;
            state = EnemyState.PAUSE;
            lastPosition = position;
            fireNum = random.nextInt(61) + 30;
        }

    }

    // Initialize a new enemy
    public Enemy() { // number one
        // wuhan virus is the above line when read as a sentence
        image = new Image("/Images/bluetankv1wider.gif");
        state = EnemyState.PAUSE;
    }

    // returns the center position of the players tank.
    public Point findPlayer() {
        Player player = World.instance().getPlayerTank();
        return new Point((int) player.getPosition().getX() + player.getWidth() / 2,
                (int) player.getPosition().getY() + player.getHeight() / 2);
    }

    public int targetPlayer() {
        Point playerPosition = findPlayer();
        double y2 = playerPosition.getY() - position.getY();
        double x2 = playerPosition.getX() - position.getX();
        return (int) Math.toDegrees(Math.atan2(y2, x2));
    }

    // Changes state randomly to a different state than the current one.
    public void changeState() {
        Random random = new Random();
        int num = random.nextInt(7);
        switch (state) {
            case CHARGE:
                if (num <= 3) {
                    state = EnemyState.PAUSE;
                } else if (num > 3) {
                    state = EnemyState.FLEE;
                }
                break;
            case FLEE:
                if (num <= 3) {
                    state = EnemyState.PAUSE;
                } else if (num > 3) {
                    state = EnemyState.CHARGE;
                }
                break;
            case PAUSE:
                state = num > 2 ? EnemyState.CHARGE : EnemyState.FLEE;
                break;
        }
    }

    // Move based off the players position
    public void move() {
        if (World.instance().getCycleCount() == (savedCycle + 18)%180) {
            turretImage = new Image("/Images/cannonbase.png");
        }
        Point playerPosition = findPlayer();
        double dx = playerPosition.getX() - position.getX();
        double dy = playerPosition.getY() - position.getY();
        double dist = Math.hypot(dx, dy);
        dx = dx / dist;
        dy = dy / dist;
        int x;
        int y;

        switch (state) {
            case CHARGE:
                // move toward player
                x = (int) position.getX() + (int) (dx * speed);
                y = (int) position.getY() + (int) (dy * speed);
                if (image.getUrl().endsWith("/Images/drone.gif")) {
                    int moveToward = targetPlayer() + 90;
                    x = (int)(speed * Math.cos(moveToward * Math.PI / 180) + position.getX() + 0.5);
                    y = (int)(speed * Math.sin(moveToward * Math.PI / 180) + position.getY() + 0.5);
                }
                lastPosition = position;
                position = new Point(x, y);
                turretDirection = targetPlayer();
                break;
            case FLEE:
                // move away from player
                x = (int) position.getX() - (int) (dx * speed);
                y = (int) position.getY() - (int) (dy * speed);
                if (image.getUrl().endsWith("/Images/drone.gif")) {
                    int moveToward = targetPlayer() - 90;
                    x = (int)(speed * Math.cos(moveToward * Math.PI / 180) + position.getX() + 0.5);
                    y = (int)(speed * Math.sin(moveToward * Math.PI / 180) + position.getY() + 0.5);
                }
                lastPosition = position;
                position = new Point(x, y);
                turretDirection = targetPlayer();
                break;
            case PAUSE:
                World.instance().addObject(fire());
                changeState();
                break;
        }
        if (image.getUrl().endsWith("/Images/drone.gif")) {
            direction = targetPlayer() + 180;
        } else {
            direction = targetPlayer() + 90;
        }
        if (position.getX() > 1330) {
            position.setLocation(1330.0, position.getY());
        } else if (position.getX() < 30) {
            position.setLocation(30, position.getY());
        } else if (position.getY() < -400) {
            position.setLocation(position.getX(), -400);
        } else if (position.getY() > 320) {
            position.setLocation(position.getX(), 320);
        }
    }

    @Override
    public Bullet fire() {
        savedCycle = World.instance().getCycleCount();
        turretImage = new Image("/Images/cannonfiresprites.gif");
        AUDIO_ENEMYSHOT.play(0.3);
        double newX = 45 * Math.cos(turretDirection * Math.PI / 180);
        double newY = 45 * Math.sin(turretDirection * Math.PI / 180);
        return new Bullet(new Image("/Images/projectile.png"),
                new Point((int) (position.getX() + newX + width / 2), (int) (position.getY() + newY + height / 2 - 8)), turretDirection, 19,
                11, 7, 1, BulletType.ENEMY);
    }

    @Override
    public void onDeath() {
        World.instance().removeObject(this);
        World.instance().setWaveScore(World.instance().getWaveScore() + 25);
        World.instance().setScore(World.instance().getScore() + 25);
    }

    @Override
    public String serialize() {
        String serialization = "EnemyTank,";
        String[] imageName = image.getUrl().split("/");
        Object[] list = new Object[] { imageName[imageName.length - 1], position.getX(), position.getY(), direction,
                height, width, health, speed, turretDirection, state, reloadTime, reloadStatus };
        for (int i = 0; i < list.length; i++) {
            serialization += list[i].toString();
            if (i != list.length - 1) {
                serialization += ",";
            }
        }
        return serialization;
    }

    @Override
    public void deserialize(String data) {
        String[] list = data.split(",");
        image = new Image(getClass().getResource("/Images/" + list[1]).toString());
        position = new Point((int) Double.parseDouble(list[2]), (int) Double.parseDouble(list[3]));
        direction = Integer.parseInt(list[4]);
        height = Integer.parseInt(list[5]);
        width = Integer.parseInt(list[6]);
        health = Integer.parseInt(list[7]);
        speed = Integer.parseInt(list[8]);
        turretDirection = Integer.parseInt(list[9]);
        switch (list[10]) {
            case "CHARGE":
                state = EnemyState.CHARGE;
                break;
            case "FLEE":
                state = EnemyState.FLEE;
                break;
            case "PAUSE":
                state = EnemyState.PAUSE;
                break;
        }
        reloadTime = Integer.parseInt(list[11]);
        reloadStatus = Integer.parseInt(list[12]);
    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public int getFireNum() {
        return fireNum;
    }

}