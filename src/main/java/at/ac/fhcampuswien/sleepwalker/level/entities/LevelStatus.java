package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.GameManager;
import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import at.ac.fhcampuswien.sleepwalker.level.LevelData;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static at.ac.fhcampuswien.sleepwalker.GameManager.getInstance;

public class LevelStatus extends Rectangle {
    private final LevelManager levelManager;
    private int currentLevelID;
    /**
     Is getting the current LevelID of LevelManager
     */
    public LevelStatus(double x, double y, boolean collision, LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
        this.setY(y);
        if (collision) {
            this.setX(x + 15);
            this.setWidth(GameProperties.TILE_UNIT*0.2);
            this.setHeight(GameProperties.TILE_UNIT*2);
        } else {
            this.setX(x - 15);
            this.setWidth(GameProperties.TILE_UNIT*2);
            this.setHeight(GameProperties.TILE_UNIT*2);
        }
        this.setFill(Color.TRANSPARENT);
    }
    /**
    Is getting the current LevelID of LevelManager
     */
    public LevelStatus(LevelManager currentLevel) {
        this.levelManager = currentLevel;
        currentLevelID = levelManager.getLoadedLevelID();
    }
    /**
    Is opening the Portal to finish the level
     */
    public void openPortal() {
        KeyFrame openingGif = new KeyFrame(Duration.ZERO, t -> {
            try {
                this.setFill(new ImagePattern(new Image(Objects.requireNonNull(Sleepwalker.class.getResource("level/Portal_start.gif")).openStream())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        KeyFrame waitTime = new KeyFrame(Duration.seconds(0.8));
        Timeline openingTimeline = new Timeline(openingGif, waitTime);
        openingTimeline.setOnFinished(t->{
            this.setFill(new ImagePattern(MediaManager.loadImage("level/Portal_idle.gif")));
        });
        openingTimeline.play();
    }

    /**
     * Finishes the level.
     */
    public void finishLevel() {
        levelManager.getCurrentLevel().Player().setVisible(false);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), this);
        fadeOut.setFromValue(100);
        fadeOut.setToValue(0);
        fadeOut.play();

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
