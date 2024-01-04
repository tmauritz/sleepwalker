package at.ac.fhcampuswien.sleepwalker.entities;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Platform Class
 */
public class Platform extends Rectangle {
    public Platform(double x, double y, double width, double height, ImagePattern tile) {
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        setFill(tile);
    }

}
