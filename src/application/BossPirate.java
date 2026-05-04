package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new
import java.util.List;


// "We're all mad here." ~ Cheshire Cat

/**
 * BOSS PIRATE CLASS
 * Implementation of the Boss abstract class Boss.java
 * Cheshire cat pirate boss that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * We may change this later to offer different explosions for different Bosses
 * NEW: Boss now shoots at the player's current position (aimed shots)
 */
public class BossPirate extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0; // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 10; // Movement speed
    private static final int BULLET_SPEED = 20; // Speed of boss projectiles (slower than player shots)
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_pizza.png"));
    
    /**
     * BOSS PIRATE CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and pirate image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public BossPirate(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_pirate.png"))); //new - Pass ImageView instead of Image
        	//Cat pirate boss png has been updated 04/04 9:26 PM by Ev
        this.health = 200; // Set current health (inherited from Boss.java)
        this.maxHealth = 200; // Set max health for health bar (inherited from Boss.java)

    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Required by Boss.java's abstract update() method
     * Handles boss movement behavior each frame
     * Boss follows player's X position with sine wave variation
     * https://forum.jogamp.org/Can-JOGL-be-used-without-requiring-GLAutoDrawable-instances-tt4034953.html#a4034966
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
        
        // Variation 
        // Source: https://forum.jogamp.org/Can-JOGL-be-used-without-requiring-GLAutoDrawable-instances-tt4034953.html#a4034966
        // Every frame, this line adds a small value to the boss's X position, making it wiggle back and forth while also following the player.
        // Returns the current time in milliseconds * 0.005) * 2, then passes it through a sine function so 
        // as time increases. Takes the current boss X position. Adds the sine value. So every frame: posX = posX + number -2 to 2
        // Math from source. Applied to posX
        posX += Math.sin(System.currentTimeMillis() * 0.005) * 2;
        
        // Keep boss within screen boundaries
        if (posX < 0) {
            posX = 0; // Left boundary check
        }
        if (posX + size > ImaginBlastMain.WIDTH) {
            posX = ImaginBlastMain.WIDTH - size; // Right boundary check
        }
        
        // Counts down until boss can shoot again
        if (shootCooldown > 0) shootCooldown--;
        

    }
    
    /**
     * CALCULATE DIRECTION VECTOR
     * Calculate direction from boss to target (our frog dude)
     * Used for aiming shots at the player
     * Source: Vector math from https://gamedev.stackexchange.com/questions/122374
     * @param targetX Target X coordinate (player center)
     * @param targetY Target Y coordinate (player center)
     * @return double array where [0] = normalized X direction, [1] = normalized Y direction
     */
    private double[] calculateDirection(double targetX, double targetY) {
        // Get boss center coordinates (where shot originates)
        double fromX = posX + size / 2;
        double fromY = posY + size / 2;
        
        // Calculate difference between target and shooter
        double dx = targetX - fromX;
        double dy = targetY - fromY;
        
        // Calculate distance (length of the vector)
        double length = Math.sqrt(dx * dx + dy * dy);
        
        // Normalize (avoid division by zero)
        if (length != 0) {
            dx /= length;
            dy /= length;
        } else {
            // If target is exactly at boss center, shoot downward as default
            dx = 0;
            dy = 1;
        }
        
        return new double[]{dx, dy};
    }
    
    /**
     * OVERRIDE SHOOT METHOD
     * Required by Boss.java's abstract shoot() method
     * Creates an enemy projectile aimed at the player's current position
     * Shots originate from center of boss sprite
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player for aiming (passed from BossScreen)
     */
    @Override
    public void shoot(List<Shot> shots) {
        // Only shoot if cooldown is zero AND boss isn't exploding
        if (shootCooldown <= 0 && !exploding) {

            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Create new enemy shot at center of boss (straight down for now)
            shots.add(new EnemyShot(shotX, shotY));
            shootCooldown = 30; // Reset cooldown (30 frames between shots)
        }
    }
    
    /**
     * OVERRIDE SHOOT WITH PLAYER METHOD
     * Acept player reference for aiming
     * Called by BossScreen with player position
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player object for aiming
     */
    @Override
    public void shootAtPlayer(List<Shot> shots, Player player) {
        // Only shoot if cooldown is zero AND boss isn't exploding
        if (shootCooldown <= 0 && !exploding) {

            
            // Get player center position
            double playerCenterX = player.posX + player.size / 2;
            double playerCenterY = player.posY + player.size / 2;
            
            // Calculate direction from boss to player
            double[] direction = calculateDirection(playerCenterX, playerCenterY);
            double velX = direction[0] * BULLET_SPEED;
            double velY = direction[1] * BULLET_SPEED;
            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Create new aimed enemy shot
            shots.add(new EnemyShot(shotX, shotY, velX, velY, BULLET_IMG));
            shootCooldown = 30; // Reset cooldown (30 frames between shots)
        }
    }
    
    /**
     * OVERRIDE TAKE DAMAGE METHOD
     * Required by Boss.java's abstract takeDamage() method
     * Reduces health and triggers explosion when defeated
     * 
     * @param amount Amount of damage to inflict
     */
    @Override
    public void takeDamage(int amount) {
    	//DEBUG REMOVE
    	System.out.println("Boss took damage! Health was: " + health + " -" + amount);
        health -= amount; // Reduce health by damage amount
      //DEBUG REMOVE
        System.out.println("Health now: " + health);
        if (health <= 0) {
            health = 0;
            explode(); // Call Creature.java's explode() method to start death animation

        }

        
    }
}