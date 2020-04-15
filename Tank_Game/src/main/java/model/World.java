package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import model.enums.Difficulty;

public class World {

    int width;
    int height;
    int score;
    int currentWave;
    Difficulty difficulty;
    Player playerTank;
    boolean cheatMode = false;
    Image floor;

    ArrayList<GameObject> listOfEntities = new ArrayList<GameObject>();

    // Singleton Implementation
    // Unsure if we want to finalize this or not

    private static World instance = new World();

    public static World instance() {
        return instance;
    }

    public static void reset() {
        instance = new World();
    }

    private World() {

    }

    /**
     * Takes a string filename, reads from that file, sends each line to the
     * appropiate GameObject and calls deserialization on it
     * 
     * @param filename - the name of a file
     */
    public void load(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        this.deserialize(reader.readLine());

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
            gameObject.deserialize(line);
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
        writer.append(this.serialize() + "\n");
        for (GameObject gameObject : listOfEntities) {
            writer.append(gameObject.serialize() + "\n");
        }
        writer.flush();
        writer.close();
    }

    // Main game loop to run every frame
    public void gameLoop() {
        // TODO: get inputs
        boolean waveComplete = true;
        for (GameObject object : listOfEntities) {
            if (object instanceof Tank) {
                if (object instanceof Player) {
                    // handle input, move tank
                } else {
                    if (waveComplete) {
                        waveComplete = false;
                    }
                    Enemy tank = (Enemy) object;
                    tank.move();
                }
            } else if (object instanceof Bullet) {
                Bullet bullet = (Bullet) object;
                bullet.move();
            }
        }
        if (waveComplete) {
            score += 20;
            createWave();
            spawnWave();
        }
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
        if (cheatMode) {
            cheatMode = false;
        } else {
            cheatMode = true;
        }
    }

    // Creates a new wave
    public void createWave() {
        // TODO
    }

    // Handles wave ending
    public void onWaveEnd() {
        // TODO
    }

    /**
     * Handles a Collision between two objects. Creates preference in order of
     * Bullet, Tank, Wall to determine which onCollision method to call.
     * 
     * @param object1
     * @param object2
     */
    public void handleCollision(GameObject object1, GameObject object2) {
        if (object1 instanceof Bullet) {
            object1.onCollision(object2);
        } else if (object2 instanceof Bullet) {
            object2.onCollision(object1);
        } else if (object1 instanceof Tank) {
            object1.onCollision(object2);
        } else if (object2 instanceof Tank) {
            object2.onCollision(object1);
        } else {
            // Wall collided with Wall?
        }
    }

    // detects collision
    public void detectCollision() {
        ArrayList<GameObject> handledObjects = new ArrayList<>();
        for (GameObject object : listOfEntities) { // Run through all objects
            handledObjects.add(object);
            for (GameObject object2 : listOfEntities) { // Test current object with all other objects
                if (!handledObjects.contains(object)) {
                    // test for collision
                    if (object.getPosition().getX() < object2.getPosition().getX() + object2.getWidth()
                            && object.getPosition().getX() + object.getWidth() > object2.getPosition().getX()
                            && object.getPosition().getY() < object2.getPosition().getY() + object2.getHeight()
                            && object.getPosition().getY() + object.getHeight() > object2.getPosition().getY()) {
                        handleCollision(object, object2);
                    }
                }
            }
        }
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
    public String serialize() {
        // TODO
        return new String();
    }

    // deserializes the world from a file
    public void deserialize(String data) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Player getPlayerTank() {
        return playerTank;
    }

    public void setPlayerTank(Player playerTank) {
        this.playerTank = playerTank;
    }

    public Image getFloor() {
        return floor;
    }

    public void setFloor(Image floor) {
        this.floor = floor;
    }
}