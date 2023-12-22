package at.ac.fhcampuswien.sleepwalker;

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
    private static final Map<String, Scene> sceneLibrary = new HashMap<>();
    public static Stage stageRoot;
    private static MediaPlayer backgroundMusic;

    /**
     * Exposes the background media player for volume control, muting etc.
     * @return MediaPlayer
     */
    public static MediaPlayer getBackgroundMusic(){
        return backgroundMusic;
    }

    /**
     * Plays background media (music) in a loop.
     *
     * @param media media to be played in the background
     */
    public static void playBackgroundMusic(Media media){
        if(backgroundMusic != null){
            backgroundMusic.setMute(true);
        }
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setOnEndOfMedia(() -> playBackgroundMusic(backgroundMusic.getMedia()));
        backgroundMusic.play();
    }

    /**
     * Stops the background media player.
     */
    public static void stopBackgroundMusic(){
        backgroundMusic.stop();
    }

    /**
     * displays the main menu
     * if the main menu has been initialized before, it will be used again
     */
    public static void showMainMenu(){
        Scene mainMenu = sceneLibrary.get("mainMenu");
        if(mainMenu == null){
            //load main menu if not present
            FXMLLoader fxmlLoader = new FXMLLoader(Sleepwalker.class.getResource("ui/MainMenu.fxml"));
            try{
                mainMenu = new Scene(fxmlLoader.load(), GameProperties.WIDTH, GameProperties.HEIGHT);
                sceneLibrary.put("mainMenu",mainMenu);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        stageRoot.setScene(mainMenu);
        stageRoot.show();
    }

    /**
     * displays the world map
     * if the world map has been initialized before, the same world map will be displayed
     * to prevent multiple world maps existing at once
     */
    public static void showWorldMap(){
        Scene worldMap = sceneLibrary.get("worldMap");
        if(worldMap == null){
            //load world map if not present
            //TODO: implement proper World Map
            Button backToMainMenu = new Button("Back to Main Menu");
            Pane x = new AnchorPane(backToMainMenu);
            backToMainMenu.setOnAction(t -> GameManager.showMainMenu()); //Look Mum, I'm using Lambdas!
            worldMap = new Scene(x, GameProperties.WIDTH, GameProperties.HEIGHT);
            sceneLibrary.put("worldMap",worldMap);
        }
        stageRoot.setScene(worldMap);
        stageRoot.show();
    }
}
