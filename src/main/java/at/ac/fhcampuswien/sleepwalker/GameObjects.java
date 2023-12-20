package at.ac.fhcampuswien.sleepwalker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameObjects {
    private static Map<String, Scene> sceneLibrary = new HashMap<>();
    public static Stage stageRoot;
    private static MediaPlayer backgroundMusic;

    public static MediaPlayer getBackgroundMusic(){
        return backgroundMusic;
    }

    /**
     * Plays background media (music).
     * @param media media to be played in the background
     */
    public static void playBackgroundMusic(Media media){
        if(backgroundMusic != null){
            backgroundMusic.setMute(true);
        }
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.play();
    }

    public static void addToLibrary(Scene scene, String name){
        sceneLibrary.put(name, scene);
    }

    public static Scene getScene(String name){
        return sceneLibrary.get(name);
    }

    public static void loadMainMenu(){
        Stage primaryStage = GameObjects.stageRoot;
        //initialize Main Menu
        FXMLLoader fxmlLoader = new FXMLLoader(Sleepwalker.class.getResource("ui/MainMenu.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), GameProperties.WIDTH, GameProperties.HEIGHT);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        String windowTitle = GameProperties.TITLE + " v" + GameProperties.GAME_VERSION;
        primaryStage.setTitle(windowTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadWorldMap(){
        Stage worldMap = new WorldMap();
        worldMap.show();
    }
}
