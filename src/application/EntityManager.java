package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

/**
 * Manages all game entities (player, shots, enemies, items, particles)
 */
public class EntityManager {
    private static final Random RAND = new Random();
    
    // Game objects collections
    private Player player;
    private List<Shot> shots;
    private List<Shot> enemyShots;
    private List<Particles> particles;
    private List<Enemy> enemies;
    private List<Item> items;
    
    // Other constants
    private int MAX_SHOTS;
    private int score;
    
    // Width and height for off-screen checks
    private int WIDTH;
    private int HEIGHT;
    
    public EntityManager(int MAX_SHOTS, int WIDTH, int HEIGHT, int MAX_BOMBS, int MAX_ITEMS) {
        this.MAX_SHOTS = MAX_SHOTS;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        initialize();
    }
    
    private void initialize() {
        particles = new ArrayList<>();
        shots = new ArrayList<>();
        enemyShots = new ArrayList<>();
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        score = 0;
        player = null;
    }
    
    // Player methods
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void updatePlayer() {
        if (player != null) {
            player.update();
        }
    }
    
    public void drawPlayer(GraphicsContext gc) {
        if (player != null) {
            player.draw(gc);
        }
    }
    
    public void movePlayer(int mouseX) {
        if (player != null) {
            player.posX = mouseX;
            if (player.posX < 0) player.posX = 0;
            if (player.posX + player.size > WIDTH) player.posX = WIDTH - player.size;
        }
    }
    
    public void resetPlayerHealth() {
        if (player != null) {
            player.resetHealth();
        }
    }
    
    public boolean isPlayerDestroyed() {
        return player != null && player.destroyed;
    }
    
    // Shots methods
    public List<Shot> getShots() {
        return shots;
    }
    
    public void addShot(Shot shot) {
        if (shot != null && shots.size() < MAX_SHOTS) {
            shots.add(shot);
        }
    }
    
    public void updateShots() {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
        }
    }
    
    public void updateShotsWithEnemyCollisions(LevelManager levelManager) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            for (Enemy enemy : enemies) {  // Renamed from 'squirrel'
                if (Collisions.shotCollides(shot, enemy) && !enemy.exploding) {
                    score++;
                    levelManager.getCurrentLevel().registerEnemyDefeated(enemy);
                    enemy.explode();
                    shot.toRemove = true;
                    break;
                }
            }
        }
    }
    
    public void updateShotsWithBossCollisions(Boss boss) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            shot.update();
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10);
                shots.remove(i);
            }
        }
    }
    
    public void drawShots(GraphicsContext gc) {
        for (Shot shot : shots) {
            shot.draw(gc);
        }
    }
    
    // Enemy shots methods
    public List<Shot> getEnemyShots() {
        return enemyShots;
    }
    
    public void updateEnemyShots() {
        for (int i = enemyShots.size() - 1; i >= 0; i--) {
            Shot shot = enemyShots.get(i);
            shot.update();
            if (shot.posY > HEIGHT || shot.toRemove) {
                enemyShots.remove(i);
                continue;
            }
            if (Collisions.shotCollides(shot, player) && !player.exploding) {
                player.takeDamage(5);
                enemyShots.remove(i);
            }
        }
    }
    
    public void drawEnemyShots(GraphicsContext gc) {
        for (Shot shot : enemyShots) {
            shot.draw(gc);
        }
    }
    
    // Enemies methods (renamed from Squirrels)
    public List<Enemy> getEnemies() {  // Renamed from getSquirrels()
        return enemies;
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    public void updateEnemies() {
        enemies.forEach(e -> e.update());
    }
    
    public void drawEnemies(GraphicsContext gc) {
        enemies.forEach(e -> e.draw(gc));
    }
    
    public void checkEnemyCollisions(GameStateManager stateManager) {
        for (Enemy e : enemies) {
            if (Collisions.playerCollides(player, e) && !player.exploding) {
                boolean stillAlive = player.takeDamage(1);
                if (!stillAlive) {
                    stateManager.setCurrentState(GameState.GAME_OVER);
                }
            }
        }
    }
    
    public void replaceDestroyedEnemies(java.util.function.Supplier<Enemy> newEnemySupplier) {  // Renamed parameter
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).destroyed) {
                enemies.set(i, newEnemySupplier.get());
            }
        }
    }
    
    public void clearEnemies() {
        enemies.clear();
    }
    
    // Items methods (renamed from Acorns)
    public List<Item> getItems() {  // Renamed from getAcornCaps()
        return items;
    }
    
    public void addItem(Item item) {  // Already correctly named
        items.add(item);
    }
    
    public void updateItems(LevelManager levelManager) {  // Renamed from updateAcorns()
        items.forEach(i -> {
            i.update(null);
            if (Collisions.itemCollides(player, i) && !i.collected) {
                levelManager.getCurrentLevel().registerItemCollected(i);
                // Cast to specific item type for onCollected()
                if (i instanceof ItemAcorn) {
                    ((ItemAcorn) i).onCollected();
                }
                // Future: add else-if for other item types
            }
        });
    }
    
    public void drawItems(GraphicsContext gc) {  // Renamed from drawAcorns()
        items.forEach(i -> i.draw(gc));
    }
    
    public void replaceCollectedItems(java.util.function.Supplier<Item> newItemSupplier) {  // Renamed parameter
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).gone) {
                items.set(i, newItemSupplier.get());
            }
        }
    }
    
    public void clearItems() {  // Renamed from clearAcorns()
        items.clear();
    }
    
    // Particles methods
    public void updateParticles(GraphicsContext gc) {
        if (RAND.nextInt(10) > 2) {
            particles.add(new Particles(gc));
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isOffScreen()) {
                particles.remove(i);
                i--;
            }
        }
    }
    
    public void drawParticles(GraphicsContext gc) {
        particles.forEach(Particles::draw);
    }
    
    // Score methods
    public int getScore() {
        return score;
    }
    
    public void resetScore() {
        score = 0;
    }
    
    public void incrementScore() {
        score++;
    }
    
    // Reset for new game
    public void resetAll() {
        initialize();
    }
}