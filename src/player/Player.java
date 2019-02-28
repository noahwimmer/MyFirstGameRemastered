package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import interfaces.HUD;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import util.Constants;

public class Player extends GameObject{
	
	private Random r;
	private Handler handler;
	private PlayerShield shield;

	private float lastVelx;
	private float lastVely;

	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		r = new Random();

		shield = new PlayerShield(x, y, ID.Shield, handler);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

		if(lastVelx != velX || lastVely != velY) {
			if(velX > 0) {

			}
		}
		
		x = Game.clamp(x, 0, Constants.GAME_WIDTH - 37);
		y = Game.clamp(y, 0, Constants.GAME_HEIGHT - 60);
		
		collision();

		lastVelx = velX;
		lastVely = velY;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		return(new Rectangle((int)x, (int)y, 32, 32));
	}
	
	private void collision() {
		for(int i = 0; i < handler.getObject().size(); i++) {
			
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Enemy || tempObject.getId() == ID.SlowEnemy || tempObject.getId() == ID.SlowEnemyBullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					// Collision code
					HUD.HEALTH -= 1.5;	
				}
			} 			
		}
	}

}
