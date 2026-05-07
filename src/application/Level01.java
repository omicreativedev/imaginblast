package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// "No room! No room!" they cried out 
// when they saw Alice coming. 
// ~ The March Hare, Alice's Adventures in Wonderland

/* 
 * Level 1 - Forest Falls
 * Enemies: Squirrels
 * Items: Acorns (with rare Bubble spawn)
 * Boss: Cheshire Cat Pirate
 */
public class Level01 extends Level {
    
    /**
     * LEVEL 01 CONSTRUCTOR
     * Calls parent constructor with level number and name, then sets background image
     */
    public Level01() {
        super(1, "Forest Falls");
        this.background = new Image("level_bg_01.png");
    }
    
    /**
     * GET QUEST TEXT METHOD
     * Returns the quest text shown to the player for this level
     * 
     * @return The quest objective string
     */
    @Override
    public String getQuestText() {
        return "Collect 3 acorns and defeat 3 squirrels";
    }
    
    /**
     * GET ENEMY GOALS METHOD
     * Returns a map of enemy types and how many must be defeated to complete the level
     * 
     * @return Map of enemy classes to required kill counts
     */
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 3);
        return goals;
    }
    
    /**
     * GET ITEM GOALS METHOD
     * Returns a map of item types and how many must be collected to complete the level
     * 
     * @return Map of item classes to required collection counts
     */
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 3);
        return goals;
    }
    
    /**
     * GET POSSIBLE ENEMIES METHOD
     * Returns a list of enemy types that can appear in this level
     * 
     * @return List of enemy classes that spawn in this level
     */
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class);
    }
    
    /**
     * GET POSSIBLE ITEMS METHOD
     * Returns a list of item types that can appear in this level
     * Bubble appears less frequently (workaround: 3 acorns + 1 bubble in the list)
     * 
     * @return List of item classes that spawn in this level
     */
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemAcorn.class, ItemAcorn.class, ItemBubble.class);
    }
    
    /**
     * CREATE ENEMY METHOD
     * Creates a new enemy instance at a random X position near the top of the screen
     * 
     * @param rand Random generator for X position
     * @param width Screen width for random bounds
     * @param playerSize Size of player (used for enemy size)
     * @param enemyClass The type of enemy to create
     * @return A new Enemy instance, or null if enemy type not recognized
     */
    @Override
    public Enemy createEnemy(Random rand, int width, int playerSize, Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return new EnemySquirrel(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        return null;
    }
    
    /**
     * CREATE ITEM METHOD
     * Creates a new item instance at a random X position near the top of the screen
     * 
     * @param rand Random generator for X position
     * @param width Screen width for random bounds
     * @param playerSize Size of player (used for item size)
     * @param itemClass The type of item to create
     * @return A new Item instance, or null if item type not recognized
     */
    @Override
    public Item createItem(Random rand, int width, int playerSize, Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return new ItemAcorn(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        if (itemClass == ItemBubble.class) {
            return new ItemBubble(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        return null;
    }
    
    /**
     * GET ENEMY IMAGE METHOD
     * Returns the appropriate ImageView for a given enemy type
     * 
     * @param enemyClass The enemy class to get the image for
     * @return ImageView of the enemy sprite, or null if not found
     */
    @Override
    public ImageView getEnemyImage(Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return ImaginBlastMain.SQUIRREL_IMG;
        }
        return null;
    }
    
    /**
     * GET ITEM IMAGE METHOD
     * Returns the appropriate ImageView for a given item type
     * 
     * @param itemClass The item class to get the image for
     * @return ImageView of the item sprite, or null if not found
     */
    @Override
    public ImageView getItemImage(Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return ImaginBlastMain.ACORN_IMG;
        }
        if (itemClass == ItemBubble.class) {
            return ImaginBlastMain.BUBBLE_IMG;
        }
        return null;
    }
    
    /**
     * GET ENEMY SPAWN RATE METHOD
     * Returns how often enemies should spawn (smaller number = more frequent)
     * 
     * @return Spawn rate in frames (10 = spawn every 10 frames)
     */
    @Override
    public int getEnemySpawnRate() {
        return 10;
    }
    
    /**
     * GET ITEM SPAWN RATE METHOD
     * Returns how often items should spawn (smaller number = more frequent)
     * 
     * @return Spawn rate in frames (5 = spawn every 5 frames)
     */
    @Override
    public int getItemSpawnRate() {
        return 5;
    }
}