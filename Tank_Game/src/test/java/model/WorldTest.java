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
        try (BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"))) {
            World world = new World();            
            world.save("GameBackup.txt");
            String line = "";
            line = reader.readLine();
            assertNotEquals(line, null);
            assertEquals(true, line.contains("World"));
            if (line != null)
                assertEquals("", line);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testWorld_Load_Success() {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"))) {
            World world = new World();
            assertEquals(world.getListOfEntities(), null);
            world.load("GameBackup.txt");
            String line = reader.readLine();
            assertNotEquals(line, null);
            assertEquals(true, line.contains("World"));
            assertNotEquals(world.getListOfEntities(), null);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
