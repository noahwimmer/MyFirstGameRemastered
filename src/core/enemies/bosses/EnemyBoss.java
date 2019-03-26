package core.enemies.bosses;

import core.interfaces.HUD;
import core.main.*;
import core.util.Constants;

import java.awt.*;
import java.util.Random;

public class EnemyBoss extends GameObject {

	private float HEALTH = 500;

	private float greenValue;

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

		greenValue = (float) (HEALTH * 2.55);

		HEALTH = Game.clamp(HEALTH, 0, 500);

		if(timer <= 0) velY = 0;
		else timer--;

		if(timer <= 0) timer2 --;
		if(timer2 <= 0) {
			if(velX == 0) velX = 5;
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new EnemyBossBullet((int)x+48, (int)y+48, ID.Enemy, handler, game));
		}

		collision();

		if(x <= 0 || x >= Constants.GAME_WIDTH - 96) velX *= -1;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);

		try {
			if (HEALTH < 500) {
				g.setColor(new Color(0, (int) greenValue, 0));
				g.fillRect((int) x - 5, (int) y + 100, (int) ((HEALTH / 500)), 6);
				g.setColor(Color.lightGray);
				g.drawRect((int) x - 5, (int) y - 10, 100, 6);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void collision() {
		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject tempObject = handler.getObject().get(i);


			if (tempObject.getId() == ID.Bullet) {
				if ((getBounds().intersects(tempObject.getBounds()))) {
					HEALTH -= Constants.BULLET_DAMAGE;
				}
			}
		}
	}
}