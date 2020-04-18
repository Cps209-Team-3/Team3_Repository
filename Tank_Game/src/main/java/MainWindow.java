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

    @FXML
    Image LOGO_IMG = new Image("/images/BlankSlide.png");
    @FXML
    Image PTANK_IMG = new Image("/images/ControlsSlide.png");
    @FXML
    Image ETANK_IMG = new Image("/images/EnemiesSlide.png");
    @FXML
    Image PUP_IMG = new Image("/images/BlankSlide.png");
    @FXML
    Image SCORING_IMG = new Image("/images/ScoringSlide.png");
    @FXML
    Image BLANK_IMG = new Image("/images/BlankSlide.png");

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
            btn.setOnAction(e -> {
                try {
                    onButtonClicked(e);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            btn.setPrefWidth(250);
            btn.setPrefHeight(100);
            btn.setStyle("-fx-font-size: 20pt;");
            MidVbox.getChildren().add(btn);
        }
        backBtn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        });
        leftBtn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        rightBtn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        fourthBtn.setStyle("-fx-font-size: 10pt;"); // REMOVE FOR BETA!!!
    }

    @FXML
    public void onButtonClicked(ActionEvent e) throws IOException {
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
                    imgView.setImage(PTANK_IMG);
                    lbl.setText("");
                    MidVbox.getChildren().removeAll(btns);
                    MidVbox.getChildren().add(slidePic);
                    LeftVbox.getChildren().add(leftBtn);
                    RightVbox.getChildren().add(rightBtn);
                    BHbox.getChildren().add(backBtn);

                    switch (slide) {
                        case CONTROLS:
                            if (btnClicked.getText().equals("<-")) {
                                slide = HelpSlide.SCORING;
                                imgView.setImage(SCORING_IMG);
                            }
                            if (btnClicked.getText().equals("->")) {
                                slide = HelpSlide.ENEMIES;
                                imgView.setImage(ETANK_IMG);
                            }
                            break;
                        case ENEMIES:
                            if (btnClicked.getText().equals("<-")) {
                                slide = HelpSlide.CONTROLS;
                                imgView.setImage(PTANK_IMG);
                            }
                            if (btnClicked.getText().equals("->")) {
                                slide = HelpSlide.POWERUPS;
                                lbl.setText("Not yet Available");
                                imgView.setImage(PUP_IMG);
                            }
                            break;
                        case POWERUPS:
                            if (btnClicked.getText().equals("<-")) {
                                slide = HelpSlide.ENEMIES;
                                imgView.setImage(ETANK_IMG);
                            }
                            if (btnClicked.getText().equals("->")) {
                                slide = HelpSlide.SCORING;
                                lbl.setText("");
                                imgView.setImage(SCORING_IMG);
                            }
                            break;
                        case SCORING:
                            if (btnClicked.getText().equals("<-")) {
                                slide = HelpSlide.POWERUPS;
                                lbl.setText("Not yet Available");
                                imgView.setImage(PUP_IMG);
                            }
                            if (btnClicked.getText().equals("->")) {
                                slide = HelpSlide.CONTROLS;
                                imgView.setImage(SCORING_IMG);
                            }
                            break;
                    }
                }

                if (btnClicked.getText().equals("About (Paid DLC: $500K or wait a week)")) {
                    screen = Screen.ABOUT;
                    // SCREEN DUE FOR BETA!!!
                }

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                    imgView.setImage(LOGO_IMG);
                    MidVbox.getChildren().removeAll(btns);
                    BHbox.getChildren().add(backBtn);
                    ArrayList<PlayerData> scores = scoreList.getHighScores();
                    lbl.setText("TANK ATTACK CHAMPIONS:");
                    lbl.setStyle("-fx-font-size: 32pt;");
                    for (PlayerData player : scores) {
                        Label lbl = new Label(player.getName() + "   " + player.getHighScore());
                        lbl.setStyle("-fx-font-size: 24pt;");
                        MidVbox.getChildren().add(lbl);
                    }
                }

                break;

            case DIFF:

                if (btnClicked.getText().equals("<- Back")) {
                    screen = Screen.TITLE;
                    btns.get(0).setText("New Game");
                    btns.get(1).setText("Load Game");
                    btns.get(2).setText("Help");
                    MidVbox.getChildren().addAll(btns.get(3), btns.get(4));
                    BHbox.getChildren().remove(backBtn);
                }
                if (btnClicked.getText().equals("Easy") || btnClicked.getText().equals("Medium")
                        || btnClicked.getText().equals("Hard")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

                    Stage gameWindow = new Stage();
                    gameWindow.setScene(new Scene(loader.load()));
                    GameWindow window = loader.getController();
                    window.initialize();

                    gameWindow.show();
                }

                break;

            case HELP:

                if (btnClicked.getText().equals("<- Back")) {
                    screen = Screen.TITLE;
                    imgView.setImage(LOGO_IMG);
                    lbl.setText("Welcome!");
                    MidVbox.getChildren().addAll(btns);
                    LeftVbox.getChildren().remove(leftBtn);
                    RightVbox.getChildren().remove(rightBtn);
                    BHbox.getChildren().remove(backBtn);
                }

                break;

            // case ABOUT :
            // if

            case HIGHSCORES:

                if (btnClicked.getText().equals("<- Back")) {
                    screen = Screen.TITLE;
                    imgView.setImage(LOGO_IMG);
                    lbl.setText("Welcome!");
                    lbl.setStyle("-fx-font-size: 28pt;");
                    MidVbox.getChildren().addAll(btns);
                    BHbox.getChildren().remove(backBtn);
                }

                break;

        }
    }
}
