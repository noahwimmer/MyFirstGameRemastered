package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
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
	private boolean showFPS = false;
	
	private boolean[] options = new boolean[3];
	private boolean[] showOptions = new boolean[1];
	
	int mx, my;
	
	public boolean[] getOptions() {
		return options;
	}
	
	public Menu(Game game, Handler handler, HUD hud, Spawn spawn) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawn = spawn;
		
		options[0] = false;
		options[1] = false;
		options[2] = false;
		
		showOptions[0] = false;
		
		if(game.gameState == Game.STATE.Menu) {
			for(int i = 0; i < 100;i++) {
			handler.addObject(new MenuParticle(r.nextInt(Math.abs(Constants.GAME_WIDTH) - 30), r.nextInt(Math.abs(Constants.GAME_HEIGHT) - 50), ID.Enemy, handler, this));
			}
		}
		
	}
	
	public void tick() {
		Point cursor = MouseInfo.getPointerInfo().getLocation();
		mx = cursor.x;
		my = cursor.y;
		
		if(mouseOver(mx, my, 18, 80, 195, 63)) {
			showOptions[1] = true;
		} else {
			showOptions[1] = false;
		}
	}
	
	//TODO -- finish the option hint rendering
	//TODO -- Already have the images just need to read them and add the rendering :p
	
	public void render(Graphics g) {
		Font bigFont = new Font("arial", 1, 75);
		Font smallFont = new Font("arial", 1, 30);		
		Font smallerFont = new Font("arial", 1, 20);
		
		if(game.gameState == STATE.Menu) {
			g.setFont(bigFont);
			g.setColor(Color.white);
			g.drawString("Wave", 245, 135);
			
			g.setFont(smallFont);
			g.drawString("Play", 309, 240);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT /3), 64);
			
			g.drawString("Help", 309, 340);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 300, (Constants.GAME_HEIGHT) / 3, 64);
			
			g.drawString("Quit", 309, 440);
			g.drawRect((Constants.GAME_WIDTH / 3) + 20, 400, (Constants.GAME_HEIGHT /3), 64);
		}
		
		if(game.gameState == STATE.Help) {
			g.setFont(bigFont);
			g.setColor(Color.white);
			g.drawString("How to Play", 140, 100);
			
			g.setFont(smallFont);
			g.drawString("Press the WASD keys to move around and", 30,200);
			g.drawString("dodge enemies. You get 10 health at the end", 30, 230);
			g.drawString("of every round.", 30, 260);
			
			g.drawString("Back", 315, 440);
			g.drawRect((Constants.GAME_WIDTH / 3) + 5, 400, (Constants.GAME_WIDTH /3), 64);
		}
		
		if(game.gameState == STATE.End) {	
			g.setFont(bigFont);
			g.setColor(Color.red);
			g.drawString("Game Over", 145, 100);
			
			g.setColor(Color.WHITE);
			g.setFont(smallFont);
			
			g.drawString("Score: " + HUD.getScore(), 280, 170);
			g.drawString("High Score: " + hud.getHighScore(), 246, 220);
			
			g.drawString("Try Again", 280, 440);
			g.drawRect(200, 400, 300, 64);
		}
		
		if(game.gameState == STATE.Options) {
			//gray backdrop {
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
			//Heading
			g.setFont(smallFont);
			g.setColor(Color.WHITE);
			
			g.drawString("Options", 30, 50);
			
			//back arrow
			g.drawString("\u2190", 630, 40);
			g.drawRect(610, 10, 70, 50);
			
			//Options
			g.setFont(smallerFont);
			g.drawString("Show Trails", 30, 120);
			g.drawRect(160, 95, 40, 40);
			
			// check mark for option 1
			if(options[0]) {
				g.setColor(Color.BLUE);
				g.setFont(new Font("ariel", 1, 60));
				// \u2713 is a checkmark in unicode :)
				g.drawString("\u2713", 155, 130);
			}
			
			// TODO check mark for option 2
			if(options[1]) {
				g.setColor(Color.BLUE);
				g.setFont(new Font("ariel", 1, 60));
				// \u2713 is a checkmark in unicode :)
				g.drawString("\u2713", 155, 190);
			}
			
			// check mark for option 3
			if(options[2]) {
				g.setColor(Color.BLUE);
				g.setFont(new Font("ariel", 1, 60));
				// \u2713 is a checkmark in unicode :)
				g.drawString("\u2713", 155, 245);
			}
			
			g.setColor(Color.WHITE);
			g.setFont(smallerFont);
			g.drawString("Show FPS", 30, 178);
			g.drawRect(160, 150, 40, 40);
			
			g.drawString("Limit FPS", 30, 236);
			g.drawRect(160, 205, 40, 40);

		}

		//renders the frames
		if(options[1]) {
			g.setFont(new Font("arial", 1, 20));
			g.setColor(Color.WHITE);
			g.drawString(game.getShowFrames() + "", 5, 20);
		}
		
	}
	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
			
		if(game.gameState == Game.STATE.Menu) {
			//play button
			if(mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT /3), 64)) {
				handler.removeAll();
				game.gameState = Game.STATE.Game;
				handler.addObject(new Player((Constants.GAME_WIDTH/2), (Constants.GAME_HEIGHT/2), ID.Player, handler));
				handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler, this));
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
				handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler, this));
			}
			
		}
		
		if(game.gameState == STATE.Options) {
			// on / off for option 1
			if(mouseOver(mx, my, 160, 95, 40, 40) && !options[0]) {
				options[0] = true;
			} else if(mouseOver(mx, my, 160, 95, 40, 40) && options[0]) {
				options[0] = false;
			} 
			
			//TODO
			// on / off for option 2
			if(mouseOver(mx, my,160, 150, 40, 40) && !options[1]) {
				options[1] = true;
				showFPS = true;
			} else if(mouseOver(mx, my,160, 150, 40, 40) && options[1]) {
				options[1] = false;
				showFPS = false;
			}
			// on / off for option 3
			if(mouseOver(mx, my, 160, 205, 40, 40) && !options[2]) {
				options[2] = true;
				Game.lock60 = true;
			} else if(mouseOver(mx, my, 160, 205, 40, 40) && options[2]) {
				options[2] = false;
				Game.lock60 = false;
			}
			
			
			// back button
			if(mouseOver(mx, my, 610, 10, 70, 50)) {
				if(game.lastState == null) {
					game.gameState = STATE.Menu;
					for(int i = 0; i < 100;i++) {
						handler.addObject(new MenuParticle(r.nextInt(Math.abs(Constants.GAME_WIDTH) - 30), r.nextInt(Math.abs(Constants.GAME_HEIGHT) - 50), ID.Enemy, handler, this));
					}
				} else {
					game.gameState = game.lastState;
				}
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
