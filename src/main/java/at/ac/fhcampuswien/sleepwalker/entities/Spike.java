package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/*
 Spike Class
 */

public class Spike extends ImageView{
    public Spike(double x, double y, double width, double height, int partX, int partY) {
        Image spikeImage = new Image(Sleepwalker.class.getResourceAsStream("level/Spike.png"));
        setImage(spikeImage);
        setFitWidth(width);
        setFitHeight(height);
        setTranslateX(x);
        setTranslateY(y);
    }

}
