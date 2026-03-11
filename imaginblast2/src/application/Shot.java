package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * SHOT BASE CLASS - All projectiles extend this
 * Defines the common properties and methods every shot type needs
 */
public abstract class Shot {
    
    // All shots have position
    protected int posX, posY;
    
    // All shots move at some speed
    protected int speed;
    
    // All shots have a standard size (can be overridden by subclasses if needed)
    protected static final int SIZE = 6;
    
    // All shots can be marked for removal
    protected boolean toRemove;
    
    /**
     * Constructor - sets initial position
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     */
    public Shot(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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