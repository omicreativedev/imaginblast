
package application;


import javafx.scene.canvas.GraphicsContext;
//import application.SpaceInvaders2.Shot;
import javafx.scene.image.Image;

public class Creature {
	
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final int EXPLOSION_W = 128;
	static final int EXPLOSION_ROWS = 3;
	static final int EXPLOSION_COL = 3;
	static final int EXPLOSION_H = 128;
	static final int EXPLOSION_STEPS = 15;

	int posX, posY, size;
	boolean exploding, destroyed;
	Image img;
	int explosionStep = 0;
	
	public Creature(int posX, int posY, int size,  Image image) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		img = image;
	}
	
	public Shot shoot() {
		return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}

	public void update() {
		if(exploding) explosionStep++;
		destroyed = explosionStep > EXPLOSION_STEPS;
	}
	
	public void draw(GraphicsContext gc) {
		if(exploding) {
			gc.drawImage(EXPLOSION_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_W, (explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,
					EXPLOSION_W, EXPLOSION_H,
					posX, posY, size, size);
		}
		else {
			gc.drawImage(img, posX, posY, size, size);
		}
	}
	
	public void explode() {
		exploding = true;
		explosionStep = -1;
	}

}