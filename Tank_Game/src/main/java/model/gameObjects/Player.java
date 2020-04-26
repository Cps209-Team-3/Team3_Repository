package model.gameObjects;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import model.World;
import model.enums.BulletType;

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

    // Moves tank in the direction of 'input' and saves last position to
    // pastPositions
    public void move(ArrayList<KeyEvent> keys) {
        GameObject object = World.instance().findCollision(this);
            String input = "";
            for(int i = 0; i < keys.size(); i++){
                input += keys.get(i).getText().charAt(0);
            }
            if (keys.size() < 3 && input.contains("w") && input.contains("a")) {
                direction = 315;
                calculateMove(object, direction);
            } else if (keys.size() < 3 && input.contains("w") && input.contains("d")){
                direction = 45;
                calculateMove(object, direction);
            } else if (keys.size() < 3 && input.contains("s") && input.contains("a")) {
                direction = 225;
                calculateMove(object, direction);
            } else if (keys.size() < 3 && input.contains("s") && input.contains("d")) {
                direction = 135;
                calculateMove(object, direction);
            } else if (keys.size() < 2 && input.contains("w")) {
                direction = 0;
                calculateMove(object, direction);
            } else if (keys.size() < 2 && input.contains("s")) {
                direction = 180;
                calculateMove(object, direction);
            } else if (keys.size() < 2 && input.contains("a")) {
                direction = 270;
                calculateMove(object, direction);
            } else if (keys.size() < 2 && input.contains("d")) {
                direction = 90;
                calculateMove(object, direction);
            }
        }

    public void calculateMove(GameObject object, int desired) {
        /*while(direction != desired){

            if(desired > direction) {
                direction += 5;
            } else {
                direction -= 5;
            }
        }*/
        if (object == null || object instanceof Bullet) {
            double newX = position.getX();
            double newY = position.getY();
            newX = speed * Math.sin(direction * Math.PI / 180);
            newY = -speed * Math.cos(direction * Math.PI / 180);
            lastPosition = position;
            position = new Point((int) (newX + position.getX()), (int) (newY + position.getY()));
        } else {
            position = lastPosition;
        }
        if (position.getX() > 1330) {
            position.setLocation(1330.0, position.getY());
        } else if (position.getX() < 30) {
            position.setLocation(30, position.getY());
        } else if (position.getY() < -400) {
            position.setLocation(position.getX(), -400);
        } else if (position.getY() > 320) {
            position.setLocation(position.getX(), 320);
        }
    }

    @Override
    public Bullet fire() {
        return new Bullet(new Image("/Images/projectile.png"),
                new Point((int) position.getX() + width / 2, (int) position.getY() + height / 2), turretDirection, 10,
                10, 5, 1, BulletType.PLAYER);
    }

    @Override
    public void onDeath() {
        if (!World.instance().isCheatMode()) {
            World.instance().removeObject(this);
            // TODO World.instance().onWaveEnd();
        }
    }

    @Override
    public String serialize() {
        String serialization = "PlayerTank,";
        Object[] list = new Object[] { image.getUrl().split("/")[17], position.getX(), position.getY(), direction,
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