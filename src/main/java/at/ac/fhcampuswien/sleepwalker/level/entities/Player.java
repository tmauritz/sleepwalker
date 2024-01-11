package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    ImagePattern idleLeft = new ImagePattern(MediaManager.loadImage("animation/player/Idle_left.gif"));
    ImagePattern idleRight = new ImagePattern(MediaManager.loadImage("animation/player/Idle_right.gif"));

    public ImagePattern getIdleLeft() {
        return idleLeft;
    }

    public ImagePattern getIdleRight() {
        return idleRight;
    }

    public void setCharTexture(ImagePattern texture) {
        this.setFill(texture);
    }

    public Player(int height, int width) {
        super();
        setWidth(width);
        setHeight(height);
        setCharTexture(idleRight);


    }
}
