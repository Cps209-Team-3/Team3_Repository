package model;

public abstract class Tank extends GameObject {

    int health;
    int speed;
    int turretDirection;

    void onDeath() {
        // TODO
    }

    abstract void move();

    abstract void fire();

    @Override
    void onCollision(GameObject object) {
        if (object instanceof Tank) {
            // Push tank out of collision
        } else if (object instanceof Wall) {
            // Push itself (this) out of wall
        }
    }
}