package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.enums.Difficulty;
import model.gameObjects.Bullet;
import model.gameObjects.Enemy;
import model.gameObjects.GameObject;
import model.gameObjects.Player;
import model.gameObjects.Tank;
import model.gameObjects.Wall;

public class World {

    int width;
    int height;
    int score;
    int currentWave;
    Difficulty difficulty;
    Player playerTank;
    boolean cheatMode = false;
    int cycleCount;

    ArrayList<GameObject> listOfEntities = new ArrayList<GameObject>();
    ArrayList<String> listOfSavedGames = new ArrayList<String>();

    // Singleton Implementation
    // Unsure if we want to finalize this or not

    private static World instance = new World();

    public static World instance() {
        return instance;
    }

    public static void reset() {
        instance = new World();
    }

    public World() {
        height = 900;
        width = 1400;
        score = -20;
        currentWave = 3;
        playerTank = new Player(new Point(37, 64), 0, 50, 60, 5, 10, 90, 5, 5, new Point(30, 60));
        listOfEntities.add(playerTank);
        fillListOfSavedGames();
    }

    public void fillListOfSavedGames() {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("##")) {
                    listOfSavedGames.add(0, line.split(",")[1]);
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
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
        int index = listOfEntities.indexOf(gameName);
        if (listOfSavedGames.size() > index) {
            endOfFile = listOfSavedGames.get(index + 1);
        }
        String line = "";
        // Goes through the file until it gets to the part that contains the correct Saved Game
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
            listOfEntities.add(gameObject);

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
        for (GameObject gameObject : listOfEntities) {
            writer.append(gameObject.serialize() + "\n");
        }
        writer.flush();
        writer.close();
        listOfSavedGames.add(0, gameName);
    }

    // Main game loop to run every frame
    public void gameLoop() {
        cycleCount += 1;
        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();
        // playerTank.setDirection((int) Math.toDegrees(Math.atan2(y2, x2)));
        boolean waveComplete = true;
        for (GameObject object : listOfEntities) {
            if (object instanceof Tank) {
                if (object instanceof Enemy) {
                    if (waveComplete) {
                        waveComplete = false;
                    }

                    Enemy tank = (Enemy) object;
                    enemies.add(tank);
                    if (cycleCount > 29) {
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
        if (cycleCount > 29) {
            cycleCount = 0;
        }
        detectAnyCollisions();
        if (waveComplete) {
            onWaveEnd();
        }
    }

    /**
     * Handles user input
     * 
     * @param inp - input of the user
     */
    public void handleInput(char inp) {
        if (inp == '%') {
            World.instance().addObject(playerTank.fire());
        } else {
            playerTank.move(inp);
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
        for (int i = 0; i < currentWave; ++i) {
            Enemy tank = new Enemy(true);
            while (!checkSpawn(tank)) {
                tank = new Enemy(true); // TODO: infinite loop when no space left.
            }
            listOfEntities.add(tank);
        }
    }

    // Handles wave ending
    public void onWaveEnd() {
        score += 20;
        currentWave += 1;
        listOfEntities.clear();
        listOfEntities.add(playerTank);
        createWave();
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
            if (object1 instanceof Bullet) {
                object1.onCollision(object2);
            } else if (object2 instanceof Bullet) {
                object2.onCollision(object1);
            } else if (object1 instanceof Tank) {
                if (object2 instanceof Tank) {
                    // TODO: Remove random push-back priority
                }
                object1.onCollision(object2);
            } else if (object2 instanceof Tank) {
                object2.onCollision(object1);
            } else {
                // Wall collided with Wall?
            }
        }
    }

    // detects collision between any two objects within listOfEntities
    public void detectAnyCollisions() {
        ArrayList<ArrayList<GameObject>> objectsToHandle = new ArrayList<>();
        for (GameObject object : listOfEntities) { // Run through all objects
            // handledObjects.add(object);
            for (GameObject object2 : listOfEntities) { // Test current object with all other objects
                if (object.equals(object2)) {
                    if (isCollision(object, object2)) {
                        ArrayList<GameObject> collided = new ArrayList<>();
                        collided.add(object);
                        collided.add(object2);
                        objectsToHandle.add(collided);
                    }
                }
            }
        }
        for (ArrayList<GameObject> objects : objectsToHandle) {
            handleCollision(objects.get(0), objects.get(1));
        }
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
    public boolean checkSpawn(Tank tank) {
        boolean valid = true;
        for (GameObject object : listOfEntities) {
            if (tank != object) {
                if (isCollision(tank, object)) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
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

    public ArrayList<String> getListOfSavedGames() {
        return listOfSavedGames;
    }

    public void setListOfSavedGames(ArrayList<String> listOfSavedGames) {
        this.listOfSavedGames = listOfSavedGames;
    }

}