package model;

import static org.junit.Assert.assertEquals;

import javafx.scene.image.Image;
import org.junit.Test;
import java.awt.Point;
import java.util.ArrayList;

public class GameObjectsTest {

    @Test
    public void testWall_parameters_success() {
        Wall wall = new Wall(new Image("/wall.png"), new Point(37, 64), 0, 50, 60);
        assertEquals(new Point(37, 64), wall.getPosition());
        assertEquals(50, wall.getHeight());
        assertEquals(60, wall.getWidth());
        String serialized = wall.serialize();
        assertEquals("/wall.png,37:64,0,50,60", serialized);

        Player playerTank = new Player(new Image("/wall.png"), new Point(37, 64), 0, 50, 60, 5, 10, 90, new ArrayList<>());
        assertEquals(10, playerTank.getSpeed());
        assertEquals(90, playerTank.getDirection());
        playerTank.move('W');
        assertEquals(new Point(42, 64), playerTank.getPosition());
        // TODO: Create unit tests for game objects
    }
}