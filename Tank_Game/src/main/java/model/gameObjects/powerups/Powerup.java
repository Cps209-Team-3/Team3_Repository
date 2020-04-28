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

    PowerupType type;
    Tank tank = null;
    int frameCount = 0;
    KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> powerupPower());
    Timeline timeline = new Timeline(keyFrame);



    public abstract void powerupPower();

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
        Object[] list = new Object[] {imageName[imageName.length - 1], position.getX(), position.getY(), direction, height, width, type};
        for (int i = 0; i < list.length; i++) {
            serialization += list[i].toString();
            if (i != list.length - 1) {
                serialization += ",";
            } 
        }
        return serialization;
    }

    @Override
    public void onCollision(GameObject object) {}
}