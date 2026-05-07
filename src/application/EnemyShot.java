package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * ENEMY SHOT CLASS
 * Represents projectiles fired by enemies and bosses
 * Extends the Shot class to inherit basic projectile properties
 * Enemy shots are larger and can move in any direction (aimed shots)
 * Supports custom bullet images per enemy/boss
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class EnemyShot extends Shot {
    
    // Enemy shot size (larger than player shots for visibility)
    private static final int ENEMY_SHOT_SIZE = 18;
    
    // Custom bullet image (null = use default red oval)
    private ImageView bulletImage;
    
    /**
     * CONSTRUCTOR - Creates a new enemy shot (straight down, original behavior)
     * 
     * @param posX Starting X coordinate (typically centered on enemy)
     * @param posY Starting Y coordinate (typically bottom of enemy)
     */
    public EnemyShot(int posX, int posY) {
        super(posX, posY);
        this.speed = 8; // Enemy shot speed (pixels per frame)
        this.velX = 0; // No horizontal movement
        this.velY = speed; // Straight down
        this.bulletImage = null; // Use default red oval
    }
    
    /**
     * CONSTRUCTOR - Creates an aimed enemy shot with specific direction
     * Used for bosses and enemies that aim at the player
     * 
     * @param posX Starting X coordinate (typically centered on enemy)
     * @param posY Starting Y coordinate (typically bottom of enemy)
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     */
    public EnemyShot(int posX, int posY, double velX, double velY) {
        super(posX, posY, velX, velY);
        this.speed = 8; // Keep speed for reference
        this.bulletImage = null; // Use default red oval
    }
    
    /**
     * CONSTRUCTOR - Creates an aimed enemy shot with custom bullet image
     * Used for bosses that have unique bullet images (pizza, potato, yarn, carrot, etc.)
     * 
     * @param posX Starting X coordinate (typically centered on enemy)
     * @param posY Starting Y coordinate (typically bottom of enemy)
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     * @param image Custom bullet image for this shot
     */
    public EnemyShot(int posX, int posY, double velX, double velY, ImageView image) {
        super(posX, posY, velX, velY);
        this.speed = 8;
        this.bulletImage = image;
    }
    
    /**
     * ENEMY SHOT SIZE GETTER
     * Provides access to the enemy shot size for other classes (bosses, etc.)
     * Used to calculate proper shot origin positions so shots come from enemy center
     * 
     * @return The size of enemy shots in pixels (18)
     */
    public static int getEnemyShotSize() {
        return ENEMY_SHOT_SIZE;
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Called every frame by EntityManager
     * Moves shot using directional velocity instead of just downward
     */
    @Override
    public void update() {
        posX += velX; // Update horizontal position
        posY += velY; // Update vertical position
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the enemy shot on screen
     * If custom bullet image is provided, draws that image
     * Otherwise draws a red oval (default fallback)
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (bulletImage != null) {
            // Draw custom bullet image (pizza, potato, yarn, carrot, etc.)
            gc.drawImage(bulletImage.getImage(), posX, posY, ENEMY_SHOT_SIZE, ENEMY_SHOT_SIZE);
        } else {
            // Default: red oval (for basic enemies)
            gc.setFill(Color.RED);
            gc.fillOval(posX, posY, ENEMY_SHOT_SIZE, ENEMY_SHOT_SIZE);
        }
    }
    
    /**
     * OVERRIDE OFF-SCREEN CHECK METHOD
     * Determines if shot has traveled beyond visible area
     * Enemy shots are removed when they go off any edge of the screen
     * 
     * @return true if shot is outside screen bounds, false otherwise
     */
    @Override
    public boolean isOffScreen() {
        return posX + ENEMY_SHOT_SIZE < 0 ||     // Off left edge
               posX > ImaginBlastMain.WIDTH ||   // Off right edge
               posY + ENEMY_SHOT_SIZE < 0 ||     // Off top edge
               posY > ImaginBlastMain.HEIGHT;    // Off bottom edge
    }
}