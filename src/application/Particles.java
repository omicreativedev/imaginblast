package application;

// JavaFX imports for graphics handling
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * PARTICLES CLASS - Background visual effects
 * Creates falling particles (stars, sparkles, dust) for atmospheric effect
 * Each particle is a small colored dot that falls and flickers
 */
public class Particles {
	
	// Random number generator for particle variety
	private static final Random RAND = new Random();
	
	// POSITION PROPERTIES
	private int posX, posY;      // Current position of the particle on screen
	
	// APPEARANCE PROPERTIES
	private int h, w;            // Height and width of the particle (1-5 pixels)
	private int r, g, b;         // RGB color components (150-250 range for pastels)
	private double opacity;      // Transparency level (flickering effect)
	
	// GRAPHICS CONTEXT
	private GraphicsContext gc;   // Needed for drawing (passed from main game)
	
	/**
	 * CONSTRUCTOR - Creates a new particle at the top of the screen
	 * All properties are randomized for visual variety
	 * @param gc GraphicsContext for drawing (from main game)
	 */
	public Particles(GraphicsContext gc) {
		this.gc = gc;                                      // Store for drawing
		posX = RAND.nextInt(ImaginBlastMain.WIDTH);        // Random X across screen width
		posY = 0;                                           // Start at top of screen
		w = RAND.nextInt(5) + 1;                            // Random width 1-5 pixels
		h = RAND.nextInt(5) + 1;                            // Random height 1-5 pixels
		r = RAND.nextInt(100) + 150;                        // Random red 150-250 (pastel)
		g = RAND.nextInt(100) + 150;                        // Random green 150-250
		b = RAND.nextInt(100) + 150;                        // Random blue 150-250
		opacity = RAND.nextFloat();                         // Random opacity 0.0-1.0
		if(opacity < 0) opacity *= -1;                       // Ensure positive
		if(opacity > 0.5) opacity = 0.5;                     // Cap at 0.5 for subtlety
	}
	
	/**
	 * DRAW METHOD - Renders the particle and updates its position
	 * Creates a flickering effect by varying opacity
	 * Particle falls downward at 20 pixels per frame
	 */
	public void draw() {
		// Flickering animation - opacity bounces between 0.1 and 0.8
		if(opacity > 0.8) opacity -= 0.01;
		if(opacity < 0.1) opacity += 0.01;
		
		// Draw the particle as a colored circle with current opacity
		gc.setFill(Color.rgb(r, g, b, opacity));
		gc.fillOval(posX, posY, w, h);
		
		// Move particle downward for next frame
		posY += 20;
	}
	
	/**
	 * OFF-SCREEN CHECK - Determines if particle has fallen past bottom
	 * Used by main game loop to remove old particles
	 * @return true if particle is below screen and should be removed
	 */
	public boolean isOffScreen() {
		return posY > ImaginBlastMain.HEIGHT;
	}
}