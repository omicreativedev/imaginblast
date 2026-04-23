package application;

// import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new

/*
 * PILLBUG ENEMY CLASS
 * A specific enemy
 * Rolls down to the player
 * Extends enemy class to inherit basic enemy properties
 */



public class EnemyPillbug extends Enemy{

	//Speed of how fast a pillbug will go down by (ppf) pixel per frame
	int SPEED = 10;
	
	/**
     * CONSTRUCTOR - Creates a new pillbug enemy
     * @param posX Initial X position (random across screen width)
     * @param posY Initial Y position (usually 0 at top of screen)
     * @param size Size of the enemy (typically PLAYER_SIZE = 60)
     * @param imageView The pillbug ImageView (PILLBUG_IMG)
     */
	
	 public EnemyPillbug(int posX, int posY, int size, ImageView imageView) { //new - Changed parameter from Image to ImageView
	        super(posX, posY, size, imageView); // Call Enemy.java constructor
	    }
	 
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