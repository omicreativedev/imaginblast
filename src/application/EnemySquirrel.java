package application;

// import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new

/**
 * SQUIRREL ENEMY CLASS
 * Specific type of enemy
 * Moves straight down at a set speed
 * Extends the Enemy class to inherit basic enemy properties
 */
public class EnemySquirrel extends Enemy {
    
    // Squirrel-specific speed
    int SPEED = 7; // Pixels per frame - controls how fast squirrel falls
    
    /**
     * CONSTRUCTOR - Creates a new squirrel enemy
     * @param posX Initial X position (random across screen width)
     * @param posY Initial Y position (usually 0 at top of screen)
     * @param size Size of the enemy (typically PLAYER_SIZE = 60)
     * @param imageView The squirrel ImageView (SQUIRREL_IMG)
     */
    public EnemySquirrel(int posX, int posY, int size, ImageView imageView) { //new - Changed parameter from Image to ImageView
        super(posX, posY, size, imageView); // Call Enemy.java constructor
    }
    
    /**
     * UPDATE METHOD - Squirrel-specific movement
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