package core.enemies.specialEnemies;

import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

import java.awt.*;
import java.util.Random;

public class RandomEnemy extends GameObject {

    private Handler handler;
    private Random r = new Random();
    private int count = 0;


    public RandomEnemy(Rectangle location, ID id, Handler handler) {
        super(location, id);
        this.handler = handler;

        velX = (r.nextFloat() * 2.9f) + 1.333f;
        velY = (r.nextFloat() * 2.9f) + 1.333f;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(count > 100) {
            handler.addObject(new RandomEnemyBullet(x, y, ID.Enemy, handler, r.nextFloat() * 4 - 2, r.nextFloat() * 4 - 2));
            count = 0;
        }

        if (y <= 0) {
            velY = ((r.nextFloat() * 2.9f) + 1.333f);
            y = 5;
        }
        if (y >= Constants.GAME_HEIGHT - 60) {
            velY = ((r.nextFloat() * 2.9f) + 1.333f) * -1;
            y = Constants.GAME_HEIGHT - 70;
        }
        if (x <= 0) {
            velX = ((r.nextFloat() * 2.9f) + 1.333f);
            x = 5;
        }
        if (x >= Constants.GAME_WIDTH - 40) {
            velX = -((r.nextFloat() * 2.9f) + 1.333f);
            x = Constants.GAME_WIDTH - 50;
        }
        count++;
    }

    @Override
    public void render(Graphics g) {
        int loc = r.nextInt(7);
        g.setColor(Color.yellow);
        switch (loc) {
            case 0:
                g.fillRect((int) x - 3, (int) y - 3, 26,26);
                break;
            case 1:
                g.fillRect((int) x, (int) y - 3, 26,26);
                break;
            case 2:
                g.fillRect((int) x - 2, (int) y + 3, 26,26);
                break;
            case 3:
                g.fillRect((int) x  - 5, (int) y, 26,26);
                break;
            case 4:
                g.fillRect((int) x +1, (int) y - 4, 26,26);
                break;
            case 5:
                g.fillRect((int) x  + 1, (int) y  + 4, 26,26);
                break;
            case 6:
                g.fillRect((int) x, (int) y, 26,26);
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 26, 26);
    }
}