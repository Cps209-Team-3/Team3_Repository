package model;

public class PlayerData implements Comparable {

    String name; // Name of the Player
    double highScore = 0; // High Score the Player earned

    /**
     * Initializes a PlayerData object by adding a required name and high score
     * 
     * @param name      String
     * @param highScore double
     */
    PlayerData(String name, double highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    /**
     * Sets the name of the PlayerData object
     * 
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the PlayerData object
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the high score of the PlayerData object
     * 
     * @param highScore double
     */
    public void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    /**
     * Returns the high score of the PlayerData object
     * 
     * @return highScore
     */
    public double getHighScore() {
        return highScore;
    }
}