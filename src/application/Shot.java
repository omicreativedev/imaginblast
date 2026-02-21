package application;

// JavaFX imports for graphics handling
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * SHOT CLASS - Represents projectiles fired by the player
 * Shots travel upward and can collide with enemies
 * Simple circular projectiles with basic movement
 */
public class Shot {
	
	// STATE FLAGS
	public boolean toRemove; // Flag indicating this shot should be removed
	                         // Set to true when shot collides with an enemy or boss
	                         // Checked in main game loop to remove from shots list

	// POSITION AND MOVEMENT
	int posX, posY;      // Current position of the shot on screen
	int speed = 10;      // Upward speed (pixels per frame)
	                     // Positive value but subtracted in update() for upward movement
	
	// SIZE CONSTANT
	static final int size = 6;  // Diameter of the shot oval (pixels)
	                            // Static because all shots are the same size
	                            // Final because size shouldn't change
	
	/**
	 * CONSTRUCTOR - Creates a new shot at specified position
	 * @param posX Initial X position (centered on player)
	 * @param posY Initial Y position (just above player)
	 */
	public Shot(int posX, int posY) {
		this.posX = posX;    // Set X position (from player's center)
		this.posY = posY;    // Set Y position (above player)
		// toRemove defaults to false
		// speed defaults to 10
	}
	
	/**
	 * UPDATE METHOD - Called every frame to move the shot
	 * Moves the shot upward by subtracting speed from Y position
	 */
	public void update() {
		posY -= speed;  // Move upward (decreasing Y coordinate)
		                // Note: In screen coordinates, Y=0 is top, Y increases downward
		                // So subtracting moves the shot UP
	}

	/**
	 * DRAW METHOD - Renders the shot on screen
	 * @param gc GraphicsContext used for drawing
	 */
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.LIGHTBLUE);     // Set shot color to light blue
		                                 // Makes shots visible against forest green background
		gc.fillOval(posX, posY, size, size); // Draw circular shot at current position
		                                     // fillOval draws a filled oval/circle
		                                     // Using same size for width and height makes it a circle
	}
}