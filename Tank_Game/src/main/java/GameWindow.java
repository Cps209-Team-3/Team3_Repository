import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import model.World;
import model.gameObjects.GameObject;
import model.gameObjects.Tank;

public class GameWindow {

    @FXML
    Pane pane;

    @FXML
    Pane background;

    @FXML
    Pane buttonPane;

    private final KeyFrame keyFrame = new KeyFrame(Duration.millis(16.67), e -> gameLoop());
    private final Timeline clock = new Timeline(keyFrame);

    private Point mouse = new Point();

    private ArrayList<GameObject> objects = World.instance().getListOfEntities();
    private Map<GameObject, ImageView> images = new HashMap<>();

    private Stage gameWindow;
    private Button saveAndExit = new Button("Save and Exit");
    private ImageView image;
    private Text score = new Text("Score: 0");
    private Text waveNum = new Text("Wave: ");

    // Player Tank Fires (TBF)
    @FXML
    void onMouseClicked(MouseEvent value) {
        World.instance().handleInput('%');
    }

    @FXML
    void onMouseMoved(MouseEvent value) {
        // Hand Mouse Coordinates to player tank's head TBF
        mouse.setLocation(value.getX(), value.getY());
    }

    /*
     * @FXML void onInputKeyPressed(KeyEvent.KEY_PRESSED event) {
     * World.instance().handleInput(event.getCharacter());
     * 
     * }
     */

    void initialize(Stage gameWindow) {
        saveAndExit.setStyle("-fx-font-size: 14");
        saveAndExit.setLayoutX(660);
        saveAndExit.setOnAction(e -> onClickedSaveAndExit(e));
        this.gameWindow = gameWindow;
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                World.instance().handleInput(event.getText().charAt(0));
            }
        });
        run();
    }

    @FXML
    void onClickedSaveAndExit(ActionEvent event) {
        String gameName = JOptionPane.showInputDialog(new JFrame(), "Please enter the name of your game.");
        while (World.instance().getListOfSavedGames().indexOf(gameName) != -1) {
            gameName = JOptionPane.showInputDialog(new JFrame(),
                    "That name is taken ... Please enter the name of your game.");
        }
        try {
            World.instance().save("GameBackup.txt", gameName);
        } catch (IOException e) {
            System.out.println(e);
        }
        gameWindow.close();
    }

    public void run() {
        image = new ImageView();
        image.setImage(new Image("/Images/map.png"));
        image.setFitWidth(pane.getWidth());
        image.setFitHeight(pane.getHeight());
        background.getChildren().add(image);

        buttonPane.getChildren().add(saveAndExit);

        score.setX(0);
        score.setY(0);
        waveNum.setX(50);
        waveNum.setY(0);
        pane.getChildren().add(score);
        pane.getChildren().add(waveNum);

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // Pauses the game loop & pops up pause screen
    public void pause() {
        clock.pause();
    }

    public void gameLoop() {

        World.instance().gameLoop();
        ArrayList<GameObject> handledObjects = new ArrayList<>();
        score.setText("Score:" + World.instance().getScore());
        waveNum.setText("Wave: " + World.instance().getCurrentWave());
        for (GameObject object : objects) {
            handledObjects.add(object);
            if (images.containsKey(object)) { // Previously handled
                image = images.get(object);
                image.setX(object.getPosition().getX());
                image.setY(object.getPosition().getY());

                image.setRotate(object.getDirection());
            } else { // Not handled
                image = new ImageView();
                image.setX(object.getPosition().getX());
                image.setY(object.getPosition().getY());

                image.setRotate(object.getDirection());
                image.setImage(object.getImage());
                image.setPreserveRatio(true);
                
                images.put(object, image);
                pane.getChildren().add(image);
            }
        }
        for (Map.Entry<GameObject, ImageView> entry : images.entrySet()) {
            if (!handledObjects.contains(entry.getKey())) {
                pane.getChildren().remove(entry.getValue());
            } else {
                handledObjects.remove(entry.getKey());
            }
        }
        for (GameObject object : handledObjects) {
            images.remove(object);
        }
    }

    public void insertTurret(Tank tank) {
        // causes severe lag and an OutOfMemoryError
        image = new ImageView();
        image.setX(tank.getPosition().getX() + tank.getWidth() / 2);
        image.setY(tank.getPosition().getY() + tank.getHeight() / 2);
        image.setImage(new Image("/Images/cannonfiresprites.gif"));
        image.setPreserveRatio(true);
        image.setRotate(tank.getTurretDirection());
        pane.getChildren().add(image);
    }
}