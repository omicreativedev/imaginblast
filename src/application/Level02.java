package application;

import java.util.HashMap;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/* 
 * 03/29 - 9:25 PM
 * Every 'block' or required componets of the level are included here, 
 * I just need to update enemy images and background image
 */
public class Level02 extends Level{	
	
	public Level02() {
		super(2, "TBD");
		this.background = "TBD"; //TBD = To Be Done, what needs to be changed once I get files/info for these.
	}
	
	@Override
    public String getQuestText() {
        return "Congrats on beating the Chesire Pirate Cat! Now, go collect 50 more acorns and squash 50 pillbugs!";
    }
    
    @Override
    public Map<Class<? extends Enemy>, Integer> getEnemyGoals() {
        Map<Class<? extends Enemy>, Integer> goals = new HashMap<>();
        goals.put(EnemySquirrel.class, 10); //NEEDS TO BE CHANGED TO PILLBUGS
        return goals;
    }
    
    @Override
    public Map<Class<? extends Item>, Integer> getItemGoals() {
        Map<Class<? extends Item>, Integer> goals = new HashMap<>();
        goals.put(ItemAcorn.class, 10);
        return goals;
    }
    
    @Override
    public List<Class<? extends Enemy>> getPossibleEnemies() {
        return Arrays.asList(EnemySquirrel.class); //CHANGE TO PILLBUGS
    }
    
    @Override
    public List<Class<? extends Item>> getPossibleItems() {
        return Arrays.asList(ItemAcorn.class);
    }
    
    @Override
    public Enemy createEnemy(Random rand, int width, int playerSize, Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) { //CHANGE TO PILLBUGS
            return new EnemySquirrel(50 + rand.nextInt(width - 100), 0, playerSize, getEnemyImage(enemyClass));
        }
        return null;
    }
    
    @Override
    public Item createItem(Random rand, int width, int playerSize, Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return new ItemAcorn(50 + rand.nextInt(width - 100), 0, playerSize, getItemImage(itemClass));
        }
        return null;
    }
    
    @Override
    public Image getEnemyImage(Class<? extends Enemy> enemyClass) {
        if (enemyClass == EnemySquirrel.class) {
            return ImaginBlastMain.PILLBUG_IMG; //Changed from SQUIRELL_IMG TO PILLBUG_IMG
        }
        return null;
    }
    
    @Override
    public Image getItemImage(Class<? extends Item> itemClass) {
        if (itemClass == ItemAcorn.class) {
            return ImaginBlastMain.ACORN_IMG;
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