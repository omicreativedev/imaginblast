package application;

import javafx.scene.image.Image;

/**
 * ENEMY CLASS
 * Represents enemies in the game
 * Extends the Creature class to inherit basic
 * creature properties and behaviors
 */
public class Enemy extends Creature {
	
	// CLASS VARIABLES
	boolean hitPlayer = false; // Track if this enemy has hit the player (prevents multiple hits from same enemy)

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
	
}