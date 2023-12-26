package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameManager class
 * Manages Scene loading and background tasks
 */
public class GameManager {
    private final Map<String, Scene> sceneLibrary = new HashMap<>();
    private final Stage stageRoot;
    private static MediaPlayer backgroundMusic;

    private static GameManager gameManager;

    private GameManager(Stage stageRoot) {
        this.stageRoot = stageRoot;
    }

    public static GameManager getInstance() {
        return getInstance(null);
    }

    public static GameManager getInstance(Stage stageRoot) {
        if (gameManager == null) gameManager = new GameManager(stageRoot);
        return gameManager;
    }

    /**
     * Exposes the background media player for volume control, muting etc.
     *
     * @return MediaPlayer
     */
    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    /**
     * Plays background media (music) in a loop.
     *
     * @param media media to be played in the background
     */
    public static void playBackgroundMusic(Media media) {
        if (backgroundMusic != null) {
            backgroundMusic.setMute(true);
        }
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setOnEndOfMedia(() -> playBackgroundMusic(backgroundMusic.getMedia()));
        backgroundMusic.play();
    }

    /**
     * Stops the background media player.
     */
    public static void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    /**
     * displays the main menu
     * if the main menu has been initialized before, it will be used again
     */
    public void showMainMenu() {
        Scene mainMenu = sceneLibrary.get("mainMenu");
        if (mainMenu == null) {
            //load main menu if not present
            FXMLLoader fxmlLoader = new FXMLLoader(Sleepwalker.class.getResource("ui/MainMenu.fxml"));
            try {
                mainMenu = new Scene(fxmlLoader.load(), GameProperties.WIDTH, GameProperties.HEIGHT);
                sceneLibrary.put("mainMenu", mainMenu);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        stageRoot.setScene(mainMenu);
        stageRoot.show();
    }
    public void showHowToPlay() {
        Scene tutorialScene;
        FXMLLoader fxmlLoader = new FXMLLoader(Sleepwalker.class.getResource("ui/HowToPlay.fxml"));
        try {
            tutorialScene = new Scene(fxmlLoader.load(), GameProperties.WIDTH, GameProperties.HEIGHT);
            stageRoot.setScene(tutorialScene);
            stageRoot.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * displays the world map
     * if the world map has been initialized before, the same world map will be displayed
     * to prevent multiple world maps existing at once
     */
    public void showWorldMap() {
        Scene worldMap = sceneLibrary.get("worldMap");
        if (worldMap == null) {
            //load world map if not present
            //TODO: implement proper World Map
            Button backToMainMenu = new Button("Back to Main Menu");
            backToMainMenu.setLayoutX(GameProperties.WIDTH - 200);
            backToMainMenu.setLayoutY(GameProperties.HEIGHT - 100);
            backToMainMenu.setOnAction(t -> getInstance().showMainMenu()); //Look Mum, I'm using lambdas!

            Button loadLevel1 = new Button("Level 1"); //TODO: automatically generate Buttons based on level number
            loadLevel1.setLayoutX(100);
            loadLevel1.setLayoutY(100);
            loadLevel1.setOnAction(t -> getInstance().loadLevel(1)); //lambdas again

            Pane x = new AnchorPane(backToMainMenu, loadLevel1);
            worldMap = new Scene(x, GameProperties.WIDTH, GameProperties.HEIGHT);
            sceneLibrary.put("worldMap", worldMap);
        }
        stageRoot.setScene(worldMap);
        stageRoot.show();
    }

    public void loadLevel(int levelId) {
        LevelManager lm = new LevelManager();
        stopBackgroundMusic();
        stageRoot.setScene(lm.loadLevel(levelId));
        stageRoot.show();
        try {
            lm.startLevel();
        } catch (LevelNotLoadedException e) {
            //TODO: figure out what to do
            throw new RuntimeException(e);
        }
    }
}