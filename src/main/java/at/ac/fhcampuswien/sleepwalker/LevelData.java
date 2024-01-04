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
                "        ----        ",
                " --  s    -    c       ",
                "        -           ",
                "--------------------",
        });
        Levels.put(2, new String[]{
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                  c ",
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
                "                     ",
                " ----         ---    ",
                "                     ",
                "                     ",
                "              ----   ",
                "  ---                ",
                "                     ",
                "        ^^           ",
                "                     ",
                "                 --- ",
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
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                  c                     ",
                "  c    ---  --   -                      ",
                " ---                                    ",
                "            ^^    -                     ",
                "  - - ----     ----                     ",
                "                -                       ",
                "    -- ----                        -    ",
                "     -                            ----  ",
                "   c -  s                          -  c ",
                "----------------------------------------",
        });
    }
}
