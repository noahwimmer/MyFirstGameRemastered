package core.enemies.specialEnemies;

import core.interfaces.Menu;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

import java.awt.*;

public class SlowEnemy extends GameObject {

    private int HEALTH = 100;

    private Handler handler;
    private Menu menu;

    public SlowEnemy(Rectangle location, ID id, Handler handler, Menu menu) {
        super(location, id);
        this.handler = handler;
        this.menu = menu;

        velX = .1f;
        velY = .3f;

        addBullets();
    }

    public SlowEnemy(float x, float y, ID id, Handler handler, Menu menu) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;


        velX = .1f;
        velY = .3f;

        addBullets();
    }

    void addBullets() {
        // Up and down bullets
        handler.addObject(new SlowEnemyBullet(x + 6, y - 10, ID.SlowEnemyBullet, handler, 0 + velX, -1 + velY, this, true, menu));
        handler.addObject(new SlowEnemyBullet(x + 6, y + 23, ID.SlowEnemyBullet, handler, 0 + velX, 1 + velY, this, false, menu));

        // left and right bullets
        handler.addObject(new SlowEnemyBullet(x + 23, y + 7, ID.SlowEnemyBullet, handler, 1 + velX, 0 + velY, this, false, menu));
        handler.addObject(new SlowEnemyBullet(x - 10, y + 7, ID.SlowEnemyBullet, handler, -1, 0 + velY, this, false, menu));

        // Top diagonal bullets
        handler.addObject(new SlowEnemyBullet(x - 10, y - 10, ID.SlowEnemyBullet, handler, -1 + velX, -1 + velY, this, false, menu));
        handler.addObject(new SlowEnemyBullet(x + 23, y - 10, ID.SlowEnemyBullet, handler, 1 + velX, -1 + velY, this, false, menu));

        //bottom diagonal bullets
        handler.addObject(new SlowEnemyBullet(x + 23, y + 23, ID.SlowEnemyBullet, handler, 1 + velX, 1 + velY, this, false, menu));
        handler.addObject(new SlowEnemyBullet(x - 10, y + 23, ID.SlowEnemyBullet, handler, -1 + velX, 1 + velY, this, false, menu));
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        collision();


        if (y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Constants.PURPLE);
        g.fillRect((int) x, (int) y, 22, 22);
    }

    private void collision() {
        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Bullet) {
                HEALTH-= .5f;
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 22, 22);
    }


}
