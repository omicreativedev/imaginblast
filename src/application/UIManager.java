package application;

import javafx.scene.canvas.GraphicsContext;

// "Everything's got a moral, if only you can find it."
// ~ The Duchess, Alice's Adventures in Wonderland

/**
 * UI MANAGER CLASS
 * Manages all UI screens (start screen, quest screen, level done screen, end screen)
 * Tells GameRenderer what to draw based on method calls from ImaginBlastMain
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class UIManager {
    
    // UI Screen instances
    private StartScreen startScreen;     // Title/start screen
    private GameRenderer gameRenderer;   // Reference to game renderer for drawing
    private EndScreen endScreen;         // Final game completion screen (after final boss)
    
    /**
     * UI MANAGER CONSTRUCTOR
     * Creates a new start screen and stores game renderer reference
     * 
     * @param gameRenderer Reference to GameRenderer for drawing operations
     */
    public UIManager(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.startScreen = new StartScreen();
    }
    
    /**
     * DRAW START SCREEN METHOD
     * Tells GameRenderer to draw the start screen
     * Called by ImaginBlastMain when in START state
     */
    public void drawStartScreen() {
        gameRenderer.drawStartScreen(startScreen);
    }
    
    /**
     * DRAW QUEST SCREEN METHOD
     * Tells GameRenderer to draw the quest screen
     * Called by ImaginBlastMain when in QUEST state
     * 
     * @param quest The quest to display (Quest01, Quest02, etc.)
     */
    public void drawQuestScreen(Quest quest) { 
        gameRenderer.drawQuestScreen(quest);
    }
    
    /**
     * DRAW LEVEL DONE SCREEN METHOD
     * Draws the level completion screen directly
     * Called after player defeats all enemies in a level
     * 
     * @param screen The LevelDone screen to draw
     */
    public void drawLevelDoneScreen(LevelDone screen) {
        screen.draw(gameRenderer.getGc());
    }
    
    /**
     * DRAW GAME OVER SCREEN METHOD
     * Tells GameRenderer to draw the game over screen
     * Called when player dies during normal gameplay
     * 
     * @param score Player's final score to display
     */
    public void drawGameOverScreen(int score) {
        gameRenderer.drawGameOver(score);
    }
    
    /**
     * DRAW END SCREEN METHOD
     * Creates and draws the final game completion screen
     * Called after player defeats the final boss (Bunny)
     * 
     * @param finalScore Player's total score at game completion
     * @param gameWon True if player defeated the final boss, false otherwise
     */
    public void drawEndScreen(int finalScore, boolean gameWon) {
        endScreen = new EndScreen(finalScore, gameWon);
        endScreen.draw(gameRenderer.getGc());
    }
    
    /**
     * HANDLE END SCREEN CLICK METHOD
     * Passes mouse click coordinates to the end screen for button detection
     * 
     * @param x Mouse click X coordinate
     * @param y Mouse click Y coordinate
     */
    public void handleEndScreenClick(double x, double y) {
        if (endScreen != null) {
            endScreen.handleClick(x, y);
        }
    }
    
    /**
     * IS END SCREEN OK PRESSED CHECK
     * Returns whether the PLAY AGAIN button was pressed on the end screen
     * 
     * @return true if PLAY AGAIN button was clicked, false otherwise
     */
    public boolean isEndScreenOkPressed() {
        return endScreen != null && endScreen.isOkPressed();
    }
    
    /**
     * RESET END SCREEN OK PRESSED METHOD
     * Resets the OK pressed flag on the end screen
     * Called after restarting the game so the button doesn't stay pressed
     */
    public void resetEndScreenOkPressed() {
        if (endScreen != null) {
            endScreen.setOkPressed(false);
        }
    }
    
    /**
     * RESET START SCREEN METHOD
     * Creates a fresh start screen instance
     * Called when returning to main menu after game over or completion
     */
    public void resetStartScreen() {
        startScreen = new StartScreen();
    }
    
    /**
     * GET GRAPHICS CONTEXT METHOD
     * Provides access to the graphics context for other UI elements
     * 
     * @return GraphicsContext from GameRenderer
     */
    public GraphicsContext getGc() {
        return gameRenderer.getGc();
    }
}