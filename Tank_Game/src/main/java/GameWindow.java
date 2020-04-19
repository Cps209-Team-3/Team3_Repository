import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.*;
import model.gameObjects.*;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.Point;

public class GameWindow {

    @FXML
    Pane pane;
    
    @FXML
    Pane background;

    private final KeyFrame keyFrame = new KeyFrame(Duration.millis(16.67), e -> gameLoop());
    private final Timeline clock = new Timeline(keyFrame);
    private Point mouse = new Point();
    private ArrayList<GameObject> objects = World.instance().getListOfEntities();
    private ArrayList<ImageView> images = new ArrayList<>(); // store images so instead of re-creating every frame we
                                                             // adjust their positions.    
    
    // Player Tank Fires (TBF)                                                             
    @FXML
    void onMouseClicked(MouseEvent value) {
        World.instance().handleInput('%');
    }   

    @FXML
    void onMouseMoved(MouseEvent value) {
        //Hand Mouse Coordinates to player tank's head TBF
        mouse.setLocation(value.getX(), value.getY());
    }

    /*@FXML
    void onInputKeyPressed(KeyEvent.KEY_PRESSED event) {
        World.instance().handleInput(event.getCharacter());
         
    }*/

    void initialize() {                
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                World.instance().handleInput(event.getText().charAt(0));
            }
        });
        run();
    }

    public void run() {
        ImageView img = new ImageView();
        img.setImage(new Image("/Images/map.png"));
        img.setPreserveRatio(true);
        background.getChildren().add(img);
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // Pauses the game loop & pops up pause screen
    public void pause() {
        clock.pause();
    }

    public void gameLoop() {
        World.instance().gameLoop();
        pane.getChildren().clear();
        clock.setCycleCount(Timeline.INDEFINITE);
        // get inputs somewhere around here
        for (int i = 0; i < objects.size(); ++i) {
            GameObject object = objects.get(i);
            if (images.size() != objects.size()) {
                images.clear();
                pane.getChildren().clear();
                // ImageView img = new ImageView();
                //     img.setImage(new Image("/Images/map.png"));
                //     pane.getChildren().add(img);
                for (GameObject obj : objects) {
                    ImageView img = new ImageView();

                    img.setX(obj.getPosition().getX());
                    img.setY(obj.getPosition().getY());

                    img.setPreserveRatio(true);
                    img.setRotate(obj.getDirection());

                    img.setImage(obj.getImage());
                    img.setVisible(true);
                    pane.getChildren().add(img);
                }
            } else {
                ImageView img = images.get(i);

                img.setX(object.getPosition().getX());
                img.setY(object.getPosition().getY());

                img.setRotate(object.getDirection());
                pane.getChildren().add(img);
            }
        }
    }
}