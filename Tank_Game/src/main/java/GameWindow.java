import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.*;
import model.gameObjects.*;
import java.util.ArrayList;

public class GameWindow {

    @FXML
    Pane pane;

    private final KeyFrame keyFrame = new KeyFrame(Duration.millis(16.67), e -> gameLoop());
    private final Timeline clock = new Timeline(keyFrame);

    private ArrayList<GameObject> objects = World.instance().getListOfEntities();
    private ArrayList<ImageView> images = new ArrayList<>(); // store images so instead of re-creating every frame we
                                                             // adjust their positions.

    void initialize() {
        run();
    }

    public void run() {
        ImageView img = new ImageView();
        img.setImage(new Image("/Images/map.png"));
        pane.getChildren().add(img);
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // Pauses the game loop & pops up pause screen
    public void pause() {
        clock.pause();
    }

    public void gameLoop() {
        World.instance().gameLoop();
        // get inputs somewhere around here
        for (int i = 0; i < objects.size(); ++i) {
            GameObject object = objects.get(i);
            if (images.size() != objects.size()) {
                images.clear();
                for (GameObject obj : objects) {
                    pane.getChildren().clear();
                    ImageView img = new ImageView();
                    img.setImage(new Image("/Images/map.png"));
                    pane.getChildren().add(img);
                    img = new ImageView();

                    img.setX(obj.getPosition().getX());
                    img.setY(obj.getPosition().getY());

                    img.setFitHeight(obj.getHeight());
                    img.setFitWidth(obj.getWidth());
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