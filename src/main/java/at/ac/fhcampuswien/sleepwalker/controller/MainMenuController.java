package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Menu Controller
 * FXML: MainMenu.xml
 */
public class MainMenuController implements Initializable {
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private ImageView backgroundView;
    @FXML
    private Button playButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button musicToggle;
    @FXML
    private Button howToPlayButton;
    @FXML
    private Button settingsButton;

    /**
     * Formats the main Menu at runtime.
     */
    private void formatMenu() {
        description.setText(GameProperties.DESCRIPTION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image background = MediaManager.loadImage("level/background/background_layer_2.png");

        formatMenu();
        if (background != null) {
            backgroundView.setImage(background);
        }
    }

    /**
     * Shows the world map
     */
    public void onPlayButtonClick() {
        //switch to WorldMap Scene
        GameManager.getInstance().showWorldMap();
    }
    public void showSettingsButton(ActionEvent actionEvent) {
        GameManager.getInstance().showSettings();
    }

    /**
     * Exits the game.
     */
    public void onExitButtonClick() {
        Platform.exit();
    }

    public void onToggleMusic(ActionEvent actionEvent) {
        //TODO: Options Menu
    }

    public void showHowToPlay(ActionEvent actionEvent) {
        GameManager.getInstance().showHowToPlay();
    }
}