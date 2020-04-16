package model;

import java.awt.Point;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Player extends Tank {

    /**
     * Initializes a new Player using parameters.
     * 
     * @param position
     * @param direction
     * @param height
     * @param width
     * @param health
     * @param speed
     * @param turretDirection
     * @param pastPositions
     */
    public Player(Point position, int direction, int height, int width, int health, int speed,
            int turretDirection, ArrayList<Point> pastPositions) {
        image = new Image(getClass().getResource("/Images/greentankv1wider.gif").toString());
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.pastPositions = pastPositions;
    }

    // Initializes a new Player.
    public Player() {
        image = new Image(getClass().getResource("/Images/greentankv1wider.gif").toString());
    }

    // Moves tank in the direction of 'input' and saves last position to pastPositions
    void move(char input) {
        // TODO Auto-generated method stub

    }

    @Override
    Bullet fire() {
        // TODO Auto-generated method stub
        return new Bullet();
    }

    @Override
    public String serialize() {
        String serialization = "PlayerTank,";
        Object[] list = new Object[] {image.getUrl().split("/")[17], position.getX(), position.getY(), direction, height, width, health, speed, turretDirection};
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
        position = new Point(Integer.parseInt(list[2]), Integer.parseInt(list[3]));
        direction = Integer.parseInt(list[4]);
        height = Integer.parseInt(list[5]);
        width = Integer.parseInt(list[6]);
        health = Integer.parseInt(list[7]);
        speed = Integer.parseInt(list[8]);
        turretDirection = Integer.parseInt(list[9]);
    }

}