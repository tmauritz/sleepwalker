package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameManager class
 * Manages Scene loading and background tasks
 */
public class GameManager {
    private static final Map<String, Scene> sceneLibrary = new HashMap<>();
    private final Stage stageRoot;
    private static GameManager gameManager;
    private final LevelManager levelManager;

    private GameManager(Stage stageRoot) {
        this.stageRoot = stageRoot;
        levelManager = new LevelManager();
    }

    public static GameManager getInstance() {
        return getInstance(null);
    }

    public static GameManager getInstance(Stage stageRoot) {
        if (gameManager == null) gameManager = new GameManager(stageRoot);
        return gameManager;
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
        MediaManager.playMusic("audio/maintheme.mpeg");
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
    public void showSettings() {
        Scene settingsScene;
        FXMLLoader fxmlLoader = new FXMLLoader(Sleepwalker.class.getResource("ui/Settings.fxml"));
        try {
            settingsScene = new Scene(fxmlLoader.load(), GameProperties.WIDTH, GameProperties.HEIGHT);
            stageRoot.setScene(settingsScene);
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
            ImageView background = new ImageView(MediaManager.loadImage(GameProperties.BACKGROUND_IMAGE_PATH));
            background.setFitWidth(GameProperties.WIDTH);
            background.setFitHeight(GameProperties.HEIGHT);

            Button backToMainMenu = new Button("Back to Main Menu");
            backToMainMenu.getStyleClass().add("button");
            backToMainMenu.setLayoutX(GameProperties.WIDTH - 200);
            backToMainMenu.setLayoutY(GameProperties.HEIGHT - 100);
            backToMainMenu.setOnAction(t -> getInstance().showMainMenu()); //Look Mum, I'm using lambdas!

            Button loadLevel1 = new Button("Level 1"); // TODO: automatically generate Buttons based on level number
            loadLevel1.getStyleClass().add("button");
            loadLevel1.setLayoutX(100);
            loadLevel1.setLayoutY(100);
            loadLevel1.setOnAction(t -> getInstance().playLevel(1));//lambdas again

            Button loadLevel2 = new Button("Level 2");
            loadLevel2.getStyleClass().add("button");
            loadLevel2.setLayoutX(200);
            loadLevel2.setLayoutY(100);
            loadLevel2.setOnAction(e -> getInstance().playLevel(2));

            Button loadLevel3 = new Button("Level 3");
            loadLevel3.getStyleClass().add("button");
            loadLevel3.setLayoutX(300);
            loadLevel3.setLayoutY(100);
            loadLevel3.setOnAction(e -> getInstance().playLevel(3));

            Button loadLevel4 = new Button("Level 4");
            loadLevel4.getStyleClass().add("button");
            loadLevel4.setLayoutX(100);
            loadLevel4.setLayoutY(200);
            loadLevel4.setOnAction(e -> getInstance().playLevel(4));

            Button loadLevel5 = new Button("Level 5");
            loadLevel5.getStyleClass().add("button");
            loadLevel5.setLayoutX(200);
            loadLevel5.setLayoutY(200);
            loadLevel5.setOnAction(e -> getInstance().playLevel(5));

            Button loadLevel6 = new Button("Level 6");
            loadLevel6.getStyleClass().add("button");
            loadLevel6.setLayoutX(300);
            loadLevel6.setLayoutY(200);
            loadLevel6.setOnAction(e -> getInstance().playLevel(6));

            AnchorPane x = new AnchorPane();
            x.getChildren().add(background);
            x.getChildren().addAll(backToMainMenu, loadLevel1, loadLevel2, loadLevel3, loadLevel4, loadLevel5, loadLevel6);
            worldMap = new Scene(x, GameProperties.WIDTH, GameProperties.HEIGHT);
            worldMap.getStylesheets().add(String.valueOf(Sleepwalker.class.getResource("ui/styles.css")));
            sceneLibrary.put("worldMap", worldMap);
        }
        stageRoot.setScene(worldMap);
        stageRoot.show();
    }

    /**
     * Loads and starts the level with a fade in/out transition.
     * @param levelId the level to be played
     */
    public void playLevel(int levelId) {
        Scene previousScene = stageRoot.getScene();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), previousScene.getRoot());
        fadeOut.setFromValue(100);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(t -> {
            try{
                levelManager.setGameOverStatus(false);
                stageRoot.setScene(levelManager.loadLevel(levelId));
                previousScene.getRoot().setOpacity(100); //reset Opacity of previous Root Node
                MediaManager.playMusic("audio/level.mpeg");
                levelManager.startLevel();
            } catch(LevelNotLoadedException e){
                throw new RuntimeException(e);
            }
        });

        fadeOut.play();

    }
}