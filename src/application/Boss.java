package application;

import javafx.scene.image.Image;
import java.util.List;

public abstract class Boss extends Creature {
    protected int health;
    protected int maxHealth;
    protected int phase;
    protected boolean portalSpawned = false;
    protected boolean portalActive = false;
    
    public Boss(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }
    
    public abstract void update(Player player); // Track player
    public abstract void shoot(List<Shot> shots); // Add boss shots
    public abstract void takeDamage(int amount);
    
    public boolean isDefeated() { return health <= 0; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
}