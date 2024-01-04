package at.ac.fhcampuswien.sleepwalker;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class TileManager {

    private static final HashMap<String, String> tileY = new HashMap<>();
    private static final HashMap<String, String> tileX = new HashMap<>();

    static{
        // initialize tile mapping
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

    }

    public static ImagePattern getTile(String[] levelData, int x, int y){
        String neighbors = getTileID(levelData, x, y);
        int tileX = getTileX(neighbors);
        int tileY = getTileY(neighbors);
        Image image =MediaManager.loadImage("level/TX Tileset Ground.png");
        ImageView imageView = new ImageView(image);
        imageView.setViewport(new javafx.geometry.Rectangle2D(tileX, tileY, GameProperties.TILE_UNIT, GameProperties.TILE_UNIT));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return new ImagePattern(imageView.snapshot(params, null));
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
        } else neighbours.append("x");

        tiles = levelData[ROW].toCharArray();
        if(COL > 0){
            neighbours.append(tiles[COL - 1] == '-' ? '-' : ' ');
        } else neighbours.append("x");

        if(COL + 1 < tiles.length){
            neighbours.append(tiles[COL + 1] == '-' ? '-' : ' ');
        } else neighbours.append("x");

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


}
