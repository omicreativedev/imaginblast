package application;

// import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new

/**
 * GARLIC ENEMY CLASS
 * Specific type of enemy
 * Moves straight down at a set speed
 * Extends the Enemy class to inherit basic enemy properties
 */
public class EnemyGarlic extends Enemy {
    
    // Garlic-specific speed
    int SPEED = 10; // Pixels per frame - controls how fast garlic falls
    
    /**
     * CONSTRUCTOR - Creates a new garlic enemy
     * @param posX Initial X position (random across screen width)
     * @param posY Initial Y position (usually 0 at top of screen)
     * @param size Size of the enemy (typically PLAYER_SIZE = 60)
     * @param imageView The garlic ImageView (GARLIC_IMG)
     */
    public EnemyGarlic(int posX, int posY, int size, ImageView imageView) { //new - Changed parameter from Image to ImageView
        super(posX, posY, size, imageView); // Call Enemy.java constructor
    }
    
    /**
     * UPDATE METHOD - Garlic-specific movement
     * Called every frame by EntityManager
     * Moves straight down until it goes off screen
     * Overrides Enemy.update() which overrides Creature.update()
     */
    @Override
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