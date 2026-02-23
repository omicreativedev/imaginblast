package application;

import javafx.scene.image.Image;
import java.util.List;

public class BossPirate extends Boss {
    private int shootCooldown = 0;
    private int speed = 4; // Increased from 2 to 4
    
    public BossPirate(int posX, int posY) {
        super(posX, posY, 256, new Image("boss_pirate_256x256.png"));
        this.health = 200;
        this.maxHealth = 200;
    }
    
    @Override
    public void update(Player player) {
        super.update(); // Handle explosion
        
        if (exploding || destroyed) return;
        
        // Track player's X position
        int targetX = player.posX - size/2; // Center boss over player
        
        // Move toward player's X position
        if (posX < targetX) {
            posX += speed;
        } else if (posX > targetX) {
            posX -= speed;
        }
        
        // Add slight sine wave variation for natural movement
        posX += Math.sin(System.currentTimeMillis() * 0.005) * 2;
        
        // Keep boss on screen
        if (posX < 0) {
            posX = 0;
        }
        if (posX + size > ImaginBlastMain.WIDTH) {
            posX = ImaginBlastMain.WIDTH - size;
        }
        
        if (shootCooldown > 0) shootCooldown--;
    }
    
    @Override
    public void shoot(List<Shot> shots) {
        if (shootCooldown <= 0 && !exploding) {
            shots.add(new EnemyShot(posX + size/2 - Shot.SIZE/2, posY + size));
            shootCooldown = 30; // Adjust as needed
        }
    }
    
    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
        
        if (health <= 0) {
            explode();
        }
    }
}