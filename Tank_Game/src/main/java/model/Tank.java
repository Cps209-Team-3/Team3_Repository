package model;

public abstract class Tank extends GameObject {

    int health;
    int speed;
    int turretDirection;

    void onDeath(){
        // TODO
    }

    abstract void move();

    abstract void fire();

    @Override
    void onCollision() {
        //  TODO Auto-generated method stub
    }
}