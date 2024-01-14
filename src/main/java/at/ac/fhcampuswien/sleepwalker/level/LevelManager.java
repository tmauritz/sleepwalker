package at.ac.fhcampuswien.sleepwalker.level;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import at.ac.fhcampuswien.sleepwalker.level.entities.Collectible;
import at.ac.fhcampuswien.sleepwalker.level.entities.LevelStatus;
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

import java.util.HashMap;
import java.util.Iterator;
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
    private final Label GUI = new Label("TOP GUI");
    private final Map<KeyCode, Boolean> pressedKeys;
    private Level currentLevel;
    private Timeline updateTimeline;
    private LevelStatus failLevel = null;
    private final Pane dialogBox = new Pane();
    private Pane GUIRoot;
    private Pane levelRootCamera;
    private int loadedLevelID;
    private long frameCounter;
    private Point2D playerVelocity;
    private boolean playerCanJump;
    private Scene loadedLevel;
    private ImageView currentHearts;
    private ImageView currentCollectibles;
    private int health = 6;
    boolean wasMovingRight = true;
    private Timeline timerTimeline;
    private long startTime;
    private Label timerLabel;
    private boolean portalOpen = false;

    public LevelManager(){
        pressedKeys = new HashMap<>();
        playerVelocity = new Point2D(0, 0);
        playerCanJump = true;
    }

    /*
    Set the health of the player
     */
    public int getHealth(){
        return health;
    }
    /*
    Get the health of the player
     */

    public void setHealth(int health){
        this.health = health;
    }

    private void jumpPlayer(){
        if(playerCanJump){
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
            for(Node platform : currentLevel.Platforms()){
                if(currentLevel.Player().getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingRight){
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() - 1);
                    } else{
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() + 1);
                    }
                    return;
                }
            }
            //Player looses one life if it touches a spike and respawns at the spawn
            for(Node spike : currentLevel.Spikes()){
                if(currentLevel.Player().getBoundsInParent().intersects(spike.getBoundsInParent())){
                    setHealth(getHealth() - 1);
                    if(getHealth() > 0){
                        currentLevel.Player().die();
                    }
                    return;
                }
            }

            //Checks player orientation for texture
            if(movingRight){
                if(!wasMovingRight){
                    currentLevel.Player().setCharTexture(currentLevel.Player().getIdleRight());
                    wasMovingRight = true;
                } else wasMovingRight = false;
            } else{
                if(wasMovingRight){
                    currentLevel.Player().setCharTexture(currentLevel.Player().getIdleLeft());
                    wasMovingRight = false;
                } else wasMovingRight = true;
            }

            if(currentLevel.Finish().getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                if(levelFinished()){
                    if(movingRight){
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() - 1);
                    } else{
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() + 1);
                    }
                    Image image0 = MediaManager.loadImage("level/5Coins.png");
                    currentCollectibles.setImage(image0);
                    currentLevel.Finish().finishLevel();
                    timerTimeline.stop();
                }
            }
            currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() + (movingRight ? 1 : -1));
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
            for(Node platform : currentLevel.Platforms()){
                if(currentLevel.Player().getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingDown){
                        currentLevel.Player().setTranslateY(currentLevel.Player().getTranslateY() - 1);
                        playerCanJump = true;
                    } else{
                        currentLevel.Player().setTranslateY(currentLevel.Player().getTranslateY() + 1);
                        playerVelocity = playerVelocity.add(0, -playerVelocity.getY()); //reset jump velocity
                    }
                    return;
                }
            }
            //Player looses one life if it touches a spike and respawns at the spawn. Camera follows to spawn
            for(Node spike : currentLevel.Spikes()){
                if(currentLevel.Player().getBoundsInParent().intersects(spike.getBoundsInParent())){
                    setHealth(getHealth() - 1);
                    if(getHealth() > 0){
                        currentLevel.Player().die();
                    }
                    return;
                }
            }
            if(currentLevel.Finish().getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                if(levelFinished()){
                    if(movingDown){
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() - 1);
                    } else{
                        currentLevel.Player().setTranslateX(currentLevel.Player().getTranslateX() + 1);
                    }
                    Image image0 = MediaManager.loadImage("level/5Coins.png");
                    currentCollectibles.setImage(image0);
                    currentLevel.Finish().finishLevel();
                    timerTimeline.stop();
                }
            }
            currentLevel.Player().setTranslateY(currentLevel.Player().getTranslateY() + (movingDown ? 1 : -1));
        }

    }

    /*
     *  Updates the HealthBar to the actual lives
     *  --> half-heart = 1 life
     */
    private void updateHealthPicture(){
        Image image0 = MediaManager.loadImage("level/0hearts.png");
        Image image1 = MediaManager.loadImage("level/1hearts.png");
        Image image2 = MediaManager.loadImage("level/2hearts.png");
        Image image3 = MediaManager.loadImage("level/3hearts.png");
        Image image4 = MediaManager.loadImage("level/4hearts.png");
        Image image5 = MediaManager.loadImage("level/5hearts.png");
        Image image6 = MediaManager.loadImage("level/6hearts.png");
        switch(health){
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
    public void updateCollectiblePicture(){
        Image image0 = MediaManager.loadImage("level/0Coins.png");
        Image image1 = MediaManager.loadImage("level/1Coins.png");
        Image image2 = MediaManager.loadImage("level/2Coins.png");
        Image image3 = MediaManager.loadImage("level/3Coins.png");
        Image image4 = MediaManager.loadImage("level/4Coins.png");
        Image image5 = MediaManager.loadImage("level/5Coins.png");
        switch(currentLevel.Collectibles().size()){
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
    private void toggleDebug(){
        debugInfo.setVisible(!debugInfo.isVisible());
    }

    /**
     * Loads a Level from level data
     *
     * @param levelId ID of the level to be loaded
     * @return a Scene containing the loaded level or null if the level could not be loaded
     */
    public Scene loadLevel(int levelId) throws LevelNotLoadedException{
        portalOpen = false;
        loadedLevelID = levelId;
        setHealth(6);

        GUIRoot = new Pane();
        GUIRoot.setBackground(Background.EMPTY);
        dialogBox.setVisible(false);
        debugInfo.setVisible(false);
        GUIRoot.getChildren().add(debugInfo);
        GUIRoot.getChildren().add(GUI);
        GUIRoot.getChildren().add(dialogBox);

        currentLevel = new Level(levelId, this);

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
        failLevel = new LevelStatus(this);
        currentLevel.getLevelRoot().getChildren().add(failLevel);
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
        levelRootCamera = currentLevel.getLevelRoot();

        Pane mainPane = new Pane();
        mainPane.getChildren().addAll(currentLevel.getBgRoot(), currentLevel.getLevelRoot(), GUIRoot);

        loadedLevel = new Scene(mainPane);
        return loadedLevel;

    }

    /**
     * starts the Level update cycle
     */
    public void startLevel() throws LevelNotLoadedException{
        if(loadedLevel == null) throw new LevelNotLoadedException("No Level loaded.");

        playerVelocity = new Point2D(0, 0);
        pressedKeys.clear();

        //Camera movement X Y
        currentLevel.Player().translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            if(offset > 300 && offset < GameProperties.WIDTH){
                levelRootCamera.setLayoutX(-(offset - 300));
            } else if(offset <= 300){
                levelRootCamera.setLayoutX(0);
            } else{
                levelRootCamera.setLayoutX(-(GameProperties.WIDTH - 600));
            }

        });
        currentLevel.Player().translateYProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            int maxYOffset = GameProperties.HEIGHT - 600;
            if(offset > 300 && offset < GameProperties.HEIGHT){
                levelRootCamera.setLayoutY(-(offset - 300));
            } else if(offset <= 300){
                levelRootCamera.setLayoutY(0);
            } else{
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
            if(keypress.getCode().equals(GameProperties.DEBUGVIEW)){
                toggleDebug();
            }
        });
        loadedLevel.setOnKeyReleased(keypress -> pressedKeys.put(keypress.getCode(), false));

        Duration FPS = Duration.millis((double) 1000 / GameProperties.FPS); //FPS
        KeyFrame updateFrame = new KeyFrame(FPS, event -> {
            update();
        });

        if(updateTimeline != null) updateTimeline.stop(); //stop timeline if previous level was loaded
        updateTimeline = new Timeline(updateFrame);
        updateTimeline.setCycleCount(Animation.INDEFINITE);
        updateTimeline.play();
    }

    /*
    Timer Calculation

     */

    private void updateTimer(){
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
    private void loadGameOver(){
        failLevel.failLevel();
    }

    private void enforceFrameBounds(){
        double playerX = currentLevel.Player().getTranslateX();
        double playerY = currentLevel.Player().getTranslateY();

        // Limit the player on the X-axis
        if(playerX < 0){
            currentLevel.Player().setTranslateX(0);
        } else if(playerX > GameProperties.WIDTH - currentLevel.Player().getBoundsInParent().getWidth()){
            currentLevel.Player().setTranslateX(GameProperties.WIDTH - currentLevel.Player().getBoundsInParent().getWidth());
        }

        // Limit the player on the Y-axis
        if(playerY < 0){
            currentLevel.Player().setTranslateY(0);
        } else if(playerY > GameProperties.HEIGHT - currentLevel.Player().getBoundsInParent().getHeight()){
            currentLevel.Player().setTranslateY(GameProperties.HEIGHT - currentLevel.Player().getBoundsInParent().getHeight());
            playerCanJump = true;  // The player can jump again when touching the ground
        }
    }

    /**
     * updates the level every frame
     */
    private void update(){
        debugInfo.setText("FRAME: " + frameCounter++ + System.lineSeparator() + "Player: (" + currentLevel.Player().getTranslateX() + " | " + currentLevel.Player().getTranslateY() + ")" + System.lineSeparator() + "VELOCITY: " + playerVelocity.toString());

        GUI.setText("Collectibles left: " + currentLevel.Collectibles().size());

        //TODO: fix collision bugs
        //process player input
        if(isPressed(GameProperties.LEFT)) movePlayerX(-GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.RIGHT)) movePlayerX(GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.JUMP)) jumpPlayer();
        //assess the gravity of the situation
        if(playerVelocity.getY() < GameProperties.TERMINAL_VELOCITY){
            playerVelocity = playerVelocity.add(0, GameProperties.GRAVITY);
        }
        movePlayerY((int) playerVelocity.getY());

        Iterator<Node> iterator = currentLevel.Collectibles().iterator();
        while(iterator.hasNext()){
            Collectible coin = (Collectible) iterator.next();
            if(coin.getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                coin.setVisible(false); //TODO: Handle Collected Coin
                iterator.remove();
            }
        }

        if(playerVelocity.getY() == 0){
            if(levelFinished()){
                if(currentLevel.Finish().getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                    currentLevel.Finish().finishLevel();
                }
            }
        }
        updateCollectiblePicture();
        if(!portalOpen && levelFinished()){
            currentLevel.Finish().openPortal();
            portalOpen = true;
        }
        enforceFrameBounds();
        updateHealthPicture();

        //TODO: keep player from exiting frame (Player can fall down)
        //TODO: scrolling
    }

    public boolean levelFinished(){
        //TODO: check for collectibles
        return currentLevel.Collectibles().isEmpty();
    }

    public int getLoadedLevelID(){
        return loadedLevelID;
    }

    /**
     * Displays a Dialog pane.
     *
     * @param options Buttons for dialog options must have EventHandlers
     */
    public void showDialog(boolean finished, Button... options){

        //TODO: fix formatting pls i can't deal with this
        dialogBox.getChildren().clear();
        dialogBox.setMinHeight(200);
        dialogBox.setMinWidth(400);
        dialogBox.setMinWidth(400);
        dialogBox.setMinHeight(200);
        if(finished){
            BackgroundImage backgroundImg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/LevelFinished.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImg);
            dialogBox.setBackground(background);
        } else{
            BackgroundImage backgroundImg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/GameOver.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImg);
            dialogBox.setBackground(background);
        }

        int buttonHeight = 20;
        int buttonAmount = 1;
        int spacing = 10;

        for(Button option : options){

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
     * Pauses the level update cycle.
     */
    public void pause(){
        updateTimeline.pause();
        timerTimeline.pause();
    }

    /**
     * Resumes the level update cycle.
     */
    public void resume(){
        updateTimeline.play();
        timerTimeline.play();
    }

    /**
     * Respawns the player at the original spawn point.
     */
    public void respawn(){
        levelRootCamera.setLayoutX(currentLevel.getSpawn().getX());
        levelRootCamera.setLayoutY(currentLevel.getSpawn().getY());
        currentLevel.Player().setTranslateX(currentLevel.getSpawn().getX());
        currentLevel.Player().setTranslateY(currentLevel.getSpawn().getY());
        resume();
    }

    /**
     * Hides the dialog pane.
     */
    public void hideDialog(){
        dialogBox.setVisible(false);
        dialogBox.getChildren().clear();
    }

}
