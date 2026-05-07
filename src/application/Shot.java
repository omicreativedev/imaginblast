package application;

import javafx.scene.canvas.GraphicsContext;

// "The rule is, jam to-morrow and jam yesterday—but 
// never jam to-day." ~ The White Queen, Through the Looking-Glass

/**
 * SHOT BASE CLASS
 * Abstract base class that all projectiles extend
 * Defines the common properties and methods every shot type needs
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 * 
 * Source for directional aiming:
 * Calculating direction vector from shooter to target
 * Dividing by length and multiplying speed to get velocity
 * Adding velocity to every frame
 * https://gamedev.stackexchange.com/questions/122374
 */
public abstract class Shot {
    
    // Position properties
    protected int posX, posY;        // Current X and Y coordinates on screen
    
    // Movement properties
    protected int speed;             // Base movement speed (pixels per frame)
    protected double velX, velY;     // Directional velocity components for aiming in any direction
    
    // Size constants
    protected static final int SIZE = 6; // Standard shot size (can be overridden by subclasses)
    
    // State flag
    protected boolean toRemove;      // True if shot should be removed from the game
    
    /**
     * CONSTRUCTOR - Creates a shot at a specific position
     * Sets default upward movement for backward compatibility
     * 
     * @param posX Starting X coordinate on screen
     * @param posY Starting Y coordinate on screen
     */
    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = 0;
        this.velY = -speed; // Default upward movement (negative Y)
    }
    
    /**
     * CONSTRUCTOR - Creates a shot with specific position and direction vector
     * Used for player aiming at mouse cursor or bosses aiming at player
     * 
     * @param posX Starting X coordinate on screen
     * @param posY Starting Y coordinate on screen
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     */
    public Shot(int posX, int posY, double velX, double velY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
    }
    
    /**
     * ABSTRACT UPDATE METHOD
     * Updates the shot's position each frame
     * Each shot type moves differently (straight, aimed, homing, etc.)
     */
    public abstract void update();
    
    /**
     * ABSTRACT DRAW METHOD
     * Renders the shot on screen
     * Each shot type looks different (smiley, rock, potato, yarn, carrot, etc.)
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public abstract void draw(GraphicsContext gc);
    
    /**
     * OFF-SCREEN CHECK METHOD
     * Determines if shot has left the visible screen area
     * Can be overridden by subclasses for custom boundary behavior
     * 
     * @return true if shot should be removed, false otherwise
     */
    public boolean isOffScreen() {
        return posY < 0; // Remove when above top of screen
    }
}