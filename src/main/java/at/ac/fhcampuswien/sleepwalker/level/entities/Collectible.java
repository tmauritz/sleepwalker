package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Collectible extends Rectangle {

    /**
     * Collectible Class
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Collectible(double x, double y, double width, double height) {
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        this.setFill(new ImagePattern(MediaManager.loadImage("level/Coin.gif")));
    }
}
