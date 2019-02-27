package interfaces;

import enemy.BasicEnemy;
import main.Game;
import main.Game.STATE;
import main.Handler;
import main.ID;
import player.Player;
import util.Constants;
import util.Spawn;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

public class Menu extends MouseAdapter {
    private Random r = new Random();
    private Game game;
    private Handler handler;
    private HUD hud;
    private Spawn spawn;
    private boolean showFPS = false;

    private Image trails;
    private Image noTrails;


    private boolean[] options = new boolean[3];
    private boolean[] showOptions = new boolean[1];

    private int mx, my;

    public Menu(Game game, Handler handler, HUD hud, Spawn spawn) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.spawn = spawn;

        try {
            trails = ImageIO.read(new File("res/Options/imgAssets/trails.PNG"));
            noTrails = ImageIO.read(new File("res/Options/imgAssets/noTrails.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sets all of the options pre-defined to false
        for (int i = 0; i < options.length; i++) {
            options[i] = false;
        }
        for (int i = 0; i < showOptions.length; i++) {
            showOptions[i] = false;
        }

        if (game.gameState == Game.STATE.Menu) {
            for (int i = 0; i < 100; i++) {
                handler.addObject(new MenuParticle(Constants.spawnZone, ID.Enemy, handler, this));
            }
        }

    }

    /**
     * @return options boolean array
     */
    public boolean[] getOptions() {
        return options;
    }

    public void tick() {
        Point cursor = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(cursor, game);
        mx = cursor.x;
        my = cursor.y;

        showOptions[0] = mouseOver(mx, my, 18, 80, 195, 63);
    }

    /**
     * @param g Graphics Component
     */
    public void render(Graphics g) {
        String backArrow = "←";

        Font bigFont = new Font("arial", Font.BOLD, 75);
        Font smallFont = new Font("arial", Font.BOLD, 30);
        Font smallerFont = new Font("arial", Font.BOLD, 20);

        if (game.gameState == STATE.Menu) {
            g.setFont(bigFont);
            g.setColor(Color.white);
            g.drawString("Wave", 245, 135);

            g.setFont(smallFont);
            g.drawString("Play", 309, 240);
            g.drawRect((Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT / 3), 64);

            g.drawString("Help", 309, 340);
            g.drawRect((Constants.GAME_WIDTH / 3) + 20, 300, (Constants.GAME_HEIGHT) / 3, 64);

            g.drawString("Quit", 309, 440);
            g.drawRect((Constants.GAME_WIDTH / 3) + 20, 400, (Constants.GAME_HEIGHT / 3), 64);
        }

        if (game.gameState == STATE.Help) {
            g.setFont(bigFont);
            g.setColor(Color.white);
            g.drawString("How to Play", 140, 100);

            g.setFont(smallFont);
            g.drawString("Press the WASD keys to move around and", 30, 200);
            g.drawString("dodge enemies. You get 10 health at the end", 30, 230);
            g.drawString("of every round.", 30, 260);

            g.drawString("Back", 315, 440);
            g.drawRect((Constants.GAME_WIDTH / 3) + 5, 400, (Constants.GAME_WIDTH / 3), 64);
        }

        if (game.gameState == STATE.End) {
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

        if (game.gameState == STATE.Options) {
            //gray backdrop
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
            //Heading
            g.setFont(smallFont);
            g.setColor(Color.WHITE);

            g.drawString("Options", 30, 50);

            //back arrow
            g.drawString(backArrow, 630, 40);
            g.drawRect(610, 10, 70, 50);

            //Options
            g.setFont(smallerFont);
            g.drawString("Show Trails", 30, 120);
            g.drawRect(160, 95, 40, 40);

            // check mark for option 1
            if (options[0]) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("ariel", Font.BOLD, 60));
                // \u2713 is a checkmark in unicode :)
                g.drawString("\u2713", 155, 135);
            }

            g.setColor(Color.WHITE);
            g.setFont(smallerFont);
            g.drawString("Show FPS", 30, 178);
            g.drawRect(160, 150, 40, 40);

            if (options[1]) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("ariel", Font.BOLD, 60));
                g.drawString("\u2713", 155, 190);
            }

            g.setFont(smallerFont);
            g.setColor(Color.WHITE);
            g.drawString("Limit FPS", 30, 236);
            g.drawRect(160, 205, 40, 40);

            // check mark for option 3
            if (options[2]) {
                g.setColor(Color.BLUE);
                g.setFont(new Font("ariel", Font.BOLD, 60));
                // \u2713 is a checkmark in unicode :)
                g.drawString("\u2713", 155, 245);
            }

            if (showOptions[0] && options[0]) {
                g.setFont(new Font("arial", Font.PLAIN, 20));
                g.drawString("On", 325, 120);
                g.drawImage(trails, 325, 120, null);
            } else if (showOptions[0]) {
                g.setFont(new Font("arial", Font.PLAIN, 20));
                g.drawString("Off", 325, 120);
                g.drawImage(noTrails, 325, 120, null);
            }

        }

        //renders the frames
        if (options[1]) {
            g.setFont(new Font("arial", Font.PLAIN, 20));
            g.setColor(Color.MAGENTA);
            g.drawString(game.getShowFrames() + "", 5, 20);
        }

    }

    public void mousePressed(MouseEvent e) {
        mx = e.getX();
        my = e.getY();

        if (game.gameState == Game.STATE.Menu) {
            //play button
            if (mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 200, (Constants.GAME_HEIGHT / 3), 64)) {
                handler.removeAll();
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player((Constants.GAME_WIDTH / 2.0f), (Constants.GAME_HEIGHT / 2.0f), ID.Player, handler));
                handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler, this));
            }

            //help button
            if (mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 300, (Constants.GAME_HEIGHT) / 3, 64)) {
                game.gameState = Game.STATE.Help;
            }


            //quit button
            if (mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 20, 400, (Constants.GAME_WIDTH / 3), 64)) {
                System.exit(1);
            }
        }

        // Help Game State
        if (game.gameState == STATE.Help) {
            //back button for help
            if (mouseOver(mx, my, (Constants.GAME_WIDTH / 3) + 5, 400, (Constants.GAME_WIDTH / 3), 64)) {
                game.gameState = STATE.Menu;
                return;
            }
        }

        // End game state
        if (game.gameState == STATE.End) {
            if (mouseOver(mx, my, 200, 400, 300, 64)) {
                HUD.HEALTH = 100;
                handler.removeAll();
                handler.removePlayer();
                spawn.setScoreKeep(0);
                HUD.score = 0;
                hud.setLevel(1);
                game.gameState = STATE.Game;
                handler.addObject(new Player((Constants.GAME_WIDTH / 2.0f), (Constants.GAME_HEIGHT / 2.0f), ID.Player, handler));
                handler.addObject(new BasicEnemy((r.nextInt(Constants.GAME_WIDTH)), (r.nextInt(Constants.GAME_HEIGHT)), ID.Enemy, handler, this));
            }

        }

        if (game.gameState == STATE.Options) {
            // on / off for option 1
            if (mouseOver(mx, my, 160, 95, 40, 40) && !options[0]) {
                options[0] = true;
            } else if (mouseOver(mx, my, 160, 95, 40, 40) && options[0]) {
                options[0] = false;
            }

            if (mouseOver(mx, my, 160, 150, 40, 40) && !options[1]) {
                options[1] = true;
                showFPS = true;
            } else if (mouseOver(mx, my, 160, 150, 40, 40) && options[1]) {
                options[1] = false;
                showFPS = false;
            }
            // on / off for option 3
            if (mouseOver(mx, my, 160, 205, 40, 40) && !options[2]) {
                options[2] = true;
                Game.lock60 = true;
            } else if (mouseOver(mx, my, 160, 205, 40, 40) && options[2]) {
                options[2] = false;
                Game.lock60 = false;
            }


            // back button
            if (mouseOver(mx, my, 610, 10, 70, 50)) {
                if (game.lastState == null) {
                    game.gameState = STATE.Menu;
                    for (int i = 0; i < 100; i++) {
                        handler.addObject(new MenuParticle(r.nextInt(Math.abs(Constants.GAME_WIDTH) - 30), r.nextInt(Math.abs(Constants.GAME_HEIGHT) - 50), ID.Enemy, handler, this));
                    }
                } else {
                    game.gameState = game.lastState;
                }
            }
        }


    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }

}
