package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * LevelManager class <br>
 * handles level loading and level logic <br>
 * logic handling loosely based on
 * <a href="https://www.youtube.com/watch?v=fnsBoamSscQ&ab_channel=AlmasBaimagambetov">
 * AlmasB's platformer tutorial
 * </a>
 */
public class LevelManager {
    private long framecount;
    private final Label debugInfo = new Label();
    private Node player;
    private Point2D playerVelocity;
    private boolean playerCanJump;
    private Scene loadedLevel;
    private final Map<KeyCode, Boolean> pressedKeys;

    public LevelManager(){
        pressedKeys = new HashMap<>();
        playerVelocity = new Point2D(0, 0);
        playerCanJump = true;
    }

    /**
     * finds out if a key is currently presses or not.
     *
     * @param key KeyCode
     * @return true if key is pressed, false if not
     */
    private boolean isPressed(KeyCode key){
        return pressedKeys.getOrDefault(key, false);
    }

    /**
     * Moves the Player along the X Axis
     *
     * @param amount how far the player is moved
     */
    private void movePlayerX(int amount){
        player.setLayoutX(player.getLayoutX() + amount);
    }

    /**
     * Moves the Player along the Y Axis
     *
     * @param amount how far the player is moved
     */
    private void movePlayerY(int amount){
        player.setLayoutY(player.getLayoutY() + amount);
    }

    private void jumpPlayer(){
        if(playerCanJump){
            playerVelocity = playerVelocity.add(0, -GameProperties.PLAYER_JUMP);
            playerCanJump = false;
        }
    }

    /**
     * handles Level loading
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level
     */
    public Scene loadLevel(int levelId){
        //TODO: actual level loading, placehoder for now
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
        levelRoot.getChildren().addAll(debugInfo, player, floor);

        loadedLevel = new Scene(levelRoot);
        return loadedLevel;

    }

    /**
     * starts the Level update cycle
     */
    public void startLevel() throws LevelNotLoadedException{
        if(loadedLevel == null) throw new LevelNotLoadedException("No Level loaded.");

        //position and style frame counter
        debugInfo.setLayoutX(0);
        debugInfo.setLayoutY(10);
        debugInfo.setTextFill(Color.WHITE);
        debugInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        //add listeners for key presses
        loadedLevel.setOnKeyPressed(keypress -> pressedKeys.put(keypress.getCode(), true));
        loadedLevel.setOnKeyReleased(keypress -> pressedKeys.put(keypress.getCode(), false));

        //updateTimer controls the level speed
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
    private void update(){
        debugInfo.setText("FRAME: " + framecount++ + System.lineSeparator() + "Player: (" + player.getLayoutX() + " | " + player.getLayoutY() + ")" + System.lineSeparator() + "VELOCITY: " + playerVelocity.toString());

        //TODO
        //process player input
        if(isPressed(GameProperties.LEFT)) movePlayerX(-GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.RIGHT)) movePlayerX(GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.JUMP)) jumpPlayer();
        //assess the gravity of the situation
        if(playerVelocity.getY() < GameProperties.TERMINAL_VELOCITY){
            playerVelocity = playerVelocity.add(0,GameProperties.GRAVITY);
        }
        movePlayerY((int) playerVelocity.getY());

    }

}
