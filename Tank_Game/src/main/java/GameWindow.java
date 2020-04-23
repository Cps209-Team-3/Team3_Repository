import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private ImageView image;
    private Text score = new Text("Score: 0");
    private Text waveNum = new Text("Wave: ");
    private MainWindow mainWindow = null;

    // Player Tank Fires (TBF)
    @FXML
    void onMouseClicked(MouseEvent value) {
        World.instance().handleInput('%');
    }

    @FXML
    void onMouseMoved(MouseEvent value) {
        // Hand Mouse Coordinates to player tank's head TBF
        mouse.setLocation(value.getX(), value.getY());
        // The image is a temporary replacement until Austin makes one.
        var newCursor = new ImageCursor(new Image("/Images/ControlsSlide.png"));
        pane.setCursor(newCursor);
    }

    /*
     * @FXML void onInputKeyPressed(KeyEvent.KEY_PRESSED event) {
     * World.instance().handleInput(event.getCharacter());
     * 
     * }
     */

    void initialize(Stage gameWindow, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.gameWindow = gameWindow;
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleInput(event);
            }
        });
        run();
    }


    public void run() {
        image = new ImageView();
        image.setImage(new Image("/Images/map.png"));
        image.setFitWidth(pane.getWidth());
        image.setFitHeight(pane.getHeight());
        background.getChildren().add(image);


        score.setX(0);
        score.setY(0);
        waveNum.setX(50);
        waveNum.setY(0);
        pane.getChildren().add(score);
        pane.getChildren().add(waveNum);

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
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

    // Pause Window
    // It can resume gameplay, exit the gamewindow,
    // or save and exit the gamewindow
    void pauseGame() {
    // Pauses the game loop & pops up pause screen
        clock.pause();
        

        
        
        gameWindow.setAlwaysOnTop(false);
        Object[] buttonTexts = {"Resume",
                            "Exit",
                            "Save and Exit"};

        JOptionPane optionPane = new JOptionPane();
        int choice = JOptionPane.showOptionDialog(null,
            "You have paused the game.",
            "Paused",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            buttonTexts,
            buttonTexts[0]
            );

        switch (choice) {
            case 0:
                clock.play();
                break;
            case 2:
                String gameName = null;
                while (true) {
                    JOptionPane nameGame = new JOptionPane();
                    gameName = JOptionPane.showInputDialog(nameGame, "Please enter the name of your game.");
                    if (World.instance().getListOfSavedGames().indexOf(gameName) != -1 && gameName != null) {
                        JOptionPane nameTaken = new JOptionPane();
                        int answer = JOptionPane.showConfirmDialog(nameTaken, "That name is taken ... would you like to override it?");
                        if (answer == 0) {
                            mainWindow.deleteSavedGame(gameName);
                            break;
                        }

                    } else {
                        break;
                    }
                }
                try {
                    if (gameName != null) {
                        World.instance().save("GameBackup.txt", gameName);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                

            case 1: 
                gameWindow.close();
                break;
        }
        gameWindow.setAlwaysOnTop(true);
    }

    void handleInput(KeyEvent key) {
        if (key.getCode() == KeyCode.ESCAPE) {
            pauseGame();
        } else {
            World.instance().handleInput(key.getText().charAt(0));
        }
    }
}