package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * ITEM CLASS - Represents collectible items in the game
 * Items fall from the top of the screen and can be collected by the player
 * Examples could include power-ups, extra points, health, or ammo
 */
public class Item {

	// POSITION AND SIZE PROPERTIES
	int posX, posY;      // Current position of the item on screen
	int size;            // Width and height of the item (square)
	Image img;           // The item's image/icon
	
	// MOVEMENT PROPERTIES
	int speed = 5;       // Falling speed (pixels per frame)
	
	// STATE FLAGS
	boolean collected = false; // True when player picks up the item
	boolean gone = false;      // True when item should be removed (collected or off-screen)
	
	/**
	 * CONSTRUCTOR - Creates a new item
	 * @param posX Initial X position (usually random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the item sprite
	 * @param image The item's image (power-up, extra life, etc.)
	 */
	public Item(int posX, int posY, int size,  Image image) {
		this.posX = posX;    // Set X position
		this.posY = posY;    // Set Y position
		this.size = size;    // Set size
		img = image;         // Set the image
	}
	
	/**
	 * DRAW METHOD - Renders the item on screen
	 * @param gc GraphicsContext used for drawing
	 */
	public void draw(GraphicsContext gc) {
		// Draw the item image at its current position, scaled to size
		gc.drawImage(img, posX, posY, size, size);
	}
	
	/**
	 * UPDATE METHOD - Called every frame to update item behavior
	 * Handles movement, collection state, and screen boundaries
	 * @param gc GraphicsContext (passed for consistency but not used directly)
	 */
	public void update(GraphicsContext gc) {
		// COLLECTION HANDLING
		// If the item has been collected, mark it as gone immediately
		if(collected == true) {
			this.gone = true; // Item disappears when collected
		}
		
		// MOVEMENT LOGIC
		// Only move down if not collected and not already gone
		if(!collected && !gone) {
			posY += speed; // Fall downward at specified speed
		}
		
		// SCREEN BOUNDARY CHECK
		// If item falls past the bottom of the screen
		if(posY > ImaginBlastMain.HEIGHT) {
			gone = true; // Mark for removal (missed opportunity)
			// Note: Item disappears without giving player any benefit
		}
	}
}