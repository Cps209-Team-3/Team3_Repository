package model;

import java.util.*;
/**
 * Modified sorting idea found at https://www.geeksforgeeks.org/collections-sort-java-examples/
 */
public class SortByScore implements Comparator<PlayerData> {

    public int compare(PlayerData a, PlayerData b) {

        return (int)b.getHighScore() - (int)a.getHighScore();

    }
}
