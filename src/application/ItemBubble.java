package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

// "I can't help it," said the Flamingo, "it's my nature."
// ~ The Flamingo, Alice's Adventures in Wonderland

// Adapted from ItemAcorn.java

/**
 * BUBBLE ITEM CLASS
 * Bubbles fall straight down and restore player health when collected
 * Extends the Item base class
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ItemBubble extends Item {
    
    private GameRenderer gameRenderer; // Reference for playing collection sound effect
    
    // MOVEMENT PROPERTIES
    int speed = 8; // Falling speed (pixels per frame)
    
    /**
     * BUBBLE ITEM CONSTRUCTOR
     * Creates a new bubble item at the specified position
     * 
     * @param posX Initial X coordinate (random across screen width)
     * @param posY Initial Y coordinate (usually 0 at top of screen)
     * @param size Width and height of the bubble (square)
     * @param imageView The bubble's visual representation
     */
    public ItemBubble(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    /**
     * ON COLLECTED METHOD
     * Called when player collects this bubble
     * Marks item as collected and plays collection sound
     */
    public void onCollected() {
        this.collected = true; // Mark as collected
        if (gameRenderer != null) {
            gameRenderer.playItemCollectSound(); // Play collection sound effect
        }
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Bubble-specific falling behavior
     * Moves straight down until collected or off screen
     * 
     * @param gc Graphics context for drawing (passed but not used here)
     */
    @Override
    public void update(GraphicsContext gc) {
        // COLLECTION HANDLING
        // If the item has been collected, mark it as gone immediately
        if (collected == true) {
            this.gone = true; // Item disappears when collected
        }
        
        // MOVEMENT LOGIC - Falls straight down
        if (!collected && !gone) {
            posY += speed; // Fall downward at specified speed
        }
        
        // SCREEN BOUNDARY CHECK
        // If item falls past the bottom of the screen, remove it
        if (posY > ImaginBlastMain.HEIGHT) {
            gone = true; // Mark for removal
        }
    }
    
    /**
     * SET GAME RENDERER METHOD
     * Connects game renderer for playing sound effects
     * 
     * @param renderer The GameRenderer instance
     */
    public void setGameRenderer(GameRenderer renderer) {
        this.gameRenderer = renderer;
    }
}