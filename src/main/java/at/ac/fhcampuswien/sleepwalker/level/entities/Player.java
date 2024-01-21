package at.ac.fhcampuswien.sleepwalker.level.entities;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.MediaManager;
import at.ac.fhcampuswien.sleepwalker.level.Level;
import at.ac.fhcampuswien.sleepwalker.level.LevelManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player extends Rectangle {
    private final LevelManager levelManager;
    private Level level;
    private final ImagePattern idleLeft = new ImagePattern(MediaManager.loadImage("animation/player/Idle_left.gif"));
    private final ImagePattern idleRight = new ImagePattern(MediaManager.loadImage("animation/player/Idle_right.gif"));
    private final Timeline deathAnimation;
    private int health;
    private final int maxHealth;
    private boolean playerCanJump;
    private boolean playerOnGround;
    boolean wasMovingRight = true;
    private Point2D playerVelocity;

    public int getHealth(){
        return health;
    }
    public ImagePattern getIdleLeft() {
        return idleLeft;
    }

    public ImagePattern getIdleRight() {
        return idleRight;
    }

    public void setCharTexture(ImagePattern texture) {
        this.setFill(texture);
    }

    public Player(int height, int width, LevelManager levelManager) {
        super();
        this.levelManager = levelManager;
        this.level = levelManager.getCurrentLevel();
        this.maxHealth = 6;
        this.health = maxHealth;
        this.playerVelocity = new Point2D(0, 0);
        deathAnimation = getDeathAnimation();
        setWidth(width);
        setHeight(height);
        setCharTexture(idleRight);

    }

    public void init(){
        this.level = levelManager.getCurrentLevel();
        this.health = maxHealth;
    }

    public void jump(){
        if(playerCanJump){
            MediaManager.playSoundFX("audio/sound/jump.wav");
            playerVelocity = playerVelocity.add(0, -GameProperties.PLAYER_JUMP);
            playerCanJump = false;
            playerOnGround = false;
        }
    }

    public void update(){
        //assess the gravity of the situation
        if(playerVelocity.getY() < GameProperties.TERMINAL_VELOCITY){
            playerVelocity = playerVelocity.add(0, GameProperties.GRAVITY);
        }

        moveY((int) playerVelocity.getY());
    }

    public Point2D getPlayerVelocity(){
        return playerVelocity;
    }

    /**
     * Moves the Player along the X Axis and checks for collision
     * if a collision is detected (overlap), player is moved back 1 unit.
     * Player can not move through platforms.
     *
     * @param amount how far the player is moved
     */
    public void moveX(int amount){
        boolean movingRight = amount > 0;

        for(int i = 1; i <= Math.abs(amount); i++){
            for(Node platform : level.Platforms()){
                if(level.Player().getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingRight){
                        level.Player().setTranslateX(level.Player().getTranslateX() - 1);
                    } else{
                        level.Player().setTranslateX(level.Player().getTranslateX() + 1);
                    }
                    return;
                }
            }

            //Checks player orientation for texture
            if(movingRight){
                if(!wasMovingRight){
                    level.Player().setCharTexture(level.Player().getIdleRight());
                    wasMovingRight = true;
                } else wasMovingRight = false;
            } else{
                if(wasMovingRight){
                    level.Player().setCharTexture(level.Player().getIdleLeft());
                    wasMovingRight = false;
                } else wasMovingRight = true;
            }

            if(level.Finish(true).getBoundsInParent().intersects(level.Player().getBoundsInParent())){
                if(levelManager.levelFinished()){
                    level.Finish(true).finishLevel();
                    return;
                }
            }
            level.Player().setTranslateX(level.Player().getTranslateX() + (movingRight ? 1 : -1));
        }

    }

    /**
     * Moves the Player along the Y Axis and checks for collision
     * if a collision is detected (overlap), player is moved back 1 unit.
     * Player can not move through platforms
     *
     * @param amount how far the player is moved
     */
    public void moveY(int amount){
        boolean movingDown = amount > 0;

        for(int i = 0; i <= Math.abs(amount); i++){
            playerOnGround = false;
            for(Node platform : level.Platforms()){
                if(level.Player().getBoundsInParent().intersects(platform.getBoundsInParent())){
                    //collision detected
                    if(movingDown){
                        if(getTranslateY() + getHeight() == platform.getTranslateY()){
                            level.Player().setTranslateY(level.Player().getTranslateY() - 1);
                            playerCanJump = true;
                            playerOnGround = true;
                            return;
                        }
                    } else{
                        if(getTranslateY() == platform.getTranslateY() + GameProperties.TILE_UNIT){
                            level.Player().setTranslateY(level.Player().getTranslateY() + 1);
                            playerVelocity = new Point2D(0,0);
                            return;
                        }
                    }
                }
            }
            //Player looses one life if it touches a spike and respawns at the spawn. Camera follows to spawn
            for(Node spike : level.Spikes()){
                if(level.Player().getBoundsInParent().intersects(spike.getBoundsInParent())){
                    takeDamage(1);
                    return;
                }
            }
            //Player losses one life if it touches an enemy and respawn at the spawn.
            if(level.isEnemyExist()){
                if(level.Player().getBoundsInParent().intersects(level.Enemy().getBoundsInParent())){
                    takeDamage(1);
                    return;
                }
            }
            if(level.Finish(true).getBoundsInParent().intersects(level.Player().getBoundsInParent())){
                if(levelManager.levelFinished()){
                    level.Finish(true).finishLevel();
                    return;
                }
            }
            if (i > 0) level.Player().setTranslateY(level.Player().getTranslateY() + (movingDown ? 1 : -1));
        }

    }

    /**
     * Damages the Player and subtracts health.
     *
     * @param amount damage points taken
     */
    public void takeDamage(int amount){
        this.health -= amount;
        if (health > 0) die();
        else level.fail();
    }

    public void heal(int amount){
        this.health += amount;
        if(health > maxHealth) health = maxHealth;
    }

    public void die(){
        levelManager.pause();
        MediaManager.playSoundFX("audio/sound/impact.wav");

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this);
        fadeTransition.setFromValue(100);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> {
            setOpacity(100);
            levelManager.respawn();
        });
        deathAnimation.setOnFinished(t -> {
            setCharTexture(idleRight);
        });
        deathAnimation.play();
        fadeTransition.play();
    }

    private Timeline getDeathAnimation(){
        KeyFrame deathGif = new KeyFrame(Duration.ZERO, t -> {
            setCharTexture(new ImagePattern(MediaManager.loadImage("animation/player/Death.gif")));
        });
        KeyFrame waitTime = new KeyFrame(Duration.seconds(1));
        return new Timeline(deathGif, waitTime);
    }
}
