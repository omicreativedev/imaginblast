package application;

// JavaFX import for image handling
import javafx.scene.image.Image;

/**
 * PLAYER CLASS - Represents the player character in the game
 * Extends Creature to inherit basic creature properties and behaviors
 * Adds player-specific attributes like health and item collection tracking
 */
public class Player extends Creature {
	
	// PLAYER STATS
	public int hp = 50;           // Player health points
	                            // Starting at 50 gives player multiple hits before game over
	
	public int col_items = 0;     // Counter for collected items (power-ups, points, etc.)
	                            // "col_items" = "collected items" - tracks total pickups
	
	/**
	 * CONSTRUCTOR - Creates a new player
	 * @param posX Initial X position (typically center of screen)
	 * @param posY Initial Y position (near bottom of screen)
	 * @param size Size of the player sprite
	 * @param image The player's image (frog character)
	 */
	public Player(int posX, int posY, int size, Image image) {
		// Call parent Creature constructor to set up position, size, and image
		super(posX, posY, size, image);
		// hp and col_items keep their default values (50 and 0)
	}
	
	/**
	 * SHOOT METHOD - Creates a projectile fired by the player
	 * Overrides the Creature.shoot() method to customize shot position
	 * @return A new Shot object positioned at the player's center
	 */
	public Shot shoot() {
		// Calculate shot position:
		// X: player's center (posX + size/2) minus half the shot width
		// Y: directly above the player (posY - full shot height)
		return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}
}