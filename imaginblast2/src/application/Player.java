package application;

import javafx.scene.image.Image;

/**
 * PLAYER CLASS
 * Represents the player character in the game
 * Extends Creature to inherit basic creature properties and behaviors
 * Adds player-specific attributes like health and item collection
 */

public class Player extends Creature {
	
	// PLAYER STATS
	public int hp = 100;        // Player starting health points
	public int maxHp = 100;     // Maximum possible health points
	                            // Starting at 50 gives player multiple hits before game over
	
	public int col_items = 0;   // Counter for collected items (power-ups, points, etc.)
	                            // "col_items" = "collected items" - tracks total pickups
								// Can we change this to collectedItems?
	
	// These stop rapid health point loss
	private int invincibilityFrames = 0;
    private static final int INVINCIBILITY_DURATION = 60;
    private boolean isShielded = false;
	
    
    
	 /**
     * Take damage from enemy collision, projectile, etc.
     * @param amount Amount of damage to take
     * @return true if player still alive, false if dead
     */
    public boolean takeDamage(int amount) {

    	if (invincibilityFrames > 0 || isShielded) {
            return true; // Still alive, but no damage
        }
        
        hp -= amount;
        
        // Future: Trigger hit animation or sound
        
        if (hp <= 0) {
            hp = 0;
            explode();
            return false;  // Player is dead
        }
        
        invincibilityFrames = INVINCIBILITY_DURATION;
        
        return true;  // Still alive
    }
    
    /**
     * Shield the player
     */
    public void updateInvincibility() {
        if (invincibilityFrames > 0) {
            invincibilityFrames--;
        }
    }
    
    /**
     * Heal the player
     * @param amount Amount to heal
     */
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
    
    /**
     * Increase max health (for power-ups)
     */
    public void increaseMaxHp(int amount) {
        maxHp += amount;
        hp += amount;  // Also heal by same amount
    }
    
    /**
     * Reset health for new game
     */
    public void resetHealth() {
        hp = maxHp;  // Reset to max (might be increased due to power-up. Must debug.
    }
    

	
    /**
     * UPDATE METHOD
     * Called every frame to update player state
     * Overrides Creature.update() to add invincibility countdown
     */
    @Override
    public void update() {
        super.update(); // Call Creature's update (handles explosion)
        updateInvincibility(); // Count down invincibility frames
    }
	
	
	
	
	/**
	 * CONSTRUCTOR
	 * Creates a new player
	 * @param posX Initial X position (typically center of screen)
	 * @param posY Initial Y position (near bottom of screen)
	 * @param size Size of the player sprite
	 * @param image The player's image (thee frogboy) Note: We should name him.
	 */
	public Player(int posX, int posY, int size, Image image) {
		// Call parent Creature constructor to set up position, size, and image
		super(posX, posY, size, image);
		// hp and col_items keep their default values
	}
	
	
	
	/**
	 * SHOOT METHOD
	 * Creates a projectile fired by the player
	 * Overrides the Creature.shoot() method to customize shot position
	 * @return A new Shot object positioned at the player's center
	 */
	@Override
	public Shot shoot() {
		// Calculate shot position:
		// X: player's center (posX + size/2) minus half the shot width
		// Y: directly above the player (posY - full shot height)
		return new ShotStandard(posX + size / 2 - Shot.SIZE / 2, posY - Shot.SIZE);
	}
}