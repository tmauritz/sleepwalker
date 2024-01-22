package at.ac.fhcampuswien.sleepwalker.level;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds data for all Levels.<br>
 * Level Data format:
 * '-': Platform
 * 's': Player Spawn
 * 'e': Enemy Spawn
 * '^' Spike
 * 'w' Wing-Spike
 * 'c' collectibles
 * all other characters are treaded as "air" with nothing loaded
 * Deco is underneath each level, key = level# * 100
 * For deco:
 * b B bushes
 * t T trees
 * C scarecrow
 * h hay
 * f barrel
 * g grass
 * s S sunflowers
 * r R rocks
 */
public class LevelData {

    public static final Map<Integer, String[]> Levels = new HashMap<>();
    static{
        Levels.put(1, new String[]{
                "-                                                                                                -",
                "-                                                                                                -",
                "-                                                                                                -",
                "-     c                                                                                          -",
                "-   -----www---www---www----                                                                     -",
                "-                              ----      c                                                       -",
                "-                                     -----                                                      -",
                "-                                            ---                                                 -",
                "-                                                  ---                                           -",
                "-                                                        ---        ------                       -",
                "-                                        c                              w-                       -",
                "-                                      -----                            w----                    -",
                "-                                                   --------------      w-                       -",
                "-                                  --                                   w-                     F -",
                "-                                                                       w-                       -",
                "-         ------            -----              --------                 w--------             ----",
                "-                                                                       w-                  ------",
                "-  s         c                                                          w-           c    --------",
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
                "                                                                                                   -",
                "                                                                            c                      -",
                "          s                                               ----        w     --                     -",
                "         ----     w       ^   ^   ^               ---                 w                            -",
                "         - c                                                     ---  w                            -",
                "         ----     ^     ------       -           c                          --           ---       -",
                "                  -                  -          --                                                 -",
                "  ^    ^         ---     w     -------     -                           h   -    ---                -",
                "  -    -                            ---    -                 ----    ---              e            -",
                "  -    -    ----        ^  ^      -        -                                                       -",
                "  -    -    -               -----          -                                                       -",
                "  -    -    -------                    -----          ---                                          -",
                "  -    - ^^           -         h                        --                                        -",
                "  -    -         ----  ---------------                    -                                        -",
                "  -              -                                                                              F  -",
                "  -  c       -    ----                                         h                            c      -",
                "---------------------------------------^^^^^^^^^^^^^^^^^^^^--------^^-^^-^^-^^-^^-^^-^^-^^-^^-------"
        });

        Levels.put(200, new String[]{
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    ",
                "                                                                                                    "
        });

        Levels.put(3, new String[]{
                "     w              w                                       ",
                "     w              w                                       ",
                "     w              w                                       ",
                " s  -w              w     -                                 ",
                "--- -wc             w          c                            ",
                "    -w         --c  w   h   w-------                        ",
                " ----w        -     w   -               -------         h^^^",
                "  ---w              w           wwwwwwwwwww         --------",
                "        --          w           w-       -w          c^^^^^^",
                "                    w     -     w-       -w         --------",
                "              -     w           w-       -w                 ",
                "              -     w           e-       -w     ^^  ------- ",
                "              -     w   -       w-       -w     ----      -F",
                "      h    c               h    w-       -w     -         - ",
                " -    -   -            ------   w-       -w     -         --",
                "wwwwwwwwwwwwwwwww-----wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
        });

        Levels.put(300, new String[]{
                "                                                            ",
                "                                                            ",
                "                                                            ",
                " R                                                          ",
                "                                 ff                         ",
                "                                                            ",
                "                                                      T     ",
                "                                                            ",
                "                                                            ",
                "              r                                             ",
                "                                                            ",
                "                                                            ",
                "                                                            ",
                " S  T    t             S hhC                                ",
                "                                                            ",
                "                                                            ",
        });

        Levels.put(4, new String[]{
                "----wwwwwwwwwwwww  F ",
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
                "                F    ",
                "                     ",
                "         c     ------",
                "        ----         ",
                "           wwwww     ",
                "                     ",
                "  ---                ",
                "                     ",
                "         c           ",
                " wwwwwww----         ",
                "                     ",
                "                     ",
                "               ---   ",
                "                     ",
                "  h                  ",
                "---------- ----------",
                "               e     ",
                "                     ",
                "                     ",
                "         ---         ",
                "-----     w     --  w",
                "          w         w",
                "          w    c    w",
                "        --w   --    w",
                "  c       w         w",
                "-----     w         w",
                "          w     --  w",
                "        --w         w",
                "          w         w",
                "-----     w   --    w",
                "                     ",
                "                     ",
                "        ------       ",
                "                     ",
                "                     ",
                "-----   wwwww   -----",
                "                     ",
                "          c          ",
                "       -------       ",
                "                     ",
                " s                   ",
                "---------------------",
        });

        Levels.put(500, new String[]{
                "                F    ",
                "                     ",
                "           g         ",
                "                     ",
                "                     ",
                "  f                  ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "             C  B    ",
                "    T            T   ",
                "                     ",
                "                     ",
                "                     ",
                " B                   ",
                "                     ",
                "                     ",
                "        f     g      ",
                "                     ",
                "  c                  ",
                "                g    ",
                "                     ",
                "                     ",
                "  B            g     ",
                "                     ",
                "                     ",
                "           s         ",
                "                     ",
                "                     ",
                " S                s  ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                "                     ",
                " s HfgB              ",
                "                T    ",
        });
        Levels.put(6, new String[]{
                "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                "                       c                  ",
                "    c                   -                 ",
                "    -                                     ",
                "               -                 c        ",
                "                                 -        ",
                "                                          ",
                "        -    -    -        -              ",
                "                                          ",
                "                      -          -        ",
                "      c                                   ",
                "      -                                   ",
                "                                        F ",
                "  s                                       ",
                "^^-^^^^^^^-^^^^-^^^^-^^^^-^^^^-^^^^-^^^^-^",
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
                "    Tt         tTt               rrR      ",
                "                                          ",
        });
    }
}
