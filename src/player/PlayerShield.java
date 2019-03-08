package player;

import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

import java.awt.*;

public class PlayerShield extends GameObject {

    private Handler handler;
    private Player player;

    // clock wise starting with top
    private static boolean[] dir = {false, false, false, false, false, false, false, false};

    public static boolean[] getDir() {
        return dir;
    }

    private boolean activated = false;

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean b) {
        activated = b;
    }

    public PlayerShield(Rectangle location, ID id, Handler handler, Player player) {
        super(location, id);
        this.handler = handler;
        this.player = player;
        handler.addObject(this);
    }

    public PlayerShield(float x, float y, ID id, Handler handler, Player player) {
        super(x, y, id);
        this.handler = handler;
        this.player = player;
        this.handler.addObject(this);
    }


    @Override
    public void tick() {
        x = player.getX();
        y = player.getY();

        if(player.getShieldOn()){
            activated = true;
        } else {
            activated = false;
        }
    }

    @Override
    public void render(Graphics g) {
        if(activated) {
            g.setColor(Color.YELLOW);

            if (dir[Constants.TOP]) {
                g.fillRect((int) player.getX(), (int) player.getY() - 15, 32, 6);
            } else if (dir[Constants.TOP_RIGHT]) {
                g.fillRect((int) player.getX() + 24, (int) player.getY() - 12, 20, 6);
                g.fillRect((int) player.getX() + 38, (int) player.getY() - 12, 6, 20);
            } else if (dir[Constants.RIGHT]) {
                g.fillRect((int) player.getX() + 40, (int) player.getY(), 6, 32);
            } else if (dir[Constants.BOTTOM_RIGHT]) {
                g.fillRect((int) player.getX() + 24, (int) player.getY() + 38, 20, 6);
                g.fillRect((int) player.getX() + 38, (int) player.getY() + 24, 6, 20);
            } else if (dir[Constants.BOTTOM]) {
                g.fillRect((int) player.getX(), (int) player.getY() + 42, 32, 6);
            } else if (dir[Constants.BOTTOM_LEFT]) {
                g.fillRect((int) player.getX() - 12, (int) player.getY() + 38, 20, 6);
                g.fillRect((int) player.getX() - 12, (int) player.getY() + 24, 6, 20);
            } else if (dir[Constants.LEFT]) {
                g.fillRect((int) player.getX() - 12, (int) player.getY(), 6, 32);
            } else if (dir[Constants.TOP_LEFT]) {
                g.fillRect((int) player.getX() - 12, (int) player.getY() - 12, 20, 6);
                g.fillRect((int) player.getX() - 12, (int) player.getY() - 12, 6, 20);
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        if(activated) {
            if (dir[Constants.TOP]) {
                return new Rectangle((int) player.getX(), (int) player.getY() - 15, 32, 6);
            } else if (dir[Constants.TOP_RIGHT]) {
                return new Rectangle((int) player.getX() + 24, (int) player.getY() - 12, 20, 20);
            } else if (dir[Constants.RIGHT]) {
                return new Rectangle((int) player.getX() + 40, (int) player.getY(), 6, 32);
            } else if (dir[Constants.BOTTOM_RIGHT]) {
                return new Rectangle((int) player.getX() + 24, (int) player.getY() + 24, 20, 20);
            } else if (dir[Constants.BOTTOM]) {
                return new Rectangle((int) player.getX(), (int) player.getY() + 42, 32, 6);
            } else if (dir[Constants.BOTTOM_LEFT]) {
                return new Rectangle((int) player.getX() - 12, (int) player.getY() + 24, 20, 20);
            } else if (dir[Constants.LEFT]) {
                return new Rectangle((int) player.getX() - 12, (int) player.getY(), 6, 32);
            } else if (dir[Constants.TOP_LEFT]) {
                return new Rectangle((int) player.getX() -12, (int) player.getY() - 12, 20, 20);
            } else {
                return new Rectangle(-100, -100, 1,1);
            }
        } else {
            return new Rectangle(-100, -100, 1,1);
        }
    }

    /**
     * @param i Use a constant from any direction (ex. Constants.TOP)
     */
    public static void onAcceleration(int i) {
        if (i == -1) {
            for (int j = 0; j < dir.length; j++) {
                dir[j] = false;
            }
            return;
        } else {
            for (int j = 0; j < dir.length; j++) {
                dir[j] = false;
            }
            try {
                dir[i] = true;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }
}
