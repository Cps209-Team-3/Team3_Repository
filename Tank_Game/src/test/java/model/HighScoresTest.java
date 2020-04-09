package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.*;
import org.junit.Test;


public class HighScoresTest {

    @Test
    public void testHighScores_AddHighScore_Success() {
        HighScores highScores = new HighScores();
        highScores.addHighScore("Jeremy", 23000);
        highScores.addHighScore("Michael", 35000);

        ArrayList<PlayerData> list = highScores.getHighScores();

        assertEquals("Jeremy", list.get(0).getName());
        assertEquals(23000, list.get(0).getHighScore());

        assertEquals("Michael", list.get(1).getName());
        assertEquals(35000, list.get(1).getHighScore());
    }
}