//---------------------------------------------------------------------
// File:    MainWindow.java
// Authors: Brandon Swain, David Disler
// Desc:    This class is used to display the Title screen for the game
//          and to navigate to other screens available in the menus.
//---------------------------------------------------------------------

import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.stage.*;
import model.*;
import model.enums.*;

public class MainWindow {

    @FXML
    HBox MainHbox; // This is the HBox that all the FXML layouts are placed into to make up the
                   // menu screens.

    // Below are the VBoxes that help design the layout of the menu screens.
    @FXML
    VBox LeftVbox = new VBox();
    @FXML
    VBox MidVbox = new VBox(10);
    @FXML
    VBox RightVbox = new VBox();

    @FXML
    HBox BHbox; // This HBox hold the back button in the menu screens.

    // Below are the text labels displayed on the various menu screens.
    @FXML
    Label champs = new Label("TANK ATTACK CHAMPIONS");
    @FXML
    Label credits = new Label("Credits:");
    @FXML
    Label easyLbl = new Label("Easy:");
    @FXML
    Label medLbl = new Label("Medium:");
    @FXML
    Label hardLbl = new Label("Hard:");
    @FXML
    Label line = new Label("___________________________________________");

    // Below are the various buttons used to navigate the menus.
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

    // Below are the images used for the menu screens.
    final Image LOGO_GIF = new Image("/Images/title.png");
    final Image PTANK_IMG = new Image("/Images/ControlsSlide.jpg");
    final Image ETANK_IMG = new Image("/Images/EnemiesSlide.jpg");
    final Image PUP_IMG = new Image("/Images/PowerupsSlide.jpg");
    final Image SCORING_IMG = new Image("/Images/ScoringSlide.jpg");

    // This Audio file is a beep that plays when a button is pressed.
    final AudioClip AUDIO_BEEP = new AudioClip(getClass().getResource("/Media/selectsfx.wav").toString());

    // Below are the ImageViews that hold the images in the Title Screen and the
    // Help Screen.
    @FXML
    ImageView imgView = new ImageView();
    @FXML
    ImageView slidePic = new ImageView();

    // Below are the arraylists of buttons sorted by how the buttons must be
    // formatted.
    ArrayList<Button> btns = new ArrayList<Button>();
    ArrayList<Button> btns2 = new ArrayList<Button>();

    // Below are the initialized enums for the Main Screens, the Slides used for the
    // Help Screen, and the Slides that sort the High Scores by difficulty.
    Screen screen = Screen.TITLE;
    HelpSlide slide = HelpSlide.CONTROLS;
    HighScoreSlides scoreSlide = HighScoreSlides.EASY;

    private Stage mainWindow; // The MainWindow Screen itself.

    /**
     * Swain, Brandon - Takes the MainWindow Stage to allow the class to close
     * itself. Intializes the Title Screen and sets up the formatting of all
     * buttons.
     * 
     * @param stage
     * @throws Exception
     */
    @FXML
    public void initialize(Stage stage) throws Exception {
        this.mainWindow = stage;
        World.reset();
        HighScores.scoreList().load();

        MainHbox.getChildren().add(LeftVbox);
        LeftVbox.setPrefWidth(100);
        LeftVbox.setAlignment(Pos.CENTER);

        MainHbox.getChildren().add(MidVbox);
        MidVbox.setPrefWidth(1240);
        MidVbox.setAlignment(Pos.CENTER);
        imgView.setImage(LOGO_GIF);
        MidVbox.getChildren().add(imgView);

        MainHbox.getChildren().add(RightVbox);
        RightVbox.setPrefWidth(100);
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

        btns2.add(backBtn);
        btns2.add(leftBtn);
        btns2.add(rightBtn);
        btns2.add(leftEasySlideBtn);
        btns2.add(rightEasySlideBtn);
        btns2.add(leftMediumSlideBtn);
        btns2.add(rightMediumSlideBtn);
        btns2.add(leftHardSlideBtn);
        btns2.add(rightHardSlideBtn);

        // Designs the Title Menu buttons to format size and apply the onButtonClicked()
        // method.
        for (Button btn : btns) {
            btn.setOnAction(e -> {
                try {
                    onButtonClicked(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            btn.setPrefWidth(250);
            btn.setPrefHeight(90);
            btn.setStyle("-fx-font-size: 20pt;");
        }

        // Applies the onButtonClicked method to all other buttons.
        for (Button btn : btns2) {
            btn.setOnAction(e -> {
                try {
                    onButtonClicked(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }

        MidVbox.getChildren().addAll(firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn, exitBtn);
    }

    /**
     * Disler, David - Resets the World and loads up the game from the
     * "GameBackup.txt"
     * 
     * @param event    - The button clicked event
     * @param gameName - The name of the game
     */
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

    // Calls the delete method and gives it the name of the game to delete
    @FXML
    void deleteGameButtonPressed(ActionEvent event, String gameName) {
        deleteSavedGame(gameName);
    }

    /**
     * Disler, David - Deletes the game that has the name gameName
     *
     * @param gameName - The name of the game to be deleted
     */
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

    /**
     * Disler, David - Sets up the Load Screen with all of the saved games, if there
     * over ten, it deletes them.
     * 
     */
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

    /**
     * Swain, Brandon - Resets the MainWindow GUI to the Title Screen.
     */
    @FXML
    public void resetTitle() {
        screen = Screen.TITLE;
        slide = HelpSlide.CONTROLS;
        scoreSlide = HighScoreSlides.EASY;
        MidVbox.getChildren().clear();
        BHbox.getChildren().clear();
        RightVbox.getChildren().clear();
        LeftVbox.getChildren().clear();
        imgView.setImage(LOGO_GIF);
        MidVbox.getChildren().addAll(imgView, firstBtn, secondBtn, thirdBtn, fourthBtn, fifthBtn, exitBtn);
    }

    /**
     * Disler, David - Sets up a new GameWindow
     * 
     */
    public void loadGame() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));

        Stage gameWindow = new Stage();
        gameWindow.setScene(new Scene(loader.load()));
        GameWindow window = loader.getController();
        window.initialize(gameWindow, this);

        gameWindow.show();
        mainWindow.close();
    }

    /**
     * Takes an event (e) to indicate the button clicked on the menus. Allows the
     * user to navigate the menus and close the screen by clicking on the buttons
     * available.
     * 
     * @param e
     * @throws Exception
     */
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
                    Label lbl1 = new Label("David Disler - Save/Load game functionality, Pause Menu & Powerups");
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

                if (btnClicked.getText().equals("High Scores")) {
                    screen = Screen.HIGHSCORES;
                    MidVbox.getChildren().clear();
                    if (HighScores.scoreList().getAllHighScores() == null) {
                        Label noScores = new Label("No High Scores Yet");
                        noScores.setStyle("-fx-font-size: 32pt;");
                        MidVbox.getChildren().add(noScores);
                        BHbox.getChildren().add(backBtn);
                    } else {
                        champs.setStyle("-fx-font-size: 32pt;");
                        MidVbox.getChildren().add(champs);
                        easyLbl.setStyle("-fx-font-size: 28pt;");
                        MidVbox.getChildren().add(easyLbl);
                        LeftVbox.getChildren().add(leftHardSlideBtn);
                        RightVbox.getChildren().add(rightMediumSlideBtn);
                        line.setStyle("-fx-font-size: 18pt;");
                        MidVbox.getChildren().add(line);
                        BHbox.getChildren().add(backBtn);
                        ArrayList<PlayerData> scores = HighScores.scoreList().getEasyHighScores();
                        for (PlayerData player : scores) {
                            Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                            lbl.setStyle("-fx-font-size: 24pt;");
                            MidVbox.getChildren().add(lbl);
                        }
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

                switch (scoreSlide) {
                    case EASY:
                        if (btnClicked.getText().equals("<- Hard")) {
                            scoreSlide = HighScoreSlides.HARD;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            hardLbl.setStyle("-fx-font-size: 28pt;");
                            MidVbox.getChildren().add(hardLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftMediumSlideBtn);
                            RightVbox.getChildren().add(rightEasySlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getHardHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("Medium ->")) {
                            scoreSlide = HighScoreSlides.MEDIUM;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            medLbl.setStyle("-fx-font-size: 28pt;");
                            MidVbox.getChildren().add(medLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftEasySlideBtn);
                            RightVbox.getChildren().add(rightHardSlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getMediumHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
                        }
                        break;

                    case MEDIUM:
                        if (btnClicked.getText().equals("<- Easy")) {
                            scoreSlide = HighScoreSlides.EASY;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            MidVbox.getChildren().add(easyLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftHardSlideBtn);
                            RightVbox.getChildren().add(rightMediumSlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getEasyHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("Hard ->")) {
                            scoreSlide = HighScoreSlides.HARD;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            hardLbl.setStyle("-fx-font-size: 28pt;");
                            MidVbox.getChildren().add(hardLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftMediumSlideBtn);
                            RightVbox.getChildren().add(rightEasySlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getHardHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
                        }
                        break;

                    case HARD:
                        if (btnClicked.getText().equals("<- Medium")) {
                            scoreSlide = HighScoreSlides.MEDIUM;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            medLbl.setStyle("-fx-font-size: 28pt;");
                            MidVbox.getChildren().add(medLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftEasySlideBtn);
                            RightVbox.getChildren().add(rightHardSlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getMediumHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("Easy ->")) {
                            scoreSlide = HighScoreSlides.EASY;
                            MidVbox.getChildren().clear();
                            LeftVbox.getChildren().clear();
                            RightVbox.getChildren().clear();

                            MidVbox.getChildren().add(champs);
                            MidVbox.getChildren().add(easyLbl);
                            MidVbox.getChildren().add(line);
                            LeftVbox.getChildren().add(leftHardSlideBtn);
                            RightVbox.getChildren().add(rightMediumSlideBtn);
                            ArrayList<PlayerData> scores = HighScores.scoreList().getEasyHighScores();
                            for (PlayerData player : scores) {
                                Label lbl = new Label(player.getName() + " - " + (int) player.getHighScore());
                                lbl.setStyle("-fx-font-size: 24pt;");
                                MidVbox.getChildren().add(lbl);
                            }
                        }

                        if (btnClicked.getText().equals("<- Back")) {
                            resetTitle();
                        }
                        break;
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
