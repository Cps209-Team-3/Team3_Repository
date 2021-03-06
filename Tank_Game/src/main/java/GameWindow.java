
//-----------------------------------------------------------
//File:   GameWindow.java
//Author: Andrew James, Austin Pennington, Brandon Swain, David disler.
//Desc:   The main game window to show the game.
//----------------------------------------------------------- 
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
import model.HighScores;
import model.World;
import model.gameObjects.Bullet;
import model.gameObjects.GameObject;
import model.gameObjects.Tank;

public class GameWindow {

    @FXML
    VBox vbox; // The main VBox

    @FXML
    Pane pane; // The game pane

    @FXML
    Pane background; // The pane for the background image

    @FXML
    Pane buttonPane; // unused

    private final KeyFrame keyFrame = new KeyFrame(Duration.millis(16.67), e -> {
        try {
            gameLoop();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }); // Keyframe to run 60 times a second
    private final Timeline clock = new Timeline(keyFrame); // timeline to run keyFrame

    private Point mouse = new Point(); // The current mouses position

    private ArrayList<GameObject> objects = World.instance().getEntities(); // The current list of World's entities
    private Map<GameObject, ArrayList<ImageView>> tiedImages = new HashMap<>(); // List of game objects tied with their
                                                                                // images
    private ArrayList<ImageView> images; // Images to tie to a new game object

    final AudioClip AUDIO_MUSIC = new AudioClip(getClass().getResource("/Media/music.mp3").toString()); // game music
    final AudioClip AUDIO_AMBIENT = new AudioClip(getClass().getResource("/Media/wind.wav").toString()); // game ambient
                                                                                                         // sounds

    private Stage gameWindow; // The current game window
    private ImageView image; // The current image to work on
    private Text score = new Text("Score: 0"); // The current score
    private Text waveNum = new Text("Wave: "); // The current wave
    private MainWindow mainWindow = null; // the current main window
    private ImageCursor newCursor = new ImageCursor(new Image("/Images/cursor2.png")); // This is the image for the
                                                                                       // custom curser - Disler, David
    private boolean isPaused = false; // true if game is paused, false otherwise.

    private ArrayList<KeyEvent> keys = new ArrayList<>(); // list of inputs from the player.

    // On mouse clicked, make the player tank fire.
    @FXML
    void onMouseClicked(MouseEvent value) {
        World.instance().handleInput('%', keys);
    }

    // Set the players turretdirection to point at mouse when it moves.
    @FXML
    void onMouseMoved(MouseEvent value) {
        // Hand Mouse Coordinates to player tank's head
        mouse.setLocation(value.getX(), value.getY());
        gameWindow.getScene().setCursor(newCursor);

        Point playerPosition = World.instance().getPlayerTank().getPosition();
        double y2 = value.getY() - playerPosition.getY();
        double x2 = value.getX() - playerPosition.getX();
        World.instance().getPlayerTank().setTurretDirection((int) Math.toDegrees(Math.atan2(y2, x2)));
    }

    // initializes the game window.
    void initialize(Stage gameWindow, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.gameWindow = gameWindow;
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            // Handle the user pressing a key
            // @param event - the key event to be processed
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ESCAPE) {
                    if (keys.size() == 0) {
                        if (event.getCode().toString().equals("W") || event.getCode().toString().equals("A")
                                || event.getCode().toString().equals("S") || event.getCode().toString().equals("D")) {
                            keys.add(event);
                        }
                    } else {
                        boolean contains = false;
                        for (int i = 0; i < keys.size(); i++) {
                            if (event.getCode().toString().equals("W") || event.getCode().toString().equals("A")
                                    || event.getCode().toString().equals("S")
                                    || event.getCode().toString().equals("D")) {
                                if (keys.get(i).getText().charAt(0) == event.getText().charAt(0)) {
                                    contains = true;
                                }
                            }
                        }
                        if (contains == false) {
                            if (event.getCode().toString().equals("W") || event.getCode().toString().equals("A")
                                    || event.getCode().toString().equals("S")
                                    || event.getCode().toString().equals("D")) {
                                keys.add(event);
                            }
                        }
                    }
                }
            }
        });
        pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            // Handle the user releasing a key
            // @param event - the key event to be processed
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    // Executes pause game when Escape is pressed
                    if (!isPaused) {
                        try {
                            pauseGame();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isPaused = true;
                    } else {
                        JOptionPane.getRootFrame().dispose();
                        clock.play();
                        isPaused = false;
                    }

                } else {
                    for (int i = 0; i < keys.size(); i++) {
                        if (event.getCode() == keys.get(i).getCode()) {
                            keys.remove(keys.get(i));
                        }
                    }
                }
            }
        });
        AUDIO_AMBIENT.setCycleCount(Timeline.INDEFINITE);
        AUDIO_MUSIC.setCycleCount(Timeline.INDEFINITE);
        AUDIO_AMBIENT.setPriority(10);
        AUDIO_MUSIC.setPriority(10);
        AUDIO_AMBIENT.play(0.1);
        AUDIO_MUSIC.play(0.5);
        run();
    }

    // Start the timeline and begin the game.
    public void run() {
        image = new ImageView();
        image.setImage(new Image("/Images/map.png"));
        image.setFitWidth(pane.getWidth());
        image.setFitHeight(pane.getHeight());
        background.getChildren().add(image);

        score.setX(45);
        score.setY(-350);
        score.setFill(Color.PINK);
        score.setScaleX(2);
        score.setScaleY(2);
        waveNum.setX(1350);
        waveNum.setY(-350);
        waveNum.setScaleX(2);
        waveNum.setScaleY(2);
        waveNum.setFill(Color.PINK);
        pane.getChildren().add(score);
        pane.getChildren().add(waveNum);

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // Main game loop to run every frame.
    public void gameLoop() throws Exception {
        World.instance().gameLoop();
        ArrayList<GameObject> handledObjects = new ArrayList<>();
        score.setText("Score: " + (int) World.instance().getScore());
        waveNum.setText("Wave: " + (World.instance().getCurrentWave()));
        World.instance().handleInput('F', keys);
        for (GameObject object : objects) {
            handledObjects.add(object);
            images = new ArrayList<>();
            if (tiedImages.containsKey(object)) { // Previously handled
                image = tiedImages.get(object).get(0);
                image.setX(object.getPosition().getX());
                image.setY(object.getPosition().getY());
                images.add(image);
                if (object instanceof Bullet) {
                    if (!object.getImage().equals(image.getImage())) {
                        image.setImage(object.getImage());
                        image.setX(object.getPosition().getX() - 50);
                        image.setY(object.getPosition().getY() - 50);
                    } else if (object.getImage().getUrl().endsWith("explosion.gif")) {
                        image.setX(object.getPosition().getX() - 50);
                        image.setY(object.getPosition().getY() - 50);
                    }
                    image.setRotate(object.getDirection() + 90);
                } else if (object instanceof Tank) {
                    Tank tank = (Tank) object;
                    image.setRotate(object.getDirection());
                    Bounds bound = image.getBoundsInLocal();
                    image = tiedImages.get(object).get(1);
                    if (!tank.getTurretImage().equals(image.getImage())) {
                        image.setImage(tank.getTurretImage());
                    }
                    image.setY(bound.getCenterY() - 100);
                    image.setX(bound.getCenterX() - 20);
                    image.setRotate(tank.getTurretDirection() + 90);

                    image = tiedImages.get(object).get(2);
                    image.setY(bound.getCenterY() - 10);
                    image.setX(bound.getCenterX() - 10);
                    if (tank.getHealth() > 10) {
                        image.setImage(new Image("/Images/NumberImages/11.png"));
                    } else {
                        if (tank.getHealth() > 0) {
                            image.setImage(new Image("/Images/NumberImages/" + tank.getHealth() + ".png"));
                        }
                    }
                } else {
                    image.setRotate(object.getDirection());
                }
            } else { // Not handled
                image = new ImageView();
                image.setX(object.getPosition().getX());
                image.setY(object.getPosition().getY());
                image.setImage(object.getImage());
                image.setPreserveRatio(true);
                images.add(image);
                pane.getChildren().add(image);
                if (object instanceof Bullet) {
                    image.setRotate(object.getDirection() + 90);
                } else if (object instanceof Tank) {
                    Tank tank = (Tank) object;
                    image.setRotate(object.getDirection());

                    Bounds bound = image.getBoundsInLocal();
                    image = new ImageView();
                    image.setX(bound.getCenterX() - 20);
                    image.setY(bound.getCenterY() - 100);
                    image.setImage(tank.getTurretImage());
                    image.setPreserveRatio(true);
                    image.setRotate(tank.getTurretDirection() + 90);
                    images.add(image);
                    pane.getChildren().add(image);

                    image = new ImageView();
                    image.setX(bound.getCenterX() - 10);
                    image.setY(bound.getCenterY() - 10);
                    if (tank.getHealth() > 10) {
                        image.setImage(new Image("/Images/NumberImages/11.png"));
                    } else {
                        if (tank.getHealth() > 0) {
                            image.setImage(new Image("/Images/NumberImages/" + tank.getHealth() + ".png"));
                        }
                    }
                    image.setPreserveRatio(true);
                    images.add(image);
                    pane.getChildren().add(image);
                } else {
                    image.setRotate(object.getDirection());
                }
                tiedImages.put(object, images);
            }
        }
        for (Map.Entry<GameObject, ArrayList<ImageView>> entry : tiedImages.entrySet()) {
            if (!handledObjects.contains(entry.getKey())) {
                pane.getChildren().remove(entry.getValue().get(0));
                if (entry.getValue().size() > 2) {
                    pane.getChildren().remove(entry.getValue().get(1));
                    pane.getChildren().remove(entry.getValue().get(2));
                } else if (entry.getValue().size() > 1) {
                    pane.getChildren().remove(entry.getValue().get(1));
                }
            } else {
                handledObjects.remove(entry.getKey());
            }
        }
        for (GameObject object : handledObjects) {
            tiedImages.remove(object);
        }
        // When the player dies
        if (!World.instance().getEntities().contains(World.instance().getPlayerTank())) {
            AUDIO_AMBIENT.stop();
            AUDIO_MUSIC.stop();
            gameWindow.close();
            clock.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EndWindow.fxml"));
            Stage endWindow = new Stage();
            endWindow.setScene(new Scene(loader.load()));
            EndWindow window = loader.getController();
            window.initialize(endWindow);
            endWindow.show();
        }
    }

    /**
     * Disler, David - Pauses the game, contains the buttons: "Resume", "Cheat",
     * "Exit", and "Save and Exit"
     * 
     * @param data - The string to be split
     * @throws Exception
     */
    void pauseGame() throws Exception {
        // Pauses the game loop & pops up pause screen
        clock.pause();
        // Allows the pause menu to appear
        gameWindow.setAlwaysOnTop(false);
        Object[] buttonTexts = { "Resume", "Cheat", "Exit", "Save and Exit" };
        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));

        int choice = JOptionPane.showOptionDialog(null, "You have paused the game.", "Paused",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttonTexts, buttonTexts[0]);

        switch (choice) {
            case 0: // This is the resume button
                clock.play();
                break;
            case 1: // This is the cheat button, it toggles the cheat mode
                World.instance().toggleCheatMode();
                clock.play();
                break;
            case 2: // This exits the GameWindow and brings up the MainWindow
                HighScores.scoreList().save();
                HighScores.scoreList().getAllHighScores().clear();
                HighScores.scoreList().getEasyHighScores().clear();
                HighScores.scoreList().getMediumHighScores().clear();
                HighScores.scoreList().getHardHighScores().clear();

                AUDIO_AMBIENT.stop();
                AUDIO_MUSIC.stop();
                gameWindow.close();
                try {
                    Stage mW = new Stage();
                    mW.setScene(new Scene(loader.load()));
                    MainWindow win = loader.getController();
                    win.initialize(mW);
                    mW.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 3: // Saves and then Exits the GameWindow
                String gameName = null;

                HighScores.scoreList().save();
                HighScores.scoreList().getAllHighScores().clear();
                HighScores.scoreList().getEasyHighScores().clear();
                HighScores.scoreList().getMediumHighScores().clear();
                HighScores.scoreList().getHardHighScores().clear();
                
                while (true) {
                    JOptionPane nameGame = new JOptionPane();
                    gameName = JOptionPane.showInputDialog(nameGame, "Please enter the name of your game.");
                    if (World.instance().getSavedGames().indexOf(gameName) != -1 && gameName != null) {
                        JOptionPane nameTaken = new JOptionPane();
                        int answer = JOptionPane.showConfirmDialog(nameTaken,
                                "That name is taken ... would you like to override it?");
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
                AUDIO_AMBIENT.stop();
                AUDIO_MUSIC.stop();
                gameWindow.close();
                try {
                    Stage mW = new Stage();
                    mW.setScene(new Scene(loader.load()));
                    MainWindow win = loader.getController();
                    win.initialize(mW);
                    mW.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        // Makes the GameWindow on top of MainWindow
        gameWindow.setAlwaysOnTop(true);
    }
}