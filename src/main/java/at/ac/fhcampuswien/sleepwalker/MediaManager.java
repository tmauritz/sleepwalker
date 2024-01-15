package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MediaManager {
    private static Map<String, Image> images = new HashMap<>();
    private static Map<String, Media> medias = new HashMap<>();
    private static double sfxVolume = 50;

    /**
     * Sets the global volume of all SFX.
     * @param volume SFX volume
     */
    public static void setSoundVolume(double volume){
        if(volume > 100) MediaManager.sfxVolume = 100;
        else if (volume < 0) MediaManager.sfxVolume = 0;
        else MediaManager.sfxVolume = volume;
    }

    /**
     * Plays an SFX sound at the global volume.
     * @param name sound to be played
     */
    public static void playSoundFX(String name){
        Media sound = loadMedia(name);
        MediaPlayer soundPlayer = new MediaPlayer(sound);
        soundPlayer.setVolume(sfxVolume);
        soundPlayer.play();
    }

    /**
     * Loads a Media object from Resources
     *
     * @param mediaPath the path of the media resource
     * @return the media object or null if there is no Media at the specified path
     */
    public static Media loadMedia(String mediaPath) {
        Media loadedMedia = medias.getOrDefault(mediaPath, null);
        if (loadedMedia == null) {
            try {
                loadedMedia = new Media(Objects.requireNonNull(Sleepwalker.class.getResource(mediaPath)).toString());
            } catch (NullPointerException e) {
                System.err.println("Media not found: " + mediaPath);
            }
        }
        if (loadedMedia != null) medias.put(mediaPath, loadedMedia);
        return loadedMedia;
    }

    /**
     * Loads an image
     *
     * @param imagePath the path of the image resource
     * @return the loaded image or null if there is no image at the specified path
     */
    public static Image loadImage(String imagePath) {
        Image loadedImage = images.getOrDefault(imagePath, null);
        if (loadedImage == null) {
            try {
                loadedImage = new Image(Objects.requireNonNull(Sleepwalker.class.getResource(imagePath)).toString());
            } catch (NullPointerException e) {
                System.err.println("Image not found: " + imagePath);
                //TODO: figure out what to do
            }
        }
        if (loadedImage != null) images.put(imagePath, loadedImage);
        return loadedImage;
    }

}