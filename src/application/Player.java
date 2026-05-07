package application;

// "Mine is a long and a sad tale!" said the Mouse, 
// turning to Alice, and sighing. 
// ~ The Mouse, Alice's Adventures in Wonderland

import javafx.scene.image.ImageView;

/**
 * PLAYER CLASS
 * Represents the player character in the game (the frog)
 * Extends Creature to inherit basic creature properties and behaviors
 * Adds player-specific attributes like health, invincibility, and item collection
 * 
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 * 
 * Source(s) for WASD and Mouse Aiming:
 * Help with Math for WASD - https://gamedev.stackexchange.com/questions/122374
 * Help with Mouse Aiming - Getting coordinates relative to the canvas
 * Calculation for the angle and vector from player to mouse
 * Division by zero issues - https://stackoverflow.com/questions/42806538
 * Math without LibGDX - https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
 */
public class Player extends Creature {
    
    // PLAYER STATS
    public int hp = 100;            // Player current health points
    public int maxHp = 100;         // Maximum possible health points
    public int col_items = 0;       // Counter for collected items (power-ups, points, etc.)
                                    // TODO: Rename to collectedItems for clarity
    
    // INVINCIBILITY PROPERTIES
    private int invincibilityFrames = 0;              // Frames remaining of invincibility after taking damage
    private static final int INVINCIBILITY_DURATION = 10; // How many frames invincibility lasts
    private boolean isShielded = false;               // Shielded status (prevents damage)
    
    // WASD MOVEMENT PROPERTIES
    private int speed = 24;              // Movement speed in pixels per frame
    private InputHandler inputHandler;   // Handles keyboard input for WASD movement
    private GameRenderer gameRenderer;   // Reference for playing sound effects
    
    // SHOOTING PROPERTIES
    private static final int BULLET_SPEED = 28; // Speed of fired projectiles (pixels per frame)
    
    /**
     * PLAYER CONSTRUCTOR
     * Creates a new player at the specified position with given size and image
     * 
     * @param posX Initial X coordinate (typically center of screen)
     * @param posY Initial Y coordinate (near bottom of screen)
     * @param size Size of the player sprite (width and height, square)
     * @param imageView The player's ImageView (the frog) - Note: We should name him.
     */
    public Player(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    /**
     * TAKE DAMAGE METHOD
     * Reduces player health when hit by enemies, boss, or projectiles
     * Invincibility frames prevent rapid consecutive damage
     * 
     * @param amount Amount of damage to inflict
     * @return true if player still alive, false if dead
     */
    public boolean takeDamage(int amount) {
        if (invincibilityFrames > 0 || isShielded) {
            return true; // Still alive, no damage taken
        }
        
        hp -= amount;
        if (gameRenderer != null) {
            gameRenderer.playPlayerDamageSound();
        }
        
        if (hp <= 0) {
            hp = 0;
            explode(); // Start death explosion animation
            return false; // Player is dead
        }
        
        invincibilityFrames = INVINCIBILITY_DURATION;
        return true; // Still alive
    }
    
    /**
     * UPDATE INVINCIBILITY METHOD
     * Decrements invincibility frames counter each frame
     * Called every frame from update()
     */
    public void updateInvincibility() {
        if (invincibilityFrames > 0) {
            invincibilityFrames--;
        }
    }
    
    /**
     * HEAL METHOD
     * Restores player health by the specified amount
     * Cannot exceed maximum health
     * 
     * @param amount Amount of health to restore
     */
    public void heal(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }
    
    /**
     * INCREASE MAX HEALTH METHOD
     * Permanently increases maximum health (for power-ups)
     * Also heals player by the same amount
     * 
     * @param amount Amount to increase max health by
     */
    public void increaseMaxHp(int amount) {
        maxHp += amount;
        hp += amount; // Also heal by same amount
    }
    
    /**
     * RESET HEALTH METHOD
     * Restores health to maximum for new game
     */
    public void resetHealth() {
        hp = maxHp;
    }
    
    /**
     * SET INPUT HANDLER METHOD
     * Called by EntityManager after player creation
     * Connects keyboard input handler for WASD movement
     * 
     * @param handler The InputHandler instance
     */
    public void setInputHandler(InputHandler handler) {
        this.inputHandler = handler;
    }
    
    /**
     * SET GAME RENDERER METHOD
     * Connects game renderer for playing sound effects
     * 
     * @param renderer The GameRenderer instance
     */
    public void setGameRenderer(GameRenderer renderer) {
        this.gameRenderer = renderer;
    }
    
    /**
     * UPDATE METHOD
     * Called every frame to update player state
     * Overrides Creature.update() to add invincibility countdown and WASD movement
     */
    @Override
    public void update() {
        super.update(); // Call Creature's update (handles explosion animation)
        updateInvincibility(); // Count down invincibility frames
        handleMovement(); // Handle WASD key movement
    }
    
    /**
     * HANDLE MOVEMENT METHOD
     * Reads input handler for WASD keys
     * Moves player in the corresponding direction each frame
     * Sprint key ('F') increases movement speed
     * 
     * Source: https://gamedev.stackexchange.com/questions/122374
     * Note: 'Shift' key wouldn't work (Oracle, 2015)
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
     */
    private void handleMovement() {
        // Skip movement if player is exploding
        if (exploding) return;
        
        // Store original position for boundary checking
        int newX = posX;
        int newY = posY;
        
        // Apply movement based on active keys
        if (inputHandler != null) {
            if (inputHandler.isUpPressed())    newY -= speed;
            if (inputHandler.isDownPressed())  newY += speed;
            if (inputHandler.isLeftPressed())  newX -= speed;
            if (inputHandler.isRightPressed()) newX += speed;
            
            // Sprint movement based on 'F' key
            if (inputHandler.isFPressed()) {
                speed = 30;
            } else {
                speed = 18;
            }
        }
        
        // Apply boundary constraints (keep player within screen)
        if (newX < 0) newX = 0;
        if (newX + size > ImaginBlastMain.WIDTH) newX = ImaginBlastMain.WIDTH - size;
        if (newY < 0) newY = 0;
        if (newY + size > ImaginBlastMain.HEIGHT) newY = ImaginBlastMain.HEIGHT - size;
        
        // Update position
        posX = newX;
        posY = newY;
    }
    
    /**
     * CALCULATE DIRECTION VECTOR METHOD
     * Computes normalized direction from player center to target (mouse cursor)
     * Used for aiming shots
     * 
     * Source: Vector math from https://gamedev.stackexchange.com/questions/122374
     * 
     * @param targetX Target X coordinate (mouse cursor)
     * @param targetY Target Y coordinate (mouse cursor)
     * @return double array where [0] = normalized X direction, [1] = normalized Y direction
     */
    private double[] calculateDirection(double targetX, double targetY) {
        // Get player center coordinates (where shot originates)
        double fromX = posX + size / 2;
        double fromY = posY + size / 2;
        
        // Calculate difference between target and shooter
        double dx = targetX - fromX;
        double dy = targetY - fromY;
        
        // Calculate distance (length of the vector)
        double length = Math.sqrt(dx * dx + dy * dy);
        
        // Normalize (avoid division by zero)
        if (length != 0) {
            dx /= length;
            dy /= length;
        } else {
            // If target is exactly at player center, shoot upward as default
            dx = 0;
            dy = -1;
        }
        
        return new double[]{dx, dy};
    }
    
    /**
     * SHOOT METHOD
     * Creates a projectile fired by the player
     * Overrides Creature.shoot() to customize shot position and aiming
     * Shoots toward mouse cursor position instead of straight up
     * 
     * @return A new Shot object positioned at the player's center, aimed at cursor
     */
    @Override
    public Shot shoot() {
        // Get mouse position from input handler (for aiming)
        double mouseX = 0;
        double mouseY = 0;
        if (inputHandler != null) {
            mouseX = inputHandler.getMouseX();
            mouseY = inputHandler.getMouseY();
        }
        
        // Calculate direction from player center to mouse cursor
        double[] direction = calculateDirection(mouseX, mouseY);
        double velX = direction[0] * BULLET_SPEED;
        double velY = direction[1] * BULLET_SPEED;
        
        // Calculate shot starting position (center of player)
        int shotX = posX + size / 2 - Shot.SIZE / 2;
        int shotY = posY + size / 2 - Shot.SIZE / 2;
        
        // Create and return a new directional shot
        return new ShotStandard(shotX, shotY, velX, velY);
    }
}