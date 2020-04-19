package model;

import java.io.*;
import java.util.*;

public class HighScores {

    private static HighScores instance = new HighScores();

    public static HighScores scoreList() {
        return instance;
    }

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
    public void addHighScore(String name, double highScore) {
        PlayerData player = new PlayerData(name, highScore);
        highScores.add(player);
        Collections.sort(highScores, (a, b) -> {
            return (int) b.getHighScore() - (int) a.getHighScore();
        });
    }

    /**
     * Returns the list of PlayerData objects.
     */
    public ArrayList<PlayerData> getHighScores() {
        return highScores;
    }

    /**
     * Saves the high scores in the highScores list to comma delimited text file
     * 
     * @throws Exception
     */
    public void save() throws Exception {
        try (PrintWriter printer = new PrintWriter(new FileWriter("highScores.txt"))) {
            for (PlayerData player : highScores) {
                printer.println(player.getName() + "," + player.getHighScore());
            }
            highScores.removeAll(highScores);
        } catch (Exception e) {
            System.out.println("Error with High Scores save");
        }
    }

    /**
     * Loads the high scores in the highScores list from a comma delimited text file
     * 
     * @throws Exception
     */
    public void load() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("highScores.txt"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                List<String> player = Arrays.asList(str.split(","));
                String name = player.get(0);
                double score = Double.parseDouble(player.get(1));
                highScores.add(new PlayerData(name, score));
            }
        }
    }
}