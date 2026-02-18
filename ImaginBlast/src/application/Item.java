package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item {

	int posX, posY, size;
	Image img;
	int speed = 5; 
	boolean collected = false;
	boolean gone = false; // lets you get rid of it if it goes off screen without being collected
	
	// constructor
	public Item(int posX, int posY, int size,  Image image) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		img = image;
	}
	
	// draw onto the screen
	public void draw(GraphicsContext gc) {
			gc.drawImage(img, posX, posY, size, size);
		}
	
	//making them move down the screen unless collected or they go off screen
	public void update(GraphicsContext gc) {
		if(collected == true) this.gone = true; // if it's collected, make it go poof
		if(!collected && !gone) posY += speed;
		if(posY > ImaginBlastMain.HEIGHT) gone = true; // get rid of it once it's off screen
	}
	

}
