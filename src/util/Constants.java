package util;

import java.awt.*;

public class Constants {

    public static final int GAME_WIDTH = 700;
    public static final int GAME_HEIGHT = GAME_WIDTH / 12 * 9;
    public static final Color PURPLE = new Color(153, 50, 204);
    static final float DELTA = .30f;
    public static final float DECAY = .15f;
    public static final Rectangle spawnZone = new Rectangle(30, 30, Constants.GAME_WIDTH - 80, Constants.GAME_HEIGHT - 100);

}
