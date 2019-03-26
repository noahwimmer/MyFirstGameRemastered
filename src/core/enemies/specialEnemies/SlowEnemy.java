package core.enemies.specialEnemies;

import core.interfaces.Menu;
import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.util.Constants;

import java.awt.*;

public class SlowEnemy extends GameObject {

	private float greenValue;

	private int HEALTH = 100;

	private Handler handler;
	private Menu menu;

	public SlowEnemy(Rectangle location, ID id, Handler handler, Menu menu) {
		super(location, id);
		this.handler = handler;
		this.menu = menu;

		velX = .1f;
		velY = .3f;

		addBullets();
	}

	public SlowEnemy(float x, float y, ID id, Handler handler, Menu menu) {
		super(x, y, id);
		this.handler = handler;
		this.menu = menu;


		velX = .1f;
		velY = .3f;

		addBullets();
	}

	void addBullets() {
		// Up and down bullets
		handler.addObject(new SlowEnemyBullet(x + 6, y - 10, ID.SlowEnemyBullet, handler, 0 + velX, -1 + velY, this, true, menu));
		handler.addObject(new SlowEnemyBullet(x + 6, y + 23, ID.SlowEnemyBullet, handler, 0 + velX, 1 + velY, this, false, menu));

		// left and right bullets
		handler.addObject(new SlowEnemyBullet(x + 23, y + 7, ID.SlowEnemyBullet, handler, 1 + velX, 0 + velY, this, false, menu));
		handler.addObject(new SlowEnemyBullet(x - 10, y + 7, ID.SlowEnemyBullet, handler, -1, 0 + velY, this, false, menu));

		// Top diagonal bullets
		handler.addObject(new SlowEnemyBullet(x - 10, y - 10, ID.SlowEnemyBullet, handler, -1 + velX, -1 + velY, this, false, menu));
		handler.addObject(new SlowEnemyBullet(x + 23, y - 10, ID.SlowEnemyBullet, handler, 1 + velX, -1 + velY, this, false, menu));

		//bottom diagonal bullets
		handler.addObject(new SlowEnemyBullet(x + 23, y + 23, ID.SlowEnemyBullet, handler, 1 + velX, 1 + velY, this, false, menu));
		handler.addObject(new SlowEnemyBullet(x - 10, y + 23, ID.SlowEnemyBullet, handler, -1 + velX, 1 + velY, this, false, menu));
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;

		greenValue = (float) (HEALTH * 2.55);

		collision();

		HEALTH = (int) Game.clamp(HEALTH, 0, 100);

		if (HEALTH == 0) {
			handler.removeObject(this);
		}

		if (y <= 0 || y >= Constants.GAME_HEIGHT - 50) velY *= -1;
		if (x <= 0 || x >= Constants.GAME_WIDTH - 30) velX *= -1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Constants.PURPLE);
		g.fillRect((int) x, (int) y, 22, 22);

		try {
			if (HEALTH < 100) {
				g.setColor(new Color(0, (int) greenValue, 0));
				g.fillRect((int) x - 5, (int) y - 10, (int) (.34 * HEALTH), 6);
				g.setColor(Color.lightGray);
				g.drawRect((int) x - 5, (int) y - 10, 34, 6);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private void collision() {
		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject tempObject = handler.getObject().get(i);

			if (tempObject.getId() == ID.Bullet) {
				if ((getBounds().intersects(tempObject.getBounds()))) {
					HEALTH -= Constants.BULLET_DAMAGE;
				}
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 22, 22);
	}


}
