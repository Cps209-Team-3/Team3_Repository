package model;

public abstract class Tank extends GameObject {

    int health;
    int speed;

    void onDeath(){
        // TODO
    }

    abstract void move();

    abstract void fire();

    @Override
    String serialization() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    void deserialization(String data) {
        // TODO Auto-generated method stub
    }

    @Override
    void onCollision() {
        //  TODO Auto-generated method stub
    }
}