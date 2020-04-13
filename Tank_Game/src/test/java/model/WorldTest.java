package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.Test;

import model.enums.Difficulty;

public class WorldTest {
    public void fillFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String world = "World,10,10,EASY,1000,4,image";
            String wall = "Wall,image,10,10,180,10,10";
            String playerTank = "PlayerTank,image,10,10,180,10,10,100,50,180";
            String enemyTank = "EnemyTank,image,10,10,180,10,10,100,50,180,Flee";
            String bullet = "Bullet,image,10,10,180,10,10,50,100,enemy";
            writer.write(world);
            writer.write(wall);
            writer.write(wall);
            writer.write(playerTank);
            writer.write(enemyTank);
            writer.write(bullet);
            writer.write(bullet);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillWorld(World world) {
        world.setDifficulty(Difficulty.EASY);
        world.setHeight(10);
        world.setScore(1000);;
        Wall wall = new Wall();
        wall.setWidth(10);
        wall.setHeight(10);
        wall.setPosition(new Point(10,10));
        Player playerTank = new Player();
        playerTank.setDirection(180);
        playerTank.setHeight(10);
        Enemy enemyTank = new Enemy();
        enemyTank.setDirection(180);
        enemyTank.setHeight(10);
        Bullet bullet = new Bullet();
        bullet.setDirection(180);
        bullet.setWidth(10);
        world.addObject(wall);
        world.addObject(playerTank);
        world.addObject(enemyTank);
        world.addObject(bullet);
    }

    @Test
    public void testWorld_Save_Success() {
        try {
            String filename = "GameSave.txt";
            World world = new World();
            fillWorld(world);
            world.save(filename);

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = reader.readLine();
                String[] worldList = line.split(",");
                assertEquals("EASY", worldList[3]);
                assertEquals("1000", worldList[4]);
                assertEquals("10", worldList[2]);

                line = reader.readLine();
                String[] wallList = line.split(",");
                assertEquals("10", wallList[5]);
                assertEquals("10", wallList[6]);
                assertEquals("10", wallList[2]);

                line = reader.readLine();
                String[] playerList = line.split(",");
                assertEquals("180", playerList[4]);
                assertEquals("10", playerList[5]);

                line = reader.readLine();
                String[] enemyList = line.split(",");
                assertEquals("180", enemyList[4]);
                assertEquals("10", enemyList[5]);

                line = reader.readLine();
                String[] bulletList = line.split(",");
                assertEquals("180", bulletList[4]);
                assertEquals("10", bulletList[6]);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testWorld_Load_Success() {
        try {
            String filename = "GameLoad.txt";

            fillFile(filename);
            World world = new World();
            assertEquals(world.getListOfEntities(), null);
            world.load(filename);
            assertNotEquals(world.getListOfEntities(), null);

            assertEquals(Difficulty.EASY, world.getDifficulty());
            assertNotEquals(null, world.getFloor());
            assertEquals(10, world.getHeight());

            Wall wall = (Wall) world.getListOfEntities().get(0);
            assertEquals(180, wall.getDirection());
            assertEquals(10, wall.getHeight());
            assertEquals(10, world.getWidth());

            Player player = (Player) world.getListOfEntities().get(1);
            assertEquals(180, player.getDirection());
            assertEquals(10, player.getHeight());

            Enemy enemy = (Enemy) world.getListOfEntities().get(1);
            assertEquals(180, enemy.getDirection());
            assertEquals(10, enemy.getHeight());

            Bullet bullet = (Bullet) world.getListOfEntities().get(1);
            assertEquals(180, bullet.getDirection());
            assertEquals(10, bullet.getHeight());
            assertEquals(10, bullet.getWidth());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
