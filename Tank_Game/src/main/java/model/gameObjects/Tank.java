package model.gameObjects;

import java.awt.Point;

import javafx.scene.image.Image;

public abstract class Tank extends GameObject {
    int health;
    int speed;
    int turretDirection; // 0-360
    int reloadTime;
    int reloadStatus;
    Image turretImage = new Image("/Images/cannonbase.png");
    int savedCycle;

    Point lastPosition; // track previous position for collision handling

    public abstract void onDeath();

    // Creates a new bullet travelling in the direction of turretDirection
    public abstract Bullet fire();

    /**
     * Moves tank back to last position to stop collision.
     * 
     * @param object - the object collided with
     */
    @Override
    public void onCollision(GameObject object) {
        Point newPosition = object.getPosition();
        double dx = newPosition.getX() - position.getX();
        double dy = newPosition.getY() - position.getY();
        double dist = Math.hypot(dx, dy);
        dx = dx / dist;
        dy = dy / dist;
        if (object instanceof Tank) {
            int x;
            int y;
            if (object instanceof Player) {
                x = (int) position.getX() - (int) (dx * 7 + 0.5);
                y = (int) position.getY() - (int) (dy * 7 + 0.5);
            } else {
                x = (int) position.getX() - (int) (dx * 5 + 0.5);
                y = (int) position.getY() - (int) (dy * 5 + 0.5);
            }
            lastPosition = position;
            position = new Point(x, y);
        } else if (object instanceof Wall) {
            int x = (int) position.getX() - (int) (dx * 7 + 0.5);
            int y = (int) position.getY() - (int) (dy * 7 + 0.5);
            lastPosition = position;
            position = new Point(x, y);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTurretDirection() {
        return turretDirection;
    }

    public void setTurretDirection(int turretDirection) {
        this.turretDirection = turretDirection;
    }

    public Point getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Point lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getReloadStatus() {
        return reloadStatus;
    }

    public void setReloadStatus(int reloadStatus) {
        this.reloadStatus = reloadStatus;
    }

    public Image getTurretImage() {
        return turretImage;
    }
}