package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Creature {
	
	int SPEED = 6;
	boolean hitPlayer = false;
	
	//constructor
	public Enemy(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
	}
	
	//making them actually move down the screen unless destroyed
	public void update(GraphicsContext gc) {
		super.update();
		if(!exploding && !destroyed) posY += SPEED;
		if(posY > ImaginBlastMain.HEIGHT) destroyed = true;
	}
}
