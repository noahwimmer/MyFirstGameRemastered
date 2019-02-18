package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import enemy.BasicEnemy;
import main.Game;
import main.Game.STATE;
import main.Handler;
import main.ID;
import player.Player;
import util.Constants;
import util.Spawn;

public class Menu extends MouseAdapter {
	private Random r = new Random();
	private Game game; 
	private Handler handler;
	private HUD hud;
	private Spawn spawn;
	
	public Menu(Game game, Handler handler, HUD hud, Spawn spawn) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawn = spawn;
		
		for(int i = 0; i < 100;i++) {
			handler.addObject(new MenuParticle(r.nextInt(Math.abs(Constants.GAME_WIDTH) - 30), r.nextInt(Math.abs(Constants.GAME_HEIGHT) - 50), ID.Enemy, handler));
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font font = new Font("arial", 1, 75);
		Font font2 = new Font("arial", 1, 30);
		
		if(game.gameState == STATE.Menu) {
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Wave", 245, 135);
			
			g.setFont(font2);
			g.drawString("Play", 309, 240);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT /3), 64);
			
			g.drawString("Help", 309, 340);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 300, (Constants.GAME_HEIGHT) / 3, 64);
			
			g.drawString("Quit", 309, 440);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 400, (Constants.GAME_HEIGHT /3), 64);
		}
		
		if(game.gameState == STATE.Help) {
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("How to Play", 140, 100);
			
			g.setFont(font2);
			g.drawString("Press the WASD keys to move around and", 30,200);
			g.drawString("dodge enemies. You get 10 health at the end", 30, 230);
			g.drawString("of every round.", 30, 260);
			
			g.setFont(font2);
			g.drawString("Back", 315, 440);
			g.drawRect((Constants.GAME_WIDTH / 3) + 5, 400, (Constants.GAME_WIDTH /3), 64);
		}
		
		if(game.gameState == STATE.End) {	
			g.setFont(font);
			g.setColor(Color.red);
			g.drawString("Game Over", 145, 100);
			
			g.setColor(Color.WHITE);
			g.setFont(font2);
			g.drawString("Score: " + HUD.getScore(), 280, 170);
			g.drawString("High Score: " + hud.getHighScore(), 246, 220);
			
			g.setFont(font2);
			g.drawString("Try Again", 280, 440);
			g.drawRect(200, 400, 300, 64);
	}
	
}
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
			
		if(game.gameState == Game.STATE.Menu) {
			//play button
			if(mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT /3), 64)) {
				handler.removeAll();
				game.gameState = Game.STATE.Game;
				handler.addObject(new Player((Constants.GAME_WIDTH/2), (Constants.GAME_HEIGHT/2), ID.Player, handler));
				handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler));
			}
			
			//help button
			if(mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 300, (Constants.GAME_HEIGHT) / 3, 64)) {
				game.gameState = Game.STATE.Help;
			}
			
			
			
			//quit button
			if(mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20,	400, (Constants.GAME_WIDTH /3), 64)) {
				System.exit(1); 
			}
		}
		 
		if(game.gameState == STATE.Help) {
			//back button for help
			if(game.gameState == STATE.Help) {
				if(mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 5, 400, (Constants.GAME_WIDTH /3), 64))
				game.gameState = STATE.Menu;
				return;
			} 
		}
		
		if(game.gameState == STATE.End) {
			if(mouseOver(mx, my, 200, 400, 300, 64)) {
				HUD.HEALTH = 100;
				handler.removeAll();
				handler.removePlayer();
				spawn.setScoreKeep(0);
				HUD.score = 0;
				hud.setLevel(1);
				game.gameState = STATE.Game;
				handler.addObject(new Player((Constants.GAME_WIDTH/2), (Constants.GAME_HEIGHT/2), ID.Player, handler));
				handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler));
			}
			
		}
		
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
}
