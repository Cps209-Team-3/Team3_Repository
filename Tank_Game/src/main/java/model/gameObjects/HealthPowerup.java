package model.gameObjects;

import javafx.scene.image.Image;
import model.World;
import model.enums.PowerupType;


public class HealthPowerup extends Powerup {

    /**
     * Initializes a new Powerup with random parameters.
     */
    public HealthPowerup() {
        //Temporary holding image
        image = new Image(getClass().getResource("/Images/healthcrate.png").toString());
        position = null;
        direction = 0;
        height = 10;
        width = 10;
        type = PowerupType.HEALTH;
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Player) {
            World.instance().removeObject(this);
            Tank tank = (Tank) object;
            tank.setHealth(tank.getHealth() + 3);
            timeline.setCycleCount(10);
            timeline.play();
        }
    }

    @Override
    public void powerupPower() {}
}