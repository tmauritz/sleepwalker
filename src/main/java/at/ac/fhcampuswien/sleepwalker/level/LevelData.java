package at.ac.fhcampuswien.sleepwalker.level;

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

    /*
     * - walkway
     * s player
     * F finish
     * ^ mines
     * c coins
     * Deco is underneath each level, key = level# * 100
     * For deco: b B bushes, t T trees, C scarecrow, h hay, f barrel, g grass, s S sunflowers, r R rocks
     */

    static{
        Levels.put(1, new String[]{
                "                    ",
                "                    ",
                "     ^^     c--     ",
                "                    ",
                "   -c--             ",
                "    ---    c        ",
                "   ---     -        ",
                "          ---       ",
                "      -             ",
                "        -c   -      ",
                "        ----      F ",
                " --  s    -    c    ",
                "        -           ",
                "--------------------",
        });

        Levels.put(100, new String[]{
                "                    ",
                "                    ",
                "             g      ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "           f        ",
                " r                  ",
                "                    ",
                "  Ss Ch             ",
                "                    ",
        });

        Levels.put(2, new String[]{
                "       c            ",
                "      ---    F      ",
                "                    ",
                "            ---     ",
                "   -c-          -   ",
                "   ---     --     - ",
                "               --   ",
                "   c                ",
                " ----               ",
                "           ^^^      ",
                "    c      ----     ",
                "  - --              ",
                "           -        ",
                "          c        e",
                "   s      ---       ",
                "--------------------",
        });

        Levels.put(200, new String[]{
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
                "                    ",
        });

        Levels.put(3, new String[]{
                " s  -^              ",
                "--- -^c             ",
                "    -^         --c  ",
                " ----^        -     ",
                "  ---^              ",
                "  -     --          ",
                "                   e",
                "              -     ",
                "              -   c ",
                " c            -     ",
                "           c      F ",
                " -    -   -         ",
                "^^^^^^^^^^^^^^^^^---",
                "--------------------",
        });

        Levels.put(300, new String[]{
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "sSs     tT           ",
                "                     ",
        });

        Levels.put(4, new String[]{
                "----^^^^^^^^^^^^^  F ",
                " c -                 ",
                "   -        c        ",
                "   - -              -",
                "  -- -             c-",
                "       -           --",
                "                    e",
                "  -      -           ",
                "              -      ",
                "          -          ",
                "  -    c             ",
                "                     ",
                "  c-      s          ",
                "  --     --          ",
                "^^--^^^^^---^^^^^^^^ ",
                "---------------------",
        });

        Levels.put(400, new String[]{
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
        });
        Levels.put(5, new String[]{
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                " s   ccccc           ",
                "---------------------",
        });

        Levels.put(500, new String[]{
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
        });
        Levels.put(6, new String[]{
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "  s       c        c         c  c   F    c",
                "------------------------------------------",
        });

        Levels.put(600, new String[]{
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
                "                                          ",
        });
    }
}
