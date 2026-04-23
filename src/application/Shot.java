package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * SHOT BASE CLASS - All projectiles extend this
 * Defines the common properties and methods every shot type needs
 * Source:
 * Calculating direction vector from shooter to target
 * Dividing by length and multiplying speed to get velocity
 * Adding velocity to every frame
 * https://gamedev.stackexchange.com/questions/122374
 */
public abstract class Shot {
    
    // All shots have position
    protected int posX, posY;
    
    // All shots move at some speed
    protected int speed;
    
    // Directional velocity components for aiming in any direction
    // Replaces the simple vertical speed for most shots
    protected double velX, velY;
    
    // All shots have a standard size (can be overridden by subclasses if needed)
    protected static final int SIZE = 6;
    
    // All shots can be marked for removal
    protected boolean toRemove;
    
    /**
     * Set initial position
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = 0;
        this.velY = -speed; // Default upward movement for backward compatibility
    }
    
    /**
     * Sets initial position and direction vector
     * Player aiming at mouse, boss aiming at player
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     * @param velX Horizontal velocity component
     * @param velY Vertical velocity component
     */
    public Shot(int posX, int posY, double velX, double velY) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
    }
    
    /**
     * Update the shot's position each frame
     * Each shot type moves differently
     */
    public abstract void update();
    
    /**
     * Draw the shot on screen
     * Each shot type looks different
     * @param gc GraphicsContext for drawing
     */
    public abstract void draw(GraphicsContext gc);
    
    /**
     * Check if shot has left the screen
     * @return true if shot should be removed
     */
    public boolean isOffScreen() {
        return posY < 0;
    }
}