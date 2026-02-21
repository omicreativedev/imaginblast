package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * ACORN ITEM CLASS
 * Acorns fall straight down and give 1 point when collected
 */

public class ItemAcorn extends Item {
	
	/**
	 * CONSTRUCTOR
	 * Create a new acorn item
	 * @param posX Initial X position (random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the acorn sprite
	 * @param image The acorn image
	 */
	public ItemAcorn(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
		// Acorn-specific initialization can go here later
	}
	
	// MOVEMENT PROPERTIES
	int speed = 5;       // Falling speed (pixels per frame)
	
	// ACORN-SPECIFIC PROPERTIES
	// Could add pointValue, special effects, etc. later
	
	/**
	 * UPDATE METHOD
	 * Acorn-specific behavior
	 * Currently uses default falling
	 * behavior
	 * @param gc GraphicsContext for drawing
	 */
	@Override
	public void update(GraphicsContext gc) {
		

		// COLLECTION HANDLING
		// If the item has been collected, mark it as gone immediately
		if(collected == true) {
			this.gone = true; // Item disappears when collected
		}
		
		// MOVEMENT LOGIC - Default is falling straight down
		// Subclasses can override for different movement patterns
		if(!collected && !gone) {
			posY += speed; // Fall downward at specified speed
		}
		
		// SCREEN BOUNDARY CHECK
		// If item falls past the bottom of the screen
		if(posY > ImaginBlastMain.HEIGHT) {
			gone = true; // Mark for removal
		}
		
		// FUTURE:
		// Examples:
		// - Bounce slightly
		// - Spin while falling
		// - Make sound when collected
		// - Give bonus points
	}
}