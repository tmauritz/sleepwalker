package at.ac.fhcampuswien.sleepwalker.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Collectible extends Rectangle {

    /**
     * Collectible Class
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
        setFill(Color.GOLD);
        //TODO:Image for the Coin
    }
}
