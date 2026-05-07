package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// "Faster! Faster!" cried the Red Queen. 
// "Don't try to talk!" 
// ~ The Red Queen, Through the Looking-Glass

/**
 * PORTAL CLASS
 * Exit portal that appears after boss is defeated
 * Player must touch the portal to complete the level
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Portal {
    
    // Position properties
    private int posX, posY;          // Portal location on screen
    private int size = 256;          // Portal size (width and height, square)
    
    // Visual properties
    private Image portalImg = new Image("portal.png"); // Portal graphic
    
    // State
    private boolean active = true;   // True when portal can be used
    
    /**
     * PORTAL CONSTRUCTOR
     * Creates a new portal positioned in the bottom right corner of the screen
     * Spawns after boss is defeated (portalVisible becomes true)
     */
    public Portal() {
        this.posX = ImaginBlastMain.WIDTH - size - 50; // Bottom right corner
        this.posY = ImaginBlastMain.HEIGHT - size - 50; // Offset from edges
    }
    
    /**
     * DRAW METHOD
     * Renders the portal on screen if active
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void draw(GraphicsContext gc) {
        if (active) {
            gc.drawImage(portalImg, posX, posY, size, size);
        }
    }
    
    /**
     * CHECK COLLISION METHOD
     * Detects if the player has touched the portal
     * Uses simple rectangle collision detection
     * Portal must be active for collision to be checked
     * 
     * @param player The player entity to check collision with
     * @return true if player touches portal and portal is active, false otherwise
     */
    public boolean checkCollision(Player player) {
        if (!active) return false;
        
        // Rectangle collision detection
        return (player.posX + player.size > posX &&
                player.posX < posX + size &&
                player.posY + player.size > posY &&
                player.posY < posY + size);
    }
    
    /**
     * DEACTIVATE METHOD
     * Disables the portal so it can no longer be used
     * Called when level is complete or portal should disappear
     */
    public void deactivate() { 
        active = false; 
    }
}