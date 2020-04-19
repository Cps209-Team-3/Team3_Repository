package model.gameObjects;

import java.awt.Point;
import javafx.scene.image.Image;
import model.enums.BulletType;

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
     * @param reloadTime
     * @param reloadStatus
     * @param pastPositions
     */
    public Player(Point position, int direction, int height, int width, int health, int speed, int turretDirection,
            int reloadTime, int reloadStatus, Point lastPosition) {
        image = new Image(getClass().getResource("/Images/greentankv1wider.gif").toString());
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.reloadTime = reloadTime;
        this.reloadStatus = reloadStatus;
        this.lastPosition = lastPosition;
    }
    // Initializes a new Player.
    public Player() {
        image = new Image("/Images/greentankv1wider.gif");
    }

    // Moves tank in the direction of 'input' and saves last position to
    // pastPositions
    public void move(char input) {
        switch (input) {
            case 'W':
                double newY = -speed * Math.sin(Math.toRadians(-(direction) + 90)) + position.getY();
                double newX = speed * Math.cos(Math.toRadians(-(direction) + 90)) + position.getX();
                lastPosition = position;
                position = new Point((int) (newX + 0.5), (int) (newY + 0.5));
                break;
            case 'A':
                direction += 2;
                break;
            case 'S':
                double newY2 = speed * Math.sin(Math.toRadians(-(direction) + 90)) + position.getY();
                double newX2 = -speed * Math.cos(Math.toRadians(-(direction) + 90)) + position.getX();
                lastPosition = position;
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
        String serialization = "PlayerTank,";
        Object[] list = new Object[] { image.getUrl().split("/")[17], position.getX(), position.getY(), direction,
                height, width, health, speed, turretDirection };
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
        reloadTime = Integer.parseInt(list[10]);
        reloadStatus = Integer.parseInt(list[11]);
    }

}