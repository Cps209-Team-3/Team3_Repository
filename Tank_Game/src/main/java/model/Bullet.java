package model;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;

    void move() {
        // TODO create movement based off speed
    }

    @Override
    String serialize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void deserialize(String data) {
        // TODO Auto-generated method stub
    }

    @Override
    void onCollision(GameObject object) {
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            tank.health -= 1;
            if (tank.health <= 0) {
                // Kill Tank
            }
        } else if (object instanceof Wall || object instanceof Bullet) {
            // Run explosion animation
            // Delete itself (this)
        }
    }
}