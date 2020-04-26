package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import model.enums.BulletType;
import model.enums.EnemyState;
import model.gameObjects.*;

public class GameObjectsTest {

    @Test
    public void testWall_parameters_success() {
        Wall wall = new Wall(new Point(37, 64), 0, 50, 60);
        assertEquals(new Point(37, 64), wall.getPosition());
        assertEquals(50, wall.getHeight());
        assertEquals(60, wall.getWidth());
        String serialized = wall.serialize();
        assertEquals("Wall,/Images/wall.png,37:64,0,50,60", serialized);
    }

    @Test
    public void testPlayer_move_onDeath_success() {
        World.instance().getEntities().clear();
        Player playerTank = new Player(new Point(37, 64), 0, 50, 60, 5, 10, 90, 5, 5, new Point(30, 60));
        assertEquals(10, playerTank.getSpeed());
        assertEquals(90, playerTank.getDirection());
        ArrayList<KeyEvent> keys = new ArrayList<>();
        playerTank.move(keys);
        assertEquals(new Point(42, 64), playerTank.getPosition());
        World.instance().addObject(playerTank);
        playerTank.onDeath();
        assertEquals(0, World.instance().getEntities().size());
    }

    @Test
    public void testEnemy_Fire_ChangeState_success() {
        Enemy enemy = new Enemy(new Point(37, 64), 0, 50, 60, 5, 10, 90, 5, 5, new Point(30, 60), EnemyState.PAUSE);
        Bullet bullet = enemy.fire();
        assertEquals(BulletType.ENEMY, bullet.getType());
        assertEquals(new Point(62, 94), bullet.getPosition());
        assertEquals(EnemyState.PAUSE, enemy.getState());
        enemy.changeState();
        assertNotEquals(EnemyState.PAUSE, enemy.getState());
    }

    @Test
    public void testBullet_move_onCollision_success() {
        Bullet bullet = new Bullet(new Image("/Images/projectile.png"), new Point(50, 50), 0, 0, 0, 10, 1, BulletType.ENEMY);
        bullet.move();
        assertEquals(new Point(50, 60), bullet.getPosition());
        Player player = new Player();
        bullet.onCollision(player);
        assertEquals(0, player.getHealth());
    }
}