package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.LevelData;
import at.ac.fhcampuswien.sleepwalker.LevelManager;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
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
        //TODO: graphics for exit
        this.setFill(Color.LIGHTGREEN);
    }

    /**
     * Finishes the level.
     */
    public void finishLevel() {

        String win = "You win!";
        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnAction(event -> GameManager.getInstance().showMainMenu());

        if (LevelData.Levels.size() > currentLevelID) {
            Button nextLevel = new Button("Next Level");
            nextLevel.setOnAction(event -> GameManager.getInstance().playLevel(++currentLevelID));
            levelManager.showDialog(win, nextLevel, backToMenu);
        } else {
            levelManager.showDialog(win, backToMenu);
        }

    }

}
