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
        height = (int) (image.getHeight() + 0.5);
        width = (int) (image.getWidth() + 0.5);
        type = PowerupType.FAST_FIRE;
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Player) {
            World.instance().removeObject(this);
            tank = (Tank) object;
            tank.setReloadTime(2);
            tank.setReloadStatus(0);
            timeline.setCycleCount(20);
            timeline.play();
            frameCount = 0;
        }
    }

    @Override
    public void powerupPower() {
        frameCount++;
        tank.setReloadTime(2);
        tank.setReloadStatus(0);
        if (frameCount >= 10) {
            tank.setReloadTime(20);
            tank.setReloadStatus(20);
        }
    }
}