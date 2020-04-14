package model;

import java.awt.Point;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Player extends Tank {

    /**
     * Initializes a new Player using parameters.
     * 
     * @param image
     * @param position
     * @param direction
     * @param height
     * @param width
     * @param health
     * @param speed
     * @param turretDirection
     * @param pastPositions
     */
    public Player(Image image, Point position, int direction, int height, int width, int health, int speed,
            int turretDirection, ArrayList<Point> pastPositions) {
        this.image = image;
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