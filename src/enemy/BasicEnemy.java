package enemy;

import interfaces.Menu;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

import java.awt.*;
import java.util.Random;

public class BasicEnemy extends GameObject {

    private Handler handler;
    private Random r = new Random();
    private Menu menu;

    public BasicEnemy(Rectangle location, ID id, Handler handler, Menu menu) {
        super(location, id);
        this.handler = handler;
        this.menu = menu;


        velX = (r.nextFloat() * 2.9f) + 1.333f;
        velY = (r.nextFloat() * 2.9f) + 1.333f;
    }

    public BasicEnemy(float x, float y, ID id, Handler handler, Menu menu) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;

        velX = (r.nextFloat() * 2.9f) + 1.333f;
        velY = (r.nextFloat() * 2.9f) + 1.333f;


    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Constants.GAME_HEIGHT - 60) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 40) velX *= -1;

        if (menu.getOptions()[0]) {
            handler.addObject(new Trail(x, y, ID.Trail, Color.red, 24, 24, Constants.DECAY, handler));
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 24, 24);
    }

    @Override
    public Rectangle getBounds() {
        return (new Rectangle((int) x, (int) y, 24, 24));
    }

}
