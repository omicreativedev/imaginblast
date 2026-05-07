package application;

//Adapted from BossScreen01.java

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

// "Every adventure requires a first step." ~ Cheshire Cat

/**
 * BOSS SCREEN 03
 * Implementation of the BossScreen abstract class (BossScreen.java)
 * Manages the third boss fight (BossBroc.java) - BROC BOSS
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class BossScreen03 extends BossScreen {
    
    private Image background; 
    private GameRenderer gameRenderer;
    
    /**
     * BOSS SCREEN 03 CONSTRUCTOR
     * Initializes the boss fight with a new BossBroc instance
     * Creates invisible portal and sets up the arena
     */
    public BossScreen03() {
        boss = new BossBroc(ImaginBlastMain.WIDTH/2 - 128, 100); // Create boss centered near top of screen
        portal = new Portal(); // Create exit portal
        portalVisible = false; // Portal starts hidden until boss is defeated
        levelComplete = false; // Fight starts incomplete
        background = new Image("boss_bg_03.png");
    }
    
    /**
     * OVERRIDE UPDATE METHOD
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
            if (gameRenderer != null) {
                gameRenderer.playExplodeSound();
            }
        }
        
        // Update boss position and behavior (called every frame regardless of defeat status)
        boss.update(player);
        
        // Prevents shooting after death. No Zombie Broc (or maybe??? LOL!)
        if (!boss.isDefeated()) {
            boss.shootAtPlayer(enemyShots, player); // Boss aims aimed shots at player
        }
        
        // Check player shots hitting boss (iterate backwards to safely remove)
        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot shot = playerShots.get(i); // Get current shot
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10); // Each shot does 10 damage to boss
                playerShots.remove(i); // Remove the shot after it hits
            }
        }
        
        // Check player body collision with boss
        if (Collisions.playerCollides(player, boss) && !player.exploding) {
            player.takeDamage(10); // Player takes damage when touching boss
            
            // Calculate push direction to stop boss and player from overlapping
            int bossCenterX = boss.posX + boss.size / 2;
            int bossCenterY = boss.posY + boss.size / 2;
            int playerCenterX = player.posX + player.size / 2;
            int playerCenterY = player.posY + player.size / 2;
            
            // Calculate direction to push the player (away from boss center)
            int pushX = playerCenterX - bossCenterX;
            int pushY = playerCenterY - bossCenterY;
            if (pushX > 0) pushX = 1;
            else if (pushX < 0) pushX = -1;
            else pushX = 0;
            if (pushY > 0) pushY = 1;
            else if (pushY < 0) pushY = -1;
            else pushY = 0;
            
            // Send the frog flying 100 pixels away from boss
            int newX = player.posX + (pushX * 100);
            int newY = player.posY + (pushY * 100);
            
            // Don't let the player fly off the screen (boundary checking)
            if (newX < 0) newX = 0;
            if (newX + player.size > ImaginBlastMain.WIDTH) newX = ImaginBlastMain.WIDTH - player.size;
            if (newY < 0) newY = 0;
            if (newY + player.size > ImaginBlastMain.HEIGHT) newY = ImaginBlastMain.HEIGHT - player.size;
            player.posX = newX;
            player.posY = newY;
        }
        
        // Check if player reached portal (only if portal is visible)
        if (portalVisible && portal.checkCollision(player)) {
            if (gameRenderer != null) {
                gameRenderer.playPortalSound();
            }
            levelComplete = true; // Mark level as complete when player enters portal
        }
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Required by BossScreen.java's abstract draw() method
     * Renders all boss fight elements to the screen
     * 
     * @param gc Graphics context for drawing to the canvas
     * @param gameRenderer Game renderer reference (used for playing sounds)
     * @param player The player entity (drawn at current position)
     * @param score Current player score (displayed if needed)
     */
    @Override
    public void draw(GraphicsContext gc, GameRenderer gameRenderer, Player player, int score) {
        
        this.gameRenderer = gameRenderer; // Store gameRenderer reference for sound effects
        
        // Draw background image
        gc.drawImage(background, 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Draw boss (uses Creature's draw method which handles normal sprite + explosion animation)
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
     * OVERRIDE COMPLETION CHECK METHOD
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