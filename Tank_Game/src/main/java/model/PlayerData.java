//----------------------------------------------------------
// File:   PlayerData.java
// Author: Brandon Swain
// Desc:   This class creates objects that hold a Player's
//         name, high score, and difficulty they played on.
//----------------------------------------------------------
package model;

public class PlayerData {

    String name; // Name of the Player
    double highScore = 0; // High Score the Player earned
    String difficulty; // difficulty the Player chose

    /**
     * Initializes a PlayerData object by adding a required name and high score.
     * 
     * @param name       String
     * @param highScore  double
     * @param difficulty String
     */
    public PlayerData(String name, double highScore, String difficulty) {
        this.name = name;
        this.highScore = highScore;
        this.difficulty = difficulty;
    }

    // Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    public double getHighScore() {
        return highScore;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}