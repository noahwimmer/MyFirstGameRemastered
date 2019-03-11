package interfaces;

import enemy.Trail;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

import java.awt.*;
import java.util.Random;

public class MenuParticle extends GameObject {

    private Random r = new Random();
    private Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    private Handler handler;
    private Game game;
    private Random random;
    private Menu menu;

    public MenuParticle(Rectangle location, ID id, Handler handler, Menu menu) {
        super(location, id);
        this.handler = handler;
        this.menu = menu;

        velX = r.nextInt(9) + 2;
        velY = r.nextInt(9) + 2;
    }

    public MenuParticle(float x, float y, ID id, Handler handler, Menu menu) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;

        velX = r.nextInt(9) + 2;
        velY = r.nextInt(9) + 2;

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 16) velX *= -1;

        if (menu.getOptions()[0]) {
            handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, Constants.DECAY, handler));
        }

    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, 16, 16);

    }
}