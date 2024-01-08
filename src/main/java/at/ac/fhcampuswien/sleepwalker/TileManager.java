package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.entities.Deco;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class TileManager {

    private static final HashMap<String, String> tileY = new HashMap<>();
    private static final HashMap<String, String> tileX = new HashMap<>();
    private static final HashMap<String, String> decoHeight = new HashMap<>();
    private static final HashMap<String, String> decoWidth = new HashMap<>();

    static{
        tileY.putAll(deserializeMap("src/main/resources/at/ac/fhcampuswien/sleepwalker/level/HashMaps/TileY.ser"));
        tileX.putAll(deserializeMap("src/main/resources/at/ac/fhcampuswien/sleepwalker/level/HashMaps/TileX.ser"));
        decoHeight.putAll(deserializeMap("src/main/resources/at/ac/fhcampuswien/sleepwalker/level/HashMaps/DecoHeight.ser"));
        decoWidth.putAll(deserializeMap("src/main/resources/at/ac/fhcampuswien/sleepwalker/level/HashMaps/DecoWidth.ser"));
    }

    public static void serializeMap(HashMap<String, String> currentMap, String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(currentMap);
            System.out.println("Serialization successful.");

        } catch (Exception e) {
            System.out.println("Serialization failed. Exception: " + e.getMessage());
        }
    }

    public static HashMap<String, String> deserializeMap(String filepath){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            @SuppressWarnings("unchecked")
            HashMap<String, String> m = (HashMap<String, String>) ois.readObject();
            return m;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Loading " + filepath + " failed. Exception: " + e.getMessage());
            return null;
        }
    }


    public static ImagePattern getTile(String[] levelData, int x, int y){
        String neighbors = getTileID(levelData, x, y);
        int tileX = getTileX(neighbors);
        int tileY = getTileY(neighbors);
        Image image = MediaManager.loadImage("level/TX Tileset Ground.png");
        ImageView imageView = new ImageView(image);
        imageView.setSmooth(false);
        imageView.setViewport(new javafx.geometry.Rectangle2D(tileX, tileY, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return new ImagePattern(imageView.snapshot(params, null));
    }

    /**
     * Since Deco tiles aren't bound by conventional tile sizes, everything concerning their creation is here.
     * (custom height & widths, especially height has to get subtracted, so it's on the correct spot)
     */
    private static ImagePattern getDecoTile(String decoID){
        int tileX = getTileX(decoID);
        int tileY = getTileY(decoID);
        Image image = MediaManager.loadImage("level/TX Village Props.png");
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-background-color: transparent;");
        imageView.setSmooth(false);
        imageView.setViewport(new javafx.geometry.Rectangle2D(tileX, tileY, getDecoWidth(decoID), getDecoHeight(decoID)));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return new ImagePattern(imageView.snapshot(params, null));
    }

    public static Deco getDeco(String decoID, int x, int y) {
        int width = getDecoWidth(decoID);
        int height = getDecoHeight(decoID);
        return new Deco(
                x * GameProperties.TILE_UNIT,
                (y + 1) * GameProperties.TILE_UNIT - height,
                width,
                height,
                getDecoTile(decoID));
    }

    /**
     * Checks tile neighbours & picks the right ID accordingly
     */
    private static String getTileID(String[] levelData, final int ROW, final int COL){
        StringBuilder neighbours = new StringBuilder();

        char[] tiles;
        if(ROW > 0){
            tiles = levelData[ROW - 1].toCharArray();
            neighbours.append(tiles[COL] == '-' ? '-' : ' ');
        } else neighbours.append(" ");

        tiles = levelData[ROW].toCharArray();
        if(COL > 0){
            neighbours.append(tiles[COL - 1] == '-' ? '-' : ' ');
        } else neighbours.append("-");

        if(COL + 1 < tiles.length){
            neighbours.append(tiles[COL + 1] == '-' ? '-' : ' ');
        } else neighbours.append("-");

        if(ROW + 1 < levelData.length){
            tiles = levelData[ROW + 1].toCharArray();
            neighbours.append(tiles[COL] == '-' ? '-' : ' ');
        } else neighbours.append("x");

        return neighbours.toString();

    }

    /**
     * Translates tile ID into X coordinates
     */
    private static int getTileX(String tileID) {
        if (tileX.containsKey(tileID)) {
            return parseInt(tileX.get(tileID));
        } else return 0;
    }

    /**
     * Translates tile ID into Y coordinates
     */
    private static int getTileY(String tileID) {
        if (tileY.containsKey(tileID)) {
            return parseInt(tileY.get(tileID));
        } else return 0;
    }

    private static int getDecoHeight(String tileID) {
        if (decoHeight.containsKey(tileID)) {
            return parseInt(decoHeight.get(tileID));
        } else return 0;
    }

    private static int getDecoWidth(String tileID) {
        if (decoWidth.containsKey(tileID)) {
            return parseInt(decoWidth.get(tileID));
        } else return 0;
    }



}
