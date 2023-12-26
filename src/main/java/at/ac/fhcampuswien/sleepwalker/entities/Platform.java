package at.ac.fhcampuswien.sleepwalker.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Platform Class
 */
public class Platform extends Rectangle{
    public Platform(double x, double y, double width, double height){
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        setFill(Color.BLACK);
    }

}
