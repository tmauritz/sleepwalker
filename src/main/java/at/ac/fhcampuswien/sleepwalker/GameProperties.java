package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.input.KeyCode;

/**
 * GameProperties class stores various constants in one place.
 */
public abstract class GameProperties {
    public static final String TITLE = "Sleepwalker";
    public static final String DESCRIPTION = "a TeamTwo platformer";
    public static final double GAME_VERSION = 0.1;
    public static final String BACKGROUND_IMAGE_PATH = "level/background/background_layer_2.png";
    public static final int TILE_UNIT = 32;
    public static final int WIDTH = TILE_UNIT * 20;
    public static final int HEIGHT = TILE_UNIT * 15;
    public static final int PLAYER_SPEED = 2;
    public static final int PLAYER_JUMP = 13;
    public static final int TERMINAL_VELOCITY = 15;
    public static final double GRAVITY = 0.3;
    public static final KeyCode JUMP = KeyCode.UP;
    public static final KeyCode LEFT = KeyCode.LEFT;
    public static final KeyCode RIGHT = KeyCode.RIGHT;
    public static final KeyCode DUCK = KeyCode.DOWN;
    public static final KeyCode DEBUGVIEW = KeyCode.F3;
    public static final int FPS = 140;

}
