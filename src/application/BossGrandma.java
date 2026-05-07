package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

// "Off with their heads!" ~ The Queen of Hearts
// "I'll have no arguing, I'll have no answering back!" ~ The Queen of Hearts

/**
 * ANGRY GRANDMA CLASS
 * Implementation of the Boss abstract class (Boss.java)
 * Grandma Boss that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * We may change this later to offer different explosions for different Bosses
 * NEW: Grandma fires a devastating 5-way spread shot with alternating patterns
 */
public class BossGrandma extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0; // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 25; // Movement speed (very fast - aggressive!)
    private static final int BULLET_SPEED = 30; // Speed of boss projectiles (fast yarn!)
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_yarn.png"));
    
    // Pattern alternation
    private int patternCounter = 0; // Tracks which spread pattern to use
    
    /**
     * BOSS GRANDMA CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and grandma image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public BossGrandma(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_gma.png")));
        this.health = 550; // Set current health (inherited from Boss.java)
        this.maxHealth = 550; // Set max health for health bar (inherited from Boss.java)
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Required by Boss.java's abstract update() method
     * Handles boss movement behavior each frame
     * Boss follows player's X position with sine wave variation
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
     * CALCULATE DIRECTION VECTOR
     * Calculates normalized direction vector from boss center to target (player)
     * Used for aiming shots at the player
     * Source: Vector math from https://gamedev.stackexchange.com/questions/122374
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
     * CALCULATE OFFSET DIRECTION
     * Creates a direction vector at an angle offset from the target direction
     * Used for spread shot patterns
     * 
     * @param targetX Target X coordinate (player center)
     * @param targetY Target Y coordinate (player center)
     * @param angleOffset Angle offset in radians (positive = clockwise, negative = counter-clockwise)
     * @return double array where [0] = X direction with offset, [1] = Y direction with offset
     */
    private double[] calculateOffsetDirection(double targetX, double targetY, double angleOffset) {
        // Get base direction to player
        double fromX = posX + size / 2;
        double fromY = posY + size / 2;
        double dx = targetX - fromX;
        double dy = targetY - fromY;
        
        // Calculate base angle
        double baseAngle = Math.atan2(dy, dx);
        
        // Add offset
        double newAngle = baseAngle + angleOffset;
        
        // Return new direction vector
        return new double[]{Math.cos(newAngle), Math.sin(newAngle)};
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
            shootCooldown = 20; // Reset cooldown (20 frames between shots)
        }
    }
    
    /**
     * OVERRIDE SHOOT AT PLAYER METHOD
     * Required by Boss.java's abstract shootAtPlayer() method
     * NEW: Fires 5 yarn balls in a devastating spread pattern
     * Alternates between two different spread patterns to keep player guessing
     * Called by BossScreen with player position for targeting
     * 
     * @param shots List of enemy shots to add the new projectile to
     * @param player Reference to player object for aiming (uses player center position)
     */
    @Override
    public void shootAtPlayer(List<Shot> shots, Player player) {
        // Only shoot if cooldown is zero AND boss isn't exploding/destroyed
        if (shootCooldown <= 0 && !exploding && !destroyed) {
            
            // Get player center position for aiming
            double playerCenterX = player.posX + player.size / 2;
            double playerCenterY = player.posY + player.size / 2;
            
            // Calculate shot starting position (center of boss)
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            
            // Spread angles for 5-way shot (in radians)
            // Angles: -0.4, -0.2, 0, 0.2, 0.4 (approximately -23°, -11.5°, 0°, 11.5°, 23°)
            double[] spreads;
            
            // Alternate between two patterns every 3 shots
            patternCounter++;
            if ((patternCounter / 3) % 2 == 0) {
                // Pattern A: Even spread
                spreads = new double[]{-0.4, -0.2, 0, 0.2, 0.4};
            } else {
                // Pattern B: Wider spread for different coverage
                spreads = new double[]{-0.5, -0.25, 0, 0.25, 0.5};
            }
            
            // Fire 5 shots in spread pattern
            for (double spread : spreads) {
                double[] direction;
                if (spread == 0) {
                    // Center shot aims directly at player
                    direction = calculateDirection(playerCenterX, playerCenterY);
                } else {
                    // Offset shots
                    direction = calculateOffsetDirection(playerCenterX, playerCenterY, spread);
                }
                
                double velX = direction[0] * BULLET_SPEED;
                double velY = direction[1] * BULLET_SPEED;
                shots.add(new EnemyShot(shotX, shotY, velX, velY, BULLET_IMG));
            }
            
            // Longer cooldown for spread shot (slower but devastating)
            shootCooldown = 45; // Reset cooldown (45 frames between spread shots)
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
        health -= amount; // Reduce health by damage amount
        System.out.println("Health now: " + health);
        if (health <= 0) {
            health = 0;
            explode(); // Call Creature.java's explode() method to start death animation
        }
    }
}