package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
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
        Image image = new Image("at/ac/fhcampuswien/sleepwalker/level/Textures/TX Tileset Ground.png");
        ImageView imageView = new ImageView(image);
        imageView.setViewport(new javafx.geometry.Rectangle2D(partX, partY, width, height));

        setFill(new ImagePattern(imageView.snapshot(null, null)));
    }

}
