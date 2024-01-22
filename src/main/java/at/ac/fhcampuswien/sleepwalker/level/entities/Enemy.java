package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {
    // Source of animation: https://www.deviantart.com/neslug/art/Mad-Raven-Animation-98847870
    private final ImagePattern idleLeft = new ImagePattern(MediaManager.loadImage("animation/enemy/Mad_Raven_Left.gif"));
    private final ImagePattern idleRight = new ImagePattern(MediaManager.loadImage("animation/enemy/Mad_Raven_Right.gif"));

    public ImagePattern getIdleLeft() {
        return idleLeft;
    }

    public ImagePattern getIdleRight() {
        return idleRight;
    }
    public void setCharTexture(ImagePattern texture) {
        this.setFill(texture);
    }
    public Enemy(int height, int width) {
        super();
        setWidth(width);
        setHeight(height);
        setCharTexture(idleRight);
    }

}
