package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// "Begin at the beginning," the King said, 
// very gravely, "and go on till you come to the end: 
// then stop." ~ The King of Hearts, Alice's Adventures in Wonderland

/* 
 * Level 4 - Old Timey Fields
 * Enemies: Squirrels, Pillbugs, Garlic, Urchins
 * Items: Acorns, Donuts, Cupcakes, Cassettes (with rare Bubble spawn)
 * Boss: Angry Grandma
 */
public class Level04 extends Level {
    
    /**
     * LEVEL 04 CONSTRUCTOR
     * Calls parent constructor with level number and name, then sets background image
     */
    public Level04() {
        super(4, "Old Timey Fields");
        this.background = new Image("level_bg_04.png");
    }
    
    /**
     * GET QUEST TEXT METHOD
     * Returns the quest text shown to the player for this level
     * 
     * @return The quest objective string
     */
    @Override
    public String getQuestText() {
        return "Collect 1 acorn, 1 donut, 1 cupcake, 1 cassette, defeat 1 squirrel, 1 pillbug, 1 garlic, and 1 urchin to go to the next level";
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
        goals.put(EnemySquirrel.class, 1);
        goals.put(EnemyPillbug.class, 1);
        goals.put(EnemyGarlic.class, 1);
        goals.put(EnemyUrchin.class, 1);
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
        goals.put(ItemAcorn.class, 1);
        goals.put(ItemDonut.class, 1);
        goals.put(ItemCupcake.class, 1);
        goals.put(ItemCassette.class, 1);
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
        return Arrays.asList(EnemySquirrel.class, EnemyPillbug.class, EnemyGarlic.class, EnemyUrchin.class);
    }
    
    /**
     * GET POSSIBLE ITEMS METHOD
     * Returns a list of item types that can appear in this level
     * Bubble appears less frequently (workaround: 3 cycles of items + 1 bubble)
     * 
     * @return List of item classes that spawn in this level
     */
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemDonut.class, ItemCupcake.class, ItemCassette.class,
                             ItemAcorn.class, ItemDonut.class, ItemCupcake.class, ItemCassette.class,
                             ItemAcorn.class, ItemDonut.class, ItemCupcake.class, ItemCassette.class,
                             ItemBubble.class);
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
        if (enemyClass == EnemyPillbug.class) {
            return new EnemyPillbug(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        if (enemyClass == EnemyGarlic.class) {
            return new EnemyGarlic(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        if (enemyClass == EnemyUrchin.class) {
            return new EnemyUrchin(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
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
        if (itemClass == ItemDonut.class) {
            return new ItemDonut(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        if (itemClass == ItemCupcake.class) {
            return new ItemCupcake(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        if (itemClass == ItemCassette.class) {
            return new ItemCassette(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
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
        if (enemyClass == EnemyPillbug.class) {
            return ImaginBlastMain.PILLBUG_IMG;
        }
        if (enemyClass == EnemyGarlic.class) {
            return ImaginBlastMain.GARLIC_IMG;
        }
        if (enemyClass == EnemyUrchin.class) {
            return ImaginBlastMain.URCHIN_IMG;
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
        if (itemClass == ItemDonut.class) {
            return ImaginBlastMain.DONUT_IMG;
        }
        if (itemClass == ItemCupcake.class) {
            return ImaginBlastMain.CUPCAKE_IMG;
        }
        if (itemClass == ItemCassette.class) {
            return ImaginBlastMain.CASSETTE_IMG;
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