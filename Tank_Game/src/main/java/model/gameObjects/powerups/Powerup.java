//-----------------------------------------------------------
//File:   Powerup.java
//Author: David Disler
//Desc:   This program is a abstract class to create powerups 
//----------------------------------------------------------- 
package model.gameObjects.powerups;

import java.awt.Point;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import model.enums.PowerupType;
import model.gameObjects.GameObject;
import model.gameObjects.Tank;

public abstract class Powerup extends GameObject {

    PowerupType type; // This specifies which PowerupType the powerup is
    Tank tank = null; // Used for the onCollision method to hold the collided tank
    int frameCount = 0; // Counts the times that powerupPower is called
    KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> powerupPower()); // Keyframe that runs the powerupPower
                                                                                  // method every second
    Timeline timeline = new Timeline(keyFrame); // Executes the keyframe

    /**
     * Disler, David - Abstract method that will return the player to its state
     * before it collided with a powerup after the frameCount reaches 10
     */
    public abstract void powerupPower();

    @Override
    public void onCollision(GameObject object) {
    }

    @Override
    public void deserialize(String data) {
        String[] list = data.split(",");
        image = new Image(getClass().getResource("/Images/" + list[1]).toString());
        position = new Point((int) Double.parseDouble(list[2]), (int) Double.parseDouble(list[3]));
        direction = Integer.parseInt(list[4]);
        height = Integer.parseInt(list[5]);
        width = Integer.parseInt(list[6]);
        switch (list[7]) {
            case "HEALTH":
                type = PowerupType.HEALTH;
            case "FAST_FIRE":
                type = PowerupType.FAST_FIRE;
            case "SPEEDY":
                type = PowerupType.SPEEDY;
        }
    }

    @Override
    public String serialize() {
        String serialization = "Powerup,";
        String[] imageName = image.getUrl().split("/");
        Object[] list = new Object[] { imageName[imageName.length - 1], position.getX(), position.getY(), direction,
                height, width, type };
        for (int i = 0; i < list.length; i++) {
            serialization += list[i].toString();
            if (i != list.length - 1) {
                serialization += ",";
            }
        }
        return serialization;
    }
}