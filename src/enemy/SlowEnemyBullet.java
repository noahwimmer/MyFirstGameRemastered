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
	private float distance;
	private float diffX, diffY;
	
	public SlowEnemyBullet(float x, float y, ID id, Handler handler, float velX, float velY, SlowEnemy slowEnemy) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.slowEnemy = slowEnemy;
	}
	
	public void comeBack() {
		velX = (float) ((-1.0/distance) * diffX);
		velY = (float) ((-1.0/distance) * diffY);
	}
	
	public void goOut() {
		velX = (float) ((1.0/distance) * diffX);
		velY = (float) ((1.0/distance) * diffY);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		diffX = x - slowEnemy.getX() - 8f;
		diffY = y - slowEnemy.getY() - 8f;
		distance = (float) Math.sqrt (
				(
						(   Math.pow(getX() - slowEnemy.getX(), 2)  ) // x2 - x1 **2
				)
				+
					   ( Math.pow(getY() - slowEnemy.getY(), 2))   // y2 - y1 ** 2
		);
		
		int limit = 50;
		
		System.out.println(distance);
		
		if(distance >= limit) {
			comeBack();
		} else if(distance <= 20) {
			goOut();
		}
	
		/*if(count >= distance) {
			distance = 0;
			handler.addTrash(this);
			slowEnemy.addBullets();
		}*/
		
		if(y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
		if(x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
		
		
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
