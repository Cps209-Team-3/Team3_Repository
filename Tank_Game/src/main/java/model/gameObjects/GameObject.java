//-----------------------------------------------------------
//File:   GameObject.java
//Author: Austin Pennington, Andrew James, David Disler
//Desc:   This class that all objects in the game inherit from
//----------------------------------------------------------- 

package model.gameObjects;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 * Initalizes properities of all game objects
 * 
 * @param image :
 * Image of the GameObject
 * @param position :
 * X and Y Position of the game object
 * @param direction :
 * Direction the gameobject is facing in degrees
 * @param height :
 * Height of the GameObject's Image
 * @param width :
 * Width of the GameObject's Image
 */
public abstract class GameObject {

    protected Image image;
    protected Point position;
    protected int direction;
    protected int height;
    protected int width;

    /**
     * Serialize all basic variables of a GameObject.
     * 
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
        this.image = new Image(separatedData[1]); // NOTE: IF FILENAME CONTAINS THE DELIMETER (comma in this case) IT
                                                  // WILL BREAK
        this.position = new Point(Integer.parseInt(separatedData[2].split(":")[0]),
                Integer.parseInt(separatedData[2].split(":")[1]));
        this.direction = Integer.parseInt(separatedData[3]);
        this.height = Integer.parseInt(separatedData[4]);
        this.width = Integer.parseInt(separatedData[5]);
    }

    /**
     * Disler, David - Takes all the variables from a GameObject and puts it into a comma-delimited
     * string
     * 
     * @return data of object in string format
     */
    public abstract String serialize();

    /**
     * Disler, David - Sets all the variables in a GameObject from a string
     * 
     * @param data - The string to be split
     */
    public abstract void deserialize(String data);

    /**
     * Disler, David - Handles a collison with another object.
     * 
     * @param object - The object collided with
     */
    public abstract void onCollision(GameObject object);


    //Getters and Setters
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
