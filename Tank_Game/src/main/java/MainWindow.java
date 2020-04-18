import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

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
    Button fourthBtn = new Button("About (Paid DLC: $500K or wait a week)");
    @FXML
    Button fifthBtn = new Button("High Scores");
    @FXML
    Button backBtn = new Button("<- Back");

    // @FXML
    // Image logo = new Image();
    // @FXML
    // Image PTank = new Image();
    // @FXML
    // Image ETank = new Image();
    // @FXML
    // Image PUP = new Image();

    @FXML
    ImageView imgView = new ImageView();

    ArrayList<Button> btns = new ArrayList<Button>();

    Screen screen = Screen.TITLE;

    HelpSlide slide = HelpSlide.Controls;

    @FXML
    public void initialize() {
        MainHbox.getChildren().add(LeftVbox);
        LeftVbox.setPrefWidth(150);
        LeftVbox.setAlignment(Pos.CENTER);

        MainHbox.getChildren().add(MidVbox);
        MidVbox.setPrefWidth(1200);
        MidVbox.setAlignment(Pos.CENTER);
        MidVbox.getChildren().add(imgView);
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
                }

                if (btnClicked.getText().equals("Load Game")) {

                }

                if (btnClicked.getText().equals("Help")) {
                    screen = Screen.HELP;
                    MidVbox.getChildren().removeAll();


                }

                if (btnClicked.getText().equals("About (Paid DLC: $500K or wait a week)")) {
                    screen = Screen.ABOUT;
                }

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                }

                // case DIFF :

                // if ()
        }
    }
}
