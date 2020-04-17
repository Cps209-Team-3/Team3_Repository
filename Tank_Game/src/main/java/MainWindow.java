import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;


public class MainWindow {

    @FXML VBox vbox;

    @FXML Label lbl = new Label("Welcome!");

    @FXML Button firstBtn = new Button("New Game");

    @FXML Button secondBtn = new Button("Load Game");

    @FXML Button thirdBtn = new Button("Help");

    @FXML Button fourthBtn = new Button("About (Paid DLC: $500K or wait a week)");

    @FXML Button fifthBtn = new Button("High Scores");

    @FXML Button backBtn = new Button("<- Back");

    // @FXML Image title = new Image();

    @FXML ImageView imgView = new ImageView();

    ArrayList<Button> btns = new ArrayList<Button>();

    Screen screen = Screen.TITLE;

    @FXML
    public void initialize() {

        vbox.getChildren().add(imgView);
        vbox.getChildren().add(lbl);

        btns.add(firstBtn);
        btns.add(secondBtn);
        btns.add(thirdBtn);
        btns.add(fourthBtn);
        btns.add(fifthBtn);

        for (Button btn : btns) {
            btn.setOnAction(e -> onButtonClicked(e));
            btn.setPrefWidth(250);
            btn.setPrefHeight(100);
            vbox.getChildren().add(btn);
        }
    }

    @FXML
    public void onButtonClicked(ActionEvent e) {
        Button btnClicked = (Button) e.getSource();

        switch(screen) {
            case TITLE :


            if (btnClicked.getText().equals("New Game")) {
                screen = Screen.DIFF;
                btns.get(0).setText("Easy");
                btns.get(0).setText("Medium");
                btns.get(0).setText("Hard");
            }

            if (btnClicked.getText().equals("Load Game")) {

            }

            if (btnClicked.getText().equals("")) {

            }

            if (btnClicked.getText().equals("New Game")) {

            }

            if (btnClicked.getText().equals("New Game")) {

            }
        }
    }
}
