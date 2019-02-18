package interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.Game;
import main.Handler;

public class HUD {
	public static float HEALTH = 100;
	private float greenValue = 255;
	
	public static int score = 0;
	private int level = 1;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	private String highScore = null;
	private Game game;
	private Handler handler;
	
	private void writeToFile(String str) throws IOException {
		File file = new File("res/high_score/high_score.txt");
		FileWriter fw = new FileWriter(file);
		try { 
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
		} catch (Exception e) {};
	}
	
	public HUD(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		try {
			reader = new BufferedReader(new FileReader(new File("res/high_score/high_score.txt")));
		} catch(Exception e) {
			e.printStackTrace();
		};
		try {
			highScore = reader.readLine();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void tick() {
		score++;
		HEALTH = Game.clamp(HEALTH, 0, 100);
		greenValue = Game.clamp(greenValue, 0, 255);
		
	 	greenValue = (float) ((float) HEALTH * 2.55);
		
		if(HEALTH < 1) {
			handler.removeAll();
			handler.removePlayer();
			game.setGameState(Game.STATE.End);
			
			try {
				reader = new BufferedReader(new FileReader(new File("res/high_score/high_score.txt")));
				highScore = reader.readLine();
				reader.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(score > Integer.parseInt(highScore)) {
				try {
					highScore = score + "";
					writeToFile(score + "");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
			
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(new Color(75, (int)greenValue, 0));
		g.fillRect((int)15, (int)15, (int)HEALTH * 2, 32);
		g.setColor(Color.white);
		g.drawRect(15,15,200,32);
		
		g.drawString("Score:   " + score, 15, 64);
		g.drawString("Level:   " + level, 15, 80);
		g.drawString("High Score: " + highScore, 15, 96);
	}
	
	public void score(int score) {
		this.score = score;
	}
	
	public String getHighScore() {
		return highScore;
	}
	
	public static int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
