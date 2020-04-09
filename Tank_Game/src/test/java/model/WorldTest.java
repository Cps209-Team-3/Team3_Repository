
import org.junit.Test;

import model.World;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;


public class WorldTest {
    @Test
    public void testWorld_Save_Success() {
        try {
            World world = new World();
            world.save("GameBackup.txt");

            assertEquals(world.getListOfEntities(), null);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testWorld_Load_Success() {
        try {
            World world = new World();
            world.save("GameBackup.txt");

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
