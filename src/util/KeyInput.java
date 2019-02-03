package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class KeyInput extends KeyAdapter{

	private Game game;
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				// Key events for the player
				
			}
			
			//Add more if-statements like above for more characters
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				// Key events for the player
			}
			
			//Add more if-statements like above for more characters
		}
	}
	
}
