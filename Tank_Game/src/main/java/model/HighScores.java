package model;

import java.io.*;
import java.util.*;

import model.enums.Difficulty;

public class HighScores {

    private static HighScores instance = new HighScores();

    public static HighScores scoreList() {
        return instance;
    }

    /**
     * List of All Players and their corresponding High Scores
     */
    ArrayList<PlayerData> allHighScores = new ArrayList<PlayerData>();

    /**
     * List of Players and their corresponding Easy High Scores
     */
    ArrayList<PlayerData> easyHighScores = new ArrayList<PlayerData>();

    /**
     * List of Players and their corresponding Medium High Scores
     */
    ArrayList<PlayerData> mediumHighScores = new ArrayList<PlayerData>();

    /**
     * List of Players and their corresponding Hard High Scores
     */
    ArrayList<PlayerData> hardHighScores = new ArrayList<PlayerData>();

    /**
     * Creates a PlayerData object that stores the name and high score of the player
     * then adds the data to a list of PlayerData objects.
     * 
     * @param name      String
     * @param highScore double
     */
    public void addHighScore(String name, double highScore, String difficulty) {
        PlayerData player = new PlayerData(name, highScore, difficulty);
        allHighScores.add(player);
        Collections.sort(allHighScores, (a, b) -> {
            return (int) b.getHighScore() - (int) a.getHighScore();
        });
    }

    /**
     * Returns the list of PlayerData objects.
     */
    public ArrayList<PlayerData> getHighScores() {
        return allHighScores;
    }

    /**
     * Saves the high scores in the highScores list to comma delimited text file named "highScores.txt"
     * 
     * @throws Exception
     */
    public void save() throws Exception {
        try (PrintWriter printer = new PrintWriter(new FileWriter("highScores.txt"))) {
            for (PlayerData player : allHighScores) {
                printer.println(player.getName() + "," + player.getHighScore());
            }
            allHighScores.removeAll(allHighScores);
        } catch (Exception e) {
            System.out.println("Error with High Scores save");
        }
    }

    /**
     * Loads the high scores in the highScores list from a comma delimited text file named "highScores.txt"
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
                String difficulty = player.get(2);
                allHighScores.add(new PlayerData(name, score, difficulty));
                if (difficulty.equals("Easy")) {
                    easyHighScores.add(new PlayerData(name, score, difficulty));
                } else if (difficulty.equals("Medium")) {
                    mediumHighScores.add(new PlayerData(name, score, difficulty));
                } else if (difficulty.equals("Hard")) {
                    hardHighScores.add(new PlayerData(name, score, difficulty));
                }
            }
        }
    }

    /**
     * Returns the list of PlayerData objects on Easy difficulty.
     */
    public ArrayList<PlayerData> getEasyHighScores() {
        return easyHighScores;
    }

    /**
     * Returns the list of PlayerData objects on Medium difficulty.
     */
    public ArrayList<PlayerData> getMediumHighScores() {
        return mediumHighScores;
    }

    /**
     * Returns the list of PlayerData objects on Hard difficulty.
     */
    public ArrayList<PlayerData> getHardHighScores() {
        return hardHighScores;
    }
}