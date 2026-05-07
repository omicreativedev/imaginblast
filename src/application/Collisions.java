package application;

// "Everything's got a moral, if only you can find it."
// ~ The Duchess, Alice's Adventures in Wonderland

/**
 * COLLISIONS CLASS
 * Handles collision detection between game objects
 * Contains static methods that can be called from anywhere in the game
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Collisions {
    
    /**
     * SHOT COLLISION DETECTION METHOD
     * Checks if a shot has hit an enemy creature
     * 
     * @param shot The shot projectile
     * @param target The enemy creature
     * @return true if shot collides with enemy, false otherwise
     */
    public static boolean shotCollides(Shot shot, Creature target) {
        
        // Determine actual shot size (player shots = 6, enemy shots = 18)
        int shotSize = (shot instanceof EnemyShot) ? 18 : Shot.SIZE;
        
        // Calculate distance between centers of shot and enemy
        int distance = distance(
            shot.posX + shotSize / 2, 
            shot.posY + shotSize / 2, 
            target.posX + target.size / 2, 
            target.posY + target.size / 2
        );
        
        // Return true if distance is less than sum of radii
        return distance < (target.size / 2 + shotSize / 2);
    } 
    
    /**
     * PLAYER COLLISION DETECTION METHOD
     * Checks if player has collided with an enemy creature
     * 
     * @param player The player creature
     * @param other The enemy creature
     * @return true if player collides with enemy, false otherwise
     */
    public static boolean playerCollides(Creature player, Creature other) {
        
        // Calculate distance between centers of player and enemy
        int distance = distance(
            player.posX + player.size / 2, 
            player.posY + player.size / 2, 
            other.posX + other.size / 2, 
            other.posY + other.size / 2
        );
        
        // Return true if distance is less than sum of radii
        return distance < (other.size / 2 + player.size / 2);
    }
    
    /**
     * ITEM COLLISION DETECTION METHOD
     * Checks if player has collected an item
     * 
     * @param player The player creature
     * @param item The collectible item
     * @return true if player collects item, false otherwise
     */
    public static boolean itemCollides(Creature player, Item item) {
        
        // Calculate distance between centers of player and item
        int distance = distance(
            player.posX + player.size / 2, 
            player.posY + player.size / 2, 
            item.posX + item.size / 2, 
            item.posY + item.size / 2
        );
        
        // Return true if distance is less than sum of radii
        return distance < (item.size / 2 + player.size / 2);
    }
    
    /**
     * DISTANCE CALCULATION METHOD
     * Calculates Euclidean distance between two points
     * Private helper used only by collision detection methods
     * 
     * @param x1 First point X coordinate
     * @param y1 First point Y coordinate
     * @param x2 Second point X coordinate
     * @param y2 Second point Y coordinate
     * @return Distance rounded to nearest integer
     */
    private static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}