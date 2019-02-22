package main;

//TODO -- Reset local high score data before release
//TODO -- Add images for options
//TODO -- make local data for saving options... hard cause i have to learn more about the buffered reader and writer :/

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import interfaces.HUD;
import interfaces.Menu;
import util.Constants;
import util.KeyInput;
import util.Spawn;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = -7071532049979466544L;

	public static boolean lock60 = false;
	private boolean running = false;
	private Thread thread;
	private int showFrames;
	
	private Handler handler;	
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	public STATE gameState = STATE.Options;
	public STATE lastState;

	public enum STATE {
		Game,
		Options,
		Menu,
		End,
		Help;
		
	};
	
	public int getShowFrames() {
		return showFrames;
	}
	
	public Menu getMenu() {
		try {
			return menu;
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}
		
	public Game() {
		handler = new Handler();
		hud = new HUD(this, handler);
		spawner = new Spawn(handler, hud, this);
		menu = new Menu(this, handler, hud, spawner);
		
		this.addKeyListener(new KeyInput(handler, spawner, this, menu));
		this.addMouseListener(menu);

		new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, "My Game Remastered", this);		
	}
	
	public void tick() {
		if(!(gameState == STATE.Options)) {
			handler.tick();
			menu.tick();
		}
		if (gameState == STATE.Game) {
			spawner.tick();
			hud.tick();
		} 
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		
		handler.render(g);

		if (gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState == STATE.Help || gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Options) {
			menu.render(g);
		}
				
		g.dispose();
		bs.show();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now  = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
				if(lock60) {
					render();
					frames++;
				}
			}
			
			if(running && !lock60) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				showFrames = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >=  max) {
			return var= max;
		}
		else if(var <= min) {
			return var = min;
		}
		else {
			return var;
		}
	}
	
	public void setGameState(STATE state) {
		this.gameState = state;
	}
	
	public static void main(String[] args) {
		new Game();
	}
}