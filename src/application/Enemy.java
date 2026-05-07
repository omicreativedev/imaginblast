package application;

import javafx.scene.image.ImageView;

// "It would be so nice if something made sense for a change."
// ~ Alice, Alice in Wonderland

/**
 * ENEMY CLASS
 * Represents enemies in the game
 * Extends the Creature class to inherit basic
 * creature properties and behaviors
 */
public class Enemy extends Creature {
	
	// CLASS VARIABLES
	// Track if this enemy has hit the player
	// (prevents multiple hits from same enemy)
	boolean hitPlayer = false;
	
	/**
	 * CONSTRUCTOR - Creates a new enemy
	 * @param posX Initial X position (usually random across screen width)
	 * @param posY Initial Y position (usually 0 at top of screen)
	 * @param size Size of the enemy sprite (typically PLAYER_SIZE = 60)
	 * @param imageView The enemy's ImageView (SQUIRREL_IMG)
	 */
	public Enemy(int posX, int posY, int size, ImageView imageView) {
		// Call the parent Creature constructor to set up basic properties
		super(posX, posY, size, imageView);
	}
	
}