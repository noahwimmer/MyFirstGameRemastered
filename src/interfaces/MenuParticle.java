package interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class MenuParticle extends GameObject {
	
	private Handler handler;
	private Game game;
	private Random random;
	
	Random r = new Random();
	
	Color color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	
	

	public MenuParticle(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = r.nextInt(9) + 2;
		velY = r.nextInt(9) + 2;
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 16) velX *= -1;
	
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}
}