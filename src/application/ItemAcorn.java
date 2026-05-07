package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

// "Seven of Spades, you're on duty next," said the Queen. 
// ~ The Queen of Hearts, Alice's Adventures in Wonderland

/**
 * ACORN ITEM CLASS
 * Acorns fall straight down and give 1 point when collected
 * Extends the Item base class
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ItemAcorn extends Item {
    
    private GameRenderer gameRenderer; // Reference for playing collection sound effect
    
    // MOVEMENT PROPERTIES
    int speed = 8; // Falling speed (pixels per frame)
    
    /**
     * ACORN ITEM CONSTRUCTOR
     * Creates a new acorn item at the specified position
     * 
     * @param posX Initial X coordinate (random across screen width)
     * @param posY Initial Y coordinate (usually 0 at top of screen)
     * @param size Width and height of the acorn (square)
     * @param imageView The acorn's visual representation
     */
    public ItemAcorn(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
        // Acorn-specific initialization can go here later
    }
    
    /**
     * ON COLLECTED METHOD
     * Called when player collects this acorn
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
     * Acorn-specific falling behavior
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