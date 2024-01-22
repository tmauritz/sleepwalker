package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class SettingsController {

    @FXML
    private Slider musicVolumeSlider;

    @FXML
    private void initialize() {

        musicVolumeSlider.setValue(MediaManager.getMusicVolume());


        musicVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MediaManager.setMusicVolume(newValue.doubleValue());
        });
    }

    public void handleBackToMainMenu() {
        GameManager.getInstance().showMainMenu();
    }
}

