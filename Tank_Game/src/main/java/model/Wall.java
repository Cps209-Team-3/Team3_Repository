package model;

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
        image = new Image("@Images/wall.png");
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
    }

    // Initializes a new wall.
    public Wall() {
    }

    String serialize() {
        return "Wall," + baseSerialize();
    }

    @Override
    void deserialize(String data) {
        baseDeserialize(data);
    }

    @Override
    void onCollision(GameObject object) {
        if (object instanceof Wall) {
            // oops, Wall collided with Wall
        }
    }
}