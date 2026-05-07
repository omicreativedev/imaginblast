package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import java.util.Map;
import java.util.List;

// "It's all about as strange as it can be,"
// said the Gryphon. 
// ~ The Gryphon, Alice's Adventures in Wonderland

/**
 * LEVEL BASE CLASS
 * Abstract base class that defines what each level contains
 * Each level defines its own goals, enemies, items, and spawn rates
 * Extended by Level01, Level02, Level03, and Level04
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public abstract class Level {
    
    // Level identification
    protected int levelNumber;      // Which level this is (1-4)
    protected String levelName;     // Name of the level (for display)
    protected Image background;     // Background image for the level
    
    // Tracking maps (keep count of collected items and defeated enemies)
    protected Map<Class<? extends Item>, Integer> itemsCollected = new HashMap<>();
    protected Map<Class<? extends Enemy>, Integer> enemiesDefeated = new HashMap<>();
    
    /**
     * LEVEL CONSTRUCTOR
     * Creates a new level with the given number and name
     * 
     * @param levelNumber The level number (1-4)
     * @param levelName The display name of the level
     */
    public Level(int levelNumber, String levelName) {
        this.levelNumber = levelNumber;
        this.levelName = levelName;
    }
    
    // ===== ABSTRACT METHODS (must be implemented by each level) =====
    
    /**
     * ABSTRACT GET QUEST TEXT METHOD
     * Each level defines its own quest objective text
     * 
     * @return The quest text for this level
     */
    public abstract String getQuestText();
    
    /**
     * ABSTRACT GET ENEMY GOALS METHOD
     * Each level defines what enemies appear and how many need to be defeated
     * 
     * @return Map of enemy classes to required kill counts
     */
    public abstract Map<Class<? extends Enemy>, Integer> getEnemyGoals();
    
    /**
     * ABSTRACT GET ITEM GOALS METHOD
     * Each level defines what items appear and how many need to be collected
     * 
     * @return Map of item classes to required collection counts
     */
    public abstract Map<Class<? extends Item>, Integer> getItemGoals();
    
    /**
     * ABSTRACT GET POSSIBLE ENEMIES METHOD
     * Each level defines what enemy types can spawn randomly
     * 
     * @return List of enemy classes that can appear in this level
     */
    public abstract List<Class<? extends Enemy>> getPossibleEnemies();
    
    /**
     * ABSTRACT GET POSSIBLE ITEMS METHOD
     * Each level defines what item types can spawn randomly
     * 
     * @return List of item classes that can appear in this level
     */
    public abstract List<Class<? extends Item>> getPossibleItems();
    
    /**
     * ABSTRACT CREATE ENEMY METHOD
     * Creates a specific enemy type at a random position
     * 
     * @param rand Random generator for position
     * @param width Screen width for random X position
     * @param playerSize Size of player (used for positioning)
     * @param enemyClass The type of enemy to create
     * @return A new Enemy instance of the specified type
     */
    public abstract Enemy createEnemy(Random rand, int width, int playerSize, Class<? extends Enemy> enemyClass);
    
    /**
     * ABSTRACT CREATE ITEM METHOD
     * Creates a specific item type at a random position
     * 
     * @param rand Random generator for position
     * @param width Screen width for random X position
     * @param playerSize Size of player (used for positioning)
     * @param itemClass The type of item to create
     * @return A new Item instance of the specified type
     */
    public abstract Item createItem(Random rand, int width, int playerSize, Class<? extends Item> itemClass);
    
    /**
     * ABSTRACT GET ENEMY IMAGE METHOD
     * Returns the ImageView for a specific enemy type
     * 
     * @param enemyClass The enemy class to get the image for
     * @return ImageView of the enemy sprite
     */
    public abstract ImageView getEnemyImage(Class<? extends Enemy> enemyClass);
    
    /**
     * ABSTRACT GET ITEM IMAGE METHOD
     * Returns the ImageView for a specific item type
     * 
     * @param itemClass The item class to get the image for
     * @return ImageView of the item sprite
     */
    public abstract ImageView getItemImage(Class<? extends Item> itemClass);
    
    /**
     * ABSTRACT GET ENEMY SPAWN RATE METHOD
     * Each level defines how frequently enemies spawn (frames between spawns)
     * 
     * @return Spawn rate in frames (lower = more enemies)
     */
    public abstract int getEnemySpawnRate();
    
    /**
     * ABSTRACT GET ITEM SPAWN RATE METHOD
     * Each level defines how frequently items spawn (frames between spawns)
     * 
     * @return Spawn rate in frames (lower = more items)
     */
    public abstract int getItemSpawnRate();
    
    // ===== TRACKING METHODS =====
    
    /**
     * REGISTER ITEM COLLECTED METHOD
     * Increments the counter for a specific item type when collected
     * 
     * @param item The item that was collected
     */
    public void registerItemCollected(Item item) {
        Class<? extends Item> itemClass = item.getClass();
        itemsCollected.put(itemClass, itemsCollected.getOrDefault(itemClass, 0) + 1);
    }
    
    /**
     * REGISTER ENEMY DEFEATED METHOD
     * Increments the counter for a specific enemy type when defeated
     * 
     * @param enemy The enemy that was defeated
     */
    public void registerEnemyDefeated(Enemy enemy) {
        Class<? extends Enemy> enemyClass = enemy.getClass();
        enemiesDefeated.put(enemyClass, enemiesDefeated.getOrDefault(enemyClass, 0) + 1);
    }
    
    /**
     * IS COMPLETE CHECK
     * Determines if all level goals have been met
     * Checks both item collection and enemy defeat requirements
     * 
     * @return true if all item and enemy goals are met, false otherwise
     */
    public boolean isComplete() {
        // Check each item goal
        for (Map.Entry<Class<? extends Item>, Integer> goal : getItemGoals().entrySet()) {
            int collected = itemsCollected.getOrDefault(goal.getKey(), 0);
            if (collected < goal.getValue()) {
                return false; // Not enough items collected
            }
        }
        
        // Check each enemy goal
        for (Map.Entry<Class<? extends Enemy>, Integer> goal : getEnemyGoals().entrySet()) {
            int defeated = enemiesDefeated.getOrDefault(goal.getKey(), 0);
            if (defeated < goal.getValue()) {
                return false; // Not enough enemies defeated
            }
        }
        
        return true; // All goals met
    }
    
    // ===== GETTER METHODS =====
    
    /**
     * GET BACKGROUND METHOD
     * 
     * @return The background image for this level
     */
    public Image getBackground() { 
        return background; 
    }
    
    /**
     * GET LEVEL NUMBER METHOD
     * 
     * @return The level number (1-4)
     */
    public int getLevelNumber() { 
        return levelNumber; 
    }
}