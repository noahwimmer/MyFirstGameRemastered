package core.util;

import core.Powerups.Powerup;
import core.enemies.basicEnemies.BasicEnemy;
import core.enemies.basicEnemies.FastEnemy;
import core.enemies.basicEnemies.SmartEnemy;
import core.enemies.bosses.EnemyBoss;
import core.enemies.specialEnemies.RandomEnemy;
import core.enemies.specialEnemies.SlowEnemy;
import core.interfaces.HUD;
import core.main.*;
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

            //levels to add a basic core.enemies
            if (hud.getLevel() == 2 || hud.getLevel() == 3 || hud.getLevel() == 5) {
                handler.addObject(new BasicEnemy(Constants.spawnZone, ID.Enemy, handler, game.getMenu()));
            }
            //levels to add a smart core.enemies
            if (hud.getLevel() == 4) {
                handler.addObject(new SmartEnemy(Constants.spawnZone, ID.Enemy, handler));
            }

            //levels to add a slow core.enemies
            if (hud.getLevel() == 8) {
                handler.addObject(new SlowEnemy(Constants.spawnZone, ID.SlowEnemy, handler, game.getMenu()));
            }

            //levels to add a fast core.enemies
            if (hud.getLevel() == 5 || hud.getLevel() == 6) {
                handler.addObject(new FastEnemy(Constants.spawnZone, ID.Enemy, handler, game.getMenu()));
            }

            if(hud.getLevel() == 15) game.getPlayer().enableShootable();

        }
    }

    public void tick() {
        scoreKeep++;
        updateSpawn();
        if(handler.getObject().size() == 2 && hud.getLevel() >= 15) {
            handler.addObject(new EnemyBoss((Constants.GAME_WIDTH / 2) - 50, -120, ID.Enemy, handler, game));
        }
    }

    public void setScoreKeep(int i) {
        scoreKeep = i;
    }

    void increaseLevel() {
        scoreKeep = 650;
    }

}
