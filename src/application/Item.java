package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

// "Everything's got a moral, if only you can find it."
// ~ The Duchess, Alice's Adventures in Wonderland

/**
 * ITEM BASE CLASS
 * Generic base class for all collectible items in the game
 * Items fall from the top of the screen and can be collected by the player
 * Specific item types (Acorn, Donut, Cupcake, Cassette, Bubble) extend this class
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Item {

    // POSITION AND SIZE PROPERTIES
    int posX, posY;          // Current position of the item on screen
    int size;                // Width and height of the item (square)
    ImageView imageView;     // Visual representation of the item
    
    // STATE FLAGS
    boolean collected = false; // True when player picks up the item
    boolean gone = false;      // True when item should be removed (collected or off-screen)
    
    /**
     * ITEM CONSTRUCTOR
     * Creates a new item at the specified position with given size and image
     * 
     * @param posX Initial X coordinate (usually random across screen width)
     * @param posY Initial Y coordinate (usually 0 at top of screen)
     * @param size Width and height of the item (assumed square)
     * @param imageView The item's visual representation (acorn, donut, etc.)
     */
    public Item(int posX, int posY, int size, ImageView imageView) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.imageView = imageView;
    }
    
    /**
     * DRAW METHOD
     * Renders the item on screen at its current position
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void draw(GraphicsContext gc) {
        gc.drawImage(imageView.getImage(), posX, posY, size, size);
    }
    
    /**
     * UPDATE METHOD
     * Called every frame to update item behavior
     * Can be overridden by subclasses for different movement patterns
     * 
     * @param gc Graphics context (passed for consistency, not used in base class)
     */
    public void update(GraphicsContext gc) {
        // Base item has no movement - subclasses override this
    }
}