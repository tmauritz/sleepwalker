package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class LevelFail extends Rectangle {
    private LevelManager levelManager;
    private int currentLevelID;

    public LevelFail(LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
    }

    /**
     * Is ending the level.
     */
    public void failLevel() {

        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());

        Button retryLevel = new Button("Retry Level");
        retryLevel.setOnAction(event -> GameManager.getInstance().playLevel(currentLevelID));
        boolean finished = false;
        levelManager.showDialog(finished, retryLevel, backToMenu);


    }
}
