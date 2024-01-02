package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.Objects;

public class MediaManager {
    /**
     * NullPointerException for Image and Media
     * @param mediaPath
     * @return
     */

    public static Media loadMedia(String mediaPath) {
        try {
            return new Media(Objects.requireNonNull(Sleepwalker.class.getResource(mediaPath)).toString());
        } catch (NullPointerException e) {
            System.err.println("Media not found: " + mediaPath);
            return null;
        }
    }

    public static Image loadImage(String imagePath) {
        try {
            return new Image(Objects.requireNonNull(Sleepwalker.class.getResource(imagePath)).toString());
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + imagePath);
            return null;
        }
    }
}
//TODO: Fix NullPointerException