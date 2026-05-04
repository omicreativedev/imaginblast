package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/* 
 * Level 2 - Muddy Ponds
 * Enemies: Squirrels, Pillbugs
 * Items: Acorns, Donuts
 * Boss: Beetle Chef
 * Note: File adapted from level01.java. See comments for details.
 */
public class Level02 extends Level{	
	
	public Level02() {
		super(2, "Muddy Ponds");
		// Background Image
		this.background = new Image("level_bg_02.png");
	}
	
	@Override
    public String getQuestText() {
        return "Collect 2 acorns, 2 donuts, defeat 2 squirrels, and defeat 2 pillbugs";
    }
    
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 2);
        goals.put(EnemyPillbug.class, 2); 
        return goals;
    }
    
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 2);
        goals.put(ItemDonut.class, 2);
        return goals;
    }
    
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class, EnemyPillbug.class);
    }
    
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemDonut.class);
    }
    
    @Override
    public Enemy createEnemy(Random rand, int width, int playerSize, Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return new EnemySquirrel(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        if (enemyClass == EnemyPillbug.class) {
            return new EnemyPillbug(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        return null;
    }
    
    @Override
    public Item createItem(Random rand, int width, int playerSize, Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return new ItemAcorn(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        if (itemClass == ItemDonut.class) {
            return new ItemDonut(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        return null;
    }
    
    @Override
    public ImageView getEnemyImage(Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return ImaginBlastMain.SQUIRREL_IMG;
        }
        if (enemyClass == EnemyPillbug.class) {
            return ImaginBlastMain.PILLBUG_IMG;
        }
        return null;
    }
    
    @Override
    public ImageView getItemImage(Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return ImaginBlastMain.ACORN_IMG;
        }
        if (itemClass == ItemDonut.class) {
            return ImaginBlastMain.DONUT_IMG;
        }
        return null;
    }
    
    @Override
    public int getEnemySpawnRate() {
        return 10;
    }
    
    @Override
    public int getItemSpawnRate() {
        return 5;
    }
	
}