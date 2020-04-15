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
        image = new Image("@Images/greentankv1wider.gif");
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
        image = new Image("@Images/greentankv1wider.gif");
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
    String serialize() {
        // TODO Auto-generated method stub
        String base = baseSerialize();
        return base + "";
    }

    @Override
    void deserialize(String data) {
        // TODO Auto-generated method stub
        baseDeserialize(data);

    }

}