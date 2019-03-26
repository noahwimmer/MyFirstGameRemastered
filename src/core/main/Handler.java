package core.main;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    private LinkedList<GameObject> object = new LinkedList<>();
    private LinkedList<GameObject> vault = new LinkedList<>();


    public LinkedList<GameObject> getObject() {
        return object;
    }

    public LinkedList<GameObject> getVault() {
        return vault;
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);

    }

    public void removePlayer() {
        for (int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Player) {
                this.removeObject(tempObject);
                i--;
            }if (tempObject.getId() == ID.PlayerShield) {
                this.removeObject(tempObject);
                i--;
            }
        }for (int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.PlayerShield) {
                this.removeObject(tempObject);
                i--;
            }
        }

    }

    public void removeAll() {
        for (int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = this.object.get(i);
            if (!(tempObject.getId() == (ID.Player) || tempObject.getId() == ID.PlayerShield)) {
                this.removeObject(tempObject);
                i--;
            }
        }
    }

    public void vaultPlayer() {
        for(int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = object.get(i);

            if(tempObject.getId() == ID.Player) {
                vaultObject(tempObject);
            }
        }
    }

    public void returnPlayer() {
        for(int i = 0; i < this.object.size(); i++) {
            GameObject tempObject = object.get(i);

            if(tempObject.getId() == ID.Player) {
                returnFromVault(tempObject);
            }
        }
    }

    public void vaultObject(GameObject o) {
        this.vault.add(o);
        this.object.remove(o);
    }

    public void returnFromVault(GameObject o) {
        this.object.add(o);
        this.vault.remove(o);
    }

}