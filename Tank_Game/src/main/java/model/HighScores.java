package model;

import java.io.*;
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
        Collections.sort(highScores, new SortByScore());
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
        try {
        String file = "highScores.txt";
        FileWriter writer = new FileWriter(file);
        PrintWriter printer = new PrintWriter(writer);
            for (PlayerData highScore : highScores) {


            }
        } catch (IOException e) {
            
        }
    }

    /**
     * Loads the high scores in the highScores list from a comma delimited text file
     * @throws Exception
     */
    public void load() throws Exception {

    }
}