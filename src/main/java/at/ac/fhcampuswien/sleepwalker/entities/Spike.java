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

public class Spike extends Rectangle{
    public Spike(double x, double y, double width, double height, int partX, int partY){
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        setFill(Color.BLACK);
        //TODO: Grafik für Spike, Optimal Die Mine aus TX Village Props.png
    }

}
