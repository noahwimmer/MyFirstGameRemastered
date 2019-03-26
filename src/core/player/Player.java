package core.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Optional;
import java.util.Random;

import core.Powerups.Powerup;
import core.interfaces.HUD;

import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

public class Player extends GameObject {
	
	private Random r;
	private Handler handler;
	private PlayerShield shield;
	private boolean shootable = false;

	private int powerUpTimer = 0;

	private boolean shieldOn = false;

	public boolean getShieldOn() {
	    return shieldOn;
    }

    public boolean getShootable() {
		return shootable;
	}

	public PlayerShield getPlayerShield() {
		return shield;
	}

	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		r = new Random();

		handler.addObject(this);

		shield = new PlayerShield(x, y, ID.PlayerShield, handler, this);
	}

	public void shoot() {
		if(shootable) {
			handler.addObject(new PlayerBullet(x, y, velX * 3, velY * 3 , ID.Bullet, handler));
		}
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

		x = Game.clamp(x, 0, Constants.GAME_WIDTH - 48);
		y = Game.clamp(y, 0, Constants.GAME_HEIGHT - 72);
		
		collision();

		if(powerUpTimer > 0) {
			powerUpTimer--;
		}

		if(powerUpTimer == 0 && shieldOn) {
			shieldOn = false;
		}

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
					HUD.HEALTH -= 2.1f;
				}
			}

			if(tempObject.getId() == ID.PowerUp) {
                if(getBounds().intersects(tempObject.getBounds())){
                    powerUpTimer = toTicks(20);

                    if(Powerup.getPower() == "health") HUD.HEALTH += 20;
                    if(Powerup.getPower() == "shield") shieldOn = true;
                }

			}
		}
	}

	private int toTicks(int seconds) {
	    return seconds * 60;
    }

    public void enableShootable() {shootable  = true;}

}
