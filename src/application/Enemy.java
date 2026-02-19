package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * ENEMY CLASS - Represents enemy squirrels in the game
 * Extends the Creature class to inherit basic creature properties and behaviors
 * Specializes in downward movement and screen boundary detection
 */
public class Enemy extends Creature {
	
	// CLASS VARIABLES
	int SPEED = 6; // Movement speed of the enemy (pixels per frame)
	              // This can change depending on the enemy type - 
	              // Future enhancement: could move to a squirrel subclass for different enemy types
	boolean hitPlayer = false; // Flag to track if this enemy has hit the player

	
	/**
	 * CONSTRUCTOR - Creates a new enemy
	 * @param posX Initial X position (usually random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the enemy sprite (typically PLAYER_SIZE = 60)
	 * @param image The enemy's image (SQUIRREL_IMG)
	 */
	public Enemy(int posX, int posY, int size, Image image) {
		// Call the parent Creature constructor to set up basic properties
		super(posX, posY, size, image);
	}
	
	/**
	 * UPDATE METHOD - Called every frame to update enemy behavior
	 * Overrides Creature.update() to add enemy-specific movement
	 * @param gc GraphicsContext for drawing (passed but not directly used here)
	 */
	public void update(GraphicsContext gc) {
		// Call parent class update first (handles explosion animation, etc.)
		super.update();
		
		// MOVE ENEMY DOWNWARD
		// Only move if not exploding and not already destroyed
		if(!exploding && !destroyed) {
			posY += SPEED; // Move down by SPEED pixels each frame
		}
		
		// SCREEN BOUNDARY CHECK
		// If enemy has moved past the bottom of the screen
		if(posY > ImaginBlastMain.HEIGHT) {
			destroyed = true; // Mark for removal (will be replaced by new enemy)
			// Note: The enemy doesn't score points or damage player when it leaves
			// It simply gets recycled into a new enemy at the top
		}
	}
}