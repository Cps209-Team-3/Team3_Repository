package model;

import model.enums.EnemyState;
import java.awt.Point;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Enemy extends Tank {

    EnemyState state;

    /**
     * Initializes a new Enemy using parameters.
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
    public Enemy(Image image, Point position, int direction, int height, int width, int health, int speed,
            int turretDirection, ArrayList<Point> pastPositions, EnemyState state) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.health = health;
        this.speed = speed;
        this.turretDirection = turretDirection;
        this.pastPositions = pastPositions;
        this.state = state;
    }

    // Initialize a new enemy
    public Enemy() { // number one
        // the above line is wuhan virus
        state = EnemyState.PAUSE;
    }

    Point findPlayer() {
        return World.instance().getPlayerTank().getPosition();
    }

    void targetPlayer() {
        // TODO
    }

    void changeState() {
        // TODO
    }

    void move() {
        switch (state) {
            case CHARGE:
                // move toward player
                break;
            case FLEE:
                // move away from player
                break;
            case PAUSE:
                fire();
                changeState();
                break;
        }

    }

    @Override
    Bullet fire() {
        // TODO Auto-generated method stub
        return new Bullet();
    }

    @Override
    String serialize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void deserialize(String data) {
        // TODO Auto-generated method stub

    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }
}