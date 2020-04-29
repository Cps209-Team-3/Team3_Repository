package model.gameObjects.powerups;

import javafx.scene.image.Image;
import model.World;
import model.enums.PowerupType;
import model.gameObjects.*;


public class SpeedyPowerup extends Powerup {

    /**
     * Initializes a new Powerup with random parameters.
     */
    public SpeedyPowerup() {
        //Temporary holding image
        image = new Image(getClass().getResource("/Images/speedcrate.png").toString());
        position = null;
        direction = 0;
        height = (int) (image.getHeight() + 0.5);
        width = (int) (image.getWidth() + 0.5);
        type = PowerupType.SPEEDY;
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Player) {
            World.instance().removeObject(this);
            tank = (Tank) object;
            tank.setSpeed(8);
            timeline.setCycleCount(20);
            timeline.play();
            frameCount = 0;
        }
    }

    @Override
    public void powerupPower() {
        frameCount++;
        tank.setSpeed(8);
        if (frameCount >= 10) {
            tank.setSpeed(5);
        }
    }
}