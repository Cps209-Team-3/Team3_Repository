package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class World {
    ArrayList<Tank> enemyTanks = new ArrayList<Tank>();
    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    public World() {

    }

    public void load(String filename) throws IOException {
        String line = "";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            String[] splitLine = line.split(",");
            switch (splitLine[0]) {
                case "World":
                    break;
                case "Wall":      
                    break;
                case "Bullet":
                    break;
                case "enemyTanks":
                    break;
            }

        }
        reader.reset();
        reader.close();
    }

    public void save(String filename) throws Exception {
        FileWriter writer = new FileWriter(filename);
        for (Tankgi gameObject : enemyTanks) {
            writer.append(gameObject.serialization());
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }

    public ArrayList<GameObject> getListOfEntities() {
        return listOfEntities;
    }

    public void addToList(GameObject gameObject) {
        listOfEntities.add(gameObject);
    }
}