package model;

import model.enums.EnemyState;

public class Enemy extends Tank {

    EnemyState state;

    // Initializes a new enemy and sets its state to parameter 'state.'
    public Enemy(EnemyState state) {
        this.state = state;
    }

    // Initialize a new enemy
    public Enemy() { // number one (hint: ignore syntax)
        state = EnemyState.PAUSE;
    }

    void findPlayer() {
        // TODO
    }

    void targetPlayer() {
        // TODO
    }

    void changeState() {
        // TODO
    }

    @Override
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
            default:
                break;
        }

    }

    @Override
    void fire() {
        // TODO Auto-generated method stub

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

}