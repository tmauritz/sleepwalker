package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class MediaManager {
    private static final Map<String, Image> images = new HashMap<>();
    private static final Map<String, Media> medias = new HashMap<>();
    private static MediaPlayer musicPlayer;
    private static final double sfxVolume = 50;
    private static double musicVolume = 50;

    /**
     * Sets the global volume of all SFX.
     * @param volume SFX volume
     */
    public static void setSoundVolume(double volume){
        musicVolume = volume;
        if(musicPlayer != null) {
            musicPlayer.setVolume(musicVolume / 100.0);
        }
    }

    /**
     * Sets the global volume of music
     * @param musicVolume music volume
     */
    public static void setMusicVolume(double musicVolume){
        if(musicVolume > 100) MediaManager.musicVolume = 100;
        else if (musicVolume < 0) MediaManager.musicVolume = 0;
        else MediaManager.musicVolume = musicVolume;
        if(musicPlayer != null) musicPlayer.setVolume(MediaManager.musicVolume / 100);
    }

    public static double getMusicVolume(){
        return musicVolume;
    }

    /**
     * Plays an SFX sound at the global volume.
     * @param name path to the sfx file
     */
    public static void playSoundFX(String name){
        Media sound = loadMedia(name);
        MediaPlayer soundPlayer = new MediaPlayer(sound);
        soundPlayer.setVolume(sfxVolume);
        soundPlayer.play();
    }

    /**
     * Plays background music at the global music volume.
     *
     * @param name path to the music file
     */
    public static void playMusic(String name){
        Media music = loadMedia(name);
        if(musicPlayer != null) musicPlayer.stop();
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setVolume(musicVolume);
        musicPlayer.setOnEndOfMedia(() -> {
            musicPlayer.seek(Duration.ZERO);
            musicPlayer.play();
        });
        musicPlayer.play();
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