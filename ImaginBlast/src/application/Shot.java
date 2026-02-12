package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shot {
	public boolean toRemove;

	int posX, posY, speed = 10;
	static final int size = 6;
		
	public Shot(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void update() {
		posY-=speed;
	}

	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.LIGHTBLUE);
		gc.fillOval(posX, posY, size, size);
	}


}
