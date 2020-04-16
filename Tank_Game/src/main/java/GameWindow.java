import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import model.*;
import model.gameObjects.*;
import java.util.ArrayList;

public class GameWindow {

    @FXML
    Pane pane;

    private final KeyFrame keyFrame = new KeyFrame(Duration.seconds(1 / 60), e -> gameLoop());
    private final Timeline clock = new Timeline(keyFrame);

    private ArrayList<GameObject> objects = World.instance().getListOfEntities();
    private ArrayList<ImageView> images = new ArrayList<>(); // store images so instead of re-creating every frame we
                                                             // adjust their positions.

    public void run() {
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
        // get inputs somewhere around here
        for (GameObject object : objects) {
            // had idea to move images instead of re-creating every frame, currently
            // unimplemented.
            if (object instanceof Tank || object instanceof Bullet) {
                ImageView img = new ImageView();

                img.setX(object.getPosition().getX());
                img.setY(object.getPosition().getY());

                img.setFitHeight(object.getHeight());
                img.setFitWidth(object.getWidth());
                img.setRotate(object.getDirection());

                img.setImage(object.getImage());
                pane.getChildren().add(img);
            }
        }
        for (GameObject object : objects) {
            if (object instanceof Wall) {
                ImageView img = new ImageView();

                img.setX(object.getPosition().getX());
                img.setY(object.getPosition().getY());

                img.setFitHeight(object.getHeight());
                img.setFitWidth(object.getWidth());
                img.setRotate(object.getDirection());

                img.setImage(object.getImage());
                pane.getChildren().add(img);
            }
        }
    }
}