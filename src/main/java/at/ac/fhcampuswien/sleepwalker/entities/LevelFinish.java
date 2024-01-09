package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LevelFinish extends Rectangle {

    private LevelManager levelManager;
    private int currentLevelID;

    public LevelFinish(double x, double y, LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
        this.setX(x);
        this.setY(y);
        this.setWidth(GameProperties.TILE_UNIT);
        this.setHeight(GameProperties.TILE_UNIT);
        this.setFill(Color.BLUE);
    }

    public void openPortal() {
        this.setFill(new ImagePattern(MediaManager.loadImage("level/Portal_idle.gif")));
    }

    /**
     * Finishes the level.
     */
    public void finishLevel() {

        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());

        if ((LevelData.Levels.size() - (LevelData.Levels.size()/2)) > currentLevelID) {
            Button nextLevel = new Button("Next Level");
            nextLevel.setOnAction(event -> GameManager.getInstance().playLevel(++currentLevelID));
            levelManager.showDialog(nextLevel, backToMenu);
        } else {
            levelManager.showDialog(backToMenu);
        }

    }

}
