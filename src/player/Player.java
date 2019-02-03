package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class Player extends GameObject{
	
	private Random r;
	private Handler handler;

	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		r = new Random();
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
	//	x = Game.clamp(x, 0, Game.WIDTH - 37);
	//	y = Game.clamp(y, 0, Game.HEIGHT - 60);
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

}
