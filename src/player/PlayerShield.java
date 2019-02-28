package player;

import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;

public class PlayerShield extends GameObject {

    private Handler handler;
    private Player player;

    // clock wise starting with top
    private static boolean[] dir = {false, false, false, false, false, false, false, false};

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
        handler.addObject(this);
    }


    @Override
    public void tick() {
        x = player.getX();
        y = player.getY();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        /*
        dir[0] = up
        dir[1] = top right
        dir[2] = right
        dir[3] = bottom right
        dir[4] = bottom
        dir[5] = bottom left
        dir[6] = left
        dir[7] = top left
        */
        if(dir[0]) {
            g.fillRect((int)player.getX(), (int)player.getY() - 10, 15,5);
        } else if(dir[1]) {
            g.fillRect((int)player.getX() - 5, (int) player.getY() + 3, 5, 15);
        } else if(dir[2]) {

        } else if(dir[3])  {

        }else if(dir[4])  {

        }else if(dir[5])  {

        }else if(dir[6])  {

        }else if(dir[7])  {

        }

    }

    @Override
    public Rectangle getBounds() {
        //TODO add implementation
        return null;
    }

    /**
     *
     * @param i Use a constant from any direction (ex. Constants.TOP)
     */
    public static void onAcceleration(int i) {
        if(i == -1) {
            for(int j = 0; j < dir.length; j++) {
                dir[j] = false;
            }
            return;
        } else {
            for (int j = 0; j < dir.length; j++) {
                dir[j] = false;
            }
            try {
                dir[i] = true;
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }
}
