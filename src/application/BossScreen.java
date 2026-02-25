package application;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

/**
 * ABSTRACT BOSS SCREEN
 * Manages the boss fight arena and all its components
 * Each boss type will have its own screen implementation
 * Handles the boss instance, exit portal, and fight progression
 */
public abstract class BossScreen {
    
    // Core boss fight components
    protected Boss boss;           // The boss entity currently being fought
    protected Portal portal;        // Exit portal that appears after boss defeat
    protected boolean levelComplete; // Is boss fight completed?
    protected boolean portalVisible; // Should portal should be drawn on screen?

    
    /**
     * ABSTRACT UPDATE
     * Updates boss behavior, checks collisions, and manages portal spawning
     * Each boss screen implements its own specific update logic
     * 
     * @param player The player entity for tracking and collision
     * @param playerShots List of player's projectiles for boss collision
     * @param enemyShots List of enemy projectiles for adding new shots
     */
    public abstract void update(Player player, List<Shot> playerShots, List<Shot> enemyShots);
    
    /**
     * ABSTRACT DRAW
     * Renders all boss fight elements to the screen
     * Each boss screen implements its own layout
     * 
     * @param gc Graphics context for drawing
     * @param renderer Game renderer for HUD and common elements
     * @param player The player entity to draw at correct position
     * @param score Current player score displayed during fight
     */
    public abstract void draw(GraphicsContext gc, GameRenderer renderer, Player player, int score);
    
    /**
     * ABSTRACT COMPLETION CHECK
     *  
     * @return true if boss fight is complete, false otherwise
     */
    public abstract boolean isComplete();
}