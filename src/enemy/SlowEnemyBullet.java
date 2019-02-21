package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import interfaces.Menu;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class SlowEnemyBullet extends GameObject {
	
	private Handler handler;
	private SlowEnemy slowEnemy;
	private Menu menu;
	
	private int count  = 0;
	private int distance = 45;
	private boolean b;

	public SlowEnemyBullet(float x, float y, ID id, Handler handler, float velX, float velY, SlowEnemy slowEnemy, boolean b, Menu menu) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.slowEnemy = slowEnemy;
		this.b = b;
		this.menu = menu;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
			
		if(count >= distance && b) {
			slowEnemy.addBullets();
		}
		if(count >= distance) handler.removeObject(this);

		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 40) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
		
		count++;
		
		
		if(menu.getOptions()[0]) {
			handler.addObject(new Trail(x, y, ID.Trail, Constants.PURPLE, 9, 9, Constants.DECAY, handler));
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Constants.PURPLE);
		g.fillRect((int) x, (int) y, 9, 9);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 9, 9);
	}

}
