package main;

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

	private boolean running = false;
	private Thread thread;
	
	private Handler handler;	
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum STATE {
		Game,
		Menu,
		End,
		Help;
		
	};
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		handler = new Handler();
		hud = new HUD();
		spawner = new Spawn(handler, hud);
		menu = new Menu(this, handler, hud, spawner);
		
		this.addKeyListener(new KeyInput(handler, spawner));
		this.addMouseListener(menu);

		new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, "My Game Remastered", this);		
	}
	
	public void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			spawner.tick();
			hud.tick();
		} else if (gameState == STATE.Menu || gameState == STATE.End) {
			menu.tick();
		}
				
		if(HUD.HEALTH < 1) {
			handler.removeAll();
			gameState = STATE.End;
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
		} else if (gameState == STATE.Help || gameState == STATE.Menu) {
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
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
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
	
	public static void main(String[] args) {
		new Game();
	}
}