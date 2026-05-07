package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// "It's no use going back to yesterday, 
// because I was a different person then." 
// ~ Alice, Alice's Adventures in Wonderland

/**
 * END SCREEN CLASS
 * Displays the game over or victory screen after the final boss
 * Shows final score and provides option to play again
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class EndScreen {
    
    // End screen attributes
    private int finalScore;      // Player's final score from the game
    private boolean gameWon;     // True if player defeated final boss, false if defeated
    private boolean okPressed = false;  // Flag for when player clicks PLAY AGAIN button
    
    /**
     * END SCREEN CONSTRUCTOR
     * Creates a new end screen with the player's final score and game outcome
     * 
     * @param finalScore Player's total score at the end of the game
     * @param gameWon True if player defeated the final boss, false otherwise
     */
    public EndScreen(int finalScore, boolean gameWon) {
        this.finalScore = finalScore;
        this.gameWon = gameWon;
    }
    
    /**
     * DRAW METHOD
     * Renders the end screen to the canvas
     * Displays victory or defeat message, final score, and PLAY AGAIN button
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void draw(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Draw black background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Draw semi-transparent overlay panel in center of screen
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        if (gameWon) {
            // VICTORY SCREEN
            gc.setFill(Color.GOLD);
            gc.setFont(Font.font(36));
            gc.fillText("YOU WIN!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 80);
            
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(24));
            gc.fillText("Congratulations! You defeated the Final Boss!", 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 30);
            
            // Score display commented out - can be enabled if needed
            // gc.setFill(Color.YELLOW);
            // gc.setFont(Font.font(20));
            // gc.fillText("Final Score: " + finalScore, 
            //             ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 20);
        } else {
            // GAME OVER SCREEN
            gc.setFill(Color.RED);
            gc.setFont(Font.font(36));
            gc.fillText("GAME OVER", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 80);
            
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(24));
            gc.fillText("You were defeated by the Final Boss!", 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 30);
            
            // Display final score on game over screen
            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font(20));
            gc.fillText("Final Score: " + finalScore, 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 20);
        }
        
        // Draw PLAY AGAIN button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 80, 200, 50);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("PLAY AGAIN", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 110);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 80, 200, 50);
    }
    
    /**
     * HANDLE CLICK METHOD
     * Checks if the player clicked the PLAY AGAIN button
     * 
     * @param x Mouse click X coordinate
     * @param y Mouse click Y coordinate
     */
    public void handleClick(double x, double y) {
        // Check if click is within PLAY AGAIN button bounds
        if (x >= ImaginBlastMain.WIDTH/2 - 100 && x <= ImaginBlastMain.WIDTH/2 + 100 &&
            y >= ImaginBlastMain.HEIGHT/2 + 80 && y <= ImaginBlastMain.HEIGHT/2 + 130) {
            okPressed = true; // Set flag to restart game
        }
    }
    
    /**
     * OK PRESSED GETTER
     * Returns whether the player has clicked PLAY AGAIN
     * 
     * @return true if PLAY AGAIN button was clicked, false otherwise
     */
    public boolean isOkPressed() { 
        return okPressed; 
    }
    
    /**
     * OK PRESSED SETTER
     * Sets the OK pressed flag (used to reset after restart)
     * 
     * @param pressed New flag value
     */
    public void setOkPressed(boolean pressed) { 
        this.okPressed = pressed; 
    }
}