package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * GAME RENDERER CLASS
 * Handles all drawing operations for the game
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
     * Draw all HUD elements (score, ammo, acorns)
     */
    public void drawHUD(int score, int shotsSize, int maxShots, int acornCount) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        
        // Score
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // Ammo
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // Acorns
        gc.setFill(Color.BROWN);
        gc.fillText("Acorns: " + acornCount, 340, 20);
    }
    
    
    
    
    public void drawStartScreen(StartScreen startScreen) {
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
    
    
    
    
    /**
     * Draw game over screen
     */
    public void drawGameOver(int score) {
        gc.setFont(Font.font(35));
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", 
                    ImaginBlastMain.WIDTH / 2, ImaginBlastMain.HEIGHT / 2.5);
    }
}