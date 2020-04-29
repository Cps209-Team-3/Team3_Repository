package model.gameObjects.powerups;

import javafx.scene.image.Image;
import model.World;
import model.enums.PowerupType;
import model.gameObjects.*;


public class HealthPowerup extends Powerup {

    boolean used = false;

    /**
     * Initializes a new Powerup with random parameters.
     */
    public HealthPowerup() {
        //Temporary holding image
        image = new Image(getClass().getResource("/Images/healthcrate.png").toString());
        position = null;
        direction = 0;
        height = (int) (image.getHeight() + 0.5);
        width = (int) (image.getWidth() + 0.5);
        type = PowerupType.HEALTH;
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Player && !used) {
            used = true;
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