package model;

import model.enums.EnemyState;
import java.awt.Point;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Tank {

    EnemyState state;

    /**
     * Initializes a new Enemy using parameters.
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
    public Enemy(Point position, int direction, int height, int width, int health, int speed,
            int turretDirection, ArrayList<Point> pastPositions, EnemyState state) {
        image = new Image("@Images/bluetankv1wider.gif");
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

    /**
     * Initializes a new enemy using random variables
     * 
     * @param useRandom - used as a way to specify weather to use random values or null values, as with Enemy().
     */
    public Enemy(boolean useRandom) {
        if (useRandom) {
            Random random = new Random();
            image = new Image("@Images/bluetankv1wider.gif");
            position = new Point(random.nextInt(1400), random.nextInt(900));
            direction = random.nextInt(360);
            height = 50; // TODO: adjust size
            width = 20;
            health = 1;
            speed = random.nextInt(10);
            turretDirection = direction;
            state = EnemyState.PAUSE;
        }
    }

    // Initialize a new enemy
    public Enemy() { // number one
        // wuhan virus is the above line when read as a sentence
        image = new Image("@Images/bluetankv1wider.gif");
        state = EnemyState.PAUSE;
    }

    // returns the center position of the players tank.
    Point findPlayer() {
        Player player = World.instance().getPlayerTank();
        return new Point((int)player.getPosition().getX() + player.getWidth()/2, (int)player.getPosition().getY() + player.getHeight()/2);
    }

    void targetPlayer() {
        Point playerPosition = findPlayer();

    }

    // Changes state randomly to a different state than the current one.
    void changeState() {
        Random random = new Random();
        int num = random.nextInt(2);
        switch (state) {
            case CHARGE:
                state = num == 0 ? EnemyState.FLEE : EnemyState.PAUSE;
                break;
            case FLEE:
                state = num == 0 ? EnemyState.CHARGE : EnemyState.PAUSE;
                break;
            case PAUSE:
            state = num == 0 ? EnemyState.CHARGE : EnemyState.FLEE;
                break;
        }
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