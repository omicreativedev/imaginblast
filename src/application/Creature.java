package application;

// JavaFX imports for graphics handling
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * CREATURE CLASS - Base class for all living/active entities in the game
 * Serves as the parent class for Player and Enemy
 * Handles common properties like position, size, image rendering, and explosion effects
 */
public class Creature {
	
	// EXPLOSION ANIMATION CONSTANTS
	// These are static because they're shared across all creatures
	static final Image EXPLOSION_IMG = new Image("explosion.png");  // Explosion sprite sheet
	static final int EXPLOSION_W = 128;      // Width of each explosion frame
	static final int EXPLOSION_ROWS = 3;     // Number of rows in sprite sheet
	static final int EXPLOSION_COL = 3;      // Number of columns in sprite sheet
	static final int EXPLOSION_H = 128;      // Height of each explosion frame
	static final int EXPLOSION_STEPS = 15;   // Total frames in explosion animation

	// POSITION AND SIZE PROPERTIES
	int posX, posY;      // Current position on screen
	int size;            // Width and height of creature (square)
	
	// STATE FLAGS
	boolean exploding;   // True when creature is in explosion animation
	boolean destroyed;   // True when explosion animation is complete
	
	// IMAGE PROPERTIES
	Image img;           // The creature's normal image (player or enemy)
	
	// ANIMATION TRACKING
	int explosionStep = 0;  // Current frame of explosion animation (0 to EXPLOSION_STEPS)
	
	/**
	 * CONSTRUCTOR - Creates a new creature
	 * @param posX Initial X position
	 * @param posY Initial Y position
	 * @param size Size of the creature (square)
	 * @param image The creature's normal image (frog for player, squirrel for enemy)
	 */
	public Creature(int posX, int posY, int size,  Image image) {
		this.posX = posX;    // Set X position
		this.posY = posY;    // Set Y position
		this.size = size;    // Set size
		img = image;         // Store the normal image
	}
	
	/**
	 * UPDATE METHOD - Called every frame to update creature state
	 * Handles explosion animation progression and destruction detection
	 */
	public void update() {
		// If currently exploding, advance to next explosion frame
		if(exploding) {
			explosionStep++;
		}
		// Creature is fully destroyed when explosion animation completes
		// (explosionStep goes from 0 to EXPLOSION_STEPS)
		destroyed = explosionStep > EXPLOSION_STEPS;
	}
	
	/**
	 * DRAW METHOD - Renders the creature on screen
	 * Shows either the normal creature image or explosion animation
	 * @param gc GraphicsContext used for drawing
	 */
	public void draw(GraphicsContext gc) {
		if(exploding) {
			// DRAW EXPLOSION ANIMATION
			// Calculate which frame of the sprite sheet to display
			// explosionStep % EXPLOSION_COL = column (0,1,2 repeating)
			// (explosionStep / EXPLOSION_ROWS) = row (0,1,2,3,4...)
			gc.drawImage(EXPLOSION_IMG, 
					// Source rectangle in sprite sheet
					explosionStep % EXPLOSION_COL * EXPLOSION_W,           // Source X
					(explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,   // Source Y (+1 fixes alignment)
					EXPLOSION_W, EXPLOSION_H,                              // Source width/height
					// Destination rectangle on screen
					posX, posY, size, size);                               // Destination position/size
		}
		else {
			// DRAW NORMAL CREATURE IMAGE
			gc.drawImage(img, posX, posY, size, size);
		}
	}
	
	/**
	 * EXPLODE METHOD - Triggers the explosion sequence
	 * Called when creature is hit by shot (enemy) or collides with enemy (player)
	 */
	public void explode() {
		exploding = true;      // Start explosion animation
		explosionStep = -1;    // Start at -1 so first update brings it to 0
		// Note: Starting at -1 ensures explosionStep becomes 0 on first update
	}
	
	/**
	 * SHOOT METHOD - Creates a projectile fired by the creature
	 * Currently only used by player, but could be used for enemy shooting in future
	 * @return A new Shot object positioned at the creature's center
	 */
	public Shot shoot() {
		// Calculate shot position:
		// X: creature's center (posX + size/2) minus half the shot width
		// Y: just above the creature (posY - shot height/2)
	    return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size / 2);
	}
}