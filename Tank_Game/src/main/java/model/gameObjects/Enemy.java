package model.gameObjects;

import model.World;
import model.enums.BulletType;
import model.enums.EnemyState;

import java.awt.Point;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Tank {

    EnemyState state;

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
    public Enemy(Point position, int direction, int height, int width, int health, int speed, int turretDirection,
            int reloadTime, int reloadStatus, ArrayList<Point> pastPositions, EnemyState state) {
        image = new Image(getClass().getResource("/Images/bluetankv1wider.gif").toString());
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.reloadTime = reloadTime;
        this.reloadStatus = reloadStatus;
        this.pastPositions = pastPositions;
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
            image = new Image(getClass().getResource("/Images/bluetankv1wider.gif").toString());
            position = new Point(random.nextInt(1400), random.nextInt(900));
            direction = random.nextInt(360);
            height = 50; // TODO: adjust size
            width = 20;
            health = 1;
            speed = random.nextInt(10) + 1;
            turretDirection = direction;
            state = EnemyState.PAUSE;
        }
    }

    // Initialize a new enemy
    public Enemy() { // number one
        // wuhan virus is the above line when read as a sentence
        image = new Image(getClass().getResource("/Images/bluetankv1wider.gif").toString());
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
        direction = (int) Math.toDegrees(Math.atan2(y2, x2));
        return direction;
    }

    // Changes state randomly to a different state than the current one.
    public void changeState() {
        Random random = new Random();
        int num = random.nextInt(2);
        switch (state) {
            case CHARGE:
                state = num == 0 ? EnemyState.FLEE : EnemyState.PAUSE;
                break;
            case FLEE:
                state = num == 0 ? EnemyState.CHARGE : EnemyState.PAUSE;
                break;
            case PAUSE:
                state = num == 0 ? EnemyState.CHARGE : EnemyState.FLEE;
                break;
        }
    }

    public void move() {
        Point playerPosition = findPlayer();;
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
                position = new Point(x, y);
                turretDirection = targetPlayer();
                break;
            case FLEE:
                // move away from player
                x = (int) position.getX() - (int) (dx * speed);
                y = (int) position.getY() - (int) (dy * speed);
                position = new Point(x, y);
                turretDirection = targetPlayer();
                break;
            case PAUSE:
                World.instance().addObject(fire());
                changeState();
                break;
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(new Image("/Images/projectile.png"),
                new Point((int) position.getX() + width / 2, (int) position.getY() + height / 2), turretDirection, 10,
                10, 5, 1, BulletType.ENEMY);
    }

    @Override
    public String serialize() {
        String serialization = "EnemyPlayer,";
        Object[] list = new Object[] { image.getUrl().split("/")[17], position.getX(), position.getY(), direction,
                height, width, health, speed, turretDirection, state };
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
        position = new Point(Integer.parseInt(list[2]), Integer.parseInt(list[3]));
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
}