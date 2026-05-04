package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * STANDARD SHOT
 * 
 * This is the Players standard shot and bullet.
 */
public class ShotStandard extends Shot {
	
	// This is the player's standard bullet
    private static final ImageView SMILEY_BULLET = new ImageView(new Image("bullet_smiley.png"));
    // This is the size of this standard shot
    private static final int DRAW_SIZE = 20;
    
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
    }
    
    // Overrides Shot and uses a custom Bullet Image and Custom Size just for this Standard Shot
    @Override
    public void draw(GraphicsContext gc) {
    	 gc.drawImage(SMILEY_BULLET.getImage(), posX, posY, DRAW_SIZE, DRAW_SIZE);
    }
}