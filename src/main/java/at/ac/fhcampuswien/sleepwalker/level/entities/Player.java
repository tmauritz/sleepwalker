package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.animation.FadeTransition;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player extends Rectangle {
    private final LevelManager levelManager;
    private final ImagePattern idleLeft = new ImagePattern(MediaManager.loadImage("animation/player/Idle_left.gif"));
    private final ImagePattern idleRight = new ImagePattern(MediaManager.loadImage("animation/player/Idle_right.gif"));

    public ImagePattern getIdleLeft() {
        return idleLeft;
    }

    public ImagePattern getIdleRight() {
        return idleRight;
    }

    public void setCharTexture(ImagePattern texture) {
        this.setFill(texture);
    }

    public Player(int height, int width, LevelManager levelManager) {
        super();
        this.levelManager = levelManager;
        setWidth(width);
        setHeight(height);
        setCharTexture(idleRight);
    }

    public void die(){
        levelManager.pause();
        MediaManager.playSound("audio/sound/impact.wav");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this);
        fadeTransition.setFromValue(100);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> {
            setOpacity(100);
            levelManager.respawn();
        });
        fadeTransition.play();
    }
}
