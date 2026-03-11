package application;

/**
 * START SCREEN
 */
public class StartScreen {
    private boolean startButtonPressed = false;
    private String startInstruc = "Mouse the mouse cursor to move and left-click to shoot!";
    
    
    
    public void update() {
        // Any logic for the rest of the start screen can  go here
    	
    }
    
    public String startInstructions() {
    	return startInstruc;
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