package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class World {

    ArrayList<GameObject> listOfEntities = new ArrayList<GameObject>();
    
    public World() {

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