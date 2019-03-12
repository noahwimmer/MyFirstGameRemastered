package util;

import Powerups.Powerup;
import interfaces.HUD;
import enemy.*;
import main.*;
public class Spawn {

    private Handler handler;
    private HUD hud;
    private GameObject object;
    private Game game;

    private int scoreKeep = 0;

    public Spawn(Handler handler, HUD hud, Game game) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
    }

    private void updateSpawn() {
        if (scoreKeep >= 650) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            HUD.HEALTH += 10;

            if(hud.getLevel() == 9) {
                handler.addObject(new RandomEnemy(Constants.spawnZone, ID.Enemy, handler));
            }

            //levels to add a shield powerup
            if (hud.getLevel() == 4 || hud.getLevel() == 13) {
                handler.addObject(new Powerup(Constants.spawnZone, ID.PowerUp, handler, "shield"));
            }

            //levels to add a health powerup
            if (hud.getLevel() == 7 || hud.getLevel() == 10) {
                handler.addObject(new Powerup(Constants.spawnZone, ID.PowerUp, handler, "health"));
            }

            //levels to add a basic enemy
            if (hud.getLevel() == 2 || hud.getLevel() == 3 || hud.getLevel() == 5) {
                handler.addObject(new BasicEnemy(Constants.spawnZone, ID.Enemy, handler, game.getMenu()));
            }
            //levels to add a smart enemy
            if (hud.getLevel() == 4) {
                handler.addObject(new SmartEnemy(Constants.spawnZone, ID.Enemy, handler));
            }

            //levels to add a slow enemy
            if (hud.getLevel() == 8) {
                handler.addObject(new SlowEnemy(Constants.spawnZone, ID.SlowEnemy, handler, game.getMenu()));
            }

            //levels to add a fast enemy
            if (hud.getLevel() == 5 || hud.getLevel() == 6) {
                handler.addObject(new FastEnemy(Constants.spawnZone, ID.Enemy, handler, game.getMenu()));
            }
        }
    }

    public void tick() {
        scoreKeep++;
        updateSpawn();
    }

    public void setScoreKeep(int i) {
        scoreKeep = i;
    }

    void increaseLevel() {
        scoreKeep = 600;
    }

}
