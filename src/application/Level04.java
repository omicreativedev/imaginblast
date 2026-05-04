package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/* 
 * Level 4 - Old Timey Fields
 * Enemies: Squirrels, Pillbugs, Garlic, Urchins
 * Items: Acorns, Donuts, Cupcakes, Cassettes
 * Boss: Angry Grandma
 * Note: File adapted from level01.java. See comments for details.
 */
public class Level04 extends Level{	
	
	public Level04() {
		super(4, "Old Timey Fields");
		// Background Image
		this.background = new Image("level_bg_04.png");
	}
	
	@Override
    public String getQuestText() {
        return "Collect 1 acorns, 1 donuts, 1 cupcakes, 1 cassettes, defeat 1 squirrels, 1 pillbugs, and 1 garlic, 1 urchins to go to the next level";
    }
    
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 1);
        goals.put(EnemyPillbug.class, 1);
        goals.put(EnemyGarlic.class, 1);
        goals.put(EnemyUrchin.class, 1);
        return goals;
    }
    
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 1);
        goals.put(ItemDonut.class, 1);
        goals.put(ItemCupcake.class, 1);
        goals.put(ItemCassette.class, 1);
        return goals;
    }
    
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class, EnemyPillbug.class, EnemyGarlic.class, EnemyUrchin.class);
    }
    
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemDonut.class, ItemCupcake.class, ItemCassette.class);
    }
    
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
        if (enemyClass == EnemyGarlic.class) {
            return ImaginBlastMain.GARLIC_IMG;
        }
        if (enemyClass == EnemyUrchin.class) {
            return ImaginBlastMain.URCHIN_IMG;
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
        if (itemClass == ItemCupcake.class) {
            return ImaginBlastMain.CUPCAKE_IMG;
        }
        if (itemClass == ItemCassette.class) {
            return ImaginBlastMain.CASSETTE_IMG;
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