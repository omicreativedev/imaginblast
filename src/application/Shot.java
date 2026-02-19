package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shot {
	public boolean toRemove; // this comes in for the collision function (remove once it's collided with an enemy or boss)

	int posX, posY, speed = 10;
	static final int size = 6;
		
	//constructor
	public Shot(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	// makes the shot move
	public void update() {
		posY-=speed;
	}

	// draw onto the screen
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.LIGHTBLUE);
		gc.fillOval(posX, posY, size, size);
	}


}
