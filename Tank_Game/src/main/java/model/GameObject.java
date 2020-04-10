package model;

import java.awt.Point;

import javafx.scene.image.Image; // TODO

public abstract class GameObject {

    Image image;
    Point position;
    int direction;
    int height;
    int width;

    abstract String serialization();

    abstract void deserialization(String data);

    abstract void onCollision();
}
