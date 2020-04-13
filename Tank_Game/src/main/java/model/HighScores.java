package model;

import java.util.ArrayList;

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

    /**
     * Saves the high scores in the highScores list to comma delimited text file
     * @throws Exception
     */
    public void save() throws Exception {
        // String file = "highScores.txt";
        // FileWriter writer = new FileWriter(file);
        // for (PlayerData highScore : highScores) {

        // }
    }

    /**
     * Loads the high scores in the highScores list from a comma delimited text file
     * @throws Exception
     */
    public void load() throws Exception {

    }
}