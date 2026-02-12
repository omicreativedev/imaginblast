package application;

import javafx.scene.image.Image;

public class PlayerClass extends Creature {
	
	public int hp = 50;

	//constructor
	public PlayerClass(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
	}
	
	public Shot shoot() {
		return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}
	

}
