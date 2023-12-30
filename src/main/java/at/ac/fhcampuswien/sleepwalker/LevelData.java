package at.ac.fhcampuswien.sleepwalker;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds data for all Levels.<br>
 * Level Data format:
 * '-': Platform
 * 's': Player Spawn
 * all other characters are treaded as "air" with nothing loaded
 */
public class LevelData {

    public static final Map<Integer, String[]> Levels = new HashMap<>();

    /* m walkway start
     * - walkway
     * n walkway end
     * _ column top
     * I single column
     * o single square
     */

    static{
        Levels.put(1, new String[]{
                "                    ",
                "                    ",
                "             mn     ",
                "                    ",
                "   o mn       ",
                "      mn            ",
                "                    ",
                "          m-n     ",
                "                    ",
                "                    ",
                "        m--n        ----      ------    -----    ----",
                "   s    I           ",
                "        I           ",
                "--------------------"
        });
    }

}
