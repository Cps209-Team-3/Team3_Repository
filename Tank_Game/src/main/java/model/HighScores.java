package model;

import java.util.*;

public class HighScores {

    /**
     * List of Players and their corresponding High Scores
     */
    ArrayList<PlayerData> highScores = new ArrayList<PlayerData>();

    /**
     * Creates a PlayerData object that stores the name and high score of the player
     * then adds the data to a list of PlayerData objects.
     * 
     * @param name      String
     * @param highScore double
     */
    void addHighScore(String name, double highScore) {
        PlayerData player = new PlayerData(name, highScore);
        highScores.add(player);
    }

    /**
     * Returns the list of PlayerData objects.
     */
    ArrayList<PlayerData> getHighScores() {
        return highScores;
    }
}