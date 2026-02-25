package application;

import java.util.ArrayList;

/**
 * Inspiration/Source: https://github.com/FLwolfy/2D-Entity-Component-System
 */

import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

/**
 * Manages all game entities (player, shots, enemies, items, particles)
 */
public class EntityManager {
    private static final Random RAND = new Random(); // Random generator for particle effects
    
    // Game objects collections
    private Player player;              // The player character
    private List<Shot> shots;            // Player-fired projectiles
    private List<Shot> enemyShots;       // Enemy-fired projectiles
    private List<Particles> particles;    // Visual effect particles
    private List<Enemy> enemies;          // Active enemy entities
    private List<Item> items;             // Active item entities (acorns, power-ups, etc.)
    
    // Other constants
    private int MAX_SHOTS;    // Maximum number of player shots allowed on screen at once
    private int score;         // Player's current score
    
    // Width and height for off-screen checks
    private int WIDTH;  // Game screen width
    private int HEIGHT; // Game screen height
    
    /**
     * CONSTRUCTOR
     * @param MAX_SHOTS Maximum allowed player shots
     * @param WIDTH Screen width
     * @param HEIGHT Screen height
     * @param MAX_BOMBS Maximum enemies (passed from main but not directly used)
     * @param MAX_ITEMS Maximum items (passed from main but not directly used)
     */
    public EntityManager(int MAX_SHOTS, int WIDTH, int HEIGHT, int MAX_BOMBS, int MAX_ITEMS) {
        this.MAX_SHOTS = MAX_SHOTS;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        initialize(); // Set up empty collections
    }
    
    /**
     * INITIALIZE
     * Creates fresh empty collections and resets score
     * Called by constructor and resetAll()
     */
    private void initialize() {
        particles = new ArrayList<>();
        shots = new ArrayList<>();
        enemyShots = new ArrayList<>();
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        score = 0;
        player = null;
    }
    
    // ===== PLAYER METHODS =====
    
    /**
     * Sets the player instance
     * @param player The player object
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * @return The current player instance
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Updates player state (called every frame)
     */
    public void updatePlayer() {
        if (player != null) {
            player.update();
        }
    }
    
    /**
     * Draws the player on screen
     * @param gc Graphics context
     */
    public void drawPlayer(GraphicsContext gc) {
        if (player != null) {
            player.draw(gc);
        }
    }
    
    /**
     * Moves player horizontally based on mouse position
     * @param mouseX Current mouse X coordinate
     */
    public void movePlayer(int mouseX) {
        if (player != null) {
            player.posX = mouseX;
            // Keep player within screen bounds
            if (player.posX < 0) player.posX = 0;
            if (player.posX + player.size > WIDTH) player.posX = WIDTH - player.size;
        }
    }
    
    /**
     * Resets player health to maximum
     */
    public void resetPlayerHealth() {
        if (player != null) {
            player.resetHealth();
        }
    }
    
    /**
     * @return true if player is destroyed (dead)
     */
    public boolean isPlayerDestroyed() {
        return player != null && player.destroyed;
    }
    
    // ===== PLAYER SHOTS METHODS =====
    
    /**
     * @return List of active player shots
     */
    public List<Shot> getShots() {
        return shots;
    }
    
    /**
     * Adds a new player shot if under the maximum limit
     * @param shot The shot to add
     */
    public void addShot(Shot shot) {
        if (shot != null && shots.size() < MAX_SHOTS) {
            shots.add(shot);
        }
    }
    
    /**
     * Updates all player shots and removes off-screen ones
     */
    public void updateShots() {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            // Remove if off top of screen or marked for removal
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
        }
    }
    
    /**
     * Updates shots and checks for collisions with enemies
     * @param levelManager For registering defeated enemies
     */
    public void updateShotsWithEnemyCollisions(LevelManager levelManager) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            // Remove if off screen or marked
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            // Check collision with each enemy
            for (Enemy enemy : enemies) {
                if (Collisions.shotCollides(shot, enemy) && !enemy.exploding) {
                    score++; // Increase score
                    levelManager.getCurrentLevel().registerEnemyDefeated(enemy);
                    enemy.explode(); // Start enemy death animation
                    shot.toRemove = true; // Mark shot for removal
                    break;
                }
            }
        }
    }
    
    /**
     * Updates shots and checks for collisions with boss
     * @param boss The current boss
     */
    public void updateShotsWithBossCollisions(Boss boss) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            shot.update();
            // Remove if off screen or marked
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            // Check collision with boss
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10); // Boss takes damage
                shots.remove(i); // Remove the shot
            }
        }
    }
    
    /**
     * Draws all player shots
     * @param gc Graphics context
     */
    public void drawShots(GraphicsContext gc) {
        for (Shot shot : shots) {
            shot.draw(gc);
        }
    }
    
    // ===== ENEMY SHOTS METHODS =====
    // Currently customized in EnemyShot.java
    // Eventually we should move to have each Boss/Enemy have its own shot types
    
    /**
     * @return List of enemy projectiles
     */
    public List<Shot> getEnemyShots() {
        return enemyShots;
    }
    
    /**
     * Updates enemy shots and checks for player collisions
     */
    public void updateEnemyShots() {
        for (int i = enemyShots.size() - 1; i >= 0; i--) {
            Shot shot = enemyShots.get(i);
            shot.update();
            // Remove if off bottom of screen or marked
            if (shot.posY > HEIGHT || shot.toRemove) {
                enemyShots.remove(i);
                continue;
            }
            // Check if shot hits player
            if (Collisions.shotCollides(shot, player) && !player.exploding) {
                player.takeDamage(5); // Player takes damage
                enemyShots.remove(i); // Remove the shot
            }
        }
    }
    
    /**
     * Draws all enemy shots
     * @param gc Graphics context
     */
    public void drawEnemyShots(GraphicsContext gc) {
        for (Shot shot : enemyShots) {
            shot.draw(gc);
        }
    }
    
    // ===== ENEMIES METHODS =====
    
    /**
     * @return List of all active enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    /**
     * Adds a new enemy
     * @param enemy The enemy to add
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * Updates all enemies
     */
    public void updateEnemies() {
        enemies.forEach(e -> e.update());
    }
    
    /**
     * Draws all enemies
     * @param gc Graphics context
     */
    public void drawEnemies(GraphicsContext gc) {
        enemies.forEach(e -> e.draw(gc));
    }
    
    /**
     * Checks for collisions between player and enemies
     * @param stateManager For game over state if player dies
     */
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
    
    /**
     * Replaces destroyed enemies with new ones
     * @param newEnemySupplier Function that creates new enemies
     */
    public void replaceDestroyedEnemies(java.util.function.Supplier<Enemy> newEnemySupplier) {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).destroyed) {
                enemies.set(i, newEnemySupplier.get());
            }
        }
    }
    
    /**
     * Removes all enemies
     */
    public void clearEnemies() {
        enemies.clear();
    }
    
    // ===== ITEMS METHODS =====
    
    /**
     * @return List of all active items
     */
    public List<Item> getItems() {
        return items;
    }
    
    /**
     * Adds a new item
     * @param item The item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Updates all items and checks for player collection
     * @param levelManager For registering collected items
     */
    public void updateItems(LevelManager levelManager) {
        items.forEach(i -> {
            i.update(null);
            if (Collisions.itemCollides(player, i) && !i.collected) {
                levelManager.getCurrentLevel().registerItemCollected(i);
                // Call specific item's collection effect
                if (i instanceof ItemAcorn) {
                    ((ItemAcorn) i).onCollected();
                }
                // Future: add else-if for other item types
            }
        });
    }
    
    /**
     * Draws all items
     * @param gc Graphics context
     */
    public void drawItems(GraphicsContext gc) {
        items.forEach(i -> i.draw(gc));
    }
    
    /**
     * Replaces collected items with new ones
     * @param newItemSupplier Function that creates new items
     */
    public void replaceCollectedItems(java.util.function.Supplier<Item> newItemSupplier) {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).gone) {
                items.set(i, newItemSupplier.get());
            }
        }
    }
    
    /**
     * Removes all items
     */
    public void clearItems() {
        items.clear();
    }
    
    // ===== PARTICLES METHODS =====
    
    /**
     * Updates particle effects - creates new ones and removes old ones
     * @param gc Graphics context
     */
    public void updateParticles(GraphicsContext gc) {
        // Randomly create new particles
        if (RAND.nextInt(10) > 2) {
            particles.add(new Particles(gc));
        }
        // Remove particles that are off screen
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isOffScreen()) {
                particles.remove(i);
                i--;
            }
        }
    }
    
    /**
     * Draws all particle effects
     * @param gc Graphics context
     */
    public void drawParticles(GraphicsContext gc) {
        particles.forEach(Particles::draw);
    }
    
    // ===== SCORE METHODS =====
    
    /**
     * @return Current player score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Resets score to zero
     */
    public void resetScore() {
        score = 0;
    }
    
    /**
     * Increases score by 1
     */
    public void incrementScore() {
        score++;
    }
    
    /**
     * Reset all entities and score for a new game
     */
    public void resetAll() {
        initialize();
    }
}