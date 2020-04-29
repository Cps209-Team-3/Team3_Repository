//-----------------------------------------------------------------
// File:   EndWindow.Java
// Author: Brandon Swain
// Desc:   This Window is displayed after a player finishes a game 
//         and asks the player for his/her title.
//-----------------------------------------------------------------

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.*;
import model.HighScores;
import model.World;

public class EndWindow {

    // This Audio file is a beep that plays when a button is pressed.
    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/selectsfx.wav").toString());

    private Stage endWindow; // The EndWindow Screen itself.

    @FXML
    VBox vbox; // The FXML VBox that holds all the contents of the EndWindow Screen.

    @FXML
    Label scoreLbl = new Label("You Scored " + (int) World.instance().getScore() + " Points"); // Label that displays
                                                                                               // the final score the
                                                                                               // player earned.
    @FXML
    Label qLbl = new Label("What is your Name, Warrior?"); // Label to ask the question "What is your Name, Warrior?"

    @FXML
    TextField nameTxt = new TextField(); // The TextField where the Player inputs his/her name.

    @FXML
    Button btn = new Button("Enter"); // Button to finalize the Player's name.

    /**
     * Initializes the EndWindow display and passes in the window itself to close
     * itself.
     * 
     * @param endWindow
     */
    @FXML
    public void initialize(Stage endWindow) {
        this.endWindow = endWindow;
        scoreLbl.setStyle("-fx-font-size: 28pt;");
        qLbl.setStyle("-fx-font-size: 24pt;");
        nameTxt.setPrefWidth(70);
        nameTxt.setPrefHeight(30);
        nameTxt.setPromptText("Enter Name Here");
        nameTxt.setStyle("-fx-font-size: 16pt;");
        btn.setPrefWidth(100);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 16pt;");
        btn.isDefaultButton();
        btn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        vbox.getChildren().addAll(scoreLbl, qLbl, nameTxt, btn);
        nameTxt.requestFocus();
    }

    /**
     * Takes an event (e) to indicate the button has been clicked. It saves the
     * Player's name and high score to the list of high scores. The EndWindow is then
     * closed after the button is clicked.
     * 
     * @param e
     * @throws Exception
     */
    @FXML
    public void onButtonClicked(ActionEvent e) throws Exception {
        AUDIO_BEEP.play();
        String name = nameTxt.getText();
        HighScores.scoreList().addHighScore(name, World.instance().getScore(), World.instance().getDiffString());
        endWindow.close();
        World.reset();
        HighScores.scoreList().save();
        HighScores.scoreList().getAllHighScores().clear();
        HighScores.scoreList().getEasyHighScores().clear();
        HighScores.scoreList().getMediumHighScores().clear();
        HighScores.scoreList().getHardHighScores().clear();

        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Stage mainWindow = new Stage();
        mainWindow.setScene(new Scene(loader.load()));
        MainWindow window = loader.getController();
        window.initialize(mainWindow);
        mainWindow.show();
    }
}