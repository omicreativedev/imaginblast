package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// import java.io.File;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;
import java.util.List;

/**
 * BOSS SCREEN Level 3
 * Implementation of the BossScreen abstract class BossScreen.java
 * Manages the third boss fight (BossBroc.java)
 */
public class BossScreen04 extends BossScreen {
    
    private Image background; 
    private GameRenderer gameRenderer;

    /**
     * CONSTRUCTOR
     * Initializes the boss fight with a new BossBroc instance
     * Creates invisible portal and sets up the arena
     */
    public BossScreen04() {
    	///TODO add boss sprites and make new boss class
    	boss = new BossGrandma(ImaginBlastMain.WIDTH/2 - 128, 100); // Create boss centered near top of screen
        portal = new Portal(); // Create exit portal
        portalVisible = false; // Portal starts hidden until boss is defeated
        levelComplete = false; // Fight starts incomplete
        background = new Image("boss_bg_04.png");
        
       
        
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
            

        }
        
        // Always update boss to advance explosion animation
        boss.update(player); // Update boss position and behavior
        
        // Prevents movement after death. No Zombie Broc (or maybe??? LOL!)
        if (!boss.isDefeated()) {
            boss.shootAtPlayer(enemyShots, player); // Boss aims at player
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
            
            // Push player away from boss (fly off)
            // Determine which direction to push based on player position relative to boss center
            int bossCenterX = boss.posX + boss.size / 2;
            int bossCenterY = boss.posY + boss.size / 2;
            int playerCenterX = player.posX + player.size / 2;
            int playerCenterY = player.posY + player.size / 2;
            
            // Calculate push direction (away from boss center)
            int pushX = playerCenterX - bossCenterX;
            int pushY = playerCenterY - bossCenterY;
            
            // Normalize direction (simplified - just use sign)
            if (pushX > 0) pushX = 1;
            else if (pushX < 0) pushX = -1;
            else pushX = 0;
            
            if (pushY > 0) pushY = 1;
            else if (pushY < 0) pushY = -1;
            else pushY = 0;
            
            // Push player 100 pixels away
            int newX = player.posX + (pushX * 100);
            int newY = player.posY + (pushY * 100);
            
            // Apply boundary constraints
            if (newX < 0) newX = 0;
            if (newX + player.size > ImaginBlastMain.WIDTH) newX = ImaginBlastMain.WIDTH - player.size;
            if (newY < 0) newY = 0;
            if (newY + player.size > ImaginBlastMain.HEIGHT) newY = ImaginBlastMain.HEIGHT - player.size;
            
            player.posX = newX;
            player.posY = newY;
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
     * @param gameRenderer Game renderer (not heavily used here but available)
     * @param player The player entity (drawn at current position)
     * @param score Current player score (can be displayed if needed)
     */
    @Override
    public void draw(GraphicsContext gc, GameRenderer gameRenderer, Player player, int score) {
    	
        // Draw background image
        gc.drawImage(background, 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
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
    } }
