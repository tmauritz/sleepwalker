package at.ac.fhcampuswien.sleepwalker;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Sleepwalker extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        String windowTitle = GameProperties.TITLE + " v" + GameProperties.GAME_VERSION;
        primaryStage.setTitle(windowTitle);
        primaryStage.setMaxWidth(GameProperties.WIDTH);
        primaryStage.setMinWidth(GameProperties.WIDTH);
        primaryStage.setMaxHeight(GameProperties.HEIGHT);
        primaryStage.setMinHeight(GameProperties.HEIGHT);
        primaryStage.setResizable(false);
        initGame(primaryStage);
    }

    private void initGame(Stage primaryStage){
        //show Intro
        Scene videoIntro;
        MediaPlayer videoPlayer = new MediaPlayer(new Media(Objects.requireNonNull(Sleepwalker.class.getResource("img/TeamTwo.mp4")).toString()));
        MediaView videoView = new MediaView(videoPlayer);
        videoView.setPreserveRatio(true);
        videoView.setFitWidth(GameProperties.WIDTH);
        Pane videoPane = new Pane(videoView);
        Rectangle bg = new Rectangle(GameProperties.WIDTH, GameProperties.HEIGHT, Color.BLACK);
        videoIntro = new Scene(new Pane(bg, videoPane));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), videoPane);
        primaryStage.setScene(videoIntro);
        fadeOut.setFromValue(100);
        fadeOut.setToValue(0);
        videoPlayer.setOnEndOfMedia(fadeOut::play);
        fadeOut.setOnFinished(t -> GameManager.getInstance(primaryStage).showMainMenu());
        primaryStage.show();
        videoPlayer.play();
    }

}