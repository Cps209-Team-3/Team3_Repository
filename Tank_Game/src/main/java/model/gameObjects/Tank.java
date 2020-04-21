package model.gameObjects;

import java.awt.Point;

import model.World;

public abstract class Tank extends GameObject { 
    int health;
    int speed;
    int turretDirection; // 0-360
    int reloadTime;
    int reloadStatus;

    Point lastPosition; // track previous position for collision handling

    public void onDeath() {
        World.instance().removeObject(this);
    }

    // Creates a new bullet travelling in the direction of turretDirection
    public abstract Bullet fire();

    /**
     * Moves tank back to last position to stop collision.
     * 
     * @param object - the object collided with
     */
    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Tank) {
            this.position = lastPosition;
            // TODO: Use current position to calculate mid-point, so both tanks don't jump
            // back too far.
        } else if (object instanceof Wall) {
            this.position = lastPosition;
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
}