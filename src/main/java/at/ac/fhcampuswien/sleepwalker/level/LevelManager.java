package at.ac.fhcampuswien.sleepwalker.level;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import at.ac.fhcampuswien.sleepwalker.level.entities.Collectible;
import at.ac.fhcampuswien.sleepwalker.level.entities.LevelStatus;
import at.ac.fhcampuswien.sleepwalker.level.entities.PowerUpHealth;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
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

import static at.ac.fhcampuswien.sleepwalker.GameManager.getInstance;

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
    private Level currentLevel;
    private Timeline updateTimeline;
    private LevelStatus failLevel = null;
    private final Pane dialogBox = new Pane();
    private final Pane decoBox = new Pane();
    private Pane GUIRoot;
    private Pane levelRootCamera;
    private int loadedLevelID;
    private long frameCounter;
    private Scene loadedLevel;
    private ImageView currentHearts;
    private ImageView currentCollectibles;
    private Timeline timerTimeline;
    private long startTime;
    private Label timerLabel;
    private long pauseStartTime;
    private int enemyMovementControl;
    private boolean enemyDirection = false;
    private boolean portalOpen = false;
    private boolean gameOverStatus = false;
    private boolean isPaused =false;
    private Pane pauseMenu;

    public LevelManager(){
        pressedKeys = new HashMap<>();
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    public boolean isGameOverStatus() {
        return gameOverStatus;
    }

    public void setGameOverStatus(boolean gameOverStatus) {
        this.gameOverStatus = gameOverStatus;
    }

    public boolean isEnemyDirection() {
        return enemyDirection;
    }

    public void setEnemyDirection(boolean enemyDirection) {
        this.enemyDirection = enemyDirection;
    }

    public int getEnemyMovementControl() {
        return enemyMovementControl;
    }

    public void setEnemyMovementControl(int enemyMovementControl) {
        this.enemyMovementControl = enemyMovementControl;
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
        switch(currentLevel.Player().getHealth()){
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
        setEnemyMovementControl(300);

        GUIRoot = new Pane();
        GUIRoot.setBackground(Background.EMPTY);
        dialogBox.setVisible(false);
        decoBox.setVisible(false);
        debugInfo.setVisible(false);
        GUIRoot.getChildren().add(debugInfo);
        GUIRoot.getChildren().add(dialogBox);
        GUIRoot.getChildren().add(decoBox);

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

        pressedKeys.clear();
        currentLevel.Player().init();

        //Camera movement X Y
        currentLevel.Player().translateXProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            if(offset > 300 && offset < currentLevel.getWidth()){
                levelRootCamera.setLayoutX(-(offset - 300));
            } else if(offset <= 300){
                levelRootCamera.setLayoutX(0);
            } else{
                levelRootCamera.setLayoutX(-(currentLevel.getWidth() - 600));
            }

        });
        currentLevel.Player().translateYProperty().addListener((observable, oldValue, newValue) -> {
            int offset = newValue.intValue();
            int maxYOffset = currentLevel.getHeight() - 600;
            if(offset > 300 && offset < currentLevel.getHeight()){
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

        debugInfo.setTextFill(Color.WHITE);
        debugInfo.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        //add listeners for key presses
        loadedLevel.setOnKeyPressed(keypress -> {
            pressedKeys.put(keypress.getCode(), true);
            if(keypress.getCode().equals(GameProperties.DEBUGVIEW)){
                toggleDebug();
            }
        });
        loadedLevel.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                togglePause();
            }
            pressedKeys.put(keyEvent.getCode(), true);
        });
        loadedLevel.setOnKeyReleased(keypress -> pressedKeys.put(keypress.getCode(), false));

        Duration FPS = Duration.millis((double) 1000 / GameProperties.FPS); //FPS
        KeyFrame updateFrame = new KeyFrame(FPS, event -> update());

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
        setGameOverStatus(true);
        //currentLevel.Enemy().setTranslateX(currentLevel.getEnemySpawn().getX());
        //currentLevel.Enemy().setTranslateY(currentLevel.getEnemySpawn().getY());
        failLevel.failLevel();
    }
    private void moveEnemyX() {
        if (!isGameOverStatus()) {
            //EnemyDirection = true --> Moving Right
            //EnemyDirection = false --> Moving Left
            if (getEnemyMovementControl() == 0) {
                setEnemyDirection(true);
                currentLevel.Enemy().setCharTexture(currentLevel.Enemy().getIdleRight());
            }
            if (getEnemyMovementControl() == 300) {
                setEnemyDirection(false);
                currentLevel.Enemy().setCharTexture(currentLevel.Enemy().getIdleLeft());
            }
            if (isEnemyDirection()) {
                currentLevel.Enemy().setTranslateX(currentLevel.Enemy().getTranslateX() + 1);
                setEnemyMovementControl(getEnemyMovementControl() + 1);
            } else {
                currentLevel.Enemy().setTranslateX(currentLevel.Enemy().getTranslateX() - 1);
                setEnemyMovementControl(getEnemyMovementControl() - 1);
            }
        }
    }

    private void enforceFrameBounds(){
        double playerX = currentLevel.Player().getTranslateX();
        double playerY = currentLevel.Player().getTranslateY();

        // Limit the player on the X-axis
        if(playerX < 0){
            currentLevel.Player().setTranslateX(0);
        } else if(playerX > currentLevel.getWidth() - currentLevel.Player().getBoundsInParent().getWidth()){
            currentLevel.Player().setTranslateX(currentLevel.getWidth() - currentLevel.Player().getBoundsInParent().getWidth());
        }

        // Limit the player on the Y-axis
        if(playerY < 0){
            currentLevel.Player().setTranslateY(0);
        } else if(playerY > currentLevel.getHeight() - currentLevel.Player().getBoundsInParent().getHeight()){
            currentLevel.Player().setTranslateY(currentLevel.getHeight() - currentLevel.Player().getBoundsInParent().getHeight());
        }
    }

    /**
     * updates the level every frame
     */
    private void update(){
        debugInfo.setText("FRAME: " + frameCounter++ + System.lineSeparator() + "Player: (" + currentLevel.Player().getTranslateX() + " | " + currentLevel.Player().getTranslateY() + ")" + System.lineSeparator() + "VELOCITY: " + currentLevel.Player().getPlayerVelocity().toString());
        //process player input
        if(isPressed(GameProperties.LEFT)) currentLevel.Player().moveX(-GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.RIGHT)) currentLevel.Player().moveX(GameProperties.PLAYER_SPEED);
        if(isPressed(GameProperties.JUMP)) currentLevel.Player().jump();
        currentLevel.Player().update();

        Iterator<Node> iterator = currentLevel.Collectibles().iterator();
        while(iterator.hasNext()){
            Collectible coin = (Collectible) iterator.next();
            if(coin.getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                MediaManager.playSoundFX("audio/sound/coin.wav");
                coin.setVisible(false);
                iterator.remove();
            }
        }
        /*
        With touching the Power Up Health, you will gain +2 health
         */
        Iterator<Node> iteratorHealth = currentLevel.PowerUpHealth().iterator();
        while(iteratorHealth.hasNext()){
            PowerUpHealth powerUpHealth = (PowerUpHealth) iteratorHealth.next();
            if(powerUpHealth.getBoundsInParent().intersects(currentLevel.Player().getBoundsInParent())){
                MediaManager.playSoundFX("audio/sound/coin.wav");
                currentLevel.Player().heal(2);
                powerUpHealth.setVisible(false);
                iteratorHealth.remove();
            }
        }

        updateCollectiblePicture();
        if(!portalOpen && levelFinished()){
            currentLevel.Finish(false).openPortal();
            portalOpen = true;
        }
        if (currentLevel.isEnemyExist()) {
            moveEnemyX();
        }
        enforceFrameBounds();
        updateHealthPicture();
    }

    public boolean levelFinished(){
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
        dialogBox.getChildren().clear();
        decoBox.getChildren().clear();
        dialogBox.toFront();
        dialogBox.setVisible(true);
        dialogBox.setMinHeight(300);
        dialogBox.setMinWidth(400);
        decoBox.setMinHeight(137);
        decoBox.setMinWidth(560);
        decoBox.setVisible(true);
        decoBox.toFront();

        if(finished){
            BackgroundImage backgroundImg = new BackgroundImage(MediaManager.loadImage("ui/gui/LevelComplete.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImg);
            dialogBox.setBackground(background);
            BackgroundImage sparklesImg = new BackgroundImage(MediaManager.loadImage("ui/gui/Sparkles.gif"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background sparkles = new Background(sparklesImg);
            decoBox.setBackground(sparkles);
        } else{
            BackgroundImage backgroundImg = new BackgroundImage(MediaManager.loadImage("ui/gui/GameOver.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImg);
            dialogBox.setBackground(background);
            BackgroundImage loserImg = new BackgroundImage(MediaManager.loadImage("ui/gui/Unfortunate.gif"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background loser = new Background(loserImg);
            decoBox.setBackground(loser);
        }

        int buttonHeight = 34;
        int buttonAmount = 1;
        int spacing = 10;

        dialogBox.getStylesheets().add((String.valueOf(Sleepwalker.class.getResource("ui/styles.css"))));

        for(Button option : options){

            option.getStyleClass().add("button");
            option.setPrefWidth(200);
            option.setMinHeight(buttonHeight);
            option.setAlignment(Pos.CENTER);
            option.setLayoutX(100);
            option.setStyle("-fx-padding: 5, 20, 5, 20");
            option.setLayoutY(110 + (buttonHeight + spacing) * buttonAmount++);

            dialogBox.getChildren().add(option);
        }
        dialogBox.setLayoutX((double) (GameProperties.WIDTH / 2) - 200);
        dialogBox.setLayoutY((double) (GameProperties.HEIGHT / 2) - 150);
        decoBox.setLayoutX((double) (GameProperties.WIDTH / 2) - 295);
        decoBox.setLayoutY((double) (GameProperties.HEIGHT / 2) - 130);

    }

    /**
     * Pauses the level update cycle.
     */
    public void pause(){
        pauseStartTime = System.currentTimeMillis();
        updateTimeline.pause();
        timerTimeline.pause();
    }

    /**
     * Resumes the level update cycle.
     */
    public void resume(){
        long pauseDuration = System.currentTimeMillis() - pauseStartTime;
        startTime += pauseDuration;
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

    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            pause();
            showPauseMenu();
        } else {
            hidePauseMenu();
            resume();
        }
    }

    private void showPauseMenu() {
        pauseMenu = new Pane();
        pauseMenu.setStyle("-fx-background-color: rgba(0,0,0,0.16);");
        pauseMenu.setPrefSize(300, 200);
        pauseMenu.setLayoutX((double) (GameProperties.WIDTH - 300) / 2);
        pauseMenu.setLayoutY((double) (GameProperties.HEIGHT - 300) / 2);

        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setLayoutX(50);
        menuBox.setLayoutY(50);

        Button resumeButton = new Button("Resume");
        resumeButton.setPrefSize(200, 30);
        resumeButton.getStyleClass().add("button");
        resumeButton.setOnAction(e -> togglePause());
        resumeButton.setLayoutX(50);
        resumeButton.setLayoutY(20);

        Button muteBackground = new Button("Mute");
        muteBackground.setPrefSize(200, 30);
        muteBackground.getStyleClass().add("button");
        muteBackground.setOnAction(e -> MediaManager.setMusicVolume(0));
        muteBackground.setLayoutX(50);
        muteBackground.setLayoutY(70);

        Button mainMenuButton = new Button("MainMenu");
        mainMenuButton.setPrefSize(200, 30);
        mainMenuButton.getStyleClass().add("button");
        mainMenuButton.setOnAction(e -> getInstance().showMainMenu());
        mainMenuButton.setLayoutX(50);
        mainMenuButton.setLayoutY(120);

        pauseMenu.getChildren().addAll(resumeButton, mainMenuButton, muteBackground);
        pauseMenu.getChildren().add(menuBox);
        pauseMenu.getStylesheets().add((String.valueOf(Sleepwalker.class.getResource("ui/styles.css"))));
        GUIRoot.getChildren().add(pauseMenu);
    }
    private void hidePauseMenu() {
        GUIRoot.getChildren().remove(pauseMenu);

    }
}
