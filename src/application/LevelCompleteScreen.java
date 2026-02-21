package application;

/**
 * LEVEL COMPLETE SCREEN
 * Appears after completing level goals
 */
public class LevelCompleteScreen {
    private int levelCompleted;
    private boolean okPressed = false;
    
    public LevelCompleteScreen(int levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
    
    public void update() {
        // Logic for level complete screen
    }
    
    public boolean isOkPressed() { 
        return okPressed; 
    }
    
    public void setOkPressed(boolean pressed) { 
        this.okPressed = pressed; 
    }
    
    // ADD THIS METHOD to use levelCompleted
    public String getCompletionMessage() {
        return "Level " + levelCompleted + " Complete!";
    }
    
    // OR ADD THIS - to know what level comes next
    public int getNextLevel() {
        return levelCompleted + 1;
    }
}