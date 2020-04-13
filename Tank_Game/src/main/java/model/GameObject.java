package model;

import java.awt.Point;

import javafx.scene.image.Image;

public abstract class GameObject {

    Image image;
    Point position;
    int direction;
    int height;
    int width;

    /**
     * Serialize all basic variables of a GameObject.
     * @return - Formatted string of this object's data
     */
    String baseSerialize() {
        return image.getUrl() + "," + position.getX() + ":" + position.getY() + "," + direction + "," + height + ","
                + width; // could rework to use position.toString() function
    }

    /**
     * deserialize all basic variables of a GameObject.
     * 
     * @param data - String to extract values from.
     */
    void baseDeserialize(String data) {
        String[] separatedData = data.split(",");
        this.image = new Image(separatedData[0]);
        this.position = new Point(Integer.parseInt(separatedData[1].split(":")[0]),
                Integer.parseInt(separatedData[1].split(":")[1]));
        this.direction = Integer.parseInt(separatedData[2]);
        this.height = Integer.parseInt(separatedData[3]);
        this.width = Integer.parseInt(separatedData[4]);
    }

    /**
     * Takes all the variables from a GameObject and puts it into a comma-delimited
     * string
     * 
     * @return data of object in string format
     */
    abstract String serialize();

    /**
     * Sets all the variables in a GameObject from a string
     * 
     * @param data - The string to be split
     */
    abstract void deserialize(String data);

    /**
     * Handles a collison with another object.
     * 
     * @param object - The object collided with
     */
    abstract void onCollision(GameObject object);

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
