package application;

import javafx.scene.image.Image;

/*
 * PILLBUG ENEMY CLASS
 * A specific enemy
 * Rolls down to the player
 * Extends enemy class to inherit basic enemy properties
 */



public class EnemyPillbug extends Enemy{

	//Speed of how fast a pillbug will go down by (ppf) pixel per frame
	int SPEED = 18;
	
	/**
     * CONSTRUCTOR - Creates a new squirrel enemy
     * @param posX Initial X position (random across screen width)
     * @param posY Initial Y position (usually 0 at top of screen)
     * @param size Size of the enemy (typically PLAYER_SIZE = 60)
     * @param image The pillbug image (PILLBUG_IMG)
     */
	
	 public EnemyPillbug(int posX, int posY, int size, Image image) {
	        super(posX, posY, size, image); // Call Enemy.java constructor
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
