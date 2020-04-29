//--------------------------------------------------------------
// File:   HighScoresTest.java
// Author: Brandon Swain
// Desc:   This file tests the methods in the HighScores class.
//--------------------------------------------------------------
package model;

import static org.junit.Assert.assertEquals;

import java.util.*;
import org.junit.Test;

public class HighScoresTest {

    @Test
    public void testHighScores_AddHighScore_Success() {
        HighScores highScores = new HighScores();
        highScores.addHighScore("Jeremy", 23000, "Easy");
        highScores.addHighScore("Michael", 35000, "Medium");
        highScores.addHighScore("Shawn", 15000, "Hard");

        ArrayList<PlayerData> list = highScores.getAllHighScores();

        assertEquals("Jeremy", list.get(1).getName());
        assertEquals(23000, list.get(1).getHighScore(), 0);

        assertEquals("Michael", list.get(0).getName());
        assertEquals(35000, list.get(0).getHighScore(), 0);
    }

    @Test
    public void testHighScores_Save_Success() throws Exception {
        HighScores highScores = new HighScores();
        highScores.addHighScore("David", 23000, "Hard");
        highScores.addHighScore("Michael", 35000, "Hard");
        highScores.addHighScore("John", 20000, "Medium");
        highScores.addHighScore("Austin", 27000, "Medium");
        highScores.addHighScore("Andrew", 15000, "Easy");
        highScores.addHighScore("Jeremy", 33000, "Easy");
        highScores.save();
        highScores.load();

        assertEquals("Michael", highScores.getAllHighScores().get(0).getName());
        assertEquals(35000, highScores.getAllHighScores().get(0).getHighScore(), 0);
        assertEquals("Jeremy", highScores.getAllHighScores().get(1).getName());
        assertEquals(33000, highScores.getAllHighScores().get(1).getHighScore(), 0);

        assertEquals("Michael", highScores.getHardHighScores().get(0).getName());
        assertEquals(35000, highScores.getHardHighScores().get(0).getHighScore(), 0);
        assertEquals("David", highScores.getHardHighScores().get(1).getName());
        assertEquals(23000, highScores.getHardHighScores().get(1).getHighScore(), 0);

        assertEquals("John", highScores.getMediumHighScores().get(1).getName());
        assertEquals(20000, highScores.getMediumHighScores().get(1).getHighScore(), 0);
        assertEquals("Austin", highScores.getMediumHighScores().get(0).getName());
        assertEquals(27000, highScores.getMediumHighScores().get(0).getHighScore(), 0);

        assertEquals("Andrew", highScores.getEasyHighScores().get(1).getName());
        assertEquals(15000, highScores.getEasyHighScores().get(1).getHighScore(), 0);
        assertEquals("Jeremy", highScores.getEasyHighScores().get(0).getName());
        assertEquals(33000, highScores.getEasyHighScores().get(0).getHighScore(), 0);
    }
}