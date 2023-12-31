package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Platform Class
 */
public class Platform extends Rectangle{
    public Platform(double x, double y, double width, double height, int partX, int partY){
        super();
        setTranslateX(x);
        setTranslateY(y);
        setWidth(width);
        setHeight(height);
        Image image = new Image(String.valueOf(Sleepwalker.class.getResource("level/TX Tileset Ground.png")));
        ImageView imageView = new ImageView(image);
        imageView.setViewport(new javafx.geometry.Rectangle2D(partX, partY, width, height));

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        setFill(new ImagePattern(imageView.snapshot(params, null)));
    }

}
