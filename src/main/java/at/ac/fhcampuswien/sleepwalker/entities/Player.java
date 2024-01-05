package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

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
