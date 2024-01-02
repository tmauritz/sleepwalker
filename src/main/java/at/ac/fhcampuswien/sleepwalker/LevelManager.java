package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.entities.Platform;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

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
     * Checks tile neighbours & picks the right ID accordingly
     */
    private String getTileID(String[] levelData, final int ROW, final int COL) {
        StringBuilder neighbours = new StringBuilder();

        char[] tiles;
        if (ROW > 0) {
            tiles = levelData[ROW - 1].toCharArray();
            neighbours.append(tiles[COL]);
        } else neighbours.append("x");

        tiles = levelData[ROW].toCharArray();
        if (COL > 0) {
            neighbours.append(tiles[COL - 1]);
        } else neighbours.append("x");

        if (COL + 1 < tiles.length) {
            neighbours.append(tiles[COL + 1]);
        } else neighbours.append("x");

        if (ROW + 1 < levelData.length) {
            tiles = levelData[ROW+1].toCharArray();
            neighbours.append(tiles[COL]);
        } else neighbours.append("x");

        return neighbours.toString();

    /**
     * Translates tile ID into X and Y coordinates respectively
     */
    private int getTileX(String tileID) {
        HashMap<String, String> tileX = new HashMap<>();


        tileX.put("x   ", "128");
        tileX.put("    ", "128");
        tileX.put("-   ", "0");
        tileX.put(" -  ", "320");
        tileX.put("  - ", "288");
        tileX.put("   -", "0");
        tileX.put(" -- ", "32");
        tileX.put("-  -", "0");
        tileX.put("--  ", "352");
        tileX.put("- - ", "128");
        tileX.put(" - -", "480");
        tileX.put("  --", "320");
        tileX.put(" ---", "256");

        tileX.put("- --", "128");
        tileX.put("-- -", "480");
        tileX.put("--- ", "320");
        tileX.put("----", "320");

        //bottom tiles
        tileX.put(" --x", "32");
        tileX.put("---x", "192");
        tileX.put(" - x", "64");
        tileX.put("  -x", "0");
        tileX.put(" -xx", "32");
        tileX.put(" x-x", "32");

        if (tileX.containsKey(tileID)) {
            return parseInt(tileX.get(tileID));
        } else return 0;
    }

    private int getTileY(String tileID) {
        HashMap<String, String> tileY = new HashMap<>();

        tileY.put("    ", "96");
        tileY.put("x   ", "96");
        tileY.put("-   ", "320");
        tileY.put(" -  ", "0");
        tileY.put("  - ", "0");
        tileY.put("   -", "256");
        tileY.put(" -- ", "384");
        tileY.put("--  ", "352");
        tileY.put("- - ", "480");
        tileY.put(" - -", "96");
        tileY.put("  --", "352");
        tileY.put("-  -", "288");
        tileY.put(" ---", "96");


        tileY.put("- --", "352");
        tileY.put("-- -", "160");
        tileY.put("--- ", "480");
        tileY.put("----", "416");

        //bottom tiles
        tileY.put(" --x", "0");
        tileY.put("---x", "288");
        tileY.put(" - x", "0");
        tileY.put("  -x", "0");
        tileY.put(" x-x", "0");
        tileY.put(" -xx", "0");


        if (tileY.containsKey(tileID)) {
            return parseInt(tileY.get(tileID));
        } else return 0;
    }

    /**
     * Loads a Level from level data
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level or null if the level could not be loaded
     */
    public Scene loadLevel(int levelId){
        //TODO: refine level loading
        Pane levelRoot = new Pane();
        BackgroundImage bg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/background_layer_1.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        levelRoot.setBackground(new Background(bg));

        String[] levelData = LevelData.Levels.getOrDefault(levelId, null);
        if (levelData == null) return null;
        levelRoot.setMinWidth(GameProperties.WIDTH);
        levelRoot.setMinHeight(GameProperties.HEIGHT);
        //process each line and make platforms
        for(int i = 0; i < levelData.length; i++){
            char[] tiles = levelData[i].toCharArray();
            for(int j = 0; j < tiles.length; j++){
                switch(tiles[j]){
                    case '-': //platform
                        Platform platform = new Platform(
                                j * GameProperties.TILE_UNIT,
                                i * GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                getTileX(getTileID(levelData, i, j)), getTileY(getTileID(levelData, i, j)));
                        platforms.add(platform);
                        levelRoot.getChildren().add(platform);
                        break;
                    case 's': //player spawn
                        //TODO: outsource player into its own class?
                        player = new Rectangle(GameProperties.TILE_UNIT - 10, GameProperties.TILE_UNIT - 10, Color.BLUE);
                        //set spawn for player
                        player.setTranslateX(GameProperties.TILE_UNIT * j);
                        player.setTranslateY(GameProperties.TILE_UNIT * i);
                        levelRoot.getChildren().add(player);
                        break;
                }
            }
        }



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

    private void enforceFrameBounds() {
        double playerX = player.getTranslateX();
        double playerY = player.getTranslateY();

        // Limit the player on the X-axis
        if (playerX < 0) {
            player.setTranslateX(0);
        } else if (playerX > GameProperties.WIDTH - player.getBoundsInParent().getWidth()) {
            player.setTranslateX(GameProperties.WIDTH - player.getBoundsInParent().getWidth());
        }

        // Limit the player on the Y-axis
        if (playerY < 0) {
            player.setTranslateY(0);
        } else if (playerY > GameProperties.HEIGHT - player.getBoundsInParent().getHeight()) {
            player.setTranslateY(GameProperties.HEIGHT - player.getBoundsInParent().getHeight());
            playerCanJump = true;  // The player can jump again when touching the ground
        }
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

        enforceFrameBounds();

        //TODO: keep player from exiting frame
        //TODO: scrolling
        //TODO: finish conditions

    }

}
