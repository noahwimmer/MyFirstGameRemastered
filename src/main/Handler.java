package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	private LinkedList<GameObject> object = new LinkedList<GameObject>();
	private LinkedList<GameObject> toDispose = new LinkedList<GameObject>();
	private LinkedList<GameObject> toAdd = new LinkedList<GameObject>();
	

	
	public LinkedList<GameObject> getObject() {
		return object;
	}
	
	public LinkedList<GameObject> getToDispose() {
		return toDispose;
	}
	
	public LinkedList<GameObject> getToAdd() {
		return toAdd;
	}
	
	public void addTrash(GameObject object) {
		toDispose.add(object);
	}
	
	public void addToList(GameObject object) {
		toAdd.add(object);
	}

	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
		// this is such a pretty for loop and so simple i love it. (I thought i'd like to remember this moment
		for(int j = 0; j < 1; j++) {
			for(int i = 0; i < toDispose.size(); i ++) {
			object.remove(toDispose.get(i));
		}
		}
		
		
		for(int j = 0; j < 1; j++) {
			for(int i = 0; i < toAdd.size(); i ++) {
				object.add(toAdd.get(i));
				
			}
			toAdd.clear();
		}
		
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
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
		for(int i = 0; i < this.object.size(); i++ ) {
			GameObject tempObject = this.object.get(i);
			if(tempObject.getId() == ID.Player) {
				this.removeObject(tempObject);
				i--;
			}
		}
		
	}

	public void removeAll() {
		for(int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if(!(tempObject.getId() == (ID.Player))) {
				this.removeObject(tempObject);
				i--;
			}
		}
	}	
}