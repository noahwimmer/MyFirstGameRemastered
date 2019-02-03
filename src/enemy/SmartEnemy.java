package enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.GameObject;
import main.Handler;
import main.ID;

public class SmartEnemy extends GameObject{
	
	private Handler handler;
	private GameObject player;

	public SmartEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			if(handler.getObject().get(i).getId() == ID.Player) player = handler.getObject().get(i);
		}
	}

	@Override
	public void tick() {
		
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt (
				(
						(   x-player.getX()   ) * (   x-player.getX()    ) // x2 - x1 **2
				)
				+
					   (y-player.getY()   ) * (   y-player.getY()   )   // y2 - y1 ** 2
		);
		
		velX = (float) ((-1.0/distance) * diffX);
		velY = (float) ((-1.0/distance) * diffY);
		
		x += velX;
		y += velY;	
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int) x, (int) y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return(new Rectangle((int)x, (int)y, 16,16));
	}

}
