package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import enemy.BasicEnemy;
import enemy.FastEnemy;
import enemy.SmartEnemy;
import interfaces.HUD;
import player.Player;
import util.Constants;
import util.KeyInput;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = -7071532049979466544L;

	private boolean running = false;
	private Thread thread;
	private Random r;
	
	private Handler handler;	
	private HUD hud;
	
	public Game() {
		r = new Random();
		handler = new Handler();
		hud = new HUD();
		
		this.addKeyListener(new KeyInput(handler));

		//Add objects to the start of the game here.

		handler.addObject(new Player((Constants.GAME_WIDTH / 2) - 16, (Constants.GAME_HEIGHT / 2) - 16, ID.Player, handler));

		handler.addObject(new BasicEnemy((r.nextFloat() * Constants.GAME_WIDTH), (r.nextFloat() * Constants.GAME_HEIGHT), ID.Enemy, handler));
		handler.addObject(new FastEnemy((r.nextFloat() * Constants.GAME_WIDTH), (r.nextFloat() * Constants.GAME_HEIGHT), ID.Enemy, handler));
		handler.addObject(new SmartEnemy((r.nextFloat() * Constants.GAME_WIDTH), (r.nextFloat() * Constants.GAME_HEIGHT), ID.Enemy, handler));

		
		new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, "My Game Remastered", this);
	}
	
	public void tick() {
		handler.tick();
		
		hud.tick();
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
		
		hud.render(g);
				
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