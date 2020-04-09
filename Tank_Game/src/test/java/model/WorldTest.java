package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Test;

public class WorldTest {
    @Test
    public void testWorld_Save_Success() {
        try {
            World world = new World();            
            world.save("GameBackup.txt");

            BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"));
            String line = "";
            line = reader.readLine();
            if (line != null)
                assertEquals("", line);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testWorld_Load_Success() {
        try {
            World world = new World();
            assertNotEquals(world.getListOfEntities(), null);
            world.load("GameBackup.txt");

            BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"));
            String line = reader.readLine();
            assertEquals(line, null);
            assertEquals(world.getListOfEntities(), null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
