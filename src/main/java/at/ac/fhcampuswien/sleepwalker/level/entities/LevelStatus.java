package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.level.LevelData;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static at.ac.fhcampuswien.sleepwalker.GameManager.getInstance;

public class LevelStatus extends Rectangle {
    private final LevelManager levelManager;
    private int currentLevelID;
    public LevelStatus(double x, double y, LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
        this.setX(x);
        this.setY(y);
        this.setWidth(GameProperties.TILE_UNIT*2);
        this.setHeight(GameProperties.TILE_UNIT*2);
        this.setFill(Color.TRANSPARENT);
    }
    public LevelStatus(LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
    }

    public void openPortal() {
        this.setFill(new ImagePattern(MediaManager.loadImage("level/Portal_start.gif")));

        this.setFill(new ImagePattern(MediaManager.loadImage("level/Portal_idle.gif")));
    }

    /**
     * Finishes the level.
     */
    public void finishLevel() {

        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());
        boolean finished = true;
        levelManager.pause();
        if ((LevelData.Levels.size()/2) > currentLevelID) {
            Button nextLevel = new Button("Next Level");
            nextLevel.setOnAction(event -> GameManager.getInstance().playLevel(++currentLevelID));
            levelManager.showDialog(finished, nextLevel, backToMenu);
        } else {
            levelManager.showDialog(finished, backToMenu);
        }

    }

    /**
     * Is ending the level.
     */
    public void failLevel() {

        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.getStyleClass().add("button");
        backToMenu.setPrefSize(200, 30);
        backToMenu.setLayoutX(50);
        backToMenu.setLayoutY(120);
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());

        Button retryLevel = new Button("Retry Level");
        retryLevel.getStyleClass().add("button");
        retryLevel.setOnAction(event -> GameManager.getInstance().playLevel(currentLevelID));
        boolean finished = false;
        levelManager.pause();
        levelManager.showDialog(finished, retryLevel, backToMenu);

    }
}
