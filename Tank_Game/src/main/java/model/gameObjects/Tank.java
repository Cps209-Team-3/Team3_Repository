package model.gameObjects;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Tank extends GameObject { 
    // TODO: WILL HEIGHT AND WIDTH BE THE SAME FOR ALL TANKS?
    int health;
    int speed;
    int turretDirection; // 0-360
    int reloadTime;
    int reloadStatus;

    ArrayList<Point> pastPositions = new ArrayList<>(); // track previous position for collision handling

    public void onDeath() {
        // TODO: on player death
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
            this.position = pastPositions.get(pastPositions.size() - 1);
            pastPositions.remove(pastPositions.size() - 1);
            // TODO: Use current position to calculate mid-point, so both tanks don't jump
            // back too far.
        } else if (object instanceof Wall) {
            this.position = pastPositions.get(pastPositions.size() - 1);
            pastPositions.remove(pastPositions.size() - 1);
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

    public ArrayList<Point> getPastPositions() {
        return pastPositions;
    }

    public void setPastPositions(ArrayList<Point> pastPositions) {
        this.pastPositions = pastPositions;
    }
}