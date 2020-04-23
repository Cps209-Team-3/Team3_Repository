package model.gameObjects;

import java.awt.Point;
import java.util.Random;

import javafx.scene.image.Image;
import model.World;
import model.enums.PowerupType;


public class HealthPowerup extends Powerup {

    /**
     * Initializes a new Powerup with random parameters.
     */
    public HealthPowerup() {
        Random random = new Random();
        //Temporary holding image
        image = new Image(getClass().getResource("/Images/wall.png").toString());
        position = null;
        direction = 0;
        height = 10;
        width = 10;
        type = PowerupType.HEALTH;
    }

    @Override
    public void onCollision(GameObject object) {
        World.instance().removeObject(this);
        if (object instanceof Player) {
            Tank tank = (Tank) object;
            tank.setHealth(5);
        }
    }
}