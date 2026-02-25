package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * ENEMY SHOT CLASS
 * Represents projectiles fired by enemies and bosses
 * Extends the Shot class to inherit basic projectile properties
 * Enemy shots are larger and move downward toward the player.
 * Later we should create different shot patterns for each boss or enemy.
 */
public class EnemyShot extends Shot {
    
    // Override size for enemy shots
    private static final int ENEMY_SHOT_SIZE = 18;
    
    /**
     * CONSTRUCTOR - Creates a new enemy shot
     * @param posX Starting X coordinate (typically centered on enemy)
     * @param posY Starting Y coordinate (typically bottom of enemy)
     */
    public EnemyShot(int posX, int posY) {
        super(posX, posY); // Call Shot.java constructor
        this.speed = 8; // Enemy shots speed
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Called every frame by EntityManager
     * Moves shot downward toward player position
     */
    @Override
    public void update() {
        posY += speed; // Moves down toward player -positive Y is down in screen
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Renders the enemy shot on screen
     * Enemy shots are drawn as red ovals (distinct from player shots)
     * 
     * @param gc Graphics context for drawing
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED); // bullet color
        // Use ENEMY_SHOT_SIZE instead of Shot.SIZE for larger projectiles
        gc.fillOval(posX, posY, ENEMY_SHOT_SIZE, ENEMY_SHOT_SIZE);
    }
    
    /**
     * OVERRIDE OFF-SCREEN CHECK METHOD
     * Determines if shot has traveled beyond visible area
     * Enemy shots are removed when they go past bottom of screen
     * 
     * @return true if shot is below screen, false otherwise
     */
    @Override
    public boolean isOffScreen() {
        return posY > ImaginBlastMain.HEIGHT; // Check if past bottom edge
    }
}