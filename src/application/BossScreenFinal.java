package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

/**
 * BOSS SCREEN FINAL
 * Manages the final boss fight (BossBunny.java)
 */
public class BossScreenFinal extends BossScreen {
    
    private Image background;
    private GameRenderer gameRenderer;
    
    public BossScreenFinal() {
        boss = new BossBunny(ImaginBlastMain.WIDTH/2 - 128, 100);
        portal = new Portal();
        portalVisible = false;
        levelComplete = false;
        background = new Image("boss_bg_final.png");
    }
    
    @Override
    public void update(Player player, List<Shot> playerShots, List<Shot> enemyShots) {

        if (boss.isDefeated() && !portalVisible) {
            portalVisible = true;
            if (gameRenderer != null) {
                gameRenderer.playExplodeSound();
            }
        }
        
        boss.update(player);
        
        if (!boss.isDefeated()) {
            boss.shootAtPlayer(enemyShots, player);
        }
        
        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot shot = playerShots.get(i);
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10);
                playerShots.remove(i);
            }
        }
        
        if (Collisions.playerCollides(player, boss) && !player.exploding) {
            player.takeDamage(10);
            
            int bossCenterX = boss.posX + boss.size / 2;
            int bossCenterY = boss.posY + boss.size / 2;
            int playerCenterX = player.posX + player.size / 2;
            int playerCenterY = player.posY + player.size / 2;
            
            int pushX = playerCenterX - bossCenterX;
            int pushY = playerCenterY - bossCenterY;
            if (pushX > 0) pushX = 1;
            else if (pushX < 0) pushX = -1;
            else pushX = 0;
            if (pushY > 0) pushY = 1;
            else if (pushY < 0) pushY = -1;
            else pushY = 0;
            
            int newX = player.posX + (pushX * 100);
            int newY = player.posY + (pushY * 100);
            
            if (newX < 0) newX = 0;
            if (newX + player.size > ImaginBlastMain.WIDTH) newX = ImaginBlastMain.WIDTH - player.size;
            if (newY < 0) newY = 0;
            if (newY + player.size > ImaginBlastMain.HEIGHT) newY = ImaginBlastMain.HEIGHT - player.size;
            
            player.posX = newX;
            player.posY = newY;
        }
        
        if (portalVisible && portal.checkCollision(player)) {
            if (gameRenderer != null) {
                gameRenderer.playPortalSound();
            }
            levelComplete = true;
        }
    }
    
    @Override
    public void draw(GraphicsContext gc, GameRenderer gameRenderer, Player player, int score) {
        this.gameRenderer = gameRenderer;
        gc.drawImage(background, 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        boss.draw(gc);
        player.draw(gc);
        if (portalVisible) {
            portal.draw(gc);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("FINAL BOSS: " + boss.getHealth() + "/" + boss.getMaxHealth(), 
                    ImaginBlastMain.WIDTH/2 - 100, 50);
        gc.setFill(Color.RED);
        gc.fillText("Health: " + player.hp + "/" + player.maxHp, 50, 50);
    }
    
    @Override
    public boolean isComplete() {
        return levelComplete;
    }
}