package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image; // TODO
import model.enums.Difficulty;

public class World {

    int width;
    int height;
    int score;
    int currentWave;
    Difficulty difficulty;
    Player playerTank;
    Image floor;

    ArrayList<GameObject> listOfEntities = new ArrayList<GameObject>();

    public World() { // Initialization function

    }

    /**
     * Takes a string filename, reads from that file, sends each line to the
     * appropiate GameObject and calls deserialization on it
     * 
     * @param filename - the name of a file
     */
    public void load(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        this.deserialization(reader.readLine());
        
        String line = "";
        GameObject gameObject = null;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Wall")) {
                gameObject = new Wall();
            } else if (line.contains("PlayerTank")) {
                gameObject = new Player();
            } else if (line.contains("EnemyTank")) {
                gameObject = new Enemy();
            } else if (line.contains("Bullet")) {
                gameObject = new Bullet();
            } 
            gameObject.deserialization(line);
            listOfEntities.add(gameObject);
        }
        reader.close();
    }

    /**
     * Takes a string filename, calls serialization on every GameObject and writes
     * that line to the file
     * 
     * @param filename - the name of a file
     */
    public void save(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.append(this.serialization() + "\n");
        for (GameObject gameObject : listOfEntities) {
            writer.append(gameObject.serialization() + "\n");
        }
        writer.flush();
        writer.close();
    }

    // Main game loop to run every frame
    public void gameLoop() {
        // TODO
    }

    // Spawn a new wave
    public void spawnWave() {
        // TODO
    }

    /**
     * Handles user input
     * 
     * @param inp - input of the user
     */
    public void handleInput(char inp) {
        // TODO
    }

    // Turns on cheat mode if on, turns off otherwise.
    public void toggleCheatMode() {
        // TODO
    }

    // Creates a new wave
    public void createWave() {
        // TODO
    }

    // Handles wave ending
    public void onWaveEnd() {
        // TODO
    }

    // handles collision
    public void handleCollision() {
        // TODO
    }

    // detects collision
    public void detectCollision() {
        // TODO
    }

    /**
     * Checks weather location chosen is valid
     * 
     * @param loc - the point to be tested
     */
    public void checkSpawn(Point loc) {
        // TODO
    }

    // Pauses the game loop & pops up pause screen
    public void pause() {
        // TODO
    }

    // serializes the world
    public String serialization() {
        // TODO
        return new String();
    }

    // deserializes the world from a file
    public void deserialization(String data) {
        // TODO
    }

    public ArrayList<GameObject> getListOfEntities() {
        return listOfEntities;
    }

    /**
     * Adds an object to the world's list of entities
     * 
     * @param gameObject - the object to be added
     */
    public void addObject(GameObject gameObject) {
        listOfEntities.add(gameObject);
    }

    /**
     * Removes gameObject from the world's list of entities
     * 
     * @param gameObject
     */
    public void removeObject(GameObject gameObject) {
        listOfEntities.remove(gameObject);
    }
}