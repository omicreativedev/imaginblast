package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class BossBunny extends Boss {
    
    private int shootCooldown = 0;
    private int speed = 8;
    private static final int BULLET_SPEED = 12;
    private static final ImageView BULLET_IMG = new ImageView(new Image("item_carrot.png"));
    
    public BossBunny(int posX, int posY) {
        super(posX, posY, 256, new ImageView(new Image("boss_bunny.png")));
        this.health = 500;
        this.maxHealth = 500;
    }
    
    @Override
    public void update(Player player) {
        super.update();
        if (exploding || destroyed) return;
        
        int targetX = player.posX - size/2;
        if (posX < targetX) posX += speed;
        else if (posX > targetX) posX -= speed;
        
        posX += Math.sin(System.currentTimeMillis() * 0.005) * 2;
        
        if (posX < 0) posX = 0;
        if (posX + size > ImaginBlastMain.WIDTH) posX = ImaginBlastMain.WIDTH - size;
        
        if (shootCooldown > 0) shootCooldown--;
    }
    
    private double[] calculateDirection(double targetX, double targetY) {
        double fromX = posX + size / 2;
        double fromY = posY + size / 2;
        double dx = targetX - fromX;
        double dy = targetY - fromY;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        } else {
            dx = 0;
            dy = 1;
        }
        return new double[]{dx, dy};
    }
    
    @Override
    public void shoot(List<Shot> shots) {
        if (shootCooldown <= 0 && !exploding) {
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            shots.add(new EnemyShot(shotX, shotY));
            shootCooldown = 25;
        }
    }
    
    @Override
    public void shootAtPlayer(List<Shot> shots, Player player) {
        if (shootCooldown <= 0 && !exploding) {
            double playerCenterX = player.posX + player.size / 2;
            double playerCenterY = player.posY + player.size / 2;
            double[] direction = calculateDirection(playerCenterX, playerCenterY);
            double velX = direction[0] * BULLET_SPEED;
            double velY = direction[1] * BULLET_SPEED;
            int shotX = posX + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            int shotY = posY + size / 2 - EnemyShot.getEnemyShotSize() / 2;
            shots.add(new EnemyShot(shotX, shotY, velX, velY, BULLET_IMG));
            shootCooldown = 25;
        }
    }
    
    @Override
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            explode();
        }
    }
}