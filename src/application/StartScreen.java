package application;

// "One side will make you grow taller, 
// and the other side will make you grow shorter."
// ~ Caterpillar, Alice's Adventures in Wonderland

/**
 * START SCREEN CLASS
 * Displays the initial game menu with start button
 * Handles button state for transitioning to quest screen
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class StartScreen {
    
    // Button state
    private boolean startButtonPressed = false; // True when PLAY button has been clicked
    
    /**
     * UPDATE METHOD
     * Called every frame to update start screen logic
     * Currently placeholder for future animations or effects
     */
    public void update() {
        // Any logic for the rest of the start screen
    }
    
    /**
     * IS START BUTTON PRESSED CHECK
     * Returns whether the start button has been clicked
     * Used by ImaginBlastMain to transition from START to QUEST state
     * 
     * @return true if PLAY button was pressed, false otherwise
     */
    public boolean isStartButtonPressed() {
        return startButtonPressed;
    }
    
    /**
     * SET START BUTTON PRESSED METHOD
     * Sets the start button pressed flag
     * Called when player clicks the PLAY button on the start screen
     * 
     * @param pressed New flag value (true = button pressed)
     */
    public void setStartButtonPressed(boolean pressed) {
        this.startButtonPressed = pressed;
    }
}