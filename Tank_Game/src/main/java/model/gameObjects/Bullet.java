//-----------------------------------------------------------
//File:   Bullet.java
//Author: David Disler, Austin Pennington, Andrew James
//Desc:   This file contains the properities of a bullet,
//        the sound effects for the bullet, and the explosion animation.
//----------------------------------------------------------- 
package model.gameObjects;
import model.World;
import model.enums.BulletType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import java.awt.Point;

public class Bullet extends GameObject {

    int speed; //How Quickly the Bullet will Travel
    int damageAmount; //Amount of damage the bullet will do
    BulletType type; //Who the bullet was shot by
    int numMoves = 0; //Number of times the bullet has moved since it has been fired
    boolean exploding = false; //Value is True if bullet collided with an object; Otherwise, false.

    /**
     * Initializes a new Bullet using parameters.
     *
     * @param image :
     * Image of the bullet
     * @param position :
     * Desired X and Y Position of Bullet
     * @param direction :
     * Direction the bullet will face in degrees
     * @param height :
     * Height of the Bullet Image
     * @param width :
     * Width of the Bullet Image
     * @param speed :
     * How quickly the bullet will travel 
     * @param damageAmount :
     * Amount of damage the bullet will do to a tank
     * @param type :
     * BulletType enum that indicates who the bullet was shot by
     */
    public Bullet(Image image, Point position, int direction, int width, int height, int speed, int damageAmount,
            BulletType type) {
        this.image = image;
        this.position = position;
        this.direction = direction;
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.damageAmount = damageAmount;
        this.type = type;
    }

    // Initializes a new bullet.
    public Bullet() {

    }

    // moves the bullet at the angle in degrees 'direction', by the amount of 'speed', and deletes the bullet if out of bounds or collided with something.
    public void move() {
        if (!exploding) {
            numMoves += 1;
            double newX = speed * Math.cos(direction * Math.PI / 180);
            double newY = speed * Math.sin(direction * Math.PI / 180);
            position = new Point((int) (newX + position.getX()), (int) (newY + position.getY()));
            if (numMoves > 360) {
                explode();
            }
        } else {
            if (numMoves > 29) {
                explode();
            } else {
                numMoves += 1;
            }
        }
    }

    /**
     * Determines what GameObject the bullet hit, deals damage to tank (if it is one), plays hit sound, and plays animation.
     * 
     * @param object :
     * GameObject that the bullet collided with
     */
    @Override
    public void onCollision(GameObject object) {
        final AudioClip AUDIO_HIT = new AudioClip(getClass().getResource("/Media/hit.wav").toString()); //Sound for Bullet Hitting Tank
        final AudioClip AUDIO_BOOM = new AudioClip(getClass().getResource("/Media/explosion.wav").toString()); //Sound for Enemy Tank Death
        AUDIO_BOOM.setPriority(3); 
        AUDIO_HIT.setPriority(3);
        if (!exploding) {
            if (object instanceof Tank) {
                if ((this.type == BulletType.PLAYER && !(object instanceof Player))
                        || (this.type == BulletType.ENEMY && !(object instanceof Enemy))) {
                    exploding = true;
                    image = new Image("/Images/explosion.gif");
                    Tank tank = (Tank) object;
                    tank.setHealth(tank.getHealth() - damageAmount);
                    if (tank.getHealth() <= 0) {
                        AUDIO_BOOM.play(0.25);
                        tank.onDeath();
                    } else {
                        AUDIO_HIT.play(0.4);
                    }
                    numMoves = 0;
                }
            } else if (object instanceof Bullet) {
                // Run explosion animation
                Bullet bullet = (Bullet) object;
                if (!bullet.isExploding() || bullet.getNumMoves() < 2) {
                    exploding = true;
                    image = new Image("/Images/explosion.gif");
                    numMoves = 0;
                }
            } else if (object instanceof Wall) {
                exploding = true;
                image = new Image("/Images/explosion.gif");
                numMoves = 0;
            }
        }
    }

    //Removes bullet from the World's object list.
    public void explode() {
        World.instance().removeObject(this);
    }

    @Override
    public String serialize() {
        String serialization = "Bullet,";
        String[] imageName = image.getUrl().split("/");
        Object[] list = new Object[] { imageName[imageName.length - 1], position.getX(), position.getY(), direction,
                height, width, speed, speed, damageAmount, type };
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
        position = new Point((int) Double.parseDouble(list[2]), (int) Double.parseDouble(list[3]));
        direction = Integer.parseInt(list[4]);
        height = Integer.parseInt(list[5]);
        width = Integer.parseInt(list[6]);
        speed = Integer.parseInt(list[7]);
        damageAmount = Integer.parseInt(list[8]);
        switch (list[9]) {
            case "PLAYER":
                type = BulletType.PLAYER;
                break;
            case "ENEMY":
                type = BulletType.ENEMY;
                break;
        }
    }

    //Getters and Setters

    public boolean isExploding() {
        return exploding;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }

    public BulletType getType() {
        return type;
    }

    public void setType(BulletType type) {
        this.type = type;
    }
}