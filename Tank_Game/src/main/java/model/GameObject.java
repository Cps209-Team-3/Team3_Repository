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
    abstract String serialize();

    /**
     * Sets all the variables in a GameObject from a string
     * @param data - A string that will be split
     */
    abstract void deserialize(String data);

    abstract void onCollision();

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
