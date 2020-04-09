package model;

public class Bullet extends GameObject {

    int speed;
    int damageAmount;

    void move(){
        // TODO create movement based off speed
    }

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