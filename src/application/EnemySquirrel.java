package application;

import javafx.scene.image.Image;

/**
 * SQUIRREL ENEMY CLASS - Specific type of enemy
 * Moves straight down at a set speed
 */
public class EnemySquirrel extends Enemy {
    
    // Squirrel-specific speed
    int SPEED = 3;
    
    /**
     * CONSTRUCTOR - Creates a new squirrel enemy
     * @param posX Initial X position
     * @param posY Initial Y position (usually 0)
     * @param size Size of the enemy
     * @param image The squirrel image
     */
    public EnemySquirrel(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }
    
    /**
     * UPDATE METHOD - Squirrel-specific movement
     * Moves straight down
     */
    @Override
    public void update() {
        super.update(); // Handle explosion animation from Creature
        
        // Move down if not exploding
        if(!exploding && !destroyed) {
            posY += SPEED;
        }
        
        // Screen boundary check
        if(posY > ImaginBlastMain.HEIGHT) {
            destroyed = true;
        }
    }
}