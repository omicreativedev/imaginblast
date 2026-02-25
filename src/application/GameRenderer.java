package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * GAME RENDERER CLASS
 * Separates rendering logic from game logic
 * Handles all drawing operations for different game states
 */
public class GameRenderer {
    
    private GraphicsContext gc; 
    
    /**
     * CONSTRUCTOR
     * @param gc GraphicsContext from the Canvas - all drawing goes through this
     */
    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
    }
    
    /**
     * Clear the screen with forest green background
     * Called at the beginning of each frame in PLAYING state
     * This is the base layer that everything else draws on top of
     * We want to change this set up for new levels going forward maybe
     */
    public void clearScreen() {
        gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
    }
    
    /**
     * GETTER
     * Provides access to the GraphicsContext for other classes
     * @return The GraphicsContext instance
     */
    public GraphicsContext getGc() {
        return gc;
    }
    
    /**
     * DRAW
     * Shows player information during normal gameplay PLAYING state
     * 
     * @param score Current player score
     * @param shotsSize Number of active player shots on screen
     * @param maxShots Maximum allowed player shots
     * @param acornCount Number of acorns collected in current level
     * @param player Player object (for health display)
     */
    public void drawHUD(int score, int shotsSize, int maxShots, int acornCount, Player player) {
        gc.setTextAlign(TextAlignment.LEFT); // Align text to left side of coordinates
        gc.setFont(Font.font(20)); // Set font size
        
        // SCORE DISPLAY (top left)
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // AMMO DISPLAY - shows remaining shots available to player
        // Calculated as maxShots - current active shots
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // ACORN COLLECTION DISPLAY - shows progress toward level goal
        gc.setFill(Color.BROWN); // Brown color for acorn theme
        gc.fillText("Acorns: " + acornCount, 340, 20);
        
        // PLAYER HEALTH BAR
        // Background (empty health) - red
        gc.setFill(Color.RED);
        gc.fillRect(480, 5, 200, 20);
        
        // Foreground (current health) - green, scaled by health percentage
        gc.setFill(Color.LIMEGREEN);
        double healthPercent = (double)player.hp / player.maxHp;
        healthPercent = Math.max(0, Math.min(1, healthPercent)); // Clamp between 0 and 1
        gc.fillRect(480, 5, 200 * healthPercent, 20);
        
        // Health text overlay
        gc.setFill(Color.WHITE);
        gc.fillText("HP: " + player.hp + "/" + player.maxHp, 580, 22);
    }
    
    /**
     * DRAW START SCREEN
     * Renders the main menu with PLAY button
     * Note: This is ugly but it works. We will fix it later.
     * 
     * @param startScreen The StartScreen object-not heavily used yet...
     */
    public void drawStartScreen(StartScreen startScreen) {
        // Forest green background (same as gameplay)
        gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Center text alignment for menu elements
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Semi-transparent black overlay box (centered, half screen size)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Game title (placeholder until logo.png is added)
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("FrogArmy", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // PLAY GAME button - green rectangle
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2, 200, 50);
        
        // Button text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("PLAY", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 30);
    
        // Button border to make it visually distinct
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2, 200, 50);
    }
    
    /**
     * DRAW QUEST SCREEN
     * Shows the level objective/quest text before gameplay begins
     * Note: This is ugly but it works. We will fix it later.
     * 
     * @param quest The Quest01 object containing quest text
     */
    public void drawQuestScreen(Quest01 quest) {
        // Center text alignment
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Semi-transparent black overlay box (same style as start screen)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Quest title
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Level 1 Quest", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        // Quest description (from Quest01 object)
        gc.setFont(Font.font(18));
        gc.setFill(Color.WHITE);
        gc.fillText(quest.getQuestText(), ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // OK button to dismiss quest screen
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        
        // Button text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);
        
        // Button border
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
    }
    
    /**
     * DRAW BOSS SCREEN (COMPLETE)
     * Renders the entire boss fight scene
     * This method is called from BossScreen classes
     * 
     * @param boss The boss entity
     * @param score Current player score
     * @param shotsSize Number of active player shots
     * @param maxShots Maximum allowed player shots
     * @param player The player entity
     * @param portal The exit portal
     */
    public void drawBossScreen(Boss boss, int score, int shotsSize, int maxShots, Player player, Portal portal) {
        // Clear screen (background can be different for boss)
        clearScreen(); // Or use a different background for each boss
        
        // Draw HUD elements (simplified for boss - no acorn count)
        drawBossHUD(score, shotsSize, maxShots, player, boss);
        
        // Draw boss (delegates to Boss's own draw method)
        boss.draw(gc);
        
        // Draw portal if visible (delegates to Portal's draw method)
        portal.draw(gc);
    }

    /**
     * DRAW BOSS HEADS-UP DISPLAY
     * Special HUD for boss fights (no acorn count, includes boss health)
     * Called by drawBossScreen()
     * 
     * @param score Current player score
     * @param shotsSize Number of active player shots
     * @param maxShots Maximum allowed player shots
     * @param player The player entity (for player health)
     * @param boss The boss entity (for boss health)
     */
    public void drawBossHUD(int score, int shotsSize, int maxShots, Player player, Boss boss) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(20));
        
        // PLAYER INFO (left side)
        // Score display
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // Ammo display
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // Player Health Bar (below score)
        // Background
        gc.setFill(Color.RED);
        gc.fillRect(60, 40, 200, 20);
        
        // Foreground (current health percentage)
        gc.setFill(Color.LIMEGREEN);
        double playerHealthPercent = (double)player.hp / player.maxHp;
        playerHealthPercent = Math.max(0, Math.min(1, playerHealthPercent));
        gc.fillRect(60, 40, 200 * playerHealthPercent, 20);
        
        // Player health text
        gc.setFill(Color.WHITE);
        gc.fillText("Player: " + player.hp + "/" + player.maxHp, 160, 57);
        
        // BOSS INFO (top center)
        // Boss Health Bar background
        gc.setFill(Color.DARKRED);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400, 30);
        
        // Boss Health Bar foreground (current health percentage)
        gc.setFill(Color.CRIMSON);
        double bossHealthPercent = (double)boss.getHealth() / boss.getMaxHealth();
        bossHealthPercent = Math.max(0, Math.min(1, bossHealthPercent));
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400 * bossHealthPercent, 30);
        
        // Boss health text (label and numbers)
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("BOSS", ImaginBlastMain.WIDTH/2, 40);
        gc.fillText(boss.getHealth() + "/" + boss.getMaxHealth(), ImaginBlastMain.WIDTH/2, 60);
    }

    /**
     * DRAW LEVEL COMPLETE SCREEN
     * Renders the screen shown after boss is defeated and portal is entered
     * Delegates drawing to the LevelDone object
     * 
     * @param levelDone The LevelDone screen object
     */
    public void drawLevelDoneScreen(LevelDone levelDone) {
        levelDone.draw(gc); // Let LevelDone handle its own drawing
    }

    /**
     * DRAW PORTAL (UTILITY METHOD)
     * Separate method for drawing just the portal if needed
     * Currently not heavily used - portal draws itself in most cases
     * 
     * @param portal The portal to draw
     */
    public void drawPortal(Portal portal) {
        portal.draw(gc);
    }
    
    /**
     * DRAW GAME OVER SCREEN
     * Renders the game over screen with final score
     * Called when player health reaches zero
     * 
     * @param score Final player score to display
     */
    public void drawGameOver(int score) {
        // Black background
        gc.setFill(Color.BLACK); 
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Center all text
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(35));
        
        // Game over message with score and replay instructions
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", 
                    ImaginBlastMain.WIDTH / 2, ImaginBlastMain.HEIGHT / 2.5);
    }
}