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
                "-                                                                                                -",
                "-                                                                                                -",
                "-                                                                                                -",
                "-     c                                                                                          -",
                "-   -----^^^---^^^---^^^----                                                                     -",
                "-                              ----      c                                                       -",
                "-                                     -----                                                      -",
                "-                                            ---                                                 -",
                "-                                                  ---                                           -",
                "-                                                        ---        ------                       -",
                "-                                        c                              ^-                       -",
                "-                                      -----                            ^----                    -",
                "-                                                   --------------      ^-                       -",
                "-                                  --                                   ^-                     F -",
                "-                                                                       ^-                       -",
                "-         ------            -----              --------                 ^--------             ----",
                "-                                                                       ^-                  ------",
                "-  s         c                                                          ^-           c    --------",
                "----------------------------------^^^^^^^^^^^^^^^^------------------------------------------------",
                "--------------------------------------------------------------------------------------------------",
                "--------------------------------------------------------------------------------------------------",
        });

        Levels.put(100, new String[]{
                "                                                                                                  ",
                "                                                                                                  ",
                "                                                                                                  ",
                "      S                                                                                           ",
                "                                                                                                  ",
                "                                      s  S                                                        ",
                "                                                                                                  ",
                "                                                                                                  ",
                "                                                                      ffC                         ",
                "                                                                                                  ",
                "                                                                                                  ",
                "                                        s            g g       gg                                 ",
                "                                                                                                  ",
                "                                                                                                  ",
                "           b                 g  g                   gg                                            ",
                "                                                                                            T     ",
                "                                                                                                  ",
                "   s   b     B      g gg   gg                     gg      g   gg               B                  ",
                "                                                                                                  ",
                "                                                                                                  ",
                "                                                                                                  ",
        });

        Levels.put(2, new String[]{
                "       c            ",
                "      ---    F      ",
                "                    ",
                "            ---     ",
                "   -c-     h    -   ",
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
                "      h    c      F ",
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
                "  h- -              -",
                "  -- -             c-",
                "       -           --",
                "                    e",
                "  -      -    h      ",
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
                "                 F   ",
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
                "                                    F     ",
                "  s       c        c         c  c        c",
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
