package application;

import javafx.scene.image.Image;
import java.util.List;
// import java.io.File;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

// "We're all mad here." ~ Cheshire Cat

/**
 * BOSS PIRATE CLASS
 * Implementation of the Boss abstract class Boss.java
 * Cheshire cat pirate boss that follows the player horizontally
 * Extends Boss.java which extends Creature.java, inheriting explosion behavior
 * We may change this later to offer different explosions for different Bosses
 */
public class BossPirate extends Boss {
    
    // Boss-specific attributes
    private int shootCooldown = 0; // Frames until boss can shoot again (prevents bullet spam)
    private int speed = 4; // Movement speed
    // private MediaPlayer bossLaughSound;
    // private MediaPlayer bossHitSound;
    // private MediaPlayer bossDefeatedSound;
    // private MediaPlayer bossShootSound;
    
    /**
     * BOSS PIRATE CONSTRUCTOR
     * Calls Boss.java constructor with predefined size (256x256) and pirate image
     * Sets health values required by Boss.java (health, maxHealth)
     * 
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public BossPirate(int posX, int posY) {
        super(posX, posY, 256, new Image("boss_pirate_256x256.png")); // Call Boss.java constructor with large size
        this.health = 200; // Set current health (inherited from Boss.java)
        this.maxHealth = 200; // Set max health for health bar (inherited from Boss.java)
        // try {
        //     Media laugh = new Media(new File("pirate_laugh.wav").toURI().toString());
        //     bossLaughSound = new MediaPlayer(laugh);
        //     Media hit = new Media(new File("pirate_hit.wav").toURI().toString());
        //     bossHitSound = new MediaPlayer(hit);
        //     Media defeated = new Media(new File("pirate_defeated.wav").toURI().toString());
        //     bossDefeatedSound = new MediaPlayer(defeated);
        //     Media shoot = new Media(new File("pirate_shoot.wav").toURI().toString());
        //     bossShootSound = new MediaPlayer(shoot);
        // } catch (Exception e) {
        //     System.out.println("Pirate sound effects aint loading dude!");
        // }
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Required by Boss.java's abstract update() method
     * Handles boss movement behavior each frame
     * Boss follows player's X position with sine wave variation
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
        
        // Phase transition sound when boss enters new phase i.e. evil mode when almost dead?
        // if (phase == 1 && health < maxHealth/2) {
        //     if (bossLaughSound != null) {
        //         bossLaughSound.stop();
        //         bossLaughSound.play();
        //     }
        // }
    }
    
    /**
     * OVERRIDE SHOOT METHOD
     * Required by Boss.java's abstract shoot() method
     * Creates a new enemy projectile when cooldown allows
     * Shots originate from bottom center of boss sprite
     * 
     * @param shots List of enemy shots to add the new projectile to
     */
    @Override
    public void shoot(List<Shot> shots) {
        // Only shoot if cooldown is zero AND boss isn't exploding
        if (shootCooldown <= 0 && !exploding) {
            // if (bossShootSound != null) {
            //     bossShootSound.stop();
            //     bossShootSound.play();
            // }
            // Create new enemy shot at bottom center of boss
            shots.add(new EnemyShot(posX + size/2 - Shot.SIZE/2, posY + size));
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
        health -= amount; // Reduce health by damage amount
        if (health < 0) health = 0; // Prevent negative health values
        
        // if (bossHitSound != null) {
        //     bossHitSound.stop();
        //     bossHitSound.play();
        // }
        
        // Check if boss is defeated (health depleted)
        if (health <= 0) {
            // if (bossDefeatedSound != null) {
            //     bossDefeatedSound.play();
            // }
            explode(); // Call Creature.java's explode() method to start death animation
            // ^^^ I think this is broken/bug
        }
    }
}