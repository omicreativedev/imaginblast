package application;

/**
 * COLLISIONS CLASS
 * Handles collision detection between game objects
 * Contains static methods that can be called from
 * anywhere in the game.
 */
public class Collisions {
    
    /**
     * SHOT COLLISION DETECTION
     * Check if a shot has hit an enemy
     * @param shot The shot projectile
     * @param target The enemy creature
     * @return true if shot hits enemy
     */
    public static boolean shotCollides(Shot shot, Creature target) {
        
        // Calculate distance between centers of shot and enemy
        int distance = distance(
            shot.posX + Shot.size / 2, 
            shot.posY + Shot.size / 2, 
            target.posX + target.size / 2, 
            target.posY + target.size / 2
        );
        
        // Return true if distance is less than sum of radii
        return distance < target.size;
    } 
    
    /**
     * PLAYER COLLISION DETECTION
     * Check if player has collided with an enemy
     * @param player The player creature
     * @param other The enemy creature
     * @return true if they collide
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
     * ITEM COLLISION DETECTION
     * Check if player has collected an item (acorn)
     * @param player The player creature
     * @param item The collectible item
     * @return true if player collects item
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
     * Calculates distance between two points
     * Private helper used only by collision methods
     * @param x1 First point X
     * @param y1 First point Y
     * @param x2 Second point X
     * @param y2 Second point Y
     * @return Distance rounded to integer
     */
    private static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}