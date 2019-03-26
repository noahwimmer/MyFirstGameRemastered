package core.player;

import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

import java.awt.*;

public class PlayerBullet extends GameObject {

    private Handler handler;

    /**
     * Old but useful constructor. Used to spawn objects in not random locations.
     *
     * @param x  X position of spawn
     * @param y  Y position of spawn
     * @param id ID of the object
     */
    public PlayerBullet(float x, float y, float velX, float velY, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.velX = velX;
        this.velY = velY;


    }

    /**
     * Every time the game does a loop this method is called.
     */
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(velX == 0 && velY == 0) {
            handler.removeObject(this);
        }

        if(y < 0 || y > Constants.GAME_HEIGHT || x < 0 || x > Constants.GAME_WIDTH) {
            handler.removeObject(this);
        }
    }

    /**
     * This method handles the rendering of the objects
     *
     * @param g Graphics component inside
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, 10,10);
    }

    /**
     * Call this method to return the hitbox of the object.
     *
     * @return The bouds (hitbox) of the object.
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 10,10);
    }
}
