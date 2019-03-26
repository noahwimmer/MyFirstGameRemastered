package core.enemies.bosses;

import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;
import core.util.Trail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBossBullet extends GameObject {

	private Game game;
	private Handler handler;
	Random r = new Random();

	public EnemyBossBullet(int x, int y, ID id, Handler handler, Game game) {
		super(x, y, id);

		this.handler = handler;
		this.game = game;

		velX = (r.nextInt(5 - -5) + - 5);
		velY = 5;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;



		// if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		// if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;

		if(y >= Constants.GAME_HEIGHT) handler.removeObject(this);

		if (game.getMenu().getOptions()[0]) {
			handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, Constants.DECAY, handler));
		}
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 10, 10, 0.099f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 10, 10);

	}

}