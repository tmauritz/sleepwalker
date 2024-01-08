package at.ac.fhcampuswien.sleepwalker.entities;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Platform Class
 */
public class Deco extends Rectangle {
    public Deco(double x, double y, double width, double height, ImagePattern tile) {
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        setSmooth(false);
        setFill(Color.TRANSPARENT);
        setFill(tile);
    }

}
