//-----------------------------------------------------------
//File:   Player.java
//Author: Austin Pennington, Andrew James, David Disler
//Desc:   This file handles holds properities of the player tank, handles the player's input, 
//        creates bullets based off the player tank's properities, and moves the player tank. 
//----------------------------------------------------------- 
package model.gameObjects;

import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import model.World;
import model.enums.BulletType;

public class Player extends Tank {
    /**
     * Initializes a new Player using parameters.
     * 
     * @param position :
     * X and Y Position of the Tank on the Screen
     * @param direction :
     * Direction the player is facing in degrees
     * @param height :
     * Height of the Player tank's image
     * @param width :
     * Width of the Player tank's image
     * @param health :
     * Amount of times the Player tank can be hit before dying
     * @param speed :
     * How quickly the player will move 
     * @param turretDirection :
     * Direction the cannon should face in degrees
     * @param reloadTime :
     * How many milliseconds the player must wait before being able to fire again
     * @param reloadStatus :
     * How many seconds the player has already waited. Initially should be the 
     * @param lastPosition :
     * X and Y Position of the Tank in the last frame. [UNUSED]
     */
    public Player(Point position, int direction, int height, int width, int health, int speed, int turretDirection,
            int reloadTime, int reloadStatus, Point lastPosition) {
        image = new Image(getClass().getResource("/Images/greentankv1wider.gif").toString());
        this.position = position;
        this.direction = direction;
        this.height = (int) (image.getHeight() + 0.5);
        this.width = (int) (image.getWidth() + 0.5);
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

    // Sets direction based off of paramter keys and calls calculateMove()
    public void move(ArrayList<KeyEvent> keys) {
        final AudioClip AUDIO_RELOAD = new AudioClip(getClass().getResource("/Media/reload.wav").toString());
        AUDIO_RELOAD.setPriority(4);

        if (World.instance().getCycleCount() == (savedCycle + 18)%180) {
            turretImage = new Image("/Images/cannonbase.png");
        }
        String input = "";
        for (int i = 0; i < keys.size(); i++) {
            input += keys.get(i).getCode().toString();
        }
        if (keys.size() < 3 && input.contains("W") && input.contains("A")) {
            direction = 315;
        } else if (keys.size() < 3 && input.contains("W") && input.contains("D")) {
            direction = 45;
        } else if (keys.size() < 3 && input.contains("S") && input.contains("A")) {
            direction = 225;
        } else if (keys.size() < 3 && input.contains("S") && input.contains("D")) {
            direction = 135;
        } else if (keys.size() < 2 && input.equals("W")) {
            direction = 0;
        } else if (keys.size() < 2 && input.equals("S")) {
            direction = 180;
        } else if (keys.size() < 2 && input.equals("A")) {
            direction = 270;
        } else if (keys.size() < 2 && input.equals("D")) {
            direction = 90;
        }
        if (!input.isBlank()) {
            calculateMove();
        }
        if(reloadStatus != reloadTime){
            if(reloadStatus == 10){
                AUDIO_RELOAD.play(0.2);
            }
            reloadStatus++; 
        }
    }

    // Move at this' speed in direction (angle)
    public void calculateMove() {
        double newX = position.getX();
        double newY = position.getY();
        newX = speed * Math.sin(direction * Math.PI / 180);
        newY = -speed * Math.cos(direction * Math.PI / 180);
        lastPosition = position;
        position = new Point((int) (newX + position.getX()), (int) (newY + position.getY()));

        if (position.getX() > 1330) {
            position.setLocation(1330.0, position.getY());
            if (position.getY() < -400) {
                position.setLocation(position.getX(), -400);
            } else if (position.getY() > 320) {
                position.setLocation(position.getX(), 320);
            }
        } else if (position.getX() < 30) {
            position.setLocation(30, position.getY());
            if (position.getY() < -400) {
                position.setLocation(position.getX(), -400);
            } else if (position.getY() > 320) {
                position.setLocation(position.getX(), 320);
            }
        } else if (position.getY() < -400) {
            position.setLocation(position.getX(), -400);
        } else if (position.getY() > 320) {
            position.setLocation(position.getX(), 320);
        }
    }

    //Resets the Player's reload cooldown, plays the player's gunshot sound, and returns a new bullet. 
    @Override
    public Bullet fire() {
        final AudioClip AUDIO_SHOT = new AudioClip(getClass().getResource("/Media/shot1.wav").toString()); //Player Cannon Shot Sound
        savedCycle = World.instance().getCycleCount();
        turretImage = new Image("/Images/cannonfiresprites.gif");
        reloadStatus = 0; 
        AUDIO_SHOT.setPriority(5);
        AUDIO_SHOT.play(0.2);
        double newX = 45 * Math.cos(turretDirection * Math.PI / 180);
        double newY = 45 * Math.sin(turretDirection * Math.PI / 180);
        return new Bullet(new Image("/Images/projectile.png"),
                new Point((int) (position.getX() + newX + width / 2), (int) (position.getY() + newY + height / 2 - 8)), turretDirection, 19,
                11, 13, 1, BulletType.PLAYER);
    }

    //Removes the player's tank from the game
    @Override
    public void onDeath() {
        if (!World.instance().isCheatMode()) {
            World.instance().removeObject(this);
        }
    }

    @Override
    public String serialize() {
        String serialization = "PlayerTank,";
        String[] imageName = image.getUrl().split("/");
        Object[] list = new Object[] { imageName[imageName.length - 1], position.getX(), position.getY(), direction,
                height, width, health, speed, turretDirection, reloadTime, reloadStatus };
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
        health = Integer.parseInt(list[7]);
        speed = Integer.parseInt(list[8]);
        turretDirection = Integer.parseInt(list[9]);
        reloadTime = Integer.parseInt(list[10]);
        reloadStatus = Integer.parseInt(list[11]);
    }
}