import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.HighScores;
import model.PlayerData;
import model.World;
import model.enums.Difficulty;

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
    Label lbl = new Label("TANK ATTACK ARENA");

    @FXML
    Button firstBtn = new Button("New Game");
    @FXML
    Button secondBtn = new Button("Load Game");
    @FXML
    Button thirdBtn = new Button("Help");
    @FXML
    Button fourthBtn = new Button("About");
    @FXML
    Button fifthBtn = new Button("High Scores");
    @FXML
    Button backBtn = new Button("<- Back");
    @FXML
    Button leftBtn = new Button("<-");
    @FXML
    Button rightBtn = new Button("->");

    @FXML
    Image LOGO_IMG = new Image("/Images/Logo.png");
    @FXML
    Image PTANK_IMG = new Image("/Images/ControlsSlide.png");
    @FXML
    Image ETANK_IMG = new Image("/Images/EnemiesSlide.png");
    @FXML
    Image PUP_IMG = new Image("/Images/PowerupsSlide.png");  // Edit url for Beta
    @FXML
    Image SCORING_IMG = new Image("/Images/ScoringSlide.png");
    // @FXML
    // Image BLANK_IMG = new Image("/Images/BlankSlide.png");  // Not sure we will need this

    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/beep-7.wav").toString());

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
    public void initialize() throws Exception {
        World.reset();

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
                } catch (Exception e1) {
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
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        leftBtn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rightBtn.setOnAction(e -> {
            try {
                onButtonClicked(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    @FXML
    void loadGameButtonPressed(ActionEvent event, String gameName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
            Stage gameWindow = new Stage();
            gameWindow.setScene(new Scene(loader.load()));
            GameWindow window = loader.getController();
            window.initialize(gameWindow, this);
            gameWindow.show();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void deleteGameButtonPressed(ActionEvent event, String gameName) {
        deleteSavedGame(gameName);
    }

    public void deleteSavedGame(String gameName) {
        // Copies GameBackup into GameBackup1 without the information that we are deleting
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameBackup1.txt"))) {
            BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"));
            boolean flag = false;
            String line = reader.readLine();
            while (line != null) {
                if (flag == true && line.contains("##")) {
                    flag = false;
                }
                if (line.contains(gameName)) {
                    flag = true;
                }
                if (!flag) {
                    writer.write(line + "\n");
                }
                line = reader.readLine();
            }
            reader.close();
                
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Copies GameBackup1 into GameBackup
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameBackup.txt"))) {
            BufferedReader reader = new BufferedReader(new FileReader("GameBackup1.txt"));
            String line = reader.readLine();

            while (line != null) {
                writer.write(line + "\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        World.instance().getListOfSavedGames().remove(World.instance().getListOfSavedGames().indexOf(gameName));
        setupLoadPage();
    }

    void setupLoadPage() {
        screen = Screen.LOAD;
        MidVbox.getChildren().clear();
        BHbox.getChildren().clear();
        BHbox.getChildren().add(backBtn);

        Label title = new Label("Saved Games");
        title.setStyle("-fx-font-size: 28pt;");
        MidVbox.getChildren().add(title);
        
        ArrayList<String> list = World.instance().getListOfSavedGames();
        if (list.size() > 0) {
            for (String savedGame : list) {
                HBox hbox = new HBox();
                Button delete = new Button("Delete Saved Game");
                Label label = new Label(savedGame);
                Button load = new Button("Load Saved Game");
                label.setStyle("-fx-font-size: 14pt;");
                load.setStyle("-fx-font-size: 14pt;");
                load.setOnAction(i -> loadGameButtonPressed(i, label.getText()));
                delete.setStyle("-fx-font-size: 14pt");
                delete.setOnAction(i -> deleteGameButtonPressed(i, label.getText()));
                hbox.setStyle("-fx-alignment: center; -fx-spacing: 15");
                hbox.getChildren().addAll(delete, load, label);
                MidVbox.getChildren().add(hbox);
            }
        } else {
            Label label = new Label("You have no saved games.");
            label.setStyle("-fx-font-size: 16");
            MidVbox.getChildren().add(label);
        }
    }


    @FXML
    public void onButtonClicked(ActionEvent e) throws Exception {
        Button btnClicked = (Button) e.getSource();
        AUDIO_BEEP.play();

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
                    setupLoadPage();
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
                }

                if (btnClicked.getText().equals("About")) {
                    screen = Screen.ABOUT;
                    // SCREEN DUE FOR BETA!!!
                }

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                    scoreList.load();
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
                    lbl.setText("TANK ATTACK ARENA");
                    btns.get(0).setText("New Game");
                    btns.get(1).setText("Load Game");
                    btns.get(2).setText("Help");
                    MidVbox.getChildren().addAll(btns.get(3), btns.get(4));
                    BHbox.getChildren().remove(backBtn);
                }
                if (btnClicked.getText().equals("Easy") || btnClicked.getText().equals("Medium")
                        || btnClicked.getText().equals("Hard")) {
                    World.instance().setDifficulty(Difficulty.EASY);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

                    Stage gameWindow = new Stage();
                    gameWindow.setScene(new Scene(loader.load()));
                    GameWindow window = loader.getController();
                    window.initialize(gameWindow, this);
                    gameWindow.show();
                    
                    screen = Screen.TITLE;
                    MidVbox.getChildren().clear();
                    BHbox.getChildren().clear();
                    lbl.setText("TANK ATTACK ARENA");
                    btns.get(0).setText("New Game");
                    btns.get(1).setText("Load Game");
                    btns.get(2).setText("Help");
                    MidVbox.getChildren().addAll(lbl, firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn);
                }

                break;

            case HELP:

                if (btnClicked.getText().equals("<- Back")) {
                    screen = Screen.TITLE;
                    imgView.setImage(LOGO_IMG);
                    lbl.setText("TANK ATTACK ARENA");
                    MidVbox.getChildren().addAll(btns);
                    LeftVbox.getChildren().remove(leftBtn);
                    RightVbox.getChildren().remove(rightBtn);
                    BHbox.getChildren().remove(backBtn);
                }

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
                            imgView.setImage(SCORING_IMG);
                        }
                        break;
                    case SCORING:
                        if (btnClicked.getText().equals("<-")) {
                            slide = HelpSlide.POWERUPS;
                            imgView.setImage(PUP_IMG);
                        }
                        if (btnClicked.getText().equals("->")) {
                            slide = HelpSlide.CONTROLS;
                            imgView.setImage(PTANK_IMG);
                        }
                        break;
                }

                break;

            // case ABOUT :
            // if

            

            case HIGHSCORES:

                if (btnClicked.getText().equals("<- Back")) {
                    screen = Screen.TITLE;
                    imgView.setImage(LOGO_IMG);
                    lbl.setText("TANK ATTACK ARENA");
                    lbl.setStyle("-fx-font-size: 28pt;");
                    MidVbox.getChildren().addAll(btns);
                    BHbox.getChildren().remove(backBtn);
                }

                break;

            case LOAD:
                screen = Screen.TITLE;
                MidVbox.getChildren().clear();
                BHbox.getChildren().clear();
                MidVbox.getChildren().addAll(lbl, firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn);
        }
    }
}
