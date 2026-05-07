package application;

import javafx.scene.image.ImageView;

/**
 * PILLBUG ENEMY CLASS
 * Specific type of enemy
 * Rolls down toward the player
 * Extends the Enemy class to inherit basic enemy properties
 */
public class EnemyPillbug extends Enemy {

    // Pillbug-specific speed
    int SPEED = 18; // Pixels per frame. Controls how fast pillbug rolls down
    
    /**
     * CONSTRUCTOR - Creates a new pillbug enemy
     * @param posX Initial X position (random across screen width)
     * @param posY Initial Y position (usually 0 at top of screen)
     * @param size Size of the enemy (typically PLAYER_SIZE = 60)
     * @param imageView The pillbug ImageView (PILLBUG_IMG)
     */
    public EnemyPillbug(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    /**
     * UPDATE METHOD - Pillbug-specific movement
     * Called every frame by EntityManager
     * Moves straight down until it goes off screen
     * Overrides Enemy.update() which overrides Creature.update()
     */
    @Override //May need to be edited later
    public void update() {
        super.update(); // Handle explosion animation from Creature
        
        // Move down if not exploding
        if(!exploding && !destroyed) {
            posY += SPEED; // Move downward at constant speed
        }
        
        // Screen boundary check - destroy when off screen
        if(posY > ImaginBlastMain.HEIGHT) {
            destroyed = true; // Mark for removal by EntityManager
        }
    }
}