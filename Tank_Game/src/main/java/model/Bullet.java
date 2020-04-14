package model;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;

    void move() {
        // CODE BELOW IS FROM ONE OF ANDREW'S OLD PYTHON SCRIPTS AND NEEDS BE TRANSLATED
        // the math checks out though, so it works
        // newy = -(float(speed)/2)*math.sin(math.radians(float(-(heading) + 90)))+ycoord
        // newx = (float(speed)/2)*math.cos(math.radians(float(-(heading) + 90)))+xcoord
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
                tank.onDeath();
            }
        } else if (object instanceof Wall || object instanceof Bullet) {
            // Run explosion animation
            // Delete itself (this)
        }
    }
}