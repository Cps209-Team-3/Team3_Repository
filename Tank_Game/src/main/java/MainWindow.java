import java.io.IOException;
import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow {

    @FXML
    HBox MainHbox;

    @FXML
    VBox LeftVbox = new VBox();
    @FXML
    VBox MidVbox = new VBox(10);
    @FXML
    VBox RightVbox = new VBox();

    @FXML
    HBox BHbox;

    @FXML
    Label lbl = new Label("Welcome!");

    @FXML
    Button firstBtn = new Button("New Game");
    @FXML
    Button secondBtn = new Button("Load Game");
    @FXML
    Button thirdBtn = new Button("Help");
    @FXML
    Button fourthBtn = new Button("About (Paid DLC: $500K or wait a week)"); // REMOVE () FOR BETA!!!
    @FXML
    Button fifthBtn = new Button("High Scores");
    @FXML
    Button backBtn = new Button("<- Back");
    @FXML
    Button leftBtn = new Button("<-");
    @FXML
    Button rightBtn = new Button("->");

    // @FXML
    // Image LOGO = new Image();
    // @FXML
    // Image PTANK = new Image();
    // @FXML
    // Image ETANK = new Image();
    // @FXML
    // Image PUP = new Image();

    @FXML
    ImageView imgView = new ImageView();
    @FXML
    ImageView slidePic = new ImageView();

    ArrayList<Button> btns = new ArrayList<Button>();

    Screen screen = Screen.TITLE;

    HelpSlide slide = HelpSlide.CONTROLS;

    World world = World.instance();

    HighScores scoreList = HighScores.scoreList();

    

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

        Stage gameWindow = new Stage();
        gameWindow.setScene(new Scene(loader.load()));
        GameWindow window = loader.getController();
        window.initialize(gameWindow.getScene());

        gameWindow.show();
        MainHbox.getChildren().add(LeftVbox);
        LeftVbox.setPrefWidth(150);
        LeftVbox.setAlignment(Pos.CENTER);

        MainHbox.getChildren().add(MidVbox);
        MidVbox.setPrefWidth(1200);
        MidVbox.setAlignment(Pos.CENTER);
        MidVbox.getChildren().add(imgView);
        lbl.setStyle("-fx-font-size: 28pt;");
        MidVbox.getChildren().add(lbl);

        MainHbox.getChildren().add(RightVbox);
        RightVbox.setPrefWidth(150);
        RightVbox.setAlignment(Pos.CENTER);

        btns.add(firstBtn);
        btns.add(secondBtn);
        btns.add(thirdBtn);
        btns.add(fourthBtn);
        btns.add(fifthBtn);

        for (Button btn : btns) {
            btn.setOnAction(e -> onButtonClicked(e));
            btn.setPrefWidth(250);
            btn.setPrefHeight(100);
            btn.setStyle("-fx-font-size: 20pt;");
            MidVbox.getChildren().add(btn);
        }
        backBtn.setOnAction(e -> onButtonClicked(e));
        leftBtn.setOnAction(e -> onButtonClicked(e));
        rightBtn.setOnAction(e -> onButtonClicked(e));

        fourthBtn.setStyle("-fx-font-size: 10pt;"); // REMOVE FOR BETA!!!
    }

    @FXML
    public void onButtonClicked(ActionEvent e) {
        Button btnClicked = (Button) e.getSource();

        switch (screen) {
            case TITLE:

                if (btnClicked.getText().equals("New Game")) {
                    screen = Screen.DIFF;
                    lbl.setText("Difficulty");
                    btns.get(0).setText("Easy");
                    btns.get(1).setText("Medium");
                    btns.get(2).setText("Hard");
                    MidVbox.getChildren().removeAll(btns.get(3), btns.get(4));
                    BHbox.getChildren().add(backBtn);
                }

                if (btnClicked.getText().equals("Load Game")) {

                    // world.load(); ASK DAVID!!! (Not due for Alpha)

                }

                if (btnClicked.getText().equals("Help")) {
                    screen = Screen.HELP;
                    MidVbox.getChildren().remove(imgView);
                    MidVbox.getChildren().remove(lbl);
                    MidVbox.getChildren().removeAll(btns);
                    MidVbox.getChildren().add(slidePic);
                    LeftVbox.getChildren().add(leftBtn);
                    RightVbox.getChildren().add(rightBtn);
                    BHbox.getChildren().add(backBtn);

                    // switch (slide) {
                    // case CONTROLS :

                    // }
                }

                if (btnClicked.getText().equals("About (Paid DLC: $500K or wait a week)")) {
                    screen = Screen.ABOUT;
                    // SCREEN DUE FOR BETA!!!
                }

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                    BHbox.getChildren().add(backBtn);
                    ArrayList<PlayerData> scores = scoreList.getHighScores();
                    Label title = new Label("TANK ATTACK CHAMPIONS:");
                    title.setStyle("-fx-font-size: 30pt;");
                    MidVbox.getChildren().add(title);
                    for (PlayerData player : scores) {
                        Label lbl = new Label(player.getName() + "   " + player.getHighScore());
                        lbl.setStyle("-fx-font-size: 24pt;");
                        MidVbox.getChildren().add(lbl);
                    }
                }

                break;

                // case DIFF :

                // if ()
        }
    }
}
