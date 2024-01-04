package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.LevelData;
import at.ac.fhcampuswien.sleepwalker.LevelManager;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelFail extends Rectangle {
    private LevelManager levelManager;
    private int currentLevelID;

    public LevelFail(double x, double y, LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
        this.setX(x);
        this.setY(y);
        this.setWidth(GameProperties.TILE_UNIT);
        this.setHeight(GameProperties.TILE_UNIT);
        //TODO: graphics for exit
        this.setFill(Color.LIGHTGREEN);
    }

    /**
     * Is ending the level.
     */
    public void failLevel() {

        String win = "Game Over!";
        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());

        Button retryLevel = new Button("Retry Level");
        retryLevel.setOnAction(event -> GameManager.getInstance().playLevel(currentLevelID));
        levelManager.showDialogDead(win, retryLevel, backToMenu);


    }
}
