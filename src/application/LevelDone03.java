package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Adapted from LevelDone01.java

// "A grin without a cat! It's the most curious
// thing I ever saw in my life!"
// ~ Alice, Alice's Adventures in Wonderland

/**
 * LEVEL DONE 03 CLASS
 * Level completion screen for Level 3
 * Extends LevelDone base class
 * Displays after defeating the Broccoli King (Broc) boss
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class LevelDone03 extends LevelDone {
    
    /**
     * LEVEL DONE 03 CONSTRUCTOR
     * Calls parent constructor with level number 3
     */
    public LevelDone03() {
        super(3);
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the level completion screen for Level 3
     * Displays a black overlay box, congratulations message, and OK button
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
        
        // Draw congratulations message
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Congratulations!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        // Draw completion message
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Level 3 Complete!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // Draw instruction text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("Click OK to go to Level 4.", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 20);
        
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