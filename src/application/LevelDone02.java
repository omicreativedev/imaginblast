package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//Adapted from LevelDone01.java

// "You couldn't cut off a head unless 
// there was a body to cut it off from." 
// ~ The Executioner, Alice's Adventures in Wonderland

/**
 * LEVEL DONE 02 CLASS
 * Level completion screen for Level 2
 * Extends LevelDone base class
 * Displays after defeating the Beetle Chef boss
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class LevelDone02 extends LevelDone {
    
    /**
     * LEVEL DONE 02 CONSTRUCTOR
     * Calls parent constructor with level number 2
     */
    public LevelDone02() {
        super(2);
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the level completion screen for Level 2
     * Displays a black overlay box, completion message, and OK button
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Draw black box overlay (centered, half screen size)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Draw completion message
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Level 2 Complete!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        // Draw instruction text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Click OK to go to Level 3.", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // Draw OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 50, 200, 50);
        
        // Draw OK button text
        gc.setFill(Color.BLACK);
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 80);
        
        // Draw button border for visibility
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 50, 200, 50);
    }
    
    /**
     * OVERRIDE HANDLE CLICK METHOD
     * Checks if the player clicked the OK button
     * Sets okPressed to true if click is within button bounds
     * 
     * @param x Mouse click X coordinate
     * @param y Mouse click Y coordinate
     */
    @Override
    public void handleClick(double x, double y) {
        // Check if click is within OK button bounds (centered, 200x50)
        if (x >= ImaginBlastMain.WIDTH/2 - 100 && x <= ImaginBlastMain.WIDTH/2 + 100 &&
            y >= ImaginBlastMain.HEIGHT/2 + 50 && y <= ImaginBlastMain.HEIGHT/2 + 100) {
            okPressed = true;
        }
    }
}