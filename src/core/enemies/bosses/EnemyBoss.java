package core.enemies.bosses;
import core.interfaces.HUD;
import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBoss extends GameObject {

	private Handler handler;
	private Game game;
	private HUD hud;
	Random r = new Random();

	private int timer = 80;
	private int timer2 = 50;


	public EnemyBoss(int x, int y, ID id, Handler handler, Game game) {
		super(x, y, id);

		this.handler = handler;
		this.game = game;

		velX = 0;
		velY = 2;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}

	public void tick() {
		x += velX;
		y += velY;

		if(timer <= 0) velY = 0;
		else timer--;

		if(timer <= 0) timer2 --;
		if(timer2 <= 0) {
			if(velX == 0) velX = 5;
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new EnemyBossBullet((int)x+48, (int)y+48, ID.Enemy, handler, game));
		}



		if(x <= 0 || x >= Constants.GAME_WIDTH - 96) velX *= -1;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);

	}

}