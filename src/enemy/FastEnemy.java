package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import interfaces.Menu;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class FastEnemy extends GameObject {
	
	private Handler handler;
	private Random r;
	private Menu menu;

	public FastEnemy(float x, float y, ID id, Handler handler, Menu menu) {
		super(x, y, id);
		this.handler = handler;
		this.menu = menu;
		r = new Random();
		
		velX = (r.nextFloat() * 5f) + 3f;
		velY = (r.nextFloat() * 5f) + 3f;
		
		
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 40) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 16) velX *= -1;
		
		if(menu.getOptions()[0]) {
			handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, Constants.DECAY, handler));
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int)x, (int)y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return(new Rectangle((int)x, (int)y, 16, 16));
	}

}
