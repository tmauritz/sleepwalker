package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/*
 * Power-Up-Health Class
 *
 * @param x
 * @param y
 * @param width
 * @param height
 * Source wings: https://www.deviantart.com/luis-bello/art/Wings-for-Guihena-390558334
 */

public class PowerUpHealth extends Rectangle {
    public PowerUpHealth(double x, double y, double width, double height) {
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        this.setFill(new ImagePattern(MediaManager.loadImage("level/Heart_Addition.gif")));
    }
}
