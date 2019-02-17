package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class SlowEnemyBullet extends GameObject {
	
	private Handler handler;
	private SlowEnemy slowEnemy;
	
	private int count  = 0;

	public SlowEnemyBullet(float x, float y, ID id, Handler handler, float velX, float velY, SlowEnemy slowEnemy) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.slowEnemy = slowEnemy;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		int distance = 40;
	
		if(count >= distance) {
			distance = 0;
			handler.addTrash(this);
			handler.getToAdd().clear();
			slowEnemy.addBullets();
		}
		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
		
		count++;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Constants.PURPLE);
		g.fillRect((int) x, (int) y, 9, 9);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
