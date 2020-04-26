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
    Button easyBtn = new Button("Easy");
    @FXML
    Button medBtn = new Button("Medium");
    @FXML
    Button hardBtn = new Button("Hard");
    @FXML
    Button cheatBtn = new Button("Cheat Mode");
    @FXML
    Button backBtn = new Button("<- Back");
    @FXML
    Button leftBtn = new Button("<-");
    @FXML
    Button rightBtn = new Button("->");

    @FXML
    Image LOGO_GIF = new Image("/Images/Logo.gif");
    @FXML
    Image PTANK_IMG = new Image("/Images/ControlsSlide.png");
    @FXML
    Image ETANK_IMG = new Image("/Images/EnemiesSlide.png");
    @FXML
    Image PUP_IMG = new Image("/Images/PowerupsSlide.png");
    @FXML
    Image SCORING_IMG = new Image("/Images/ScoringSlide.png");

    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/selectsfx.wav").toString());

    @FXML
    ImageView imgView = new ImageView();
    @FXML
    ImageView slidePic = new ImageView();

    ArrayList<Button> btns = new ArrayList<Button>();

    Screen screen = Screen.TITLE;

    HelpSlide slide = HelpSlide.CONTROLS;

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
        imgView.setImage(LOGO_GIF);
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
        btns.add(easyBtn);
        btns.add(medBtn);
        btns.add(hardBtn);
        btns.add(cheatBtn);

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

        MidVbox.getChildren().addAll(firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn);
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void deleteGameButtonPressed(ActionEvent event, String gameName) {
        deleteSavedGame(gameName);
    }

    public void deleteSavedGame(String gameName) {
        // Copies GameBackup into GameBackup1 without the information that we are
        // deleting
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameBackup1.txt"))) {
            BufferedReader reader = new BufferedReader(new FileReader("GameBackup.txt"));
            boolean flag = false;
            String line = reader.readLine();
            while (line != null) {
                if (flag == true && line.contains("##")) {
                    flag = false;
                }
                if (line.contains(gameName) && line.contains("##")) {
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
        World.instance().getSavedGames().remove(World.instance().getSavedGames().indexOf(gameName));
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

        ArrayList<String> list = World.instance().getSavedGames();
        if (list.size() > 0) {
            if (list.size() <= 10) {
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
                deleteSavedGame(list.get(list.size() - 1));
            }

        } else {
            Label label = new Label("You have no saved games.");
            label.setStyle("-fx-font-size: 16");
            MidVbox.getChildren().add(label);
        }
    }

    @FXML
    public void resetTitle() {
        screen = Screen.TITLE;
        MidVbox.getChildren().clear();
        BHbox.getChildren().clear();
        RightVbox.getChildren().clear();
        LeftVbox.getChildren().clear();
        imgView.setImage(LOGO_GIF);
        MidVbox.getChildren().addAll(imgView, lbl, firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn);
    }

    public void loadGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

        Stage gameWindow = new Stage();
        gameWindow.setScene(new Scene(loader.load()));
        GameWindow window = loader.getController();
        window.initialize(gameWindow, this);
        gameWindow.show();
    }

    @FXML
    public void onButtonClicked(ActionEvent e) throws Exception {
        Button btnClicked = (Button) e.getSource();
        AUDIO_BEEP.play();

        switch (screen) {
            case TITLE:

                if (btnClicked.getText().equals("New Game")) {
                    screen = Screen.DIFF;
                    MidVbox.getChildren().clear();
                    Label difLbl = new Label("Difficulty");
                    difLbl.setStyle("-fx-font-size: 28pt;");
                    MidVbox.getChildren().addAll(difLbl, easyBtn, medBtn, hardBtn, cheatBtn);
                    BHbox.getChildren().add(backBtn);
                }

                if (btnClicked.getText().equals("Load Game")) {
                    setupLoadPage();
                }

                if (btnClicked.getText().equals("Help")) {
                    screen = Screen.HELP;
                    imgView.setImage(PTANK_IMG);
                    MidVbox.getChildren().remove(lbl);
                    MidVbox.getChildren().removeAll(btns);
                    MidVbox.getChildren().add(slidePic);
                    LeftVbox.getChildren().add(leftBtn);
                    RightVbox.getChildren().add(rightBtn);
                    BHbox.getChildren().add(backBtn);
                }

                if (btnClicked.getText().equals("About")) {
                    screen = Screen.ABOUT;
                    MidVbox.getChildren().clear();
                    Label credits = new Label("Credits");
                    credits.setStyle("-fx-font-size: 28pt;");
                    Label lbl1 = new Label("David Disler - Save/Load game functionality & Powerups");
                    lbl1.setStyle("-fx-font-size: 20pt;");
                    Label lbl2 = new Label("Andrew James - Game Logic and Game Screen");
                    lbl2.setStyle("-fx-font-size: 20pt;");
                    Label lbl3 = new Label("Austin Pennington - Game Logic and Art Design");
                    lbl3.setStyle("-fx-font-size: 20pt;");
                    Label lbl4 = new Label("Brandon Swain - Menus and Save/Load High Scores");
                    lbl4.setStyle("-fx-font-size: 20pt;");
                    MidVbox.getChildren().addAll(lbl1, lbl2, lbl3, lbl4);
                }

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                    scoreList.load();
                    imgView.setImage(LOGO_GIF);
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
                    resetTitle();
                }
                if (btnClicked.getText().equals("Easy")) {
                    World.reset();
                    World.instance().setDifficulty(Difficulty.EASY);
                    // loadGame();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EndWindow.fxml"));
                    Stage endWindow = new Stage();
                    endWindow.setScene(new Scene(loader.load()));
                    endWindow.show();

                    resetTitle();
                }
                if (btnClicked.getText().equals("Medium")) {
                    World.reset();
                    World.instance().setDifficulty(Difficulty.MEDIUM);
                    loadGame();
                    resetTitle();
                }
                if (btnClicked.getText().equals("Hard")) {
                    World.reset();
                    World.instance().setDifficulty(Difficulty.HARD);
                    loadGame();
                    resetTitle();
                }
                if (btnClicked.getText().equals("Cheat Mode")) {
                    World.reset();
                    loadGame();
                    resetTitle();
                    // Add Cheat Code!!!
                }

                break;

            case HELP:

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
                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
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
                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
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
                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
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
                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
                        }
                        break;
                }

                break;

            case ABOUT:
                if (btnClicked.getText().equals("<- Back")) {
                    resetTitle();
                }
                break;

            case HIGHSCORES:

                if (btnClicked.getText().equals("<- Back")) {
                    resetTitle();
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
