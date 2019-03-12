package core.enemies.basicEnemies;

import core.util.Trail;
import core.interfaces.Menu;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.player.PlayerShield;
import core.util.Constants;

import java.awt.*;
import java.util.Random;

public class FastEnemy extends GameObject {

    private Handler handler;
    private Random r = new Random();
    private Menu menu;

    public FastEnemy(Rectangle location, ID id, Handler handler, Menu menu) {
        super(location, id);
        this.handler = handler;
        this.menu = menu;

        velX = (r.nextFloat() * 5f) + 3f;
        velY = (r.nextFloat() * 5f) + 3f;
    }

    public FastEnemy(float x, float y, ID id, Handler handler, Menu menu) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;

        velX = (r.nextFloat() * 5f) + 3f;
        velY = (r.nextFloat() * 5f) + 3f;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Constants.GAME_HEIGHT - 40) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 16) velX *= -1;

        collision();

        if (menu.getOptions()[0]) {
            handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, Constants.DECAY, handler));
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect((int) x, (int) y, 16, 16);
    }

    @Override
    public Rectangle getBounds() {
        return (new Rectangle((int) x, (int) y, 16, 16));
    }

    private void collision() {
        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.PlayerShield) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (PlayerShield.getDir()[Constants.TOP]) y -= 6;
                    if (PlayerShield.getDir()[Constants.RIGHT]) x += 6;
                    if (PlayerShield.getDir()[Constants.BOTTOM]) y += 6;
                    if (PlayerShield.getDir()[Constants.LEFT]) x -= 10;
                    if (PlayerShield.getDir()[Constants.TOP_LEFT]) {
                        x -= 6;
                        y -= 6;
                    }
                    if (PlayerShield.getDir()[Constants.TOP_RIGHT]) {
                        x += 6;
                        y -= 6;
                    }
                    if (PlayerShield.getDir()[Constants.BOTTOM_LEFT]) {
                        y += 6;
                        x -= 6;
                    }
                    if (PlayerShield.getDir()[Constants.BOTTOM_RIGHT]) {
                        y += 6;
                        x += 6;
                    }
                }
            }
        }
    }
}
