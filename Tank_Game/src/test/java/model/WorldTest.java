package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.Test;

import javafx.scene.image.Image;
import model.enums.BulletType;
import model.enums.Difficulty;
import model.gameObjects.Bullet;
import model.gameObjects.Enemy;
import model.gameObjects.Player;
import model.gameObjects.Wall;

public class WorldTest {
    public void fillFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String world = "World,10,10,EASY,1000,4,image\n";
            String wall = "Wall,image,10,10,180,10,10\n";
            String playerTank = "PlayerTank,image,10,10,180,10,10,100,50,180\n";
            String enemyTank = "EnemyTank,image,10,10,180,10,10,100,50,180,Flee\n";
            String bullet = "Bullet,image,10,10,180,10,10,50,100,enemy\n";
            writer.write(world);
            writer.write(wall);
            writer.write(playerTank);
            writer.write(enemyTank);
            writer.write(bullet);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fillWorld(World world) {
        world.setWidth(1);
        world.setHeight(2);
        world.setDifficulty(Difficulty.EASY);
        world.setScore(4);
        world.setCurrentWave(5);
        Wall wall = new Wall(new Point(1,2), 3, 4, 5);
        Player playerTank = new Player(new Point(1,2), 3,4,5,6,7,8,9,10,new ArrayList<Point>());
        Enemy enemyTank = new Enemy(true);
        Bullet bullet = new Bullet(new Image(getClass().getResource("/Images/projectile.png").toString()), new Point(1,2), 3,4,5,6,7,BulletType.ENEMY);
        world.addObject(wall);
        world.addObject(playerTank);
        world.addObject(enemyTank);
        world.addObject(bullet);
    }

    @Test
    public void testWorld_Save_Success() {
        try {
            String filename = "GameSave.txt";
            World world = World.instance();
            fillWorld(world);
            world.save(filename);

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = reader.readLine();
                String[] worldList = line.split(",");
                assertEquals("EASY", worldList[3]);
                assertEquals("4", worldList[4]);
                assertEquals("2", worldList[2]);

                line = reader.readLine();
                String[] wallList = line.split(",");
                assertEquals("4", wallList[5]);
                assertEquals("5", wallList[6]);
                assertEquals("1", wallList[2]);

                line = reader.readLine();
                String[] playerList = line.split(",");
                assertEquals("3", playerList[4]);
                assertEquals("4", playerList[5]);

                line = reader.readLine();
                String[] enemyList = line.split(",");
                assertEquals("3", enemyList[4]);
                assertEquals("4", enemyList[5]);

                line = reader.readLine();
                String[] bulletList = line.split(",");
                assertEquals("3", bulletList[4]);
                assertEquals("5", bulletList[6]);

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
            World world = World.instance();
            world.load(filename);

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
