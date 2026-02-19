package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Creature {
	
	int SPEED = 6; // this can change depending on the enemy, might move to a squirrel subclass?
	boolean hitPlayer = false;
	
	//constructor
	public Enemy(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
	}
	
	//making them move down the screen unless destroyed
	public void update(GraphicsContext gc) {
		super.update();
		if(!exploding && !destroyed) posY += SPEED;
		if(posY > ImaginBlastMain.HEIGHT) destroyed = true; // get rid of it once it's off screen
	}
}
