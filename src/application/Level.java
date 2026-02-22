package application;

import java.util.HashMap;
import java.util.Map;

/**
 * LEVEL BASE CLASS
 * Defines what each level contains
 * Each level defines its own goals, enemies, and items
 */
public abstract class Level {
    protected int levelNumber;
    protected String levelName;
    protected String background;
    
    // TRACKING MAPS - ADD THESE
    protected Map<Class<? extends Item>, Integer> itemsCollected = new HashMap<>();
    protected Map<Class<? extends Enemy>, Integer> enemiesDefeated = new HashMap<>();
    
    public Level(int levelNumber, String levelName) {
        this.levelNumber = levelNumber;
        this.levelName = levelName;
    }
    
    // Each level defines its own goals
    public abstract String getQuestText();
    
    // Each level defines what enemies appear
    public abstract Map<Class<? extends Enemy>, Integer> getEnemyGoals();
    
    // Each level defines what items appear
    public abstract Map<Class<? extends Item>, Integer> getItemGoals();
    
    // TRACKING METHODS - ADD THESE
    public void registerItemCollected(Item item) {
        Class<? extends Item> itemClass = item.getClass();
        itemsCollected.put(itemClass, itemsCollected.getOrDefault(itemClass, 0) + 1);
    }
    
    public void registerEnemyDefeated(Enemy enemy) {
        Class<? extends Enemy> enemyClass = enemy.getClass();
        enemiesDefeated.put(enemyClass, enemiesDefeated.getOrDefault(enemyClass, 0) + 1);
    }
    
    // WIN CONDITION CHECK - ADD THIS
    public boolean isComplete() {
        // Check each item goal
        for (Map.Entry<Class<? extends Item>, Integer> goal : getItemGoals().entrySet()) {
            int collected = itemsCollected.getOrDefault(goal.getKey(), 0);
            if (collected < goal.getValue()) {
                return false;
            }
        }
        
        // Check each enemy goal
        for (Map.Entry<Class<? extends Enemy>, Integer> goal : getEnemyGoals().entrySet()) {
            int defeated = enemiesDefeated.getOrDefault(goal.getKey(), 0);
            if (defeated < goal.getValue()) {
                return false;
            }
        }
        
        return true;
    }
    
    // Each level defines spawn rates
    public abstract int getEnemySpawnRate();
    public abstract int getItemSpawnRate();
    
    public String getBackground() { return background; }
    public int getLevelNumber() { return levelNumber; }
}