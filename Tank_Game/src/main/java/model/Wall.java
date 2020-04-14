package model;

import javafx.scene.image.Image;
import java.awt.Point;
import java.util.ArrayList;

public class Wall extends GameObject {

    /**
     * Initializes a new Wall using parameters.
     * 
     * @param image
     * @param position
     * @param direction
     * @param height
     * @param width
     */
    public Wall(Image image, Point position, int direction, int height, int width) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
    }

    // Initializes a new wall.
    public Wall() {
    }

    String serialize() {
        return baseSerialize();
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