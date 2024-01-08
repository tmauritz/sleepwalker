package at.ac.fhcampuswien.sleepwalker;

import at.ac.fhcampuswien.sleepwalker.entities.Deco;
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
    private static final HashMap<String, String> decoHeight = new HashMap<>();
    private static final HashMap<String, String> decoWidth = new HashMap<>();

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

        //deco tiles
        tileX.put("t", "693");
        tileY.put("t", "459");
        decoHeight.put("t", "148");
        decoWidth.put("t", "120");

        tileX.put("T", "832");
        tileY.put("T", "445");
        decoHeight.put("T", "172");
        decoWidth.put("T", "153");

        tileX.put("g", "421");
        tileY.put("g", "459");
        decoHeight.put("g", "21");
        decoWidth.put("g", "23");

        tileX.put("b", "356");
        tileY.put("b", "569");
        decoHeight.put("b", "40");
        decoWidth.put("b", "57");

        tileX.put("B", "452");
        tileY.put("B", "563");
        decoHeight.put("B", "46");
        decoWidth.put("B", "89");

        tileX.put("f", "195");
        tileY.put("f", "19");
        decoHeight.put("f", "44");
        decoWidth.put("f", "27");

        tileX.put("h", "193");
        tileY.put("h", "245");
        decoHeight.put("h", "38");
        decoWidth.put("h", "62");

        tileX.put("C", "704");
        tileY.put("C", "221");
        decoHeight.put("C", "99");
        decoWidth.put("C", "64");

        tileX.put("s", "870");
        tileY.put("s", "253");
        decoHeight.put("s", "67");
        decoWidth.put("s", "22");

        tileX.put("S", "900");
        tileY.put("S", "248");
        decoHeight.put("S", "71");
        decoWidth.put("S", "26");

        tileX.put("r", "816");
        tileY.put("r", "355");
        decoHeight.put("r", "28");
        decoWidth.put("r", "33");

        tileX.put("R", "874");
        tileY.put("R", "348");
        decoHeight.put("R", "35");
        decoWidth.put("R", "46");

        tileX.put("", "");
        tileY.put("", "");
        decoHeight.put("", "");
        decoWidth.put("", "");

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
