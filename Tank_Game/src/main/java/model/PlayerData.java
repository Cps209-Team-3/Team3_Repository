package model;


public class PlayerData extends GameObject {

    String name;
    double highScore = 0;

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    double getHighScore() {
        return highScore;
    }
}