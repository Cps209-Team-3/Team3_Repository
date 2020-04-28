package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.KeyEvent;
import model.enums.BulletType;
import model.enums.Difficulty;
import model.gameObjects.*;
import model.gameObjects.powerups.*;

public class World {

    int width;
    int height;
    double score;
    double waveScore;
    int currentWave;
    Difficulty difficulty;
    String diffString;
    Player playerTank;
    boolean cheatMode = false;
    int cycleCount;
    boolean difficultySet = false;

    ArrayList<GameObject> entities = new ArrayList<GameObject>();
    ArrayList<String> savedGames = new ArrayList<String>();

    // Singleton Implementation

    private static World instance = new World();

    public static World instance() {
        return instance;
    }

    public static void reset() {
        instance = new World();
    }

    private World() {
        height = 900;
        width = 1440;
        score = -20;
        currentWave = 0;
        difficulty = Difficulty.EASY; 
        playerTank = new Player(new Point(37, 64), 0, 50, 60, 5, 5, 90, 5, 5, new Point(30, 60));
        entities.add(playerTank);
        fillSavedGames();
    }

    /**
     * Fills up the savedGames with all the saved games from the file
     * "GameBackup.txt"
     */
    public void fillSavedGames() {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("##")) {
                    savedGames.add(0, line.split(",")[1]);
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Generates Walls to put into the arena.
    public void generateDifficulty() {
        switch (difficulty) {
            case HARD:
                diffString = "Hard";
                // NO WALLS!
                playerTank.setHealth(5);
                break;
            case MEDIUM:
                diffString = "Medium";
                for (int i = 0; i < 7; ++i) {
                    Wall wall = new Wall(new Point(i * 80 + 400, 100), 0, 80, 80);
                    entities.add(wall);
                }
                playerTank.setHealth(7);
                break;
            case EASY:
                diffString = "Easy";
                for (int i = 0; i < 13; ++i) {
                    Wall wall = new Wall(new Point(i * 80 + 200, 100), 0, 80, 80);
                    entities.add(wall);
                }
                playerTank.setHealth(10);
                break;
        }
    }

    /**
     * Puts a random amount of random powerups randomly around the world
     */
    public void generatePowerups() {
        Random random = new Random();
        int powerupCount = random.nextInt(2) + 1;
        Powerup powerup = null;
        for (int i = 0; i < powerupCount; i++) {
            int powerupID = random.nextInt(3);
            switch (powerupID) {
                case 0:
                    powerup = new HealthPowerup();
                    break;
                case 1:
                    powerup = new SpeedyPowerup();
                    break;
                case 2:
                    powerup = new FastFirePowerup();
                    break;
            }
            powerup.setPosition(new Point(random.nextInt(width - 100), random.nextInt(760) - 360));
            while (!checkSpawn(powerup)) {
                powerup.setPosition(new Point(random.nextInt(width - 100), random.nextInt(760) - 360));
            }
            entities.add(powerup);
        }
    }

    /**
     * Takes a string filename, reads from that file, sends each line to the
     * appropiate GameObject and calls deserialization on it
     * 
     * @param filename - the name of a file
     */
    public void load(String filename, String gameName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String endOfFile = null;
        int index = entities.indexOf(gameName);
        if (savedGames.size() > index) {
            endOfFile = savedGames.get(index + 1);
        }
        String line = "";
        // Goes through the file until it gets to the part that contains the correct
        // Saved Game
        while (!line.contains(gameName)) {
            line = reader.readLine();
        }
        line = reader.readLine();
        this.deserialize(line);
        line = reader.readLine();

        GameObject gameObject = null;
        while (line != null) {
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
            entities.add(gameObject);

            if ((line = reader.readLine()).contains("##")) {
                line = null;
            }
        }
        reader.close();
    }

    /**
     * Takes a string filename, calls serialization on every GameObject and writes
     * that line to the file
     * 
     * @param filename - the name of a file
     */
    public void save(String filename, String gameName) throws IOException {
        FileWriter writer = new FileWriter(filename, true);
        writer.append("##," + gameName + "\n");
        writer.append(this.serialize() + "\n");
        for (GameObject gameObject : entities) {
            writer.append(gameObject.serialize() + "\n");
        }
        writer.flush();
        writer.close();
        savedGames.add(0, gameName);
    }

    // Main game loop to run every frame
    public void gameLoop() {
        if (!difficultySet) {
            generateDifficulty();
            difficultySet = true;
        }
        cycleCount += 1;
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();
        // playerTank.setDirection((int) Math.toDegrees(Math.atan2(y2, x2)));
        boolean waveComplete = true;
        for (GameObject object : entities) {

            if (object instanceof Tank) {
                if (object instanceof Enemy) {
                    if (waveComplete) {
                        waveComplete = false;
                    }

                    Enemy tank = (Enemy) object;
                    enemies.add(tank);
                    if (cycleCount % tank.getFireNum() == 0) {
                        tank.changeState();
                    }
                }
            } else if (object instanceof Bullet) {
                Bullet bullet = (Bullet) object;
                bullets.add(bullet);
            }
        }
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        if (cycleCount > 180) {
            cycleCount = 0;
        }
        detectAnyCollisions();
        if (waveComplete) {
            onWaveEnd(cycleCount);
        }
    }

    /**
     * Handles user input
     * 
     * @param inp - input of the user
     */
    public void handleInput(char inp, ArrayList<KeyEvent> keys) {
        // Check for game end, may want to adjust this so GameWindow knows.a
        if (entities.contains(playerTank)) {
            if (inp == '%') {
                World.instance().addObject(playerTank.fire());
            } else {
                playerTank.move(keys);
            }
        }
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
        waveScore = 0;
        for (int i = 0; i < currentWave; ++i) {
            Enemy tank = new Enemy(true);
            while (!checkSpawn(tank)) {
                tank = new Enemy(true);
            }
            entities.add(tank);
        }
        generatePowerups();
    }

    // Handles wave ending
    public void onWaveEnd(int readyNum) {
        ArrayList<GameObject> toRemove = new ArrayList<>();
        score += waveScore/2;
        for (GameObject object : entities) {
            if (object instanceof Bullet) {
                Bullet bullet = (Bullet) object;
                if (bullet.getType() == BulletType.ENEMY) {
                    toRemove.add(object);
                }
            }
        }
        for (GameObject object : toRemove) {
            entities.remove(object);
        }
        if (readyNum > 179) {
            if (entities.contains(playerTank)) {
                score += 20;
                currentWave += 1;
                toRemove = new ArrayList<>();
                for (GameObject object : entities) {
                    if (!(object instanceof Wall)) {
                        toRemove.add(object);
                    }
                }
                for (GameObject object : toRemove) {
                    entities.remove(object);
                }
                entities.add(playerTank);
                createWave();
            }
        }
    }

    /**
     * Handles a Collision between two objects. Creates preference in order of
     * Bullet, Tank, Wall to determine which onCollision method to call.
     * 
     * @param object1
     * @param object2
     */
    public void handleCollision(GameObject object1, GameObject object2) {
        if (object1 != object2) {
            object1.onCollision(object2);
            object2.onCollision(object1);
        }
    }

    // detects collision between any two objects within entities
    public void detectAnyCollisions() {
        ArrayList<ArrayList<GameObject>> objectsToHandle = new ArrayList<>();
        for (GameObject object : entities) { // Run through all objects
            // handledObjects.add(object);
            GameObject collided = findCollision(object);
            if (collided != null) {
                ArrayList<GameObject> objects = new ArrayList<>();
                objects.add(collided);
                objects.add(object);
                objectsToHandle.add(objects);
            }
        }
        for (ArrayList<GameObject> objects : objectsToHandle) {
            handleCollision(objects.get(0), objects.get(1));
        }
    }

    public GameObject findCollision(GameObject object) {
        for (GameObject object2 : entities) { // Test current object with all other objects
            if (!object.equals(object2)) {
                if (isCollision(object, object2)) {
                    return object2;
                }
            }
        }
        return null;
    }

    /**
     * Returns true if object1 and object2 collide, false otherwise.
     * 
     * @param object1
     * @param object2
     * @return - true if object1 and object2 collide, false otherwise.
     */
    public boolean isCollision(GameObject object1, GameObject object2) {

        if (object1.getPosition().getX() < object2.getPosition().getX() + object2.getWidth()
                && object1.getPosition().getX() + object1.getWidth() > object2.getPosition().getX()
                && object1.getPosition().getY() < object2.getPosition().getY() + object2.getHeight()
                && object1.getPosition().getY() + object1.getHeight() > object2.getPosition().getY()) {
            return true;
        }
        return false;
    }

    /**
     * Checks weather location chosen to spawn a tank is valid
     * 
     * @param tank - the tank that contains the area to be tested
     */
    public boolean checkSpawn(GameObject object) {
        for (GameObject object2 : entities) {
            if (object != object2) {
                if (isCollision(object, object2)) {
                    return false;
                }
            }
        }
        return true;
    }

    // serializes the world
    public String serialize() {
        String serialization = "World,";
        Object[] list = new Object[] { width, height, difficulty, score, currentWave, cheatMode };
        for (int i = 0; i < list.length; i++) {
            serialization += list[i].toString();
            if (i != list.length - 1) {
                serialization += ",";
            }
        }
        return serialization;
    }

    // deserializes the world from a file
    public void deserialize(String data) {
        String[] list = data.split(",");
        width = Integer.parseInt(list[1]);
        height = Integer.parseInt(list[2]);
        switch (list[3]) {
            case "EASY":
                difficulty = Difficulty.EASY;
                break;
            case "MEDIUM":
                difficulty = Difficulty.MEDIUM;
                break;
            case "HARD":
                difficulty = Difficulty.HARD;
                break;
        }
        score = Integer.parseInt(list[4]);
        currentWave = Integer.parseInt(list[5]);
        if (list[6].equals("true")) {
            cheatMode = true;
        } else if (list[6].equals("false")) {
            cheatMode = false;
        }
    }

    public ArrayList<GameObject> getEntities() {
        return entities;
    }

    /**
     * Adds an object to the world's list of entities
     * 
     * @param gameObject - the object to be added
     */
    public void addObject(GameObject gameObject) {
        entities.add(gameObject);
    }

    /**
     * Removes gameObject from the world's list of entities
     * 
     * @param gameObject
     */
    public void removeObject(GameObject gameObject) {
        entities.remove(gameObject);
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
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

    public ArrayList<String> getSavedGames() {
        return savedGames;
    }

    public void setSavedGames(ArrayList<String> savedGames) {
        this.savedGames = savedGames;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    public double getWaveScore() {
        return waveScore;
    }

    public void setWaveScore(double waveScore) {
        this.waveScore = waveScore;
    }

    public String getDiffString() {
        return diffString;
    }

    public void setDiffString(String diffString) {
        this.diffString = diffString;
    }
}