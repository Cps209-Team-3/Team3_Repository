package model.gameObjects;

import model.enums.BulletType;
import javafx.scene.image.Image;
import java.awt.Point;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;
    BulletType type;

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
        double newY = -(speed / 2.0) * Math.sin(Math.toRadians(-(direction) + 90)) + position.getY();
        double newX = (speed / 2.0) * Math.cos(Math.toRadians(-(direction) + 90)) + position.getX();
        position = new Point((int) (newX + 0.5), (int) (newY + 0.5));
    }

    @Override
    public String serialize() {
        // TODO: serialize
        return null;
    }

    @Override
    public void deserialize(String data) {
        // TODO: deserialize
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            tank.health -= 1;
            if (tank.health <= 0) {
                tank.onDeath();
            }
        } else if (object instanceof Wall || object instanceof Bullet) {
            // Run explosion animation
            // Delete itself (this)
        }
    }
}