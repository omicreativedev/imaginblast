package application;

import java.util.HashMap;
import java.util.Map;

/**
 * LEVEL 1
 * Forest level - collect acorns, defeat squirrels
 */
public class Level01 extends Level {
    
    public Level01() {
        super(1, "Forest Falls");
        this.background = "FORESTGREEN";
    }
    
    @Override
    public String getQuestText() {
        return "Collect 50 acorns and defeat 50 squirrels";
    }
    
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 50);  // Need 50 squirrels
        return goals;
    }
    
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 50);  // Need 50 acorns
        return goals;
    }
    
    @Override
    public int getEnemySpawnRate() {
        return 10;
    }
    
    @Override
    public int getItemSpawnRate() {
        return 8;
    }
}