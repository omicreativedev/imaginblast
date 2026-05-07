package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

// "Who are you?" said the Caterpillar. ~ Alice's Adventures in Wonderland
// "Begin at the beginning," the King said, "and go on till you come to the end: then stop." ~ The King of Hearts

/**
 * BOSS BUNNY CLASS
 * Implementation of the Boss abstract class (Boss.java)
 * Final boss bunny that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * We may change this later to offer different explosions for different Bosses
 * NEW: Bunny fires a ring of carrots (8 directions) as her unique pattern
 */
public class BossBunny extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0; // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 8; // Movement speed
    private static final int BULLET_SPEED = 12; // Speed of boss projectiles
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_carrot.png"));
    
    /**
     * BOSS BUNNY CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and bunny image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public BossBunny(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_bunny.png")));
        this.health = 700; // Set current health (inherited from Boss.java)
        this.maxHealth = 700; // Set max health for health bar (inherited from Boss.java)
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Required by Boss.java's abstract update() method
     * Handles boss movement behavior each frame
     * Boss follows player's X position with sine wave variation
     * 
     * Reference: https://forum.jogamp.org/Can-JOGL-be-used-without-requiring-GLAutoDrawable-instances-tt4034953.html#a4034966
     * 
     * @param player Reference to player object (used for tracking/targeting)
     */
    @Override
    public void update(Player player) {
        super.update(); // Call Creature.java's update method to handle explosion animation
        
        // If boss is exploding or destroyed, skip movement logic
        if (exploding || destroyed) return;
        
        // Calculate target X position to center boss over player
        int targetX = player.posX - size/2; // Center boss over player
        
        // Move toward player's X position (horizontal tracking behavior)
        if (posX < targetX) {
            posX += speed; // Move right if player is to the right
        } else if (posX > targetX) {
            posX -= speed; // Move left if player is to the left
        }
        
        // Sine wave variation for organic movement
        // Source: https://forum.jogamp.org/Can-JOGL-be-used-without-requiring-GLAutoDrawable-instances-tt4034953.html#a4034966
        // Every frame, this line adds a small value (-2 to 2) to the boss's X position,
        // making it wiggle back and forth while also following the player.
        // Math explanation: Takes current time in milliseconds * 0.005, passes through sine function,
        // multiplies by 2 to get range of -2 to 2, then adds to posX each frame
        posX += Math.sin(System.currentTimeMillis() * 0.005) * 2;
        
        // Keep boss within screen boundaries
        if (posX < 0) {
            posX = 0; // Left boundary check
        }
        if (posX + size > ImaginBlastMain.WIDTH) {
            posX = ImaginBlastMain.WIDTH - size; // Right boundary check
        }
        
        // Decrement shoot cooldown counter each frame
        if (shootCooldown > 0) shootCooldown--;
    }
    
    /**
     * CALCULATE FIXED DIRECTION
     * Creates a direction vector at a fixed angle (for ring shots)
     * 
     * @param angleRadians Angle in radians
     * @return double array where [0] = X direction, [1] = Y direction
     */
    private double[] calculateFixedDirection(double angleRadians) {
        return new double[]{Math.cos(angleRadians), Math.sin(angleRadians)};
    }
    
    /**
     * OVERRIDE SHOOT METHOD
     * Required by Boss.java's abstract shoot() method
     * Creates an enemy projectile (currently unaimed, straight down)
     * Shots originate from center of boss sprite
     * 
     * @param shots List of enemy shots to add the new projectile to
     */
    @Override
    public void shoot(List<Shot> shots) {
        // Only shoot if cooldown is zero AND boss isn't exploding/destroyed
        if (shootCooldown <= 0 && !exploding && !destroyed) {
            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Create new enemy shot at center of boss (straight down for now)
            shots.add(new EnemyShot(shotX, shotY));
            shootCooldown = 25; // Reset cooldown (25 frames between shots)
        }
    }
    
    /**
     * OVERRIDE SHOOT AT PLAYER METHOD
     * Required by Boss.java's abstract shootAtPlayer() method
     * NEW: Fires a ring of 8 carrots in all directions (unique final boss pattern)
     * Called by BossScreen with player position for targeting
     * 
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player object for aiming (uses player center position)
     */
    @Override
    public void shootAtPlayer(List<Shot> shots, Player player) {
        // Only shoot if cooldown is zero AND boss isn't exploding/destroyed
        if (shootCooldown <= 0 && !exploding && !destroyed) {
            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Ring of 8 carrots (every 45 degrees)
            for (int i = 0; i < 8; i++) {
                double angle = (Math.PI * 2 / 8) * i;
                double[] direction = calculateFixedDirection(angle);
                double velX = direction[0] * BULLET_SPEED;
                double velY = direction[1] * BULLET_SPEED;
                shots.add(new EnemyShot(shotX, shotY, velX, velY, BULLET_IMG));
            }
            
            // Longer cooldown for ring shot (slower but devastating)
            shootCooldown = 50; // Reset cooldown (50 frames between ring shots)
        }
    }
    
    /**
     * OVERRIDE TAKE DAMAGE METHOD
     * Required by Boss.java's abstract takeDamage() method
     * Reduces boss health and triggers explosion animation when defeated
     * 
     * @param amount Amount of damage to inflict on the boss
     */
    @Override
    public void takeDamage(int amount) {
        health -= amount; // Reduce health by damage amount
        if (health <= 0) {
            health = 0;
            explode(); // Call Creature.java's explode() method to start death animation
        }
    }
}