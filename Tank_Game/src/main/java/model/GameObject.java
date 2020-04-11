package model;

import java.awt.Point;

import javafx.scene.image.Image; // TODO

public abstract class GameObject {

    Image image;
    Point position;
    int direction;
    int height;
    int width;

    /**
     * Takes all the variables from a GameObject
     * and puts it into a comma-delimited string
     */
    abstract String serialization();

    /**
     * Sets all the variables in a GameObject from a string
     * @param data - A string that will be split
     */
    abstract void deserialization(String data);

    abstract void onCollision();
}
