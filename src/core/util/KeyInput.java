package core.util;

import core.interfaces.Menu;
import core.interfaces.MenuParticle;
import core.main.Game;
import core.main.GameObject;
import core.main.Handler;
import core.main.ID;
import core.player.Player;
import core.player.PlayerShield;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Game game;
    private Handler handler;
    private Spawn spawn;
    private Menu menu;
    private boolean[] keyDown = new boolean[4];

    public KeyInput(Handler handler, Spawn spawn, Game game, Menu menu) {
        this.handler = handler;
        this.spawn = spawn;
        this.game = game;
        this.menu = menu;

        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    public void tick() {
        if (keyDown[0] && !keyDown[1] && !keyDown[2] && !keyDown[3]) {
            PlayerShield.onAcceleration(Constants.TOP);
        }
        if (keyDown[0] && keyDown[3] && !keyDown[1] && !keyDown[2]) {
            PlayerShield.onAcceleration(Constants.TOP_RIGHT);
        }
        if (keyDown[3] && !keyDown[0] && !keyDown[1] && !keyDown[2]) {
            PlayerShield.onAcceleration(Constants.RIGHT);
        }
        if (keyDown[3] && keyDown[1] && !keyDown[0] && !keyDown[2]) {
            PlayerShield.onAcceleration(Constants.BOTTOM_RIGHT);
        }
        if (keyDown[1] && !keyDown[0] && !keyDown[2] && !keyDown[3]) {
            PlayerShield.onAcceleration(Constants.BOTTOM);
        }
        if (keyDown[1] && keyDown[2] && !keyDown[0] && !keyDown[3]) {
            PlayerShield.onAcceleration(Constants.BOTTOM_LEFT);
        }
        if (keyDown[2] && !keyDown[0] && !keyDown[1] && !keyDown[3]) {
            PlayerShield.onAcceleration(Constants.LEFT);
        }
        if (keyDown[2] && keyDown[0] && !keyDown[1] && !keyDown[3]) {
            PlayerShield.onAcceleration(Constants.TOP_LEFT);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE && !(game.gameState == Game.STATE.Options)) {
            game.lastState = game.gameState;
            game.gameState = Game.STATE.Options;
        } else if (key == KeyEvent.VK_ESCAPE) {
            if (game.lastState == null) {
                game.gameState = Game.STATE.Menu;
                for (int i = 0; i < 100; i++) {
                    handler.addObject(new MenuParticle(Constants.spawnZone, ID.Enemy, handler, menu));
                }
            } else game.gameState = game.lastState;
        }

        for (int i = 0; i < handler.getObject().size(); i++) {

            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Player) {
                // Key events for the core.player
                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-5);
                    keyDown[0] = true;
                }
                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(+5);
                    keyDown[1] = true;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-5);
                    keyDown[2] = true;
                }
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(+5);
                    keyDown[3] = true;
                }
                if(key == KeyEvent.VK_SPACE && game.getPlayer().getShootable()) {
                    game.getPlayer().shoot();
                }
            }


            //Add more if-statements like above for more characters
        }
        if (key == KeyEvent.VK_1) spawn.increaseLevel();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < handler.getObject().size(); i++) {
            GameObject tempObject = handler.getObject().get(i);

            if (tempObject.getId() == ID.Player) {
                // Key events for the core.player
                if (key == KeyEvent.VK_W) keyDown[0] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_S) keyDown[1] = false; //tempObject.setVelY(0);
                if (key == KeyEvent.VK_A) keyDown[2] = false; //tempObject.setVelX(0);
                if (key == KeyEvent.VK_D) keyDown[3] = false; //tempObject.setVelX(0);

                // Vertical movement
                if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
                // Horizontal movement
                if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
            }

            PlayerShield.onAcceleration(-1);

            //Add more if-statements like above for more characters
        }


    }

}
