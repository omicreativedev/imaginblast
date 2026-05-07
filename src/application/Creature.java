package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//"The question is,' said Alice, 'whether you can make words mean so many different things.'"
//~ Alice, Through the Looking-Glass

/**
 * CREATURE CLASS
 * Base class for all entities in the game
 * Serves as the parent class for Player, Enemy and Boss
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Creature {
	
	// EXPLOSION ANIMATION CONSTANTS
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final int EXPLOSION_W = 128;      // Width of each explosion frame
	static final int EXPLOSION_ROWS = 3;     // Number of rows in sprite sheet
	static final int EXPLOSION_COL = 3;      // Number of columns in sprite sheet
	static final int EXPLOSION_H = 128;      // Height of each explosion frame
	static final int EXPLOSION_STEPS = 15;   // Total frames in explosion animation

	// POSITION AND SIZE PROPERTIES
	int posX, posY;      	// Current position on screen
	int size;            	// Width and height of creature (square)
	
	// STATE
	boolean exploding;   	// True when creature is in explosion animation
	boolean destroyed;   	// True when explosion animation is complete
	
	// IMAGE PROPERTIES
	ImageView imageView;
	
	// ANIMATION TRACKING
	int explosionStep = 0;  // Current frame of explosion animation (0 to EXPLOSION_STEPS)
	
	/**
	 * CREATURE CONSTRUCTOR
	 * Constructs a new Creature at the specified position with given size and image
	 * 
	 * @param posX Initial X coordinate on screen
	 * @param posY Initial Y coordinate on screen
	 * @param size Width and height of the creature (assumed square)
	 * @param imageView The creature's visual representation (frog for player, squirrel for enemy, etc.)
	 */
	public Creature(int posX, int posY, int size, ImageView imageView) {
		this.posX = posX;    // Set X position
		this.posY = posY;    // Set Y position
		this.size = size;    // Set size
		this.imageView = imageView;
	}
	
	/**
	 * UPDATE METHOD
	 * Called every frame to update creature state
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
	 * DRAW METHOD
	 * Renders the creature on screen
	 * Shows either the normal creature image or the current explosion animation frame
	 * 
	 * @param gc GraphicsContext used for drawing to the canvas
	 */
	public void draw(GraphicsContext gc) {
		if(exploding) {
			// DRAW EXPLOSION ANIMATION
			// Calculates which frame of the sprite sheet to display based on current step
			gc.drawImage(EXPLOSION_IMG, 
					// Source rectangle in sprite sheet
					explosionStep % EXPLOSION_COL * EXPLOSION_W,           // Source X coordinate
					(explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,    // Source Y coordinate (+1 fixes alignment issue)
					EXPLOSION_W, EXPLOSION_H,                              // Source width and height
					// Destination rectangle on screen
					posX, posY, size, size);                               // Destination position and size
		}
		else {
			// DRAW NORMAL CREATURE IMAGE
			gc.drawImage(imageView.getImage(), posX, posY, size, size);
		}
	}
	
	/**
	 * EXPLODE METHOD
	 * Triggers the explosion sequence for this creature
	 * Called when creature is hit by a shot (enemy) or collides with an enemy (player)
	 */
	public void explode() {
		exploding = true;      // Start explosion animation
		explosionStep = -1;    // Start at -1 so first update brings it to 0
		// Note: Starting at -1 ensures explosionStep becomes 0 on the first update call
	}
	
	/**
	 * SHOOT METHOD
	 * Creates a projectile fired by the creature
	 * Currently only implemented by Player class, but could be extended for enemies
	 * 
	 * @return A new Shot object positioned at the creature's center, or null if creature doesn't shoot
	 */
	public Shot shoot() {
		return null;  // Most creatures don't shoot (base class default behavior)
	}
}