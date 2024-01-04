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

    /* - walkway
     * s player
     * F finish
     */

    static{
        Levels.put(1, new String[]{
                "                    ",
                "                    ",
                "     ^^      --     ",
                "                    ",
                "   - --       ",
                "    ---    c       ",
                "   ---     -           ",
                "          ---     ",
                "      -              ",
                "        -    -        ",
                "        ----      F  ",
                " --  s    -    c       ",
                "        -           ",
                "--------------------",
        });
        Levels.put(2, new String[]{
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "             F     c ",
                "  c    ---  --   -   ",
                " ---                ",
                "            ^^    -  ",
                "  - - ----     ----  ",
                "                -    ",
                "    -- ----     -    ",
                "     -         ----  ",
                "   c -  s       -  c ",
                "--------------------",
        });
        Levels.put(3, new String[]{
                "                     ",
                "               F     ",
                " ----         ---    ",
                "                     ",
                "                     ",
                "              ----   ",
                "  ---                ",
                "                     ",
                "        ^^           ",
                "                ---- ",
                "                     ",
                "                     ",
                "   s           ^^ -  ",
                "-------------------  ",
                "                     ",
                "                     ",
                "                    -",
                "                     ",
                " ccccccccccccccccccc ",
                "---------------------",
        });
        Levels.put(4, new String[]{
                "                                ",
                "                                ",
                "                                ",
                "                                ",
                "                        F     c ",
                "                c    --  --     ",
                "               ---              ",
                "                       ^^    -  ",
                "                - - ----  ----  ",
                "                           -    ",
                "                  -- ----  -    ",
                "                   -      ----  ",
                "                 c -  s    -  c ",
                "--------------------------------",
        });
    }
}
