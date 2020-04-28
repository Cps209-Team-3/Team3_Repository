package model.gameObjects.powerups;

import javafx.scene.image.Image;
import model.World;
import model.enums.PowerupType;
import model.gameObjects.*;


public class FastFirePowerup extends Powerup {

    /**
     * Initializes a new Powerup with random parameters.
     */
    public FastFirePowerup() {
        //Temporary holding image
        image = new Image(getClass().getResource("/Images/powercrate.png").toString());
        position = null;
        direction = 0;
        height = 10;
        width = 10;
        type = PowerupType.FAST_FIRE;
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Player) {
            World.instance().removeObject(this);
            tank = (Tank) object;
            tank.setReloadTime(2);
            timeline.setCycleCount(10);
            timeline.play();
        }
    }

    @Override
    public void powerupPower() {
        frameCount++;
        if (frameCount >= 10) {
            tank.setReloadTime(5);
        }
    }
}