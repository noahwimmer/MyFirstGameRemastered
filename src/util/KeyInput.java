package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import interfaces.Menu;
import interfaces.MenuParticle;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class KeyInput extends KeyAdapter{

	private Game game;
	private Handler handler;
	private Spawn spawn;
	private Menu menu;
	private Random r = new Random();
	private boolean[] keyDown = new boolean[4];
	
	public KeyInput(Handler handler, Spawn spawn, Game game, Menu menu) {
		this.handler = handler;
		this.spawn = spawn;
		this.game = game;
		this.menu = menu;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE && !(game.gameState == Game.STATE.Options)) {
			game.lastState = game.gameState;
			game.gameState = Game.STATE.Options;
		} else if(key == KeyEvent.VK_ESCAPE && game.gameState == Game.STATE.Options) {
			if(game.lastState == null) {
				game.gameState = Game.STATE.Menu;
				for(int i = 0; i < 100;i++) {
					handler.addObject(new MenuParticle((Math.abs((r.nextFloat() - Constants.DELTA)) * Constants.GAME_WIDTH), (Math.abs((r.nextFloat() - Constants.DELTA)) * Constants.GAME_HEIGHT), ID.Enemy, handler, menu));
				}
			}
			else game.gameState = game.lastState;
		}
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				// Key events for the player
				if(key == KeyEvent.VK_W) {tempObject.setVelY(- 5); keyDown[0] = true;}
				if(key == KeyEvent.VK_S) {tempObject.setVelY(+ 5); keyDown[1] = true;}
				if(key == KeyEvent.VK_A) {tempObject.setVelX(- 5); keyDown[2] = true;}
				if(key == KeyEvent.VK_D) {tempObject.setVelX(+ 5); keyDown[3] = true;}
				
			}
			
			if(key == KeyEvent.VK_1) spawn.increaseLevel();
			
			
			//Add more if-statements like above for more characters
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				// Key events for the player
				if(key == KeyEvent.VK_W) keyDown[0] = false; //tempObject.setVelY(0);
				if(key == KeyEvent.VK_S) keyDown[1] = false; //tempObject.setVelY(0);
				if(key == KeyEvent.VK_A) keyDown[2] = false; //tempObject.setVelX(0);
				if(key == KeyEvent.VK_D) keyDown[3] = false; //tempObject.setVelX(0);
				
				// Vertical movement
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				// Horizontal movement
				if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
			}
			
			
			
			//Add more if-statements like above for more characters
		}
		
		
	}
	
}
