package main;

import java.awt.*;
import java.util.Random;

public abstract class GameObject {

    protected float x, y;
    protected ID id;
    protected float velX, velY;

    private Random r = new Random();

    //This constructor is in beta to make the spawn more consistent while being able to define the spawn location within Constants
    public GameObject(Rectangle spawnLocation, ID id) {
        this.x = (r.nextFloat() * (spawnLocation.width - spawnLocation.x)) + (spawnLocation.x - 25);
        this.y = (r.nextFloat() * (spawnLocation.height - spawnLocation.y)) + (spawnLocation.y - 25);
        this.id = id;
    }

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Every time the game cycles the loop, this method is called
     */
    public abstract void tick();

    /**
     * This is the render method. All rendering for the class should be here
     * @param g The graphics component initialized inside <code>main.Game.render()</code>
     */
    public abstract void render(Graphics g);

    /**
     *
     * @return The bounds (hitbox) of the Object
     */
    public abstract Rectangle getBounds();

    /**
     *
     * @return The x position of the object
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x value of the object to the param
     * @param x The float value of the new x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     *
     * @return The y position of the object
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y value of the object to the param
     * @param y The float value of the new y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     *
     * @return The id of the object
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets a new ID for the object
     * @param id The new id of the object
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     *
     * @return The x velocity of the object
     */
    public float getVelX() {
        return velX;
    }

    /**
     * Sets a new velocity in the X direction for the object
     * @param velX The new velocity of the object
     */
    public void setVelX(int velX) {
        this.velX = velX;
    }

    /**
     *
     * @return The y velocity of the object
     */
    public float getVelY() {
        return velY;
    }
    /**
     * Sets a new velocity in the Y direction for the object
     * @param velY The new velocity of the object
     */
    public void setVelY(int velY) {
        this.velY = velY;
    }


}
