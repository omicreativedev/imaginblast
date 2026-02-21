package application;

import java.util.List;

/**
 * LEVEL BASE CLASS
 * Defines what each level contains
 * Each level defines its own goals, enemies, and items
 */
public abstract class Level {
    protected int levelNumber;
    protected String levelName;
    protected String background;
    
    public Level(int levelNumber, String levelName) {
        this.levelNumber = levelNumber;
        this.levelName = levelName;
    }
    
    // Each level defines its own goals
    public abstract String getQuestText();
    
    // Each level defines what enemies appear
    public abstract List<Class<? extends Enemy>> getAllowedEnemies();
    
    // Each level defines what items appear
    public abstract List<Class<? extends Item>> getAllowedItems();
    
    // Each level defines spawn rates
    public abstract int getEnemySpawnRate();
    public abstract int getItemSpawnRate();
    
    // Each level defines win conditions
    public abstract boolean checkWinCondition(int enemiesDefeated, int itemsCollected);
    
    public String getBackground() { return background; }
    public int getLevelNumber() { return levelNumber; }
}