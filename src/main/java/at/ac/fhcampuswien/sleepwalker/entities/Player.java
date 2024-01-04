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


    public Image idleAnimation() {
        return MediaManager.loadImage("animation/player/IdleNoSlingshot.gif");
    }

    public Player(int height, int width){
        super();
        setWidth(width);
        setHeight(height);
        this.setFill(new ImagePattern(idleAnimation()));


    }
}
