package model;

import model.enums.BulletType;
import javafx.scene.image.Image;
import java.awt.Point;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;
    BulletType type;

     /**
      * Initializes a new Bullet using parameters.
      *
      * @param image
      * @param position
      * @param direction
      * @param height
      * @param width
      * @param speed
      * @param damageAmount
      * @param type
      */
    public Bullet(Image image, Point position, int direction, int height, int width, int speed, int damageAmount, BulletType type) {
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
    public Bullet(){

    }

    void move() {
        // CODE BELOW IS FROM ONE OF ANDREW'S OLD PYTHON SCRIPTS AND NEEDS BE TRANSLATED
        // the math checks out though, so it works.
        // newy = -(float(speed)/2)*math.sin(math.radians(float(-(heading) + 90)))+ycoord
        // newx = (float(speed)/2)*math.cos(math.radians(float(-(heading) + 90)))+xcoord
        // TODO create movement based off speed
    }

    @Override
    public String serialize() {
        String serialization = "Bullet,";
        Object[] list = new Object[] {image.getUrl().split("/")[17], position.getX(), position.getY(), direction, height, width, speed, speed, damageAmount, type};
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
        speed = Integer.parseInt(list[7]);
        damageAmount = Integer.parseInt(list[8]);
        switch (list[9]) {
            case "PLAYER":
                type = type.PLAYER;
                break;
            case "ENEMY":
                type = type.ENEMY;
                break;
        }
    }

    @Override
    void onCollision(GameObject object) {
        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            tank.health -= 1;
            if (tank.health <= 0) {
                tank.onDeath();
            }
        } else if (object instanceof Wall || object instanceof Bullet) {
            // Run explosion animation
            // Delete itself (this)
        }
    }
}