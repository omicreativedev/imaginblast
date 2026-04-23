package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * DONUT ITEM CLASS
 * Donuts fall straight down and give points when collected
 */
public class ItemDonut extends Item {
	
	// MOVEMENT PROPERTIES
	int speed = 8;       // Falling speed (pixels per frame)
	
	/**
	 * CONSTRUCTOR
	 * Create a new donut item
	 * @param posX Initial X position (random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the donut sprite
	 * @param image The donut image
	 */
	public ItemDonut(int posX, int posY, int size, Image image) {
		super(posX, posY, size, image);
		// Donut-specific initialization can go here later
	}
	

	
	// DONUT-SPECIFIC PROPERTIES
	// Could add pointValue, special effects, etc. later
	
	public void onCollected() {
	    // Mark as collected
	    this.collected = true;
	    
	    // FUTURE: Play sound here
	    // FUTURE: Add visual effect
	    // FUTURE: Give bonus points
	    
	    // For now, just mark as collected
	    // The base Item class will handle setting gone = true in update()
	}
	
	/**
	 * UPDATE METHOD
	 * Donut-specific behavior
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