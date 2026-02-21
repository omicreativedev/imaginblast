package application;

import java.util.ArrayList;
import java.util.List;

/**
 * LEVEL 1
 * Forest level - collect acorns, defeat squirrels
 */
public class Level01 extends Level {
    
    private int targetEnemies = 50;
    private int targetAcorns = 50;
    
    public Level01() {
        super(1, "Forest Falls");
        this.background = "FORESTGREEN";
    }
    
    @Override
    public String getQuestText() {
        return "Collect " + targetAcorns + " acorns and defeat " + targetEnemies + " squirrels";
    }
    
    @Override
    public List<Class<? extends Enemy>> getAllowedEnemies() {
        List<Class<? extends Enemy>> enemies = new ArrayList<>();
        enemies.add(EnemySquirrel.class);  // Level 1 only has squirrels
        return enemies;
    }
    
    @Override
    public List<Class<? extends Item>> getAllowedItems() {
        List<Class<? extends Item>> items = new ArrayList<>();
        items.add(ItemAcorn.class);  // Level 1 only has acorns
        return items;
    }
    
    @Override
    public int getEnemySpawnRate() {
        return 10; // Spawn enemies frequently
    }
    
    @Override
    public int getItemSpawnRate() {
        return 8; // Spawn items frequently
    }
    
    @Override
    public boolean checkWinCondition(int enemiesDefeated, int itemsCollected) {
        return enemiesDefeated >= targetEnemies && itemsCollected >= targetAcorns;
    }
}