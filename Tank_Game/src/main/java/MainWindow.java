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
    Label champs = new Label("TANK ATTACK CHAMPIONS:");
    @FXML
    Label credits = new Label("Credits:");
    @FXML
    Label easyLbl = new Label("Easy");
    @FXML
    Label medLbl = new Label("Medium");
    @FXML
    Label hardLbl = new Label("Hard");

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
    Button exitBtn = new Button("Exit");
    @FXML
    Button easyBtn = new Button("Easy");
    @FXML
    Button medBtn = new Button("Medium");
    @FXML
    Button hardBtn = new Button("Hard");
    @FXML
    Button backBtn = new Button("<- Back");
    @FXML
    Button leftBtn = new Button("<-");
    @FXML
    Button rightBtn = new Button("->");
    @FXML
    Button leftEasySlideBtn = new Button("<- Easy");
    @FXML
    Button rightEasySlideBtn = new Button("Easy ->");
    @FXML
    Button leftMediumSlideBtn = new Button("<- Medium");
    @FXML
    Button rightMediumSlideBtn = new Button("Medium ->");
    @FXML
    Button leftHardSlideBtn = new Button("<- Hard");
    @FXML
    Button rightHardSlideBtn = new Button("Hard ->");

    final Image LOGO_GIF = new Image("/Images/Logo.gif");
    final Image PTANK_IMG = new Image("/Images/ControlsSlide.png");
    final Image ETANK_IMG = new Image("/Images/EnemiesSlide.png");
    final Image PUP_IMG = new Image("/Images/PowerupsSlide.png");
    final Image SCORING_IMG = new Image("/Images/ScoringSlide.png");

    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/selectsfx.wav").toString());

    @FXML
    ImageView imgView = new ImageView();
    @FXML
    ImageView slidePic = new ImageView();

    ArrayList<Button> btns = new ArrayList<Button>();

    Screen screen = Screen.TITLE;
    HelpSlide slide = HelpSlide.CONTROLS;
    HighScoreSlides scoreSlide = HighScoreSlides.EASY;

    HighScores scoreList = HighScores.scoreList();

    private Stage mainWindow;

    @FXML
    public void initialize(Stage stage) throws Exception {
        this.mainWindow = stage;
        World.reset();

        MainHbox.getChildren().add(LeftVbox);
        LeftVbox.setPrefWidth(150);
        LeftVbox.setAlignment(Pos.CENTER);

        MainHbox.getChildren().add(MidVbox);
        MidVbox.setPrefWidth(1200);
        MidVbox.setAlignment(Pos.CENTER);
        imgView.setImage(LOGO_GIF);
        MidVbox.getChildren().add(imgView);
        lbl.setStyle("-fx-font-size: 32pt;");
        MidVbox.getChildren().add(lbl);

        MainHbox.getChildren().add(RightVbox);
        RightVbox.setPrefWidth(150);
        RightVbox.setAlignment(Pos.CENTER);

        btns.add(firstBtn);
        btns.add(secondBtn);
        btns.add(thirdBtn);
        btns.add(fourthBtn);
        btns.add(fifthBtn);
        btns.add(exitBtn);
        btns.add(easyBtn);
        btns.add(medBtn);
        btns.add(hardBtn);

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

        MidVbox.getChildren().addAll(firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn, exitBtn);
    }

    @FXML
    void loadGameButtonPressed(ActionEvent event, String gameName) {
        try {
            World.reset();
            World.instance().load("GameBackup.txt", gameName);
            loadGame();
            resetTitle();
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
        MidVbox.getChildren().addAll(imgView, lbl, firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn, exitBtn);
    }

    public void loadGame() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

        Stage gameWindow = new Stage();
        gameWindow.setScene(new Scene(loader.load()));
        GameWindow window = loader.getController();
        window.initialize(gameWindow, this);
        
        gameWindow.show();
        mainWindow.close();
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
                    MidVbox.getChildren().addAll(difLbl, easyBtn, medBtn, hardBtn);
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
                    BHbox.getChildren().add(backBtn);
                    credits.setStyle("-fx-font-size: 28pt;");
                    Label lbl1 = new Label("David Disler - Save/Load game functionality & Powerups");
                    lbl1.getStyleClass().add("about");
                    Label lbl2 = new Label("Andrew James - Game Logic and Game Screen");
                    lbl2.getStyleClass().add("about");
                    Label lbl3 = new Label("Austin Pennington - Game Logic, Art Design, and Audio Design");
                    lbl3.getStyleClass().add("about");
                    Label lbl4 = new Label("Brandon Swain - Menus and Save/Load High Scores");
                    lbl4.getStyleClass().add("about");
                    Label lbl5 = new Label("Music: Floating Cities by Kevin MacLeod");
                    lbl5.getStyleClass().add("about");
                    Label lbl6 = new Label("Link: https://incompetech.filmmusic.io/song/3765-floating-cities");
                    lbl6.getStyleClass().add("about");
                    Label lbl7 = new Label("License: http://creativecommons.org/licenses/by/4.0/");
                    lbl7.getStyleClass().add("about");
                    MidVbox.getChildren().addAll(credits, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7);
                }

                if (btnClicked.getText().equals("High Scores")) {  // REDO THIS FOR DIFFICULTY SORTED SCORES!!!
                    screen = Screen.HIGHSCORES;
                    scoreList.load();
                    MidVbox.getChildren().clear();
                    MidVbox.getChildren().add(champs);
                    LeftVbox.getChildren().add(leftHardSlideBtn);
                    RightVbox.getChildren().add(rightMediumSlideBtn);
                    MidVbox.setStyle("-fx-font-size: 32pt;");
                    Label lines = new Label("___________________________________________");
                    MidVbox.getChildren().add(lines);
                    MidVbox.getChildren().add(easyLbl);
                    BHbox.getChildren().add(backBtn);
                    ArrayList<PlayerData> scores = scoreList.getHighScores();
                    for (PlayerData player : scores) {
                        Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                        lbl.setStyle("-fx-font-size: 24pt;");
                        MidVbox.getChildren().add(lbl);
                    }
                }

                if (btnClicked.getText().equals("Exit")) {
                    mainWindow.close();
                }

                break;

            case DIFF:

                if (btnClicked.getText().equals("<- Back")) {
                    resetTitle();
                }
                World.reset();
                if (btnClicked.getText().equals("Easy")) {
                    World.instance().setDifficulty(Difficulty.EASY);
                    loadGame();
                    resetTitle();
                } else if (btnClicked.getText().equals("Medium")) {
                    World.instance().setDifficulty(Difficulty.MEDIUM);
                    loadGame();
                    resetTitle();
                } else if (btnClicked.getText().equals("Hard")) {
                    World.instance().setDifficulty(Difficulty.HARD);
                    loadGame();
                    resetTitle();
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

                if (btnClicked.getText().equals("<- Back")) {
                    resetTitle();
                }
                break;
        }
    }
}
