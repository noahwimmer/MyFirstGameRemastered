package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class SlowEnemy extends GameObject{
	
	private Handler handler;

	public SlowEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		

		velX = .1f;
		velY = .3f;
		
		addBullets();
	}
	
	public void addBullets() {
		// Up and down bullets
		handler.addToList(new SlowEnemyBullet(x + 6, y - 10, ID.SlowEnemyBullet, handler, 0 + velX, -1 + velY, this));
		handler.addToList(new SlowEnemyBullet(x + 6, y + 23, ID.SlowEnemyBullet, handler, 0 + velX, 1 + velY, this));
		
		// left and right bullets
		handler.addToList(new SlowEnemyBullet(x + 23, y + 7, ID.SlowEnemyBullet, handler, 1 + velX, 0 + velY, this));
		handler.addToList(new SlowEnemyBullet(x - 10, y + 7, ID.SlowEnemyBullet, handler, -1, 0 + velY, this));
		
		// Top diagonal bullets
		handler.addToList(new SlowEnemyBullet(x - 10, y - 10, ID.SlowEnemyBullet, handler, -1 + velX, -1 + velY, this));
		handler.addToList(new SlowEnemyBullet(x + 23, y - 10, ID.SlowEnemyBullet, handler, 1 + velX, -1 + velY, this));
		
		//bottom diagonal bullets
		handler.addToList(new SlowEnemyBullet(x + 23, y + 23, ID.SlowEnemyBullet, handler, 1 + velX, 1 + velY, this));
		handler.addToList(new SlowEnemyBullet(x - 10, y + 23, ID.SlowEnemyBullet, handler, -1 + velX, 1 + velY, this));
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Constants.PURPLE);
		g.fillRect((int) x,(int) y, 22, 22);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y, 22, 22);
	}


}
