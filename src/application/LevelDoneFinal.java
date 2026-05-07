package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

//Adapted from LevelDone01.java

// "I've had scores of them," said the White Knight, 
// "and one way or another I always got them off." 
// ~ The White Knight, Through the Looking-Glass

/**
 * LEVEL DONE FINAL CLASS
 * Level completion screen for the final boss (Bunny)
 * Extends LevelDone base class
 * Displays after defeating the Final Boss Bunny
 * Transitions to the End Screen (victory screen)
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class LevelDoneFinal extends LevelDone {
    
    /**
     * LEVEL DONE FINAL CONSTRUCTOR
     * Calls parent constructor with level number 5 (final)
     */
    public LevelDoneFinal() {
        super(5);
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the final boss completion screen
     * Displays a black overlay box, victory message, and OK button
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
        
        // Draw victory message
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Final Boss Defeated!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // Draw instruction text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Click OK to see your victory screen", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Draw OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        
        // Draw OK button text
        gc.setFill(Color.BLACK);
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);
        
        // Draw button border for visibility
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
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
            y >= ImaginBlastMain.HEIGHT/2 + 100 && y <= ImaginBlastMain.HEIGHT/2 + 150) {
            okPressed = true;
        }
    }
}