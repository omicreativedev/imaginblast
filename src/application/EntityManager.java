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
    private List<Enemy> squirrels;
    private List<Item> acornCaps;
    
    // Game balance constants

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
        squirrels = new ArrayList<>();
        acornCaps = new ArrayList<>();
        score = 0;
        player = null; // will be set later
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
            // Keep player on screen
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
            for (Enemy squirrel : squirrels) {
                if (Collisions.shotCollides(shot, squirrel) && !squirrel.exploding) {
                    score++;
                    levelManager.getCurrentLevel().registerEnemyDefeated(squirrel);
                    squirrel.explode();
                    shot.toRemove = true;
                    break; // shot removed, exit inner loop
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
    
    // Enemies methods
    public List<Enemy> getSquirrels() {
        return squirrels;
    }
    
    public void addEnemy(Enemy enemy) {
        squirrels.add(enemy);
    }
    
    public void updateEnemies() {
        squirrels.forEach(e -> {
            e.update();
        });
    }
    
    public void drawEnemies(GraphicsContext gc) {
        squirrels.forEach(e -> e.draw(gc));
    }
    
    public void checkEnemyCollisions(GameStateManager stateManager) {
        for (Enemy e : squirrels) {
            if (Collisions.playerCollides(player, e) && !player.exploding) {
                boolean stillAlive = player.takeDamage(1);
                if (!stillAlive) {
                    stateManager.setCurrentState(GameState.GAME_OVER);
                }
            }
        }
    }
    
    public void replaceDestroyedEnemies(java.util.function.Supplier<Enemy> newSquirrelSupplier) {
        for (int i = squirrels.size() - 1; i >= 0; i--) {
            if (squirrels.get(i).destroyed) {
                squirrels.set(i, newSquirrelSupplier.get());
            }
        }
    }
    
    public void clearEnemies() {
        squirrels.clear();
    }
    
    // Acorns methods
    public List<Item> getAcornCaps() {
        return acornCaps;
    }
    
    public void addAcorn(Item acorn) {
        acornCaps.add(acorn);
    }
    
    public void updateAcorns(LevelManager levelManager) {
        acornCaps.forEach(i -> {
            i.update(null); // gc not used in update
            if (Collisions.itemCollides(player, i) && !i.collected) {
                levelManager.getCurrentLevel().registerItemCollected(i);
                ((ItemAcorn) i).onCollected();
            }
        });
    }
    
    public void drawAcorns(GraphicsContext gc) {
        acornCaps.forEach(i -> i.draw(gc));
    }
    
    public void replaceCollectedAcorns(java.util.function.Supplier<Item> newAcornSupplier) {
        for (int i = acornCaps.size() - 1; i >= 0; i--) {
            if (acornCaps.get(i).gone) {
                acornCaps.set(i, newAcornSupplier.get());
            }
        }
    }
    
    public void clearAcorns() {
        acornCaps.clear();
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