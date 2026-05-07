package application;

// "Everyones got a right to be mad, 
// but not everyone uses it." 
// ~ Cheshire Cat, Alice's Adventures in Wonderland

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * PARTICLES CLASS
 * Background visual effects for atmospheric ambiance
 * Creates falling particles (stars, sparkles, dust) that flicker and drift downward
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Particles {
    
    // Random number generator for particle variety
    private static final Random RAND = new Random();
    
    // POSITION PROPERTIES
    private int posX, posY;      // Current position of the particle on screen
    
    // APPEARANCE PROPERTIES
    private int h, w;            // Height and width of the particle (1-5 pixels)
    private int r, g, b;         // RGB color components (150-250 range for pastel colors)
    private double opacity;      // Transparency level (creates flickering effect)
    
    // GRAPHICS CONTEXT
    private GraphicsContext gc;   // Needed for drawing (passed from main game)
    
    /**
     * PARTICLES CONSTRUCTOR
     * Creates a new particle at the top of the screen with randomized properties
     * All visual attributes are random for natural variety
     * 
     * @param gc GraphicsContext for drawing (from main game)
     */
    public Particles(GraphicsContext gc) {
        this.gc = gc;                                      // Store for drawing
        posX = RAND.nextInt(ImaginBlastMain.WIDTH);        // Random X across screen width
        posY = 0;                                           // Start at top of screen
        w = RAND.nextInt(5) + 1;                            // Random width 1-5 pixels
        h = RAND.nextInt(5) + 1;                            // Random height 1-5 pixels
        r = RAND.nextInt(100) + 150;                        // Random red 150-250 (pastel range)
        g = RAND.nextInt(100) + 150;                        // Random green 150-250
        b = RAND.nextInt(100) + 150;                        // Random blue 150-250
        opacity = RAND.nextFloat();                         // Random opacity 0.0-1.0
        if (opacity < 0) opacity *= -1;                     // Ensure positive (safety check)
        if (opacity > 0.5) opacity = 0.5;                   // Cap at 0.5 for subtle effect
    }
    
    /**
     * DRAW METHOD
     * Renders the particle and updates its position each frame
     * Creates a flickering effect by varying opacity over time
     * Particle falls downward at 20 pixels per frame
     */
    public void draw() {
        // Flickering animation - opacity bounces between 0.1 and 0.8
        if (opacity > 0.8) opacity -= 0.01;
        if (opacity < 0.1) opacity += 0.01;
        
        // Draw the particle as a colored circle with current opacity
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        
        // Move particle downward for next frame
        posY += 20;
    }
    
    /**
     * OFF-SCREEN CHECK METHOD
     * Determines if particle has fallen past the bottom of the screen
     * Used by main game loop to remove old particles and prevent memory buildup
     * 
     * @return true if particle is below screen and should be removed, false otherwise
     */
    public boolean isOffScreen() {
        return posY > ImaginBlastMain.HEIGHT;
    }
}