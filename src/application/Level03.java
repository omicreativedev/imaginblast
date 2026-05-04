package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/* 
 * Level 3 - Final Level
 * Enemies: Squirrels, Pillbugs, Garlic
 * Items: Acorns, Donuts, Cupcakes
 * Boss: Broc
 */
public class Level03 extends Level{	
	
	public Level03() {
		super(3, "Broccoli Fields");
		
		this.background = new Image("level_bg_03.png");
	}
	
	@Override
    public String getQuestText() {
        return "Collect 2 acorns, 2 donuts, 2 cupcakes, defeat 2 squirrels, 2 pillbugs, and 2 garlic";
    }
    
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 2);
        goals.put(EnemyPillbug.class, 2);
        goals.put(EnemyGarlic.class, 2);
        return goals;
    }
    
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 2);
        goals.put(ItemDonut.class, 2);
        goals.put(ItemCupcake.class, 2);
        return goals;
    }
    
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class, EnemyPillbug.class, EnemyGarlic.class);
    }
    
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class, ItemDonut.class, ItemCupcake.class);
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
        return null;
    }
    
    @Override
    public ImageView getEnemyImage(Class<? extends Enemy> enemyClass) { //new - Changed return type from Image to ImageView
        if (enemyClass == EnemySquirrel.class) {
            return ImaginBlastMain.SQUIRREL_IMG;
        }
        if (enemyClass == EnemyPillbug.class) {
            return ImaginBlastMain.PILLBUG_IMG;
        }
        if (enemyClass == EnemyGarlic.class) {
            return ImaginBlastMain.GARLIC_IMG; // You'll need to add this
        }
        return null;
    }
    
    @Override
    public ImageView getItemImage(Class<? extends Item> itemClass) { //new - Changed return type from Image to ImageView
        if (itemClass == ItemAcorn.class) {
            return ImaginBlastMain.ACORN_IMG;
        }
        if (itemClass == ItemDonut.class) {
            return ImaginBlastMain.DONUT_IMG;
        }
        if (itemClass == ItemCupcake.class) {
            return ImaginBlastMain.CUPCAKE_IMG;
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