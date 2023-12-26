package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlayController {

    public void showBackToMainMenu() {
        GameManager.getInstance().showMainMenu();
    }

}
