package Powerups;

import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;


public class Powerup extends GameObject {

    private Handler handler;

    private static String power;

    public static String getPower() {
        return power;
    }

    public Powerup(Rectangle location, ID id, Handler handler, String power) {
        super(location, id);
        this.handler = handler;
        this.power = power;

    }

    @Override
    public void tick() {
        collision();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect((int) x, (int) y, 18,18);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 18,18);
    }

    private void collision() {
        for(int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if(tempObject.getId() == ID.Player) {
                if(tempObject.getBounds().intersects(getBounds())) {
                    try{
                        Thread.sleep(1);
                    } catch(Exception e) {}
                    handler.removeObject(this);
                }

            }
        }
    }

}
