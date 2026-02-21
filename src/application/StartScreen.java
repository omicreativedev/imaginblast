package application;

/**
 * START SCREEN
 */
public class StartScreen {
    private boolean startButtonPressed = false;
    
    public void update() {
        // Logic for the rest of the start screen will go here
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