package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

// "Every adventure requires a first step." ~ Cheshire Cat
// "How puzzling all these changes are! I'm never sure what I'm going to be, from one minute to another." ~ Alice

/**
 * BOSS BROC CLASS
 * Implementation of the Boss abstract class (Boss.java)
 * Broc (Broccoli) boss that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * We may change this later to offer different explosions for different Bosses
 * NEW: Broc fires spread shots (3 potatoes) to challenge the player's dodging
 */
public class BossBroc extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0; // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 10; // Movement speed
    private static final int BULLET_SPEED = 21; // Speed of boss projectiles
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_potato.png"));
    
    /**
     * BOSS BROC CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and broc image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public BossBroc(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_broc.png")));
        this.health = 300; // Set current health (inherited from Boss.java)
        this.maxHealth = 300; // Set max health for health bar (inherited from Boss.java)
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
        
        // Normalize the vector to unit length (preserves direction, sets magnitude to 1)
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
        // Only shoot if cooldown is zero AND boss isn't exploding
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
     * NEW: Fires 3 potatoes in a spread pattern (aimed, slightly left, slightly right)
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
            
            // Spread shot angles (in radians)
            // 0.2 radians = approximately 11.5 degrees
            double spreadAngle = 0.2;
            
            // Shot 1: Aimed directly at player
            double[] dirCenter = calculateDirection(playerCenterX, playerCenterY);
            double velX1 = dirCenter[0] * BULLET_SPEED;
            double velY1 = dirCenter[1] * BULLET_SPEED;
            shots.add(new EnemyShot(shotX, shotY, velX1, velY1, BULLET_IMG));
            
            // Shot 2: Slightly to the left
            double[] dirLeft = calculateOffsetDirection(playerCenterX, playerCenterY, -spreadAngle);
            double velX2 = dirLeft[0] * BULLET_SPEED;
            double velY2 = dirLeft[1] * BULLET_SPEED;
            shots.add(new EnemyShot(shotX, shotY, velX2, velY2, BULLET_IMG));
            
            // Shot 3: Slightly to the right
            double[] dirRight = calculateOffsetDirection(playerCenterX, playerCenterY, spreadAngle);
            double velX3 = dirRight[0] * BULLET_SPEED;
            double velY3 = dirRight[1] * BULLET_SPEED;
            shots.add(new EnemyShot(shotX, shotY, velX3, velY3, BULLET_IMG));
            
            // Longer cooldown for spread shot (slower but more bullets)
            shootCooldown = 35; // Reset cooldown (35 frames between spread shots)
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
        System.out.println("Health now: " + health);
        if (health <= 0) {
            health = 0;
            explode(); // Call Creature.java's explode() method to start death animation
        }
    }
}