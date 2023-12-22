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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LevelManager class <br>
 * handles level loading and level logic <br>
 * collision handling loosely based on
 * <a href="https://www.youtube.com/watch?v=fnsBoamSscQ&ab_channel=AlmasBaimagambetov">
 * AlmasB's platformer tutorial
 * </a>
 */
public class LevelManager {
    private final Label debugInfo = new Label();
    private final Map<KeyCode, Boolean> pressedKeys;
    private final List<Node> platforms;
    private long frameCounter;
    private Node player;
    private Point2D playerVelocity;
    private boolean playerCanJump;
    private Scene loadedLevel;

    public LevelManager(){
        pressedKeys = new HashMap<>();
        platforms = new ArrayList<>();
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
     * Moves the Player along the X Axis and checks for collision
     * if a collision is detected (overlap), player is moved back 1 unit.
     * Player can not move through platforms.
     *
     * @param amount how far the player is moved
     */
    private void movePlayerX(int amount){
        boolean movingRight = amount > 0;

        for(int i = 1; i <= Math.abs(amount); i++){
            for(Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingRight){
                        player.setTranslateX(player.getTranslateX() - 1);
                    } else{
                        player.setTranslateX(player.getTranslateX() + 1);
                    }
                    return;
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    /**
     * Moves the Player along the Y Axis and checks for collision
     * if a collision is detected (overlap), player is moved back 1 unit.
     * Player can not move through platforms
     *
     * @param amount how far the player is moved
     */
    private void movePlayerY(int amount){
        boolean movingDown = amount > 0;

        for(int i = 1; i <= Math.abs(amount); i++){
            for(Node platform : platforms){
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingDown){
                        player.setTranslateY(player.getTranslateY() - 1);
                        playerCanJump = true;
                    } else{
                        player.setTranslateY(player.getTranslateY() + 1);
                        playerVelocity = playerVelocity.add(0, -playerVelocity.getY()); //reset jump velocity
                    }
                    return;
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer(){
        if(playerCanJump){
            playerVelocity = playerVelocity.add(0, -GameProperties.PLAYER_JUMP);
            playerCanJump = false;
        }
    }

    /**
     * Toggles debug info visibility.
     */
    private void toggleDebug(){
        debugInfo.setVisible(!debugInfo.isVisible());
    }

    /**
     * handles Level loading
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level
     */
    public Scene loadLevel(int levelId){
        //TODO: actual level loading, this is just a test placehoder for now
        Pane levelRoot = new Pane();
        //create Player
        player = new Rectangle(GameProperties.TILE_UNIT - 10, GameProperties.TILE_UNIT - 10, Color.BLUE);

        //set spawn for player
        player.setTranslateX(GameProperties.TILE_UNIT * 2);
        player.setTranslateY(GameProperties.TILE_UNIT * 4);

        //create floor
        Rectangle floor = new Rectangle(0, GameProperties.HEIGHT - GameProperties.TILE_UNIT, GameProperties.TILE_UNIT * (GameProperties.WIDTH / GameProperties.TILE_UNIT), GameProperties.TILE_UNIT);
        floor.setFill(Color.BLACK);
        //create second test platform
        Rectangle platform = new Rectangle(GameProperties.TILE_UNIT * 8, GameProperties.TILE_UNIT * 10, GameProperties.TILE_UNIT * (GameProperties.HEIGHT / GameProperties.TILE_UNIT / 2), GameProperties.TILE_UNIT);
        platform.setFill(Color.BLACK);

        Rectangle wall = new Rectangle(GameProperties.TILE_UNIT * 8, GameProperties.HEIGHT - (GameProperties.TILE_UNIT * 6), GameProperties.TILE_UNIT, GameProperties.TILE_UNIT * 6);

        platforms.add(floor);
        platforms.add(platform);
        platforms.add(wall);

        //add everything to the level
        levelRoot.getChildren().addAll(debugInfo, player, floor, platform, wall);

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
        loadedLevel.setOnKeyPressed(keypress -> {
            pressedKeys.put(keypress.getCode(), true);
            if(keypress.getCode().equals(GameProperties.DEBUGVIEW)){
                toggleDebug();
            }
        });
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
        debugInfo.setText("FRAME: " + frameCounter++ + System.lineSeparator() + "Player: (" + player.getTranslateX() + " | " + player.getTranslateY() + ")" + System.lineSeparator() + "VELOCITY: " + playerVelocity.toString());
        //process player input
        if(isPressed(GameProperties.LEFT)) movePlayerX(-GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.RIGHT)) movePlayerX(GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.JUMP)) jumpPlayer();
        //assess the gravity of the situation
        if(playerVelocity.getY() < GameProperties.TERMINAL_VELOCITY){
            playerVelocity = playerVelocity.add(0, GameProperties.GRAVITY);
        }
        movePlayerY((int) playerVelocity.getY());

        //TODO: keep player from exiting frame
        //TODO: scrolling
        //TODO: finish conditions

    }

}
