import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.*;
import model.HighScores;
import model.PlayerData;
import model.World;

public class EndWindow {

    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/beep-7.wav").toString());

    private Stage endWindow;

    @FXML
    VBox vbox;

    @FXML
    Label scoreLbl = new Label("You Scored " + World.instance().getScore() + " Points");
    @FXML
    Label qLbl = new Label("What is your Name, Warrior?");

    @FXML
    TextField nameTxt = new TextField();

    @FXML
    Button btn = new Button("Enter");


    @FXML
    public void initialize() {
        scoreLbl.setStyle("-fx-font-size: 28pt;");
        qLbl.setStyle("-fx-font-size: 24pt;");
        nameTxt.setPrefWidth(100);
        nameTxt.setPrefHeight(30);
        nameTxt.setPromptText("Name");
        nameTxt.setStyle("-fx-font-size: 16pt;");
        btn.setPrefWidth(50);
        btn.setPrefHeight(20);
        btn.setStyle("-fx-font-size: 14pt;");
        btn.setDisable(true);
        btn.setOnAction(e -> onButtonClicked(e));

        vbox.getChildren().addAll(scoreLbl, qLbl, nameTxt, btn);
    }



    @FXML
    public void onButtonClicked(ActionEvent e) {
        AUDIO_BEEP.play();
        String name = nameTxt.getText();
        HighScores.scoreList().addHighScore(name, World.instance().getScore());
        endWindow.close();
    }

}