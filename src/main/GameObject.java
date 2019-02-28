package main;

import java.awt.*;
import java.util.Random;

public abstract class GameObject {

    protected float x, y;
    protected ID id;
    protected float velX, velY;

    private Random r = new Random();

    //This constructor is in beta to make the spawn more consistent while being able to define the spawn location within Constants
    public GameObject(Rectangle locationWindow, ID id) {
        this.x = (r.nextFloat() * (locationWindow.width - locationWindow.x)) + locationWindow.x;
        this.y = (r.nextFloat() * (locationWindow.height - locationWindow.y)) + locationWindow.y;
        this.id = id;
    }

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }


}
