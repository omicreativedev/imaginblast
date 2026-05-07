package application;

import java.util.ArrayList;

/**
 * Inspiration/Source: https://github.com/FLwolfy/2D-Entity-Component-System
 */

import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

/**
 * ENTITY MANAGER CLASS
 * Manages all game entities (player, shots, enemies, items, particles)
 * Central hub for updating, drawing, and storing every object in the game world
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class EntityManager {
    private static final Random RAND = new Random(); // Random generator for particle effects
    
    // Game objects collections
    private Player player;                 // The player character (frog)
    private List<Shot> shots;              // Player-fired projectiles
    private List<Shot> enemyShots;         // Enemy and boss-fired projectiles
    private List<Particles> particles;     // Visual effect particles (floating dust, etc.)
    private List<Enemy> enemies;           // Active enemy entities
    private List<Item> items;              // Active item entities (acorns, donuts, bubbles, etc.)
    
    // Game settings
    private int MAX_SHOTS;                 // Maximum number of player shots allowed on screen at once
    private int score;                     // Player's current score
    
    // Screen dimensions
    private int WIDTH;                     // Game screen width (for off-screen checks)
    private int HEIGHT;                    // Game screen height (for off-screen checks)
    
    // External references
    private InputHandler inputHandler;     // For WASD player movement
    private GameRenderer gameRenderer;     // For playing sound effects
    
    /**
     * ENTITY MANAGER CONSTRUCTOR
     * Sets up empty collections and stores game configuration
     * 
     * @param MAX_SHOTS Maximum allowed player shots on screen at once
     * @param WIDTH Screen width (for off-screen boundary checks)
     * @param HEIGHT Screen height (for off-screen boundary checks)
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
     * INITIALIZE METHOD
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
     * SET PLAYER METHOD
     * Sets the player instance and connects external references
     * 
     * @param player The player object (frog)
     */
    public void setPlayer(Player player) {
        this.player = player;
        // Connect input handler to player if both exist
        if (this.player != null && inputHandler != null) {
            this.player.setInputHandler(inputHandler);
        }
        // Connect game renderer to player for sound effects
        if (this.player != null && gameRenderer != null) {
            this.player.setGameRenderer(gameRenderer);
        }
    }
    
    /**
     * SET INPUT HANDLER METHOD
     * Sets the input handler for WASD movement
     * Called by ImaginBlastMain after creating EntityManager
     * 
     * @param handler The InputHandler instance (for keyboard input)
     */
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
        // If player already exists, connect them now
        if (player != null) {
            player.setInputHandler(inputHandler);
        }
    }
    
    /**
     * SET GAME RENDERER METHOD
     * Sets the game renderer reference for playing sound effects
     * 
     * @param renderer The GameRenderer instance (for sounds)
     */
    public void setGameRenderer(GameRenderer renderer) {
        this.gameRenderer = renderer;
        if (player != null) {
            player.setGameRenderer(gameRenderer);
        }
    }
    
    /**
     * GET PLAYER METHOD
     * 
     * @return The current player instance
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * UPDATE PLAYER METHOD
     * Updates player state (called every frame)
     */
    public void updatePlayer() {
        if (player != null) {
            player.update();
        }
    }
    
    /**
     * DRAW PLAYER METHOD
     * Draws the player on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawPlayer(GraphicsContext gc) {
        if (player != null) {
            player.draw(gc);
        }
    }
    
    /**
     * MOVE PLAYER METHOD (DEPRECATED)
     * WASD movement has replaced mouse movement.
     * This method is intentionally empty but kept for backward compatibility.
     * 
     * @param mouseX Current mouse X coordinate (unused)
     * 
     * DEPRECATED: WASD movement now handles player positioning.
     * TODO: Remove this method entirely after confirming ImaginBlastMain no longer calls it.
     */
    public void movePlayer(int mouseX) {
        // WASD movement has replaced mouse movement.
        // This method is intentionally left empty but not deleted.
        // If called, it does nothing. The player now moves via handleMovement() in Player.java.
        // Keeping the method signature so existing code that calls it doesn't break.
    }
    
    /**
     * RESET PLAYER HEALTH METHOD
     * Restores player health to maximum
     */
    public void resetPlayerHealth() {
        if (player != null) {
            player.resetHealth();
        }
    }
    
    /**
     * IS PLAYER DESTROYED CHECK
     * 
     * @return true if player is destroyed (dead), false otherwise
     */
    public boolean isPlayerDestroyed() {
        return player != null && player.destroyed;
    }
    
    // ===== PLAYER SHOTS METHODS =====
    
    /**
     * GET SHOTS METHOD
     * 
     * @return List of active player shots
     */
    public List<Shot> getShots() {
        return shots;
    }
    
    /**
     * ADD SHOT METHOD
     * Adds a new player shot if under the maximum limit
     * 
     * @param shot The shot to add (created by Player.shoot())
     */
    public void addShot(Shot shot) {
        if (shot != null && shots.size() < MAX_SHOTS) {
            shots.add(shot);
        }
    }
    
    /**
     * UPDATE SHOTS METHOD
     * Updates all player shots and removes off-screen ones
     */
    public void updateShots() {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            // Remove if off screen (any edge) or marked for removal
            if (shot.posX + Shot.SIZE < 0 || shot.posX > WIDTH || 
                shot.posY + Shot.SIZE < 0 || shot.posY > HEIGHT || 
                shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
        }
    }
    
    /**
     * UPDATE SHOTS WITH ENEMY COLLISIONS METHOD
     * Updates player shots and checks for collisions with enemies
     * 
     * @param levelManager For registering defeated enemies to the current level
     */
    public void updateShotsWithEnemyCollisions(LevelManager levelManager) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            // Remove if off screen (any edge) or marked for removal
            if (shot.posX + Shot.SIZE < 0 || shot.posX > WIDTH || 
                shot.posY + Shot.SIZE < 0 || shot.posY > HEIGHT || 
                shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            // Check collision with each enemy
            for (Enemy enemy : enemies) {
                if (Collisions.shotCollides(shot, enemy) && !enemy.exploding) {
                    score++; // Increase score for defeating enemy
                    levelManager.getCurrentLevel().registerEnemyDefeated(enemy);
                    enemy.explode(); // Start explosion animation
                    if (gameRenderer != null) {
                        gameRenderer.playExplodeSound();
                    }
                    shot.toRemove = true;
                    break;
                }
            }
        }
    }
    
    /**
     * UPDATE SHOTS WITH BOSS COLLISIONS METHOD
     * Updates player shots and checks for collisions with boss
     * 
     * @param boss The current boss (any class extending Boss)
     */
    public void updateShotsWithBossCollisions(Boss boss) {
        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            shot.update();
            // Remove if off screen (any edge) or marked for removal
            if (shot.posX + Shot.SIZE < 0 || shot.posX > WIDTH || 
                shot.posY + Shot.SIZE < 0 || shot.posY > HEIGHT || 
                shot.toRemove) {
                shots.remove(i);
                continue;
            }
            // Check collision with boss
            if (Collisions.shotCollides(shot, boss) && !boss.exploding) {
                boss.takeDamage(10); // Each player shot does 10 damage to boss
                shots.remove(i);
            }
        }
    }
    
    /**
     * DRAW SHOTS METHOD
     * Draws all player shots on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawShots(GraphicsContext gc) {
        for (Shot shot : shots) {
            shot.draw(gc);
        }
    }
    
    // ===== ENEMY SHOTS METHODS =====
    // Currently customized in EnemyShot.java
    
    /**
     * GET ENEMY SHOTS METHOD
     * 
     * @return List of enemy projectiles
     */
    public List<Shot> getEnemyShots() {
        return enemyShots;
    }
    
    /**
     * UPDATE ENEMY SHOTS METHOD
     * Updates enemy shots and checks for player collisions
     */
    public void updateEnemyShots() {
        for (int i = enemyShots.size() - 1; i >= 0; i--) {
            Shot shot = enemyShots.get(i);
            shot.update();
            // Remove if off bottom of screen or marked for removal
            if (shot.posY > HEIGHT || shot.toRemove) {
                enemyShots.remove(i);
                continue;
            }
            // Check if shot hits player
            if (Collisions.shotCollides(shot, player) && !player.exploding) {
                player.takeDamage(5); // Player takes 5 damage from enemy shots
                enemyShots.remove(i); // Remove the shot after it hits
            }
        }
    }
    
    /**
     * DRAW ENEMY SHOTS METHOD
     * Draws all enemy shots on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawEnemyShots(GraphicsContext gc) {
        for (Shot shot : enemyShots) {
            shot.draw(gc);
        }
    }
    
    // ===== ENEMIES METHODS =====
    
    /**
     * GET ENEMIES METHOD
     * 
     * @return List of all active enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
    
    /**
     * ADD ENEMY METHOD
     * Adds a new enemy to the game world
     * 
     * @param enemy The enemy to add (Garlic, Pillbug, Squirrel, Urchin, etc.)
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * UPDATE ENEMIES METHOD
     * Updates all enemies (movement, state, etc.)
     */
    public void updateEnemies() {
        enemies.forEach(e -> e.update());
    }
    
    /**
     * DRAW ENEMIES METHOD
     * Draws all enemies on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawEnemies(GraphicsContext gc) {
        enemies.forEach(e -> e.draw(gc));
    }
    
    /**
     * CHECK ENEMY COLLISIONS METHOD
     * Checks for collisions between player and enemies
     * Pushes player away and applies damage on collision
     * 
     * @param stateManager For triggering GAME_OVER state if player dies
     */
    public void checkEnemyCollisions(GameStateManager stateManager) {
        for (Enemy e : enemies) {
            if (Collisions.playerCollides(player, e) && !player.exploding && !player.destroyed) {
                boolean stillAlive = player.takeDamage(5); // Enemy does 5 damage on contact
                if (!stillAlive) {
                    stateManager.setCurrentState(GameState.GAME_OVER);
                    return;
                }
                
                // Push player away from enemy (same knockback as boss)
                int enemyCenterX = e.posX + e.size / 2;
                int enemyCenterY = e.posY + e.size / 2;
                int playerCenterX = player.posX + player.size / 2;
                int playerCenterY = player.posY + player.size / 2;
                
                // Calculate push direction (away from enemy center)
                int pushX = playerCenterX - enemyCenterX;
                int pushY = playerCenterY - enemyCenterY;
                
                // Normalize direction to just the sign
                if (pushX > 0) pushX = 1;
                else if (pushX < 0) pushX = -1;
                else pushX = 0;
                
                if (pushY > 0) pushY = 1;
                else if (pushY < 0) pushY = -1;
                else pushY = 0;
                
                // Push player 80 pixels away from enemy
                int newX = player.posX + (pushX * 80);
                int newY = player.posY + (pushY * 80);
                
                // Apply boundary constraints (keep player on screen)
                if (newX < 0) newX = 0;
                if (newX + player.size > ImaginBlastMain.WIDTH) newX = ImaginBlastMain.WIDTH - player.size;
                if (newY < 0) newY = 0;
                if (newY + player.size > ImaginBlastMain.HEIGHT) newY = ImaginBlastMain.HEIGHT - player.size;
                
                player.posX = newX;
                player.posY = newY;
                
                break; // Only collide with one enemy per frame
            }
        }
    }
    
    /**
     * REPLACE DESTROYED ENEMIES METHOD
     * Replaces destroyed enemies with fresh ones
     * Used for infinite enemy spawning during normal gameplay
     * 
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
     * CLEAR ENEMIES METHOD
     * Removes all enemies from the game world
     */
    public void clearEnemies() {
        enemies.clear();
    }
    
    // ===== ITEMS METHODS =====
    
    /**
     * GET ITEMS METHOD
     * 
     * @return List of all active items
     */
    public List<Item> getItems() {
        return items;
    }
    
    /**
     * ADD ITEM METHOD
     * Adds a new item to the game world
     * 
     * @param item The item to add (acorn, donut, cupcake, cassette, bubble)
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * UPDATE ITEMS METHOD
     * Updates all items and checks for player collection
     * 
     * @param levelManager For registering collected items to the current level
     */
    public void updateItems(LevelManager levelManager) {
        items.forEach(i -> {
            i.update(null);
            if (Collisions.itemCollides(player, i) && !i.collected) {
                levelManager.getCurrentLevel().registerItemCollected(i);
                // Call specific item's collection effect based on type
                if (i instanceof ItemAcorn) {
                    ((ItemAcorn) i).setGameRenderer(gameRenderer); 
                    ((ItemAcorn) i).onCollected();
                }
                if (i instanceof ItemDonut) {
                    ((ItemDonut) i).setGameRenderer(gameRenderer); 
                    ((ItemDonut) i).onCollected();
                }
                if (i instanceof ItemCupcake) {
                    ((ItemCupcake) i).setGameRenderer(gameRenderer); 
                    ((ItemCupcake) i).onCollected();
                }
                if (i instanceof ItemCassette) {
                    ((ItemCassette) i).onCollected();
                }
                if (i instanceof ItemBubble) {
                    ((ItemBubble) i).setGameRenderer(gameRenderer); 
                    ((ItemBubble) i).onCollected();
                    player.resetHealth(); // Bubble gives full health restore
                }
                // Future: add else-if for other item types
            }
        });
    }
    
    /**
     * DRAW ITEMS METHOD
     * Draws all items on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawItems(GraphicsContext gc) {
        items.forEach(i -> i.draw(gc));
    }
    
    /**
     * REPLACE COLLECTED ITEMS METHOD
     * Replaces collected items with fresh ones
     * Used for infinite item spawning during normal gameplay
     * 
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
     * CLEAR ITEMS METHOD
     * Removes all items from the game world
     */
    public void clearItems() {
        items.clear();
    }
    
    // ===== PARTICLES METHODS =====
    
    /**
     * UPDATE PARTICLES METHOD
     * Creates new particle effects and removes off-screen ones
     * 
     * @param gc Graphics context for drawing (passed to new Particles)
     */
    public void updateParticles(GraphicsContext gc) {
        // Randomly create new particles (about 80% of frames)
        if (RAND.nextInt(10) > 2) {
            particles.add(new Particles(gc));
        }
        // Remove particles that have floated off screen
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isOffScreen()) {
                particles.remove(i);
                i--;
            }
        }
    }
    
    /**
     * DRAW PARTICLES METHOD
     * Draws all particle effects on screen
     * 
     * @param gc Graphics context for drawing to the canvas
     */
    public void drawParticles(GraphicsContext gc) {
        particles.forEach(Particles::draw);
    }
    
    // ===== SCORE METHODS =====
    
    /**
     * GET SCORE METHOD
     * 
     * @return Current player score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * RESET SCORE METHOD
     * Resets player score to zero
     */
    public void resetScore() {
        score = 0;
    }
    
    /**
     * INCREMENT SCORE METHOD
     * Increases player score by 1 (called when enemy is defeated)
     */
    public void incrementScore() {
        score++;
    }
    
    /**
     * RESET ALL METHOD
     * Reset all entities and score for a brand new game
     */
    public void resetAll() {
        initialize();
    }
}