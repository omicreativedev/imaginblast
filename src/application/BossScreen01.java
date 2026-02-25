package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class BossScreen01 extends BossScreen {
  
    
    public BossScreen01() {
        boss = new BossPirate(ImaginBlastMain.WIDTH/2 - 128, 100);
        portal = new Portal();
        portalVisible = false;
    }
    
    @Override
    public void update(Player player, List<Shot> playerShots, List<Shot> enemyShots) {
        // Check if boss is defeated
        if (boss.isDefeated() && !portalVisible) {
            portalVisible = true;
        }
        
        // Only update boss if not defeated
        if (!boss.isDefeated()) {
            boss.update(player);
            boss.shoot(enemyShots);
        }
        
        // Check player shots hitting boss
        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot shot = playerShots.get(i);
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10); // Each shot does 10 damage
                playerShots.remove(i);
            }
        }
        
        // Check if player reached portal
        if (portalVisible && portal.checkCollision(player)) {
            levelComplete = true;
        }
    }
    
    @Override
    public void draw(GraphicsContext gc, GameRenderer renderer, Player player, int score) {
        // Clear screen with dark background
        gc.setFill(Color.DARKSLATEBLUE);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Draw boss
        boss.draw(gc);
        
        // Draw player - ADD THIS LINE
        player.draw(gc);
        

        
        // Draw portal if visible
        if (portalVisible) {
            portal.draw(gc);
        }
        
        // Draw boss health
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("Boss: " + boss.getHealth() + "/" + boss.getMaxHealth(), 
                    ImaginBlastMain.WIDTH/2 - 80, 50);
        
        // Draw player health
        gc.setFill(Color.RED);
        gc.fillText("Health: " + player.hp + "/" + player.maxHp, 50, 50);
    }
    
    @Override
    public boolean isComplete() {
        return levelComplete;
    }
}