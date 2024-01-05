package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.entities.*;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;

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
    private final Label GUI = new Label("TOP GUI");
    private final Map<KeyCode, Boolean> pressedKeys;
    private final List<Node> platforms;
    private final List<Node> spikes;
    private final List<Node> collectibles;
    private Timeline updateTimeline;
    private LevelFinish levelFinish = null;
    private LevelFail failLevel = null;
    private final Pane dialogBox = new Pane();
    private final Pane dialogBoxDead = new Pane();
    private Pane levelRootCamera;
    private int loadedLevelID;
    private long frameCounter;
    private Player player;
    private Point2D playerVelocity;
    private boolean playerCanJump;
    private Scene loadedLevel;
    private ImageView currentHearts;
    private ImageView currentCollectibles;
    // Instances for respawning : spawnPositionX and spawnPositionY
    private int spawnPositionX;
    private int spawnPositionY;
    private int health = 6;
    private int xFail;
    private int yFail;
    boolean wasMovingRight = true;
    private Timeline timerTimeline;
    private long startTime;
    private Label timerLabel;


    public LevelManager() {
        pressedKeys = new HashMap<>();
        platforms = new ArrayList<>();
        spikes = new ArrayList<>();
        collectibles = new ArrayList<>();
        playerVelocity = new Point2D(0, 0);
        playerCanJump = true;
    }

    /*
    Set the health of the player
     */
    public int getHealth() {
        return health;
    }
    /*
    Get the health of the player
     */

    public void setHealth(int health) {
        this.health = health;
    }

    private void jumpPlayer() {
        if (playerCanJump) {
            playerVelocity = playerVelocity.add(0, -GameProperties.PLAYER_JUMP);
            playerCanJump = false;
        }
    }


    /**
     * finds out if a key is currently presses or not.
     *
     * @param key KeyCode
     * @return true if key is pressed, false if not
     */
    private boolean isPressed(KeyCode key) {
        return pressedKeys.getOrDefault(key, false);
    }


    /**
     * Moves the Player along the X Axis and checks for collision
     * if a collision is detected (overlap), player is moved back 1 unit.
     * Player can not move through platforms.
     *
     * @param amount how far the player is moved
     */
    private void movePlayerX(int amount) {
        boolean movingRight = amount > 0;

        for (int i = 1; i <= Math.abs(amount); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    //collision detected
                    if (movingRight) {
                        player.setTranslateX(player.getTranslateX() - 1);
                    } else {
                        player.setTranslateX(player.getTranslateX() + 1);
                    }
                    return;
                }
            }
            //Player looses one life if it touches a spike and respawns at the spawn
            for (Node spike : spikes) {
                if (player.getBoundsInParent().intersects(spike.getBoundsInParent())) {
                    setHealth(getHealth() - 1);
                    if (getHealth() == 0) {
                        Image image0 = MediaManager.loadImage("level/0hearts.png");
                        currentHearts.setImage(image0);
                        failLevel.failLevel();
                    } else {
                        player.setTranslateX(spawnPositionX);
                        player.setTranslateY(spawnPositionY);
                        levelRootCamera.setLayoutX(spawnPositionX);
                        levelRootCamera.setLayoutY(spawnPositionY);
                    }
                }
            }

            //Checks player orientation for texture
            if (movingRight) {
                if (!wasMovingRight) {
                    player.setCharTexture(player.getIdleRight());
                    wasMovingRight = true;
                } else wasMovingRight = false;
            } else {
                if (wasMovingRight) {
                    player.setCharTexture(player.getIdleLeft());
                    wasMovingRight = false;
                } else wasMovingRight = true;
            }


            if (levelFinish.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (levelFinished()) {
                    if (movingRight) {
                        player.setTranslateX(player.getTranslateX() - 1);
                    } else {
                        player.setTranslateX(player.getTranslateX() + 1);
                    }
                    Image image0 = MediaManager.loadImage("level/5Coins.png");
                    currentCollectibles.setImage(image0);
                    levelFinish.finishLevel();
                    timerTimeline.stop();
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
    private void movePlayerY(int amount) {
        boolean movingDown = amount > 0;

        for (int i = 1; i <= Math.abs(amount); i++) {
            for (Node platform : platforms) {
                if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    //collision detected
                    if (movingDown) {
                        player.setTranslateY(player.getTranslateY() - 1);
                        playerCanJump = true;
                    } else {
                        player.setTranslateY(player.getTranslateY() + 1);
                        playerVelocity = playerVelocity.add(0, -playerVelocity.getY()); //reset jump velocity
                    }
                    return;
                }
            }
            //Player looses one life if it touches a spike and respawns at the spawn. Camera follows to spawn
            for (Node spike : spikes) {
                if (player.getBoundsInParent().intersects(spike.getBoundsInParent())) {
                    setHealth(getHealth() - 1);
                    if (getHealth() == 0) {
                        Image image0 = MediaManager.loadImage("level/0hearts.png");
                        currentHearts.setImage(image0);
                        failLevel.failLevel();
                    } else {
                        player.setTranslateX(spawnPositionX);
                        player.setTranslateY(spawnPositionY);
                        levelRootCamera.setLayoutX(spawnPositionX);
                        levelRootCamera.setLayoutY(spawnPositionY);
                    }
                }
            }
            if (levelFinish.getBoundsInParent().intersects(player.getBoundsInParent())) {
                if (levelFinished()) {
                    if (movingDown) {
                        player.setTranslateX(player.getTranslateX() - 1);
                    } else {
                        player.setTranslateX(player.getTranslateX() + 1);
                    }
                    Image image0 = MediaManager.loadImage("level/5Coins.png");
                    currentCollectibles.setImage(image0);
                    levelFinish.finishLevel();
                    timerTimeline.stop();
                }
            }
            player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
        }

    }

    /*
     *  Updates the HealthBar to the actual lives
     *  --> half-heart = 1 life
     */
    private void updateHealthPicture() {
        Image image0 = MediaManager.loadImage("level/0hearts.png");
        Image image1 = MediaManager.loadImage("level/1hearts.png");
        Image image2 = MediaManager.loadImage("level/2hearts.png");
        Image image3 = MediaManager.loadImage("level/3hearts.png");
        Image image4 = MediaManager.loadImage("level/4hearts.png");
        Image image5 = MediaManager.loadImage("level/5hearts.png");
        Image image6 = MediaManager.loadImage("level/6hearts.png");
        switch (health) {
            case 0:
                currentHearts.setImage(image0);
                loadGameOver();
                break;
            case 1:
                currentHearts.setImage(image1);
                break;
            case 2:
                currentHearts.setImage(image2);
                break;
            case 3:
                currentHearts.setImage(image3);
                break;
            case 4:
                currentHearts.setImage(image4);
                break;
            case 5:
                currentHearts.setImage(image5);
                break;
            case 6:
                currentHearts.setImage(image6);
                break;
            default:
                // Handling for other potential values of health:
                // Implement desired behavior or error handling here.
                break;
        }
    }

    /*
    Updates the Collectibles Graphic
     */
    public void updateCollectiblePicture() {
        Image image0 = MediaManager.loadImage("level/0Coins.png");
        Image image1 = MediaManager.loadImage("level/1Coins.png");
        Image image2 = MediaManager.loadImage("level/2Coins.png");
        Image image3 = MediaManager.loadImage("level/3Coins.png");
        Image image4 = MediaManager.loadImage("level/4Coins.png");
        Image image5 = MediaManager.loadImage("level/5Coins.png");
        switch (collectibles.size()) {
            case 0:
                currentCollectibles.setImage(image5);
                break;
            case 1:
                currentCollectibles.setImage(image4);
                break;
            case 2:
                currentCollectibles.setImage(image3);
                break;
            case 3:
                currentCollectibles.setImage(image2);
                break;
            case 4:
                currentCollectibles.setImage(image1);
                break;
            case 5:
                currentCollectibles.setImage(image0);
                break;
            default:
                // Handling for other potential values of collectibles:
                // Implement desired behavior or error handling here.
                break;
        }
    }

    /**
     * Toggles debug info visibility.
     */
    private void toggleDebug() {
        debugInfo.setVisible(!debugInfo.isVisible());
    }


    /**
     * Loads a Level from level data
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level or null if the level could not be loaded
     */
    public Scene loadLevel(int levelId) {
        loadedLevelID = levelId;
        setHealth(6);
        //TODO: refine level loading

        platforms.clear();
        spikes.clear();
        collectibles.clear();

        Pane levelRoot = new Pane();
        Pane bgRoot = new Pane();
        Pane GUIRoot = new Pane();

        GUIRoot.getChildren().add(debugInfo);

        debugInfo.setVisible(false);
        GUIRoot.getChildren().add(GUI);
        GUIRoot.getChildren().add(dialogBox);
        dialogBox.setVisible(false);
        BackgroundImage bg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/background_layer_1.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        bgRoot.setMinWidth(GameProperties.WIDTH);
        bgRoot.setMinHeight(GameProperties.HEIGHT);
        bgRoot.setBackground(new Background(bg));

        levelRoot.setBackground(Background.EMPTY);
        GUIRoot.setBackground(Background.EMPTY);

        String[] levelData = LevelData.Levels.getOrDefault(levelId, null);
        if (levelData == null) return null;
        levelRoot.setMinWidth(GameProperties.WIDTH);
        levelRoot.setMinHeight(GameProperties.HEIGHT);
        //process each line and make platforms
        for (int i = 0; i < levelData.length; i++) {
            char[] tiles = levelData[i].toCharArray();
            for (int j = 0; j < tiles.length; j++) {
                switch (tiles[j]) {
                    case '-': //platform
                        Platform platform = new Platform(
                                j * GameProperties.TILE_UNIT,
                                i * GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                TileManager.getTile(levelData, i, j));
                        platforms.add(platform);
                        levelRoot.getChildren().add(platform);
                        break;
                    case 's': //player spawn
                        //TODO: outsource player into its own class?
                        player = new Player(GameProperties.TILE_UNIT - 10, GameProperties.TILE_UNIT - 10);
                        //set spawn for player
                        player.setTranslateX(GameProperties.TILE_UNIT * j);
                        player.setTranslateY(GameProperties.TILE_UNIT * i);
                        spawnPositionX = GameProperties.TILE_UNIT * j;
                        spawnPositionY = GameProperties.TILE_UNIT * i;
                        levelRoot.getChildren().add(player);
                        break;
                    case '^': //Spike Hindernis wird platziert
                        Spike spike = new Spike(
                                j * GameProperties.TILE_UNIT,
                                i * GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT);
                        spikes.add(spike);
                        levelRoot.getChildren().add(spike);
                        break;
                    case 'c': //set coin placement
                        Collectible coin = new Collectible(
                                j * GameProperties.TILE_UNIT,
                                i * GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT,
                                GameProperties.TILE_UNIT);
                        levelRoot.getChildren().add(coin);
                        collectibles.add(coin);
                        break;
                    case 'F': //level exit
                        xFail = j * GameProperties.TILE_UNIT;
                        yFail = i * GameProperties.TILE_UNIT;
                        levelFinish = new LevelFinish(
                                j * GameProperties.TILE_UNIT,
                                i * GameProperties.TILE_UNIT,
                                this);
                        levelRoot.getChildren().add(levelFinish);
                        break;
                }
            }
        }
        startTime = System.currentTimeMillis();
        timerLabel = new Label();
        timerLabel.setFont(new Font(20));
        timerLabel.setLayoutX(10);
        timerLabel.setLayoutY(10);
        timerLabel.setTextFill(Color.WHITE);
        GUIRoot.getChildren().add(timerLabel);

        // Update timer every second
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
        //game Over exit
        failLevel = new LevelFail(
                xFail,
                yFail,
                this);
        levelRoot.getChildren().add(failLevel);
        //create Health-Bar
        Image image = MediaManager.loadImage("level/6hearts.png");
        currentHearts = new ImageView(image);

        currentHearts.setLayoutX(GameProperties.WIDTH - 120);
        currentHearts.setLayoutY(0);

        Image imageCoin = MediaManager.loadImage("level/0Coins.png");
        currentCollectibles = new ImageView(imageCoin);

        currentCollectibles.setLayoutX(GameProperties.WIDTH - 170);
        currentCollectibles.setLayoutY(GameProperties.HEIGHT - 80);

        GUIRoot.getChildren().add(currentCollectibles);
        GUIRoot.getChildren().add(currentHearts);
        levelRootCamera = levelRoot;


        Pane mainPane = new Pane();
        mainPane.getChildren().addAll(bgRoot, levelRoot, GUIRoot);

        loadedLevel = new Scene(mainPane);
        return loadedLevel;

    }

    /**
     * starts the Level update cycle
     */
    public void startLevel() throws LevelNotLoadedException {
        if (loadedLevel == null) throw new LevelNotLoadedException("No Level loaded.");

        playerVelocity = new Point2D(0, 0);
        pressedKeys.clear();


        //Camera movement X Y
        player.translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 300 && offset < GameProperties.WIDTH) {
                levelRootCamera.setLayoutX(-(offset - 300));
            } else if (offset <= 300) {
                levelRootCamera.setLayoutX(0);
            } else {
                levelRootCamera.setLayoutX(-(GameProperties.WIDTH - 600));
            }

        });
        player.translateYProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            int maxYOffset = GameProperties.HEIGHT - 600;
            if (offset > 300 && offset < GameProperties.HEIGHT) {
                levelRootCamera.setLayoutY(-(offset - 300));
            } else if (offset <= 300) {
                levelRootCamera.setLayoutY(0);
            } else {
                levelRootCamera.setLayoutY(-maxYOffset);
            }

        });

        //position and style frame counter
        debugInfo.setLayoutX(0);
        debugInfo.setLayoutY(10);
        debugInfo.setTextFill(Color.WHITE);
        debugInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        GUI.setLayoutX((double) GameProperties.WIDTH / 2);
        GUI.setLayoutY(10);
        debugInfo.setTextFill(Color.WHITE);
        debugInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        //add listeners for key presses
        loadedLevel.setOnKeyPressed(keypress -> {
            pressedKeys.put(keypress.getCode(), true);
            if (keypress.getCode().equals(GameProperties.DEBUGVIEW)) {
                toggleDebug();
            }
        });
        loadedLevel.setOnKeyReleased(keypress -> pressedKeys.put(keypress.getCode(), false));

        Duration FPS = Duration.millis((double) 1000 / GameProperties.FPS); //FPS
        KeyFrame updateFrame = new KeyFrame(FPS, event -> {
            update();
        });

        if (updateTimeline != null) updateTimeline.stop(); //stop timeline if previous level was loaded
        updateTimeline = new Timeline(updateFrame);
        updateTimeline.setCycleCount(Animation.INDEFINITE);
        updateTimeline.play();
    }

    /*
    Timer Calculation

     */

    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - startTime) / 1000;
        long minutes = elapsedSeconds / 60;
        long seconds = elapsedSeconds % 60;

        // Format time as MM:SS
        String formattedTime = String.format("Time: %02d:%02d", minutes, seconds);
        timerLabel.setText(formattedTime);
    }


    /*
    Is showing GameOver Screen
     */
    private void loadGameOver() {
        failLevel.failLevel();
        timerTimeline.stop();
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
    private void update() {
        debugInfo.setText("FRAME: " + frameCounter++ + System.lineSeparator() + "Player: (" + player.getTranslateX() + " | " + player.getTranslateY() + ")" + System.lineSeparator() + "VELOCITY: " + playerVelocity.toString());

        GUI.setText("Collectibles left: " + collectibles.size());

        //TODO: fix collision bugs
        //process player input
        if (isPressed(GameProperties.LEFT)) movePlayerX(-GameProperties.PLAYER_SPEED);
        if (isPressed(GameProperties.RIGHT)) movePlayerX(GameProperties.PLAYER_SPEED);
        if (isPressed(GameProperties.JUMP)) jumpPlayer();
        //assess the gravity of the situation
        if (playerVelocity.getY() < GameProperties.TERMINAL_VELOCITY) {
            playerVelocity = playerVelocity.add(0, GameProperties.GRAVITY);
        }
        movePlayerY((int) playerVelocity.getY());

        Iterator<Node> iterator = collectibles.iterator();
        while (iterator.hasNext()) {
            Collectible coin = (Collectible) iterator.next();
            if (coin.getBoundsInParent().intersects(player.getBoundsInParent())) {
                coin.setVisible(false); //TODO: Handle Collected Coin
                iterator.remove();
            }
        }

        if (playerVelocity.getY() == 0) {
            if (levelFinished()) {
                if (levelFinish.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    levelFinish.finishLevel();
                }
            }
        }
        updateCollectiblePicture();
        enforceFrameBounds();
        updateHealthPicture();

        //TODO: keep player from exiting frame (Player can fall down)
        //TODO: scrolling
        //TODO: finish conditions

    }

    public boolean levelFinished() {
        //TODO: check for collectibles
        return collectibles.isEmpty();
    }

    public int getLoadedLevelID() {
        return loadedLevelID;
    }

    /**
     * Displays a Dialog pane.
     *
     * @param options Buttons for dialog options must have EventHandlers
     */
    public void showDialog(Button... options) {

        //TODO: fix formatting pls i can't deal with this
        dialogBox.getChildren().clear();
        dialogBox.setMinHeight(200);
        dialogBox.setMinWidth(400);
        dialogBox.setMinWidth(400);
        dialogBox.setMinHeight(200);
        BackgroundImage backgroundImg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/LevelFinished.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImg);
        dialogBox.setBackground(background);

        int buttonHeight = 20;
        int buttonAmount = 1;
        int spacing = 10;

        for (Button option : options) {

            option.setPrefWidth(200);
            option.setMinHeight(buttonHeight);
            option.setAlignment(Pos.CENTER);
            option.setLayoutX((dialogBox.getWidth() / 2) - (option.getPrefWidth() / 2));
            option.setLayoutY(110 + (buttonHeight + spacing) * buttonAmount++);

            dialogBox.getChildren().add(option);
        }

        dialogBox.setLayoutX((double) (GameProperties.WIDTH / 2) - (dialogBox.getWidth() / 2));
        dialogBox.setLayoutY((double) (GameProperties.HEIGHT / 2) - (dialogBox.getHeight() / 2));
        dialogBox.toFront();
        dialogBox.setVisible(true);

    }

    /**
     * Hides the dialog pane.
     */
    public void hideDialog() {
        dialogBox.setVisible(false);
        dialogBox.getChildren().clear();
    }

    /**
     * Displays a Dialog pane.
     *
     * @param options Buttons for dialog options must have EventHandlers
     */
    public void showDialogDead(Button... options) {

        //TODO: fix formatting pls i can't deal with this
        dialogBox.getChildren().clear();
        dialogBox.setMinHeight(200);
        dialogBox.setMinWidth(400);
        dialogBox.setMinWidth(400);
        dialogBox.setMinHeight(200);
        BackgroundImage backgroundImg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/GameOver.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImg);
        dialogBox.setBackground(background);

        int buttonHeight = 20;
        int buttonAmount = 1;
        int spacing = 10;

        for (Button option : options) {

            option.setPrefWidth(200);
            option.setMinHeight(buttonHeight);
            option.setAlignment(Pos.CENTER);
            option.setLayoutX((dialogBox.getWidth() / 2) - (option.getPrefWidth() / 2));
            option.setLayoutY(105 + (buttonHeight + spacing) * buttonAmount++);

            dialogBox.getChildren().add(option);
        }


        dialogBox.setLayoutX((double) (GameProperties.WIDTH / 2) - (dialogBox.getWidth() / 2));
        dialogBox.setLayoutY((double) (GameProperties.HEIGHT / 2) - (dialogBox.getHeight() / 2));
        dialogBox.toFront();
        dialogBox.setVisible(true);

    }

    /*
    hide Dialog for gameOver
     */
    public void hideDialogDead() {
        dialogBoxDead.setVisible(false);
        dialogBoxDead.getChildren().clear();
    }

}
