package enemy;

import interfaces.Menu;
import main.GameObject;
import main.Handler;
import main.ID;
import player.Player;
import player.PlayerShield;
import util.Constants;

import java.awt.*;
import java.util.Random;

public class BasicEnemy extends GameObject {

    private Handler handler;
    private Random r = new Random();
    private Menu menu;
    private GameObject player;

    public BasicEnemy(Rectangle location, ID id, Handler handler, Menu menu) {
        super(location, id);
        this.handler = handler;
        this.menu = menu;

        for(int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId()== ID.Player) {
                this.player = tempObject;
            }
        }

        velX = (r.nextFloat() * 2.9f) + 1.333f;
        velY = (r.nextFloat() * 2.9f) + 1.333f;
    }

    public BasicEnemy(float x, float y, ID id, Handler handler, Menu menu) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;

        velX = (r.nextFloat() * 2.9f) + 1.333f;
        velY = (r.nextFloat() * 2.9f) + 1.333f;

        for(int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId()== ID.Player) {
                this.player = tempObject;
            }
        }


    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Constants.GAME_HEIGHT - 60) velY *= -1;
        if (x <= 0 || x >= Constants.GAME_WIDTH - 40) velX *= -1;

        collision();

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

    private void collision() {
        for(int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.PlayerShield) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    //TODO implement way better collision code for moving out of the way of the player
                    if(PlayerShield.getDir()[Constants.TOP]) y -= 4;
                    if(PlayerShield.getDir()[Constants.RIGHT]) x += 4;
                    if(PlayerShield.getDir()[Constants.BOTTOM]) y += 4;
                    if(PlayerShield.getDir()[Constants.LEFT]) x -= 8;
                    if(PlayerShield.getDir()[Constants.TOP_LEFT]) {
                        x -= 4;
                        y -= 4;
                    }
                    if(PlayerShield.getDir()[Constants.TOP_RIGHT]) {
                        x+= 4;
                        y -=4;
                    }
                    if(PlayerShield.getDir()[Constants.BOTTOM_LEFT]) {
                        y += 4;
                        x -= 4;
                    }
                    if(PlayerShield.getDir()[Constants.BOTTOM_RIGHT]) {
                        y += 4;
                        x += 4;
                    }
                }
            }
        }
    }

}
