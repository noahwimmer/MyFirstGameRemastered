package enemy;

import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;

public class Trail extends GameObject {
	
	private float alpha = 1;
	private float life;
	
	private Handler handler;
	private Color color;
	
	private int width, height;

	/**
	 * Constructor for a new Trail
	 *
	 * @param x       x position of the trail
	 * @param y       y position of the trail
	 * @param id      Use ID.Trail
	 * @param color   Color of the trail
	 * @param width   Width of the trail
	 * @param height  Height of the Trail
	 * @param life    How long it lives
	 * @param handler Handler component
	 */
	public Trail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		this.life = life;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public void tick() {
		if(alpha > life) {
			alpha -= (life - 0.0001f);
		} else handler.removeObject(this);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
		
		g2d.setComposite(makeTransparent(1));
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	

	public Rectangle getBounds() {
		return null;
	}

}
