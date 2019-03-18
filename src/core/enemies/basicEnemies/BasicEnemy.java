package core.enemies.basicEnemies;

import core.interfaces.Menu;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.player.PlayerShield;
import core.util.Constants;
import core.util.Trail;

import java.awt.*;
import java.util.Random;

public class BasicEnemy extends GameObject {

    private boolean collide;

    private float greenValue;

    private int width;

    private Handler handler;
    private Random r = new Random();
    private Menu menu;

    private float HEALTH = 100f;

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

        greenValue = (float) (HEALTH * 2.55);

        if (y <= 0) {
            velY *= -1;
            y = 5;
        }
        if (y >= Constants.GAME_HEIGHT - 60) {
            velY *= -1;
            y = Constants.GAME_HEIGHT - 70;
        }
        if (x <= 0) {
            velX *= -1;
            x = 5;
        }
        if (x >= Constants.GAME_WIDTH - 40) {
            velX *= -1;
            x = Constants.GAME_WIDTH - 50;
        }

        collision();

        if (menu.getOptions()[0]) {
            handler.addObject(new Trail(x, y, ID.Trail, Color.red, 24, 24, Constants.DECAY, handler));
        }

        if (HEALTH == 0) {
            handler.removeObject(this);
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 24, 24);

        if (HEALTH < 100) {
            g.setColor(new Color(0, (int) greenValue, 0));
            g.fillRect((int) x - 5, (int) y - 10, (int)(.34 * HEALTH), 6);
            g.setColor(Color.lightGray);
            g.drawRect((int) x - 5, (int) y - 10, 34, 6);
        }
    }

    @Override
    public Rectangle getBounds() {
        return (new Rectangle((int) x, (int) y, 24, 24));
    }

    private void collision() {
        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.PlayerShield) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (PlayerShield.getDir()[Constants.TOP]) y -= 8;
                    if (PlayerShield.getDir()[Constants.RIGHT]) x += 8;
                    if (PlayerShield.getDir()[Constants.BOTTOM]) y += 8;
                    if (PlayerShield.getDir()[Constants.LEFT]) x -= 12;
                    if (PlayerShield.getDir()[Constants.TOP_LEFT]) {
                        x -= 8;
                        y -= 8;
                    }
                    if (PlayerShield.getDir()[Constants.TOP_RIGHT]) {
                        x += 8;
                        y -= 8;
                    }
                    if (PlayerShield.getDir()[Constants.BOTTOM_LEFT]) {
                        y += 8;
                        x -= 8;
                    }
                    if (PlayerShield.getDir()[Constants.BOTTOM_RIGHT]) {
                        y += 8;
                        x += 8;
                    }

                }
            }
            if (tempObject.getId() == ID.Bullet) {
                if ((getBounds().intersects(tempObject.getBounds()))) {
                    HEALTH -= 10f;
                }
            }

        }
    }
    /*private void collision() {
        collide = false;
        for(int i = 0; i < handler.getObject().size(); i++){
            GameObject tempObject;
            if(handler.getObject().get(i)!=this) {
                tempObject = handler.getObject().get(i);
                if(tempObject.getId() == ID.Enemy){

                    double xDis = (this.x)-(tempObject.getX());
                    double yDis = (this.y)-(tempObject.getY());
                    double[] Ap = {this.x,this.y};
                    double[] Bp = {tempObject.getX(), tempObject.getY()};
                    //xDis = (this.x+(width/2))-(tempObject.getBounds().x+(width/2));
                    //yDis = (this.y+(Height/2))-(tempObject.getBounds().y+(Height/2));
                    //Space balls apart
                    if(((xDis*xDis)+(yDis*yDis))<(32*32) && true) {
                        //i =0;

                        double disP1 = this.x-((this.x+tempObject.getX())/2.0);
                        double disP2 = this.y-((this.y+tempObject.getY())/2.0);
                        double Dis1 = Math.sqrt((disP1*disP1)+(disP2*disP2))-.01;
                        double Dis2 = Math.sqrt((disP1*disP1)+(disP2*disP2))+.01;
                        double slope = (tempObject.getY()-this.y)/(tempObject.getX()-this.x);
                        double xSign = Math.abs(xDis)/xDis;
                        double halfWidth = width/2.0;

                        double x2 = (xSign*((Dis2+halfWidth)/(Math.sqrt(1.0+(slope*slope)))))-this.x;
                        double y2 = (xSign*slope*((Dis2+halfWidth)/(Math.sqrt(1.0+(slope*slope)))))-this.y;
                        double x1 = (xSign*((Dis1-halfWidth)/(Math.sqrt(1.0+(slope*slope)))))-this.x;
                        double y1 = (xSign*slope*((Dis1-halfWidth)/(Math.sqrt(1.0+(slope*slope)))))-this.y;


                        if(Double.isNaN(slope)||Double.isInfinite(slope)) {
                            //System.out.println("yote");
                            x2 = -(tempObject.getX());
                            y2 = -(tempObject.getY()-(((width+.5)-yDis)/2.0));
                            x1 = -(this.x);
                            y1 = -(this.y+(((width+.5)-yDis)/2.0));
                            //System.out.println(-x1);
                            //System.out.println(-y1);
                            //System.out.println(-x2);
                            //System.out.println(-y2);


                        }


                        this.x =(float)(-1*x1);
                        this.y =(float)(-1*y1);

                        tempObject.setX((float)(-1*x2));
                        tempObject.setY((float)(-1*y2));

                        if(Double.isNaN(slope)) {
                            //System.out.println("yeet");
                            xDis = (-x1)-(-x2);
                            yDis = (-x2)-(-y2);
                            double[] nAp = {-x1,-y1};
                            Ap = nAp;
                            double[] nBp = {-x2, -y2};
                            Bp = nBp;

                            //System.out.println(this.x);
                            //System.out.println(this.y);
                            //System.out.println(tempObject.x);
                            //System.out.println(tempObject.y);
                        }
                    }


                    if(((xDis*xDis)+(yDis*yDis))<(width*width) && true) {
                        //System.out.println((((xDis*xDis)+(yDis*yDis))<(width*Width)) +", " +i);

                        collide = true;



                        double[] Av = {this.velX, this.velY};
                        double[] Bv = {tempObject.getVelX(), tempObject.getVelY()};

                        double Xdis = Ap[0]-Bp[0];
                        double Ydis = Ap[1]-Bp[1];
                        double hypo = Math.sqrt((xDis*xDis)+(yDis*yDis));
                        double[] N = {(xDis)/hypo,(yDis)/hypo};

                        double[] J = {Av[0]-Bv[0],Av[1]-Bv[1]};
                        double dotPro = 2*((J[0]*N[0])+(J[1]*N[1]));
                        J[0] = N[0]*dotPro;
                        J[1] = N[1]*dotPro;

                        this.setVelX((float)(Av[0]-J[0]));
                        this.setVelY((float)(Av[1]-J[1]));
                        tempObject.setVelX((float)(Bv[0]+J[0]));
                        tempObject.setVelY((float)(Bv[1]+J[1]));

                        //this.x += this.speedX;
                        //this.y += this.speedY;
                        //tempObject.x += tempObject.speedX;
                        //tempObject.y += tempObject.speedY;

                    }
                    else {
                        //collide = false;
                    }



                }
            }



        }

    }*/

}
