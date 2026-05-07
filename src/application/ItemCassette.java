package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

// "I didn't write it, and they can't prove I did," 
// said the Knave. ~ The Knave of Hearts, Alice's Adventures in Wonderland

//Adapted from ItemAcorn.java

/**
 * CASSETTE ITEM CLASS
 * Cassettes fall straight down and give points when collected
 * Extends the Item base class
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ItemCassette extends Item {
    
    // MOVEMENT PROPERTIES
    int speed = 8; // Falling speed (pixels per frame)
    
    /**
     * CASSETTE ITEM CONSTRUCTOR
     * Creates a new cassette item at the specified position
     * 
     * @param posX Initial X coordinate (random across screen width)
     * @param posY Initial Y coordinate (usually 0 at top of screen)
     * @param size Width and height of the cassette (square)
     * @param imageView The cassette's visual representation
     */
    public ItemCassette(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    /**
     * ON COLLECTED METHOD
     * Called when player collects this cassette
     * Marks item as collected (no sound effect for cassettes)
     */
    public void onCollected() {
        this.collected = true; // Mark as collected
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Cassette-specific falling behavior
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
}