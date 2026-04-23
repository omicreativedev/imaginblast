package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * STANDARD SHOT - Default blue oval shot that travels straight up
 * This is the same as the original Shot class
 * NEW: Now supports directional aiming (up, down, left, right, diagonal)
 */
public class ShotStandard extends Shot {
    
    public ShotStandard(int posX, int posY) {
        super(posX, posY);
        this.speed = 10;
        // Default direction: straight up (0, -speed)
        this.velX = 0;
        this.velY = -speed;
    }
    
    /**
     * Creates a shot with specific direction
     * Used for aiming at mouse cursor or enemy targeting
     * @param posX Starting X coordinate
     * @param posY Starting Y coordinate
     * @param velX Horizontal velocity component (negative = left, positive = right)
     * @param velY Vertical velocity component (negative = up, positive = down)
     */
    public ShotStandard(int posX, int posY, double velX, double velY) {
        super(posX, posY, velX, velY);
        this.speed = 10; // Keep speed for reference, velX/velY are used for movement
    }
    
    @Override
    public void update() {
        // Use directional velocity instead of just moving up
        posX += velX;
        posY += velY;
      //DEBUG REMOVE
        System.out.println("Shot Y: " + posY); // DEBUG
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(posX, posY, SIZE, SIZE);
    }
}