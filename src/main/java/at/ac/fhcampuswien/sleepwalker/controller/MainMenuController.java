package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Menu Controller
 * FXML: MainMenu.xml
 */
public class MainMenuController implements Initializable {
    @FXML
    private Label description;
    @FXML
    private ImageView backgroundView;

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
    public void showSettingsButton() {
        GameManager.getInstance().showSettings();
    }

    /**
     * Exits the game.
     */
    public void onExitButtonClick() {
        Platform.exit();
    }

    public void showHowToPlay() {
        GameManager.getInstance().showHowToPlay();
    }
}