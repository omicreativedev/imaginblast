package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// "Contrariwise," continued Tweedledee, "if it was so, 
// it might be; and if it were so, it would be; 
// but as it isn't, it ain't. That's logic." 
// ~ Tweedledee, Through the Looking-Glass

/**
 * STANDARD SHOT CLASS
 * Represents the player's standard projectile (smiley face bullet)
 * Extends the Shot class to inherit basic projectile properties
 * Players fire these at enemies and bosses
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ShotStandard extends Shot {
    
    // Shot-specific constants
    private static final ImageView SMILEY_BULLET = new ImageView(new Image("bullet_smiley.png")); // Player's standard bullet image
    private static final int DRAW_SIZE = 20; // Size of the standard shot (pixels)
    
    /**
     * CONSTRUCTOR - Creates a new standard shot (straight up)
     * Shots originate from the player's position and move upward
     * 
     * @param posX Starting X coordinate (typically player's center)
     * @param posY Starting Y coordinate (typically player's center)
     */
    public ShotStandard(int posX, int posY) {
        super(posX, posY);
        this.speed = 10; // Movement speed (pixels per frame)
        this.velX = 0; // No horizontal movement
        this.velY = -speed; // Straight up (negative Y)
    }
    
    /**
     * CONSTRUCTOR - Creates a standard shot with specific direction
     * Used for aiming at mouse cursor or enemy targeting
     * 
     * @param posX Starting X coordinate (typically player's center)
     * @param posY Starting Y coordinate (typically player's center)
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     */
    public ShotStandard(int posX, int posY, double velX, double velY) {
        super(posX, posY, velX, velY);
        this.speed = 10; // Keep speed for reference (velX/velY are used for movement)
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Called every frame by EntityManager
     * Moves shot using directional velocity instead of just moving upward
     */
    @Override
    public void update() {
        posX += velX; // Update horizontal position
        posY += velY; // Update vertical position
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the standard shot on screen using custom smiley bullet image
     * Overrides Shot.draw() which would draw a generic oval
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(SMILEY_BULLET.getImage(), posX, posY, DRAW_SIZE, DRAW_SIZE);
    }
}