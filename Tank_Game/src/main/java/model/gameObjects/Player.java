package model.gameObjects;

import java.awt.Point;
import javafx.scene.image.Image;
import model.enums.BulletType;

import java.util.ArrayList;
import javafx.scene.image.Image;

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
     * @param reloadTime
     * @param reloadStatus
     * @param pastPositions
     */
    public Player(Point position, int direction, int height, int width, int health, int speed, int turretDirection,
            int reloadTime, int reloadStatus, ArrayList<Point> pastPositions) {
        image = new Image("@Images/greentankv1wider.gif");
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.reloadTime = reloadTime;
        this.reloadStatus = reloadStatus;
        this.pastPositions = pastPositions;
    }

    // Initializes a new Player.
    public Player() {
        image = new Image("@Images/greentankv1wider.gif");
    }

    // Moves tank in the direction of 'input' and saves last position to
    // pastPositions
    public void move(char input) {
        switch (input) {
            case 'W':
                double newY = -(speed / 2.0) * Math.sin(Math.toRadians(-(direction) + 90)) + position.getY();
                double newX = (speed / 2.0) * Math.cos(Math.toRadians(-(direction) + 90)) + position.getX();
                position = new Point((int) (newX + 0.5), (int) (newY + 0.5));
                break;
            case 'A':
                direction -= 2;
                break;
            case 'S':
                double newY2 = (speed / 2.0) * Math.sin(Math.toRadians(-(direction) + 90)) + position.getY();
                double newX2 = -(speed / 2.0) * Math.cos(Math.toRadians(-(direction) + 90)) + position.getX();
                position = new Point((int) (newX2 + 0.5), (int) (newY2 + 0.5));
                break;
            case 'D':
                direction -= 2;
                break;
        }

    }

    @Override
    public Bullet fire() {
        return new Bullet(new Image("@Images/projectile.png"),
                new Point((int) position.getX() + width / 2, (int) position.getY() + height / 2), turretDirection, 10,
                10, 5, 1, BulletType.PLAYER);
    }

    @Override
    public String serialize() {
        // TODO: serialize
        String base = baseSerialize();
        return base + "";
    }

    @Override
    public void deserialize(String data) {
        // TODO: deserialize
        baseDeserialize(data);
    }

}