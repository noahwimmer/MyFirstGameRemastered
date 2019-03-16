package core.interfaces;

import core.Powerups.Powerup;
import core.util.Trail;
import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

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

        collision();

        if (y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 38) velX *= -1;

        if (menu.getOptions()[0]) {
            handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, Constants.DECAY, handler));
        }

    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, 16, 16);

    }

    private void collision() {
        for(int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);



            if(tempObject.getId() == ID.Player) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    // Collision code
                    velX *= -1;
                    velY *= -1;
                }
            }


        }
    }


}