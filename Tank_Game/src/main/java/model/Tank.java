package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Tank extends GameObject {

    int health;
    int speed;
    int turretDirection; // 0-360
    ArrayList<Point> pastPositions = new ArrayList<>(); // track previous position for collision handling

    /**
     * Initializes a new tank using parameters.
     * 
     * @param health
     * @param speed
     * @param turretDirection
     * @param pastPositions
     */
    public Tank(int health, int speed, int turretDirection, ArrayList<Point> pastPositions) {
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.pastPositions = pastPositions;
    }

    // Initializes a new tank.
    public Tank() {
    }

    void onDeath() {
        // TODO
    }

    // Moves the tank into a new position, and saves it's last position into pastPositions
    abstract void move();

    // Creates a new bullet travelling in the direction of turretDirection
    abstract void fire();

    /** 
     * Moves tank back to last position to stop collision.
     * 
     * @param object - the object collided with
    */
    @Override
    void onCollision(GameObject object) {
        if (object instanceof Tank) {
            this.position = pastPositions.get(pastPositions.size() - 1);
            pastPositions.remove(pastPositions.size() - 1);
            // TODO: Use current position to calculate mid-point, so both tanks don't jump back too far.
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