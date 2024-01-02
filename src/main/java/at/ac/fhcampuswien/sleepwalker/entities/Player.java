package at.ac.fhcampuswien.sleepwalker.entities;

import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class Player extends Rectangle {

    public void idleAnimation() {
        ImageView animation = new ImageView();

        String[] FRAME_FILES = {
                "img/Hobbit - Idle1.png",
                "img/Hobbit - Idle2.png",
                "img/Hobbit - Idle3.png",
                "img/Hobbit - Idle4.png"
        };

        Timeline timeline = createTimeline(FRAME_FILES, animation);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private Timeline createTimeline(String[] FRAME_FILES, ImageView animation) {
        Timeline timeline = new Timeline();

        for (int i = 0; i < FRAME_FILES.length; i++) {
            String frameFile = FRAME_FILES[i];

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(100),
                    event -> {
                        // Update the ImageView with the current frame
                        try {
                            Image frameImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(frameFile)));
                            animation.setImage(frameImage);
                        } catch (NullPointerException e) {
                            System.out.println("invalid path");
                        }
                    }
            );

            timeline.getKeyFrames().add(keyFrame);
        }
        return timeline;
    }

    public Player(int height, int width){
        idleAnimation();


    }
}
