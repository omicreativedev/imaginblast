package application;

import javafx.scene.canvas.GraphicsContext;

// "Everybody has won, and all must have prizes." 
// ~ The Dodo, Alice's Adventures in Wonderland

/**
 * LEVEL DONE BASE CLASS
 * Abstract base class for level completion screens
 * Extended by LevelDone01, LevelDone02, LevelDone03, and LevelDone04
 * Displays after a boss is defeated and portal is entered
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public abstract class LevelDone {
    
    // Level progression
    protected int levelCompleted;   // Which level was just completed (1-4)
    protected boolean okPressed = false; // True when player clicks OK button
    
    /**
     * LEVEL DONE CONSTRUCTOR
     * Creates a level completion screen for a specific level
     * 
     * @param levelCompleted The level number that was just finished (1-4)
     */
    public LevelDone(int levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
    
    /**
     * ABSTRACT DRAW METHOD
     * Renders the level completion screen
     * Each level can have its own completion screen design
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public abstract void draw(GraphicsContext gc);
    
    /**
     * ABSTRACT HANDLE CLICK METHOD
     * Processes mouse clicks on the level completion screen
     * Checks if OK button was pressed
     * 
     * @param x Mouse click X coordinate
     * @param y Mouse click Y coordinate
     */
    public abstract void handleClick(double x, double y);
    
    /**
     * IS OK PRESSED CHECK
     * Returns whether the OK button was pressed
     * 
     * @return true if OK button was clicked, false otherwise
     */
    public boolean isOkPressed() { 
        return okPressed; 
    }
    
    /**
     * SET OK PRESSED METHOD
     * Sets the OK button pressed flag
     * Called after handling the click to reset for next level
     * 
     * @param pressed New flag value
     */
    public void setOkPressed(boolean pressed) { 
        this.okPressed = pressed; 
    }
    
    /**
     * GET NEXT LEVEL METHOD
     * Returns the next level number to load
     * 
     * @return The next level number (levelCompleted + 1)
     */
    public int getNextLevel() { 
        return levelCompleted + 1; 
    }
}