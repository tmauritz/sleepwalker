package at.ac.fhcampuswien.sleepwalker;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * LevelManager class <br>
 * handles level loading and level logic <br>
 * logic handling loosely based on
 * <a href="https://www.youtube.com/watch?v=fnsBoamSscQ&ab_channel=AlmasBaimagambetov">
 * AlmasB's platformer tutorial
 * </a>
 */
public class LevelManager {
    private static long framecount;
    private static final Label debugFrameCount = new Label();
    private static Node player;

    /**
     * handles Level loading
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level
     */
    public static Scene loadLevel(int levelId){
        //TODO: actual level loading
        Pane levelRoot = new Pane();
        //create Player
        player = new Rectangle(40, 40, Color.BLUE);

        //set spawn for player
        player.setLayoutX(200);
        player.setLayoutY(200);

        //create floor
        Rectangle floor = new Rectangle(0, GameProperties.HEIGHT - 50, GameProperties.WIDTH, 50);
        floor.setFill(Color.BLACK);

        //add everything to the level
        levelRoot.getChildren().addAll(debugFrameCount, player, floor);
        return new Scene(levelRoot);

    }

    /**
     * starts the Level update cycle
     */
    public static void startLevel(){
        //position and style frame counter
        debugFrameCount.setLayoutX(0);
        debugFrameCount.setLayoutY(10);
        debugFrameCount.setTextFill(Color.WHITE);
        debugFrameCount.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        AnimationTimer updateTimer = new AnimationTimer() {
            @Override
            public void handle(long now){
                update();
            }
        };
        updateTimer.start();
    }

    /**
     * updates the level every frame
     */
    private static void update(){
        debugFrameCount.setText("FRAME: " + framecount++);
        //TODO
    }
}
