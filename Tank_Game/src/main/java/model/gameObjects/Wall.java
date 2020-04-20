package model.gameObjects;

import javafx.scene.image.Image;
import java.awt.Point;

public class Wall extends GameObject {

    /**
     * Initializes a new Wall using parameters.
     * 
     * @param position
     * @param direction
     * @param height
     * @param width
     */
    public Wall(Point position, int direction, int height, int width) {
        image = new Image(getClass().getResource("/Images/wall.png").toString());
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
    }

    // Initializes a new wall.
    public Wall() {
    }

    public String serialize() {
        String serialization = "Wall,";
        Object[] list = new Object[] {image.getUrl().split("/")[17], position.getX(), position.getY(), direction, height, width};
        for (int i = 0; i < list.length; i++) {
            serialization += list[i].toString();
            if (i != list.length - 1) {
                serialization += ",";
            } 
        }
        return serialization;
    }

    @Override
    public void deserialize(String data) {
        String[] list = data.split(",");
        image = new Image(getClass().getResource("/Images/" + list[1]).toString());
        position = new Point((int)Double.parseDouble(list[2]), (int)Double.parseDouble(list[3]));
        direction = Integer.parseInt(list[4]);
        height = Integer.parseInt(list[5]);
        width = Integer.parseInt(list[6]);
    }

    @Override
    public void onCollision(GameObject object) {
        if (object instanceof Wall) {
            // oops, Wall collided with Wall
        }
    }
}