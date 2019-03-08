package main;

//TODO -- Reset local high score data before release
//TODO -- make local data for saving options... hard cause i have to learn more about the buffered reader and writer :/

import interfaces.HUD;
import interfaces.Menu;
import util.Constants;
import util.KeyInput;
import util.Spawn;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = -7071532049979466544L;

    public static boolean lock60 = false;
    public STATE gameState = STATE.Menu; // Initial gameState on launch
    public STATE lastState;
    private boolean running = false;
    private int showFrames;
    private Thread thread;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private KeyInput keyInput;

    // GETTERS AND SETTERS

    /**
     * sets <code>gameState</code> to the argument
     *
     * @param state the state you want the game to be
     */
    public void setGameState(STATE state) {
        this.gameState = state;
    }

    /**
     * @return the menu instance
     */
    public Menu getMenu() {
        if (menu != null) return menu;
        else return null;
    }

    /**
     * @return the integer value of the variable <code>showFrames</code>
     */
    public int getShowFrames() {
        return showFrames;
    }

    public enum STATE {
        Game,
        Options,
        Menu,
        End,
        Help

    }

    /**
     * Constructor for the Game class
     */
    public Game() {
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gameState != STATE.Options) {
                    System.out.println("Automatically changed state to options at time of focusLost");
                    lastState = gameState;
                    gameState = STATE.Options;
                }
            }
        });
        handler = new Handler();
        hud = new HUD(this, handler);
        spawner = new Spawn(handler, hud, this);
        menu = new Menu(this, handler, hud, spawner);
        keyInput = new KeyInput(handler,spawner,this,menu);

        this.addKeyListener(keyInput);
        this.addMouseListener(menu);

        new Window(Constants.GAME_WIDTH, Constants.GAME_HEIGHT, "My Game Remastered", this, menu);
    }


    synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        if (!(gameState == STATE.Options)) {
            handler.tick();
        }
        menu.tick();
        if (gameState == STATE.Game) {
            spawner.tick();
            hud.tick();
        }
        keyInput.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        handler.render(g);

        if (gameState == STATE.Game) {
            hud.render(g);
        }
        menu.render(g);

        g.dispose();
        bs.show();
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
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (!lock60) tick();
                delta--;
                if (lock60) {
                    synchronizedLoop();
                    frames++;
                }
            }

            if (running && !lock60) {
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                showFrames = frames;
                frames = 0;
            }
        }
        stop();
    }

    private synchronized void synchronizedLoop() {
        tick();
        render();
    }

    /**
     * @param var Your variable
     * @param min The minimum
     * @param max The maximum
     * @return the variable within the bounds of <code>min</code> and <code>max</code>
     */
    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    /**
     * @param args args for the command line
     */
    public static void main(String[] args) {

        new Game();
    }
}