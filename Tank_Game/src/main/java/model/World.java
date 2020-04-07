package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class World {
    ArrayList<GameObject> listOfEntities = new ArrayList<GameObject>();
    

    void load(String filename) {
        try {
            listOfEntities.clear();
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((line = reader.readLine()) != null) {
                GameObject gameObject = new GameObject();
                gameObject.deserialization(line);
            }
            reader.close();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.toString());
            alert.show();
        }
    }

    void save(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (GameObject gameObject : listOfEntities) {
                writer.append(gameObject.serialization());
                writer.append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.toString());
            alert.show();
        }
    }
}