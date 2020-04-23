package model.gameObjects;

import java.awt.Point;
import javafx.scene.image.Image;
import model.enums.PowerupType;

public class Powerup extends GameObject {

    PowerupType type;


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
        String serialization = "HealthPowerup,";
        Object[] list = new Object[] {image.getUrl().split("/")[17], position.getX(), position.getY(), direction, height, width, type};
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