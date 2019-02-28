package player;

import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;

public class PlayerShield extends GameObject {

    private Handler handler;

    private static Color color = Color.YELLOW;

    // clock wise starting with top
    private boolean[] dir = {false, false, false, false, false, false, false, false};

    public PlayerShield(Rectangle location, ID id, Handler handler) {
        super(location, id);
        this.handler = handler;
    }

    public PlayerShield(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
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

        } else if(dir[1]) {

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

    public void onAcceleration(int i) {
        for(int j = 0; j < dir.length; j++) {
            dir[j] = false;
        }
        dir[i] = true;
    }
}
