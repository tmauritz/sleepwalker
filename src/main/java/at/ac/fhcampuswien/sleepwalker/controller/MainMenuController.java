package at.ac.fhcampuswien.sleepwalker.controller;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    /**
     * fixes layout of the main menu
     */
    private void formatMenu(){
        title.setText(GameProperties.TITLE);
        description.setText(GameProperties.DESCRIPTION);
        //TODO: format menu
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO: outsource media loading into MediaManager class to better separate GUI and background tasks
        Media mainTheme = new Media(Sleepwalker.class.getResource("audio/maintheme.mp3").toString()); //TODO: fix null pointer possibility
        Image background = new Image(Sleepwalker.class.getResource("img/placeholder_image1.jpg").toString()); //TODO: fix null pointer possibility
        formatMenu();
        backgroundView.setImage(background);
        GameManager.playBackgroundMusic(mainTheme);
    }

    /**
     * Shows the world map
     */
    public void onPlayButtonClick(){
        //switch to WorldMap Scene
        GameManager.showWorldMap();
    }

    /**
     * Exits the game.
     */
    public void onExitButtonClick(){
        Platform.exit();
    }

    public void onToggleMusic(ActionEvent actionEvent){
        MediaPlayer music = GameManager.getBackgroundMusic();
        if(music.isMute()){
            music.setMute(false);
            musicToggle.setText("Mute");
        } else{
            music.setMute(true);
            musicToggle.setText("Unmute");
        }
    }
}