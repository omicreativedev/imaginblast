package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * ENEMY SHOT CLASS
 * Represents projectiles fired by enemies and bosses
 * Extends the Shot class to inherit basic projectile properties
 * Enemy shots are larger and move downward toward the player.
 * Later we should create different shot patterns for each boss or enemy.
 * NEW: Now supports directional aiming (enemies/boss can shoot at player)
 */
public class EnemyShot extends Shot {
    
    // Override size for enemy shots
    private static final int ENEMY_SHOT_SIZE = 18;
    
    /**
     * Creates a new enemy shot (straight down, original behavior)
     * @param posX Starting X coordinate (typically centered on enemy)
     * @param posY Starting Y coordinate (typically bottom of enemy)
     */
    public EnemyShot(int posX, int posY) {
        super(posX, posY);
        this.speed = 8; // Enemy shots speed
        // Default direction: straight down
        this.velX = 0;
        this.velY = speed;
    }
    
    /**
     * ENEMY SHOT SIZE
     * Provides access to the enemy shot size for other classes (BossPirate, etc.)
     * Used to calculate proper shot origin positions
     * @return The size of enemy shots in pixels (example: 18)
     */
    public static int getEnemyShotSize() {
        return ENEMY_SHOT_SIZE;
    }
    
    /**
     * Creates an aimed enemy shot with specific direction
     * Used for bosses and enemies that aim at the player
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     */
    public EnemyShot(int posX, int posY, double velX, double velY) {
        super(posX, posY, velX, velY);
        this.speed = 8; // Keep speed for reference
    }
    
    /**
     * OVERRIDE UPDATE METHOD
     * Called every frame by EntityManager
     * Moves shot using directional velocity instead of just downward
     */
    @Override
    public void update() {
        posX += velX;
        posY += velY;
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
     * Checks all four directions (off any edge)
     * 
     * @return true if shot is outside screen bounds, false otherwise
     */
    @Override
    public boolean isOffScreen() {
        return posX + ENEMY_SHOT_SIZE < 0 ||           // Off left edge
               posX > ImaginBlastMain.WIDTH ||         // Off right edge
               posY + ENEMY_SHOT_SIZE < 0 ||           // Off top edge
               posY > ImaginBlastMain.HEIGHT;          // Off bottom edge
    }
}