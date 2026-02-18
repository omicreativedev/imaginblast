package application;

import javafx.scene.image.Image;

public class PlayerClass extends Creature {
	
	public int hp = 50;
	public int col_items = 0; // short for collected items

	//constructor
	public PlayerClass(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
	}
	
	// lets the player shoot stuff!
	public Shot shoot() {
		return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}
	

}
