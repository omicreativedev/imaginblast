package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * GAME RENDERER CLASS
 * Separates rendering logic from game logic
 */
public class GameRenderer {
    
    private GraphicsContext gc;
    
    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
    }
    
    /**
     * Clear the screen with forest green background
     */
    public void clearScreen() {
        gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
    }
    
    /**
     * Draw score, ammo, acorns
     */
    public void drawHUD(int score, int shotsSize, int maxShots, int acornCount, Player player) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(20));
        
        // Score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // Ammo
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // Acorns
        gc.setFill(Color.BROWN);
        gc.fillText("Acorns: " + acornCount, 340, 20);
        
        
        // Health Bar
        gc.setFill(Color.RED);
        gc.fillRect(480, 5, 200, 20);  // Background (empty)
        gc.setFill(Color.LIMEGREEN);
        double healthPercent = (double)player.hp / player.maxHp;
        healthPercent = Math.max(0, Math.min(1, healthPercent));
        gc.fillRect(480, 5, 200 * healthPercent, 20);
        gc.setFill(Color.WHITE);
        gc.fillText("HP: " + player.hp + "/" + player.maxHp, 580, 22);
        
        
        
    }
    
    
    // This is ugly but it works. We will fix it later.
    public void drawStartScreen(StartScreen startScreen) {
    	
    	gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
    	 gc.setTextAlign(TextAlignment.CENTER);
        // Draw black box overlay (50% of screen, centered)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Draw logo.png later
        // For now, just a placeholder
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("FrogArmy", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // Draw PLAY GAME button (green rectangle)
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2, 200, 50);
        
        // Button text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("PLAY", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 30);
    
        // Draw button border to make it obvious
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2, 200, 50);
    
    }
    
    // This is ugly but it works. We will fix it later.
    public void drawQuestScreen(Quest01 quest) {
    	 gc.setTextAlign(TextAlignment.CENTER);
        // Draw black box overlay
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Draw quest text
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Level 1 Quest", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        gc.setFont(Font.font(18));
        gc.setFill(Color.WHITE);
        gc.fillText(quest.getQuestText(), ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // Draw OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);
        
        // Button border
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
    }
    
    /**
     * Draw boss screen 
     */
    public void drawBossScreen(Boss boss, int score, int shotsSize, int maxShots, Player player, Portal portal) {
        // Clear screen (background can be different for boss)
        clearScreen(); // Or use a different background
        
        // Draw HUD elements (simplified for boss)
        drawBossHUD(score, shotsSize, maxShots, player, boss);
        
        // Draw boss
        boss.draw(gc);
        
        // Draw portal if visible
        portal.draw(gc);
    }

    /**
     * Special HUD for boss fights (no acorn count)
     */
    public void drawBossHUD(int score, int shotsSize, int maxShots, Player player, Boss boss) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(20));
        
        // Score (top left)
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // Ammo (top left, next to score)
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // Player Health Bar (top left, below score)
        gc.setFill(Color.RED);
        gc.fillRect(60, 40, 200, 20);  // Background
        
        gc.setFill(Color.LIMEGREEN);
        double playerHealthPercent = (double)player.hp / player.maxHp;
        playerHealthPercent = Math.max(0, Math.min(1, playerHealthPercent));
        gc.fillRect(60, 40, 200 * playerHealthPercent, 20);
        
        gc.setFill(Color.WHITE);
        gc.fillText("Player: " + player.hp + "/" + player.maxHp, 160, 57);
        
        // Boss Health Bar (top center)
        gc.setFill(Color.DARKRED);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400, 30);  // Background
        
        gc.setFill(Color.CRIMSON);
        double bossHealthPercent = (double)boss.getHealth() / boss.getMaxHealth();
        bossHealthPercent = Math.max(0, Math.min(1, bossHealthPercent));
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400 * bossHealthPercent, 30);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("BOSS", ImaginBlastMain.WIDTH/2, 40);
        gc.fillText(boss.getHealth() + "/" + boss.getMaxHealth(), ImaginBlastMain.WIDTH/2, 60);
    }

    /**
     * Draw level complete screen
     */
    public void drawLevelDoneScreen(LevelDone levelDone) {
        levelDone.draw(gc);
    }

    /**
     * Draw portal (if you want a separate method)
     */
    public void drawPortal(Portal portal) {
        portal.draw(gc);
    }
    
    /**
     * Draw game over screen
     */
    public void drawGameOver(int score) {
  
        gc.setFill(Color.BLACK); 
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Center text
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(35));
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", 
                    ImaginBlastMain.WIDTH / 2, ImaginBlastMain.HEIGHT / 2.5);
    }
}