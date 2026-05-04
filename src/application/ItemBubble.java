package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

/**
 * BUBBLE ITEM CLASS
 * Bubbles fall straight down and restore player health when collected
 */
public class ItemBubble extends Item {
	
	private GameRenderer gameRenderer; //new - Reference for sound effects
	
	// MOVEMENT PROPERTIES
	int speed = 8;       // Falling speed (pixels per frame)
	
	/**
	 * CONSTRUCTOR
	 * Create a new bubble item
	 * @param posX Initial X position (random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the bubble sprite
	 * @param imageView The bubble ImageView
	 */
	public ItemBubble(int posX, int posY, int size, ImageView imageView) {
		super(posX, posY, size, imageView);
	}
	
	public void onCollected() {
	    // Mark as collected
	    this.collected = true;
	    if (gameRenderer != null) {
	        gameRenderer.playItemCollectSound();
	    }
	}
	
	/**
	 * UPDATE METHOD
	 * Bubble-specific behavior
	 */
	@Override
	public void update(GraphicsContext gc) {
		// COLLECTION HANDLING
		// If the item has been collected, mark it as gone immediately
		if(collected == true) {
			this.gone = true; // Item disappears when collected
		}
		
		// MOVEMENT LOGIC - Default is falling straight down
		if(!collected && !gone) {
			posY += speed; // Fall downward at specified speed
		}
		
		// SCREEN BOUNDARY CHECK
		// If item falls past the bottom of the screen
		if(posY > ImaginBlastMain.HEIGHT) {
			gone = true; // Mark for removal
		}
	}
	
	// Set game renderer for sound effects
	public void setGameRenderer(GameRenderer renderer) {
	    this.gameRenderer = renderer;
	}
}