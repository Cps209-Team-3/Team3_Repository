package model;

public class Player extends Tank {

    // Initializes a new Player.
    public Player() {
    }

    @Override
    void move() {
        // TODO Auto-generated method stub

    }

    @Override
    void fire() {
        // TODO Auto-generated method stub

    }

    @Override
    String serialize() {
        // TODO Auto-generated method stub
        String base = baseSerialize();
        return null;
    }

    @Override
    void deserialize(String data) {
        // TODO Auto-generated method stub
        baseDeserialize(data);

    }

}