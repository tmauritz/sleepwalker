package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.MediaManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 Spike Class
 */

public class Spike extends ImageView {
    public Spike(double x, double y, double width, double height){
        Image spikeImage = MediaManager.loadImage("level/Spike.png");
        setImage(spikeImage);
        setFitWidth(width);
        setFitHeight(height);
        setTranslateX(x);
        setTranslateY(y);
    }

}
