import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.*;
import model.HighScores;
import model.World;

public class EndWindow {

    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/selectsfx.wav").toString());

    private Stage endWindow;

    @FXML
    VBox vbox;

    @FXML
    Label scoreLbl = new Label("You Scored " + (int)World.instance().getScore() + " Points");
    @FXML
    Label qLbl = new Label("What is your Name, Warrior?");

    @FXML
    TextField nameTxt = new TextField();

    @FXML
    Button btn = new Button("Enter");

    @FXML
    public void initialize(Stage stage) {
        this.endWindow = stage;
        scoreLbl.setStyle("-fx-font-size: 28pt;");
        qLbl.setStyle("-fx-font-size: 24pt;");
        nameTxt.setPrefWidth(70);
        nameTxt.setPrefHeight(30);
        nameTxt.setPromptText("Enter Name Here");
        nameTxt.setStyle("-fx-font-size: 16pt;");
        btn.setPrefWidth(100);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 16pt;");
        btn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        vbox.getChildren().addAll(scoreLbl, qLbl, nameTxt, btn);
    }

    @FXML
    public void onButtonClicked(ActionEvent e) throws Exception {
        AUDIO_BEEP.play();
        String name = nameTxt.getText();
        HighScores.scoreList().addHighScore(name, World.instance().getScore());
        HighScores.scoreList().save();
        endWindow.close();
        World.reset();
    }

}