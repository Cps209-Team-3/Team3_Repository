package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Point;

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

    public void load(String filename) throws IOException {
        String line = "";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            GameObject gameObject = new Wall();
            gameObject.deserialization(line);
            listOfEntities.add(gameObject);
        }
        reader.reset();
        reader.close();
    }

    public void save(String filename) throws Exception {
        FileWriter writer = new FileWriter(filename);
        for (GameObject gameObject : listOfEntities) {
            writer.append(gameObject.serialization());
            writer.append("\n");
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
  
    /** Checks weather location chosen is valid
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
    public void serialization() {
        // TODO
    }

    // deserializes the world from a file
    public void deserialization() {
        // TODO
    }

    public ArrayList<GameObject> getListOfEntities() {
        return listOfEntities;
    }

    /**
     * Adds an object to the world's list of entities
     * @param gameObject - the object to be added
     */
    public void addObject(GameObject gameObject) {
        listOfEntities.add(gameObject);
    }

    /**
     * Removes gameObject from the world's list of entities
     * @param gameObject
     */
    public void removeObject(GameObject gameObject) {
        listOfEntities.remove(gameObject);
    }
}