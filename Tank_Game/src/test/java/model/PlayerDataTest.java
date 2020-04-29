package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerDataTest {

    @Test
    public void testPlayerData_SetName_Success() {
        PlayerData player = new PlayerData("Shawn", 25000, "Easy");
        player.setName("Bob");
        assertEquals("Bob", player.getName());
    }

    @Test
    public void testPlayerData_SetHighScore_Success() {
        PlayerData player = new PlayerData("Shawn", 25000, "Easy");
        player.setHighScore(30000);
        assertEquals(30000, player.getHighScore(), 0);
    }

    @Test
    public void testPlayerData_SetDifficulty_Success() {
        PlayerData player = new PlayerData("Shawn", 25000, "Easy");
        player.setDifficulty("Hard");
        assertEquals("Hard", player.getDifficulty());
    }
}