package at.ac.fhcampuswien.sleepwalker.level;

import at.ac.fhcampuswien.sleepwalker.GameProperties;
import at.ac.fhcampuswien.sleepwalker.Sleepwalker;
import at.ac.fhcampuswien.sleepwalker.TileManager;
import at.ac.fhcampuswien.sleepwalker.exceptions.LevelNotLoadedException;
import at.ac.fhcampuswien.sleepwalker.level.entities.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final LevelManager manager;
    private final List<Node> platforms;
    private final List<Node> spikes;
    private final List<Node> collectibles;
    private final List<Node> powerUpHealth;
    private final List<Node> decoration;
    private Pane levelRoot = new Pane();
    private Pane decoRoot = new Pane();
    private Pane bgRoot = new Pane();
    private Point2D spawn;
    private Player player;
    private Enemy enemy;
    private LevelStatus goal;
    private LevelStatus goalTexture;
    private int width;
    private int height;
    private boolean enemyExist;

    public Level(int levelID, LevelManager manager) throws LevelNotLoadedException{
        this.manager = manager;
        platforms = new ArrayList<>();
        spikes = new ArrayList<>();
        collectibles = new ArrayList<>();
        powerUpHealth = new ArrayList<>();
        decoration = new ArrayList<>();
        loadLevel(levelID);
    }

    public boolean isEnemyExist() {
        return enemyExist;
    }

    public void setEnemyExist(boolean enemyExist) {
        this.enemyExist = enemyExist;
    }

    public List<Node> Platforms(){
        return platforms;
    }

    public List<Node> Spikes(){
        return spikes;
    }

    public List<Node> Collectibles(){
        return collectibles;
    }

    public List<Node> PowerUpHealth(){
        return powerUpHealth;
    }

    public List<Node> Decoration(){
        return decoration;
    }

    public Pane getLevelRoot(){
        return levelRoot;
    }

    public Pane getBgRoot(){
        return bgRoot;
    }

    public Player Player(){
        return player;
    }
    public Enemy Enemy(){
        return enemy;
    }

    public Point2D getSpawn(){
        return spawn;
    }

    public LevelStatus Finish(boolean collision){
        if (collision) return goal;
        else return goalTexture;
    }

    /**
     *
     * @return the level width in on-screen pixels
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return the level height in on-screen pixels
     */
    public int getHeight(){
        return height;
    }

    private void loadLevel(int levelId) throws LevelNotLoadedException{
        //TODO: refine level loading

        levelRoot = new Pane();
        decoRoot = new Pane();
        bgRoot = new Pane();

        BackgroundImage bg = new BackgroundImage(new Image(String.valueOf(Sleepwalker.class.getResource("level/background/background_layer_1.png"))), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        bgRoot.setMinWidth(GameProperties.WIDTH);
        bgRoot.setMinHeight(GameProperties.HEIGHT);
        bgRoot.setBackground(new Background(bg));

        levelRoot.setBackground(Background.EMPTY);
        levelRoot.getChildren().add(decoRoot);

        setEnemyExist(false);

        String[] levelData = LevelData.Levels.getOrDefault(levelId, null);
        String[] decoData = LevelData.Levels.getOrDefault(levelId * 100, null);
        if(levelData == null || decoData == null) throw new LevelNotLoadedException("Unable to load Level " + levelId);
        levelRoot.setMinWidth(GameProperties.WIDTH);
        levelRoot.setMinHeight(GameProperties.HEIGHT);
        //process each line and make platforms
        width = levelData[1].length() * GameProperties.TILE_UNIT;
        height = levelData.length * GameProperties.TILE_UNIT;
        for(int i = 0; i < levelData.length; i++){
            char[] tiles = levelData[i].toCharArray();
            char[] decoTiles = decoData[i].toCharArray();
            for(int j = 0; j < tiles.length; j++){
                switch(tiles[j]){
                    case '-': //platforms
                        Platform platform = new Platform(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, TileManager.getTile(levelData, i, j));
                        platforms.add(platform);
                        levelRoot.getChildren().add(platform);
                        break;
                    case 's': //player spawn
                        player = new Player(GameProperties.TILE_UNIT - 10, GameProperties.TILE_UNIT - 10, manager);
                        //set spawn for player
                        player.setTranslateX(GameProperties.TILE_UNIT * j);
                        player.setTranslateY(GameProperties.TILE_UNIT * i);
                        spawn = new Point2D(GameProperties.TILE_UNIT * j, GameProperties.TILE_UNIT * i);
                        levelRoot.getChildren().add(player);
                        break;
                    case '^': //spikes
                        Spike spike = new Spike(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, false);
                        spikes.add(spike);
                        levelRoot.getChildren().add(spike);
                        break;
                    case 'w': //spikes
                        Spike spike2 = new Spike(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, true);
                        spikes.add(spike2);
                        levelRoot.getChildren().add(spike2);
                        break;
                    case 'c': //coins
                        Collectible coin = new Collectible(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT);
                        levelRoot.getChildren().add(coin);
                        collectibles.add(coin);
                        break;
                    case 'h': //PowerUp - Health
                        PowerUpHealth health = new PowerUpHealth(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT);
                        levelRoot.getChildren().add(health);
                        powerUpHealth.add(health);
                        break;
                    case 'F': //level exit
                        goal = new LevelStatus(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, true, manager);
                        goalTexture = new LevelStatus(j * GameProperties.TILE_UNIT, i * GameProperties.TILE_UNIT, false, manager);
                        levelRoot.getChildren().addAll(goalTexture, goal);
                        break;
                    case 'e': //enemy spawn
                        enemy = new Enemy(GameProperties.TILE_UNIT - 10, GameProperties.TILE_UNIT - 10);
                        //set spawn for enemy
                        enemy.setTranslateX(GameProperties.TILE_UNIT * j);
                        enemy.setTranslateY(GameProperties.TILE_UNIT * i);
                        setEnemyExist(true);
                        levelRoot.getChildren().add(enemy);
                        break;

                }

                if(decoTiles[j] != ' '){
                    String decoID = String.valueOf(decoTiles[j]);
                    Deco decoration = TileManager.getDeco(decoID, j, i);
                    this.decoration.add(decoration);
                    decoRoot.getChildren().add(decoration);
                }

            }
        }

    }

    /**
     * Fails the Level.
     */
    public void fail(){
        goal.failLevel();
    }

}
