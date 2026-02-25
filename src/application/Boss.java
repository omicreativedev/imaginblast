package application;

import javafx.scene.image.Image;
import java.util.List;

/**
 * ABSTRACT BOSS CLASS
 * Defines the base structure for all boss enemies in the game
 * Extends Creature to inherit basic entity properties
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public abstract class Boss extends Creature {
    
    // Boss-specific attributes
    protected int health;           // Current health of the boss
    protected int maxHealth;         // Maximum health for health bar display
    protected int phase;             // Current battle phase 1, 2, etc. - changes boss behavior
    protected boolean portalSpawned; // Has the exit portal been created?
    protected boolean portalActive;  // Is the portal currently active/collidable?
    
    /**
     * BOSS CONSTRUCTOR
     * Creates a new boss at specified position with given size and image
     * Calls parent constructor (Creature) to handle basic entity setup
     * 
     * Reference: https://docs.oracle.com/javase/tutorial/java/IandI/super.html
     * 
     * @param posX  Initial X coordinate
     * @param posY  Initial Y coordinate
     * @param size  Width/height of boss sprite (assumed square)
     * @param image Visual representation of the boss
     */
    public Boss(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image); // Pass parameters to Creature constructor
    }
    
    /**
     * ABSTRACT UPDATE METHOD
     * Each boss type must implement its own movement and behavior logic
     * Called every frame during boss fight to update boss position/state
     * 
     * @param player Reference to player object (bosses track player for targeting)
     */
    public abstract void update(Player player); // Track player
    
    /**
     * ABSTRACT SHOOT METHOD
     * Each boss type implements its own attack pattern
     * Creates and adds boss projectiles to the enemy shots list
     * 
     * @param shots List of enemy shots to add new projectiles to
     */
    public abstract void shoot(List<Shot> shots); // Add boss shots
    
    /**
     * ABSTRACT DAMAGE METHOD
     * Each boss type handles damage differently (may have invincibility phases, etc.)
     * Reduces health and potentially triggers phase changes
     * 
     * @param amount Amount of damage to inflict
     */
    public abstract void takeDamage(int amount);
    
    /**
     * DEFEATED CHECK
     * Determines if boss has been defeated (health depleted)
     * Used to trigger portal spawning and level completion
     * 
     * @return true if health is 0 or below, false otherwise
     */
    public boolean isDefeated() { return health <= 0; }
    
    /**
     * HEALTH GETTER
     * Returns current boss health for UI display (health bars)
     * 
     * @return Current health value
     */
    public int getHealth() { return health; }
    
    /**
     * MAX HEALTH GETTER
     * Returns maximum boss health for UI display (health bar scaling)
     * 
     * @return Maximum health value
     */
    public int getMaxHealth() { return maxHealth; }
}