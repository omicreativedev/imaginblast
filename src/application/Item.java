package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * ITEM CLASS - Generic base class for all collectible items in the game
 * Items fall from the top of the screen and can be collected by the player
 * Specific item types (Acorn, Carrot, Starfish) extend this class
 */
public class Item {

	// POSITION AND SIZE PROPERTIES
	int posX, posY;      // Current position of the item on screen
	int size;            // Width and height of the item (square)
	Image img;           // The item's image/icon
	

	
	// STATE FLAGS
	boolean collected = false; // True when player picks up the item
	boolean gone = false;      // True when item should be removed (collected or off-screen)
	
	/**
	 * CONSTRUCTOR - Creates a new item
	 * @param posX Initial X position (usually random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the item sprite
	 * @param image The item's image
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
	 * Can be overridden by subclasses for different movement patterns
	 * @param gc GraphicsContext (passed for consistency)
	 */
	public void update(GraphicsContext gc) {
		}
	}
