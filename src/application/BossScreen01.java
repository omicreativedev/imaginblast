package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// import java.io.File;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;
import java.util.List;

/**
 * BOSS SCREEN Level 1
 * Implementation of the BossScreen abstract class BossScreen.java
 * Manages the first boss fight (BossPirate.java)
 */
public class BossScreen01 extends BossScreen {
    
    // Background music for this boss fight
    // private MediaPlayer bossMusicPlayer;
	
    // Background image specific to this boss screen
    // private Image backgroundImage;
    
    /**
     * CONSTRUCTOR
     * Initializes the boss fight with a new BossPirate instance
     * Creates invisible portal and sets up the arena
     */
    public BossScreen01() {
        boss = new BossPirate(ImaginBlastMain.WIDTH/2 - 128, 100); // Create boss centered near top of screen
        portal = new Portal(); // Create exit portal
        portalVisible = false; // Portal starts hidden until boss is defeated
        levelComplete = false; // Fight starts incomplete
        
        // Load the background image for this specific boss
        // backgroundImage = new Image("boss_screen_01_background.png");
        
        // Load and play boss music when screen is created
        // try {
        //     Media music = new Media(new File("boss_music.mp3").toURI().toString());
        //     bossMusicPlayer = new MediaPlayer(music);
        //     bossMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop forever
        //     bossMusicPlayer.play(); // Start playing
        // } catch (Exception e) {
        //     System.out.println("Could not load boss music");
        // }
        
    }
    
    /**
     * OVERRIDE UPDATE
     * Required by BossScreen.java's abstract update() method
     * Called every frame during boss fight
     * Updates boss behavior, checks collisions, and manages portal spawning
     * 
     * @param player The player entity for collision detection
     * @param playerShots List of player's projectiles checking hits on boss
     * @param enemyShots List of enemy projectiles boss adds new shots here
     */
    @Override
    public void update(Player player, List<Shot> playerShots, List<Shot> enemyShots) {

        // Check if boss is defeated and portal hasn't been spawned yet
        if (boss.isDefeated() && !portalVisible) {
            portalVisible = true; // Make portal appear when boss dies
            
            // Stop boss music when defeated
            // if (bossMusicPlayer != null) {
            //     bossMusicPlayer.stop();
            // }
        }
        
        // Prevents movement after death. No Zombie Pirate (or maybe??? LOL!)
        if (!boss.isDefeated()) {
            boss.update(player); // Update boss position and behavior
            boss.shoot(enemyShots); // Boss adds new projectiles to enemyShots list
        }
        
        // Check player shots hitting boss (iterate backwards to safely remove)
        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot shot = playerShots.get(i); // Get current shot
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10); // Each shot does 10 damage to boss
                playerShots.remove(i); // Remove the shot after it hits
            }
        }
        
        // Check if player reached portal (only if portal is visible)
        if (portalVisible && portal.checkCollision(player)) {
            levelComplete = true; // Mark level as complete when player enters portal
        }
    }
    
    /**
     * OVERRIDE DRAW
     * Required by BossScreen.java's abstract draw() method
     * Renders all boss fight elements to the screen
     * 
     * @param gc Graphics context for drawing
     * @param renderer Game renderer (not heavily used here but available)
     * @param player The player entity (drawn at current position)
     * @param score Current player score (can be displayed if needed)
     */
    @Override
    public void draw(GraphicsContext gc, GameRenderer renderer, Player player, int score) {
    	
        // Draw background image first so everything else appears on top
        // gc.drawImage(backgroundImage, 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Clear screen with dark background for boss fight atmosphere
        gc.setFill(Color.DARKSLATEBLUE);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Draw boss (uses Creature's draw method)
        boss.draw(gc);
        
        // Draw player (must be explicitly drawn in boss screens)
        player.draw(gc);
        
        // Draw portal if visible (appears after boss defeat)
        if (portalVisible) {
            portal.draw(gc);
        }
        
        // Draw boss health display at top center of screen
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("Boss: " + boss.getHealth() + "/" + boss.getMaxHealth(), 
                    ImaginBlastMain.WIDTH/2 - 80, 50);
        
        // Draw player health display at top left of screen
        gc.setFill(Color.RED);
        gc.fillText("Health: " + player.hp + "/" + player.maxHp, 50, 50);
    }
    
    /**
     * OVERRIDE COMPLETION CHECK
     * Required by BossScreen.java's abstract isComplete() method
     * Returns whether the boss fight has been completed
     * Used by ImaginBlastMain to transition to LEVEL_DONE state
     * 
     * @return true if player has entered the portal, false otherwise
     */
    @Override
    public boolean isComplete() {
        return levelComplete;
    }
}