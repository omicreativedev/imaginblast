package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

// "No room! No room!" they cried out when they saw 
// Alice coming. ~ The Hatter, Alice's Adventures in Wonderland

// Adapted from ItemAcorn.java

/**
 * CUPCAKE ITEM CLASS
 * Cupcakes fall straight down and give points when collected
 * Extends the Item base class
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ItemCupcake extends Item {
    
    private GameRenderer gameRenderer; // Reference for playing collection sound effect
    
    // MOVEMENT PROPERTIES
    int speed = 8; // Falling speed (pixels per frame)
    
    /**
     * CUPCAKE ITEM CONSTRUCTOR
     * Creates a new cupcake item at the specified position
     * 
     * @param posX Initial X coordinate (random across screen width)
     * @param posY Initial Y coordinate (usually 0 at top of screen)
     * @param size Width and height of the cupcake (square)
     * @param imageView The cupcake's visual representation
     */
    public ItemCupcake(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
        // Cupcake-specific initialization can go here later
        // gameRenderer will be set later via setGameRenderer()
    }
    
    /**
     * ON COLLECTED METHOD
     * Called when player collects this cupcake
     * Marks item as collected and plays collection sound
     */
    public void onCollected() {
        this.collected = true; // Mark as collected
        if (gameRenderer != null) {
            gameRenderer.playItemCollectSound(); // Play collection sound effect
        }
        
        // FUTURE: Add visual effect or bonus points here
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Cupcake-specific falling behavior
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