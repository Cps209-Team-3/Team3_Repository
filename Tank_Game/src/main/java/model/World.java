package model;

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

    public void load(String filename) throws IOException {
        listOfEntities.clear();
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

    public void gameLoop() {
        // TODO
    }

    public void spawnWave() {
        // TODO
    }

    public void handleInput() {
        // TODO
    }

    public void toggleCheatMode() {
        // TODO
    }

    public void createWave() {
        // TODO
    }

    public void onWaveEnd() {
        // TODO
    }

    public void handleCollision() {
        // TODO
    }

    public void detectCollision() {
        // TODO
    }
  
    public void checkSpawn() {
        // TODO
    }

    public void pause() {
        // TODO
    }

    public void serialization() {
        // TODO
    }

    public void deserialization() {
        // TODO
    }

    public ArrayList<GameObject> getListOfEntities() {
        return listOfEntities;
    }

    public void addObject(GameObject gameObject) {
        listOfEntities.add(gameObject);
    }

    public void removeObject(GameObject gameObject) {
        listOfEntities.remove(gameObject);
    }
}