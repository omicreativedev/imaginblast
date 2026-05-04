package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/* 
 * Level 1 - Forest Falls
 * Enemies: Squirrels
 * Items: Acorns
 * Boss: Cheshire Cat Pirate
 */
public class Level01 extends Level {
    
    // Constructor for Level01 – calls parent constructor with level number and name, then sets background image
    public Level01() {
        super(1, "Forest Falls");
        // Background Image
        this.background = new Image("level_bg_01.png");
    }
    
    // Returns the quest text shown to the player for this level
    @Override
    public String getQuestText() {
        return "Collect 3 acorns and defeat 3 squirrels";
    }
    
    // Returns a map of enemy types and how many must be defeated to complete the level
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 3);
        return goals;
    }
    
    // Returns a map of item types and how many must be collected to complete the level
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 3);
        return goals;
    }
    
    // Returns a list of enemy types that can appear in this level
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class);
    }
    
    
    // We want the bubble class to appear less frequent so we're using
    // a workaround to just render it once per 3 other items
    // Returns a list of item types that can appear in this level
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemAcorn.class, ItemAcorn.class, ItemBubble.class);
    }
    
    // Creates a new enemy instance at a random X position near the top of the screen
    @Override
    public Enemy createEnemy(Random rand, int width, int playerSize, Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return new EnemySquirrel(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        return null;
    }
    
    // Creates a new item instance at a random X position near the top of the screen
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
    
    
    
    // Returns the appropriate ImageView for a given enemy type
    @Override
    public ImageView getEnemyImage(Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return ImaginBlastMain.SQUIRREL_IMG;
        }
        return null;
    }
    
    // Returns the appropriate ImageView for a given item type
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
    
    // Returns how often enemies should spawn (smaller number = more frequent)
    @Override
    public int getEnemySpawnRate() {
        return 10;
    }
    
    // Returns how often items should spawn (smaller number = more frequent)
    @Override
    public int getItemSpawnRate() {
        return 5;
    }
}