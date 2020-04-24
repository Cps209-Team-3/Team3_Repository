package model.gameObjects;

import model.World;
import model.enums.BulletType;
import javafx.scene.image.Image;
import java.awt.Point;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;
    BulletType type;
    int numMoves = 0;

    /**
     * Initializes a new Bullet using parameters.
     *
     * @param image
     * @param position
     * @param direction
     * @param height
     * @param width
     * @param speed
     * @param damageAmount
     * @param type
     */
    public Bullet(Image image, Point position, int direction, int width, int height, int speed, int damageAmount,
            BulletType type) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.damageAmount = damageAmount;
        this.type = type;
    }

    // Initializes a new bullet.
    public Bullet() {

    }

    // moves the bullet in the direction of 'direction', by the amount of 'speed'.
    public void move() {
        numMoves += 1;
        double newX = speed * Math.cos(direction * Math.PI / 180);
        double newY = speed * Math.sin(direction * Math.PI / 180);
        position = new Point((int) (newX + position.getX()), (int) (newY + position.getY()));
        if (numMoves > 360) {
            World.instance().removeObject(this);
        }
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Tank) {
            if ((this.type == BulletType.PLAYER && !(object instanceof Player))
                    || (this.type == BulletType.ENEMY && !(object instanceof Enemy))) {
                Tank tank = (Tank) object;
                tank.setHealth(tank.getHealth() - damageAmount);
                if (tank.getHealth() <= 0) {
                    tank.onDeath();
                }
                World.instance().removeObject(this);
            }
        } else if (object instanceof Wall || object instanceof Bullet) {
            // Run explosion animation
            World.instance().removeObject(this);
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }

    public BulletType getType() {
        return type;
    }

    public void setType(BulletType type) {
        this.type = type;
    }

    @Override
    public String serialize() {
        String serialization = "Bullet,";
        Object[] list = new Object[] { image.getUrl().split("/")[17], position.getX(), position.getY(), direction,
                height, width, speed, speed, damageAmount, type };
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
        speed = Integer.parseInt(list[7]);
        damageAmount = Integer.parseInt(list[8]);
        switch (list[9]) {
            case "PLAYER":
                type = BulletType.PLAYER;
                break;
            case "ENEMY":
                type = BulletType.ENEMY;
                break;
        }
    }
}