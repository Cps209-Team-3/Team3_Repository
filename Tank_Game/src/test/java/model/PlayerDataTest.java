package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PlayerDataTest {

    @Test
    public void testPlayerData_SetName_Success() {
        PlayerData player = new PlayerData("Shawn", 25000);
        player.setName("Bob");
        assertEquals("Bob", player.getName());
    }

    @Test
    public void testPlayerData_SetHighScore_Success() {
        PlayerData player = new PlayerData("Shawn", 25000);
        player.setHighScore(30000);
        assertEquals(30000, player.getHighScore());
    }
}