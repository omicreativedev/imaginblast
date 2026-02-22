package application;

/**
 * START SCREEN
 */
public class StartScreen {
    private boolean startButtonPressed = false;
    
    public void update() {
        // Any logic for the rest of the start screen can  go here
    }
    
    // Is the start button pressed?
    public boolean isStartButtonPressed() {
        return startButtonPressed;
    }
    
    // The start button IS pressed
    public void setStartButtonPressed(boolean pressed) {
        this.startButtonPressed = pressed;
    }
}