package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

        /*
        * Spike Class
        *
        * @param x
        * @param y
        * @param width
        * @param height
        * Source wings: https://www.deviantart.com/luis-bello/art/Wings-for-Guihena-390558334
        */

public class Spike extends Rectangle {
    public Spike(double x, double y, double width, double height, boolean wings) {
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        if (wings) {
            this.setFill(new ImagePattern(MediaManager.loadImage("level/Spike_Wings.gif")));
        } else {
            this.setFill(new ImagePattern(MediaManager.loadImage("level/Spike.png")));
        }
    }

}
