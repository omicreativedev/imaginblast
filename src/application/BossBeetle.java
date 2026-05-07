package application;

//Adapted from BossPirate.java

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Random;

// "We're all mad here." ~ Cheshire Cat

/**
 * BOSS BEETLE CLASS
 * Implementation of the Boss abstract class (Boss.java)
 * Beetle boss that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * Note: We may change this later to offer different explosions for different Bosses
 * Boss now shoots at the player's current position (aimed shots)
 * NEW: Added burst fire and random spread for increased difficulty
 */
public class BossBeetle extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0;      // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 15;             // Movement speed (pixels per frame)
    private static final int BULLET_SPEED = 20;        // Speed of boss projectiles (slower than player shots)
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_rock.png"));
    
    // Burst fire variables
    private int burstRemaining = 0;     // Number of shots remaining in current burst
    private int burstDelay = 0;         // Frames between shots within a burst
    private Random random = new Random(); // For random spread
    
    /**
     * BOSS BEETLE CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and beetle image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate on screen
     * @param posY Starting Y coordinate on screen
     */
    public BossBeetle(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_beetle.png")));
        this.health = 250;      // Set current health (inherited from Boss.java)
        this.maxHealth = 250;   // Set max health for health bar (inherited from Boss.java)
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
        int targetX = player.posX - size/2; // Center boss horizontally over player
        
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
        
        // Update burst delay counter
        if (burstDelay > 0) {
            burstDelay--;
        }
        
        // Decrement shoot cooldown counter each frame
        if (shootCooldown > 0) shootCooldown--;
    }
    
    /**
     * CALCULATE DIRECTION VECTOR
     * Calculates normalized direction vector from boss center to target (player)
     * Used for aiming shots at the player
     * 
     * Reference: Vector math from https://gamedev.stackexchange.com/questions/122374
     * 
     * @param targetX Target X coordinate (player center)
     * @param targetY Target Y coordinate (player center)
     * @return double array where [0] = normalized X direction, [1] = normalized Y direction
     */
    private double[] calculateDirection(double targetX, double targetY) {
        // Get boss center coordinates (where shot originates)
        double fromX = posX + size / 2;
        double fromY = posY + size / 2;
        
        // Calculate difference between target and shooter (direction vector)
        double dx = targetX - fromX;
        double dy = targetY - fromY;
        
        // Calculate distance (length of the vector)
        double length = Math.sqrt(dx * dx + dy * dy);
        
        // Normalize the vector to unit length, preserves direction, sets magnitude to 1
        if (length != 0) {
            dx /= length;
            dy /= length;
        } else {
            // If target is exactly at boss center, shoot downward as default fallback
            dx = 0;
            dy = 1;
        }
        return new double[]{dx, dy};
    }
    
    /**
     * CALCULATE DIRECTION WITH RANDOM SPREAD
     * Same as calculateDirection but adds random spread angle for variety
     * 
     * @param targetX Target X coordinate (player center)
     * @param targetY Target Y coordinate (player center)
     * @param spreadAngle Maximum angle deviation in radians (0.1 = ~5.7 degrees)
     * @return double array where [0] = X direction with spread, [1] = Y direction with spread
     */
    private double[] calculateDirectionWithSpread(double targetX, double targetY, double spreadAngle) {
        double[] direction = calculateDirection(targetX, targetY);
        double dx = direction[0];
        double dy = direction[1];
        
        // Add random spread to the direction
        double angle = Math.atan2(dy, dx);
        angle += (random.nextDouble() - 0.5) * spreadAngle;
        
        double newDx = Math.cos(angle);
        double newDy = Math.sin(angle);
        
        return new double[]{newDx, newDy};
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
        if (shootCooldown <= 0 && !exploding && !destroyed && burstDelay == 0) {
            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Create new enemy shot at center of boss (straight down default direction)
            shots.add(new EnemyShot(shotX, shotY));
            shootCooldown = 20; // Reset cooldown (20 frames between bursts)
        }
    }
    
    /**
     * OVERRIDE SHOOT AT PLAYER METHOD
     * Required by Boss.java's abstract shootAtPlayer() method
     * Creates enemy projectiles aimed at the player's current position
     * NEW: Fires in bursts of 3 with random spread for increased difficulty
     * Called by BossScreen with player position for targeting
     * 
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player object for aiming (uses player center position)
     */
    @Override
    public void shootAtPlayer(List<Shot> shots, Player player) {
        // Only shoot if boss isn't exploding/destroyed
        if (exploding || destroyed) return;
        
        // Handle burst firing
        if (burstRemaining > 0 && burstDelay == 0) {
            // Fire one shot of the burst
            fireSingleShot(shots, player);
            burstRemaining--;
            burstDelay = 5; // 5 frames between shots in a burst (faster than cooldown)
        } else if (burstRemaining <= 0 && shootCooldown <= 0) {
            // Start a new burst
            burstRemaining = 3; // Fire 3 shots per burst
            fireSingleShot(shots, player);
            burstRemaining--;
            burstDelay = 5;
            shootCooldown = 30; // Longer cooldown between bursts (30 frames)
        }
    }
    
    /**
     * FIRE SINGLE SHOT
     * Helper method that creates one aimed projectile at the player
     * Uses random spread for bullet variety
     * 
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player object for aiming
     */
    private void fireSingleShot(List<Shot> shots, Player player) {
        // Get player center position for aiming
        double playerCenterX = player.posX + player.size / 2;
        double playerCenterY = player.posY + player.size / 2;
        
        // Add small random spread to make shots less predictable (0.15 rad = ~8.6 degrees)
        double[] direction = calculateDirectionWithSpread(playerCenterX, playerCenterY, 0.15);
        double velX = direction[0] * BULLET_SPEED;
        double velY = direction[1] * BULLET_SPEED;
        
        // Calculate shot starting position (center of boss)
        int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
        int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
        
        // Create new aimed enemy shot with calculated velocity
        shots.add(new EnemyShot(shotX, shotY, velX, velY, BULLET_IMG));
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
            health = 0;  // Clamp health to zero minimum
            explode();   // Call Creature.java's explode() method to start death animation
        }
    }
}