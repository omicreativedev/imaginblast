package application;

import javafx.scene.paint.Color;

/* 
 * ACKNOWLEDGEMENTS AND SOURCES:
 * Much of this code was started from this tutorial, and this github page:
 * Gaspared. (2019, November 29). Gaspared/space-invaders: A simple space invaders game in javafx. 
 * for more information visit my YouTube channel. GitHub. https://github.com/Gaspared/Space-Invaders 
 * Tutorial Part 1: https://www.youtube.com/watch?v=0szmaHH1hno
 * Tutorial Part 2: https://www.youtube.com/watch?v=dzcQgv9hqXI&t=87s
 * ---
 * Media Player: https://blog.idrsolutions.com/write-media-player-javafx-using-netbeans-ide-part-2/
 * ---
 * Comments assisted with Gemini Prompt: Please reformat this code neatly. Don't remove any existing 
 * comments. But clean up the formatting and be sure to include comments where some are missing.
 * Each block should be commented at the top. And each line should have a very short comment. 
 * Follow my own comment format. Do not change my code!!! DO NOT DELETE MY COMMENTS!
 * ---
 * Code comparison tool: https://www.diffchecker.com/
 * ---
 * Image resources for game elements
 * Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/image/Image.html
 */

// "Beware the Jabberwock, my son! The jaws that bite, 
// the claws that catch!" 
// ~ Jabberwocky, Through the Looking-Glass

import java.util.Random;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * MAIN GAME CLASS
 * Entry point for the entire game application
 * Sets up game window, input handlers, game loop, and manages game states
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class ImaginBlastMain extends Application {
    
    // Music state tracking
    private boolean bossMusicStarted = false; // Track if boss music has already started playing
    
    // Constants and global variables
    private static final Random RAND = new Random(); // Random generator for spawning
    public static final int WIDTH = 1280; // Game window width (pixels)
    public static final int HEIGHT = 720; // Game window height (pixels)
    private static final int PLAYER_SIZE = 60; // Player sprite size (square)
    
    // Player images
    static final ImageView PLAYER_IMG = new ImageView(new Image("player_frog_walk.gif"));
    
    // Enemy images
    static final ImageView PILLBUG_IMG = new ImageView(new Image("enemy_pillbug.png"));
    static final ImageView SQUIRREL_IMG = new ImageView(new Image("enemy_squirrel_walk.gif"));
    static final ImageView GARLIC_IMG = new ImageView(new Image("enemy_garlic.png"));
    
    // Effect and item images
    static final Image EXPLOSION_IMG = new Image("explosion.png");
    static final ImageView ACORN_IMG = new ImageView(new Image("item_acorn.png"));
    static final ImageView DONUT_IMG = new ImageView(new Image("item_donut.png"));
    static final ImageView CUPCAKE_IMG = new ImageView(new Image("item_cupcake.png"));
    static final ImageView URCHIN_IMG = new ImageView(new Image("item_urchin.png"));
    static final ImageView CASSETTE_IMG = new ImageView(new Image("item_cassette.png"));
    static final ImageView BUBBLE_IMG = new ImageView(new Image("item_bubble.png"));
    
    // Explosion animation properties
    static final int EXPLOSION_W = 128; // Width of explosion sprite (pixels)
    static final int EXPLOSION_ROWS = 3; // Number of rows in explosion sprite sheet
    static final int EXPLOSION_COL = 3; // Number of columns in explosion sprite sheet
    static final int EXPLOSION_H = 128; // Height of explosion sprite (pixels)
    static final int EXPLOSION_STEPS = 15; // Total number of frames in explosion animation
    
    // Game balance constants
    final int MAX_BOMBS = 10; // Maximum number of enemies on screen at once
    final int MAX_SHOTS = MAX_BOMBS * 2; // Maximum number of player shots allowed
    final int MAX_ITEMS = 6; // Maximum number of items on screen at once
    
    // Graphics context (shared across methods)
    public GraphicsContext gc;
    
    // Game system managers
    GameStateManager stateManager; // Tracks current game state (START, PLAYING, BOSS, etc.)
    EntityManager entityManager; // Manages all game entities (player, enemies, shots, items)
    GameRenderer gameRenderer; // Handles all drawing operations
    UIManager uiManager; // Manages UI screens (start, quest, level done, end)
    boolean questConfirmed = false; // Flag for quest screen confirmation
    LevelManager levelManager; // Manages level progression and content
    InputHandler inputHandler; // Handles keyboard and mouse input
    
    /**
     * START METHOD
     * Set up the game window, canvas, input handlers, and animation timeline
     * Called automatically when the JavaFX application launches
     * 
     * Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/Canvas.html
     * 
     * @param stage The primary stage window for the application
     */
    public void start(Stage stage) throws Exception {
        
        // Drawing canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT); // Create canvas with game dimensions
        gc = canvas.getGraphicsContext2D(); // Get graphics context for drawing
        gameRenderer = new GameRenderer(gc); // Initialize renderer with graphics context

        // Initialize game systems
        stateManager = new GameStateManager();
        levelManager = new LevelManager();
        entityManager = new EntityManager(MAX_SHOTS, WIDTH, HEIGHT, MAX_BOMBS, MAX_ITEMS);
        uiManager = new UIManager(gameRenderer);
        setup(); // Initial entity setup
        inputHandler = new InputHandler(stateManager, levelManager, entityManager, gameRenderer, uiManager, MAX_SHOTS, WIDTH, HEIGHT);
        
        // Connect input handler to entity manager so player can access key states
        entityManager.setInputHandler(inputHandler);
        entityManager.setGameRenderer(gameRenderer);
        
        // KEYBOARD INPUT for WASD movement
        // Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
        canvas.setFocusTraversable(true); // Make canvas focusable to receive key events
        canvas.setOnKeyPressed(e -> inputHandler.handleKeyPressed(e)); // Handle key down events
        canvas.setOnKeyReleased(e -> inputHandler.handleKeyReleased(e)); // Handle key up events
        
        // MOUSE INPUT for aiming and shooting
        // Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/MouseEvent.html
        canvas.setOnMouseMoved(e -> inputHandler.handleMouseMoved(e.getX(), e.getY())); // Track mouse for aiming only
        
        canvas.setOnMouseClicked(e -> { // Handle mouse clicks for shooting and UI
            inputHandler.handleMouseClicked(e, this::setup, this::resetGame); // Process click with setup callback
        });
        
        // Set up game loop animation (100ms intervals = 10 fps)
        // Create animation timeline
        // Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/animation/Timeline.html
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), _ -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE); // Loop forever
        timeline.play(); // Start the animation

        stage.setScene(new Scene(new StackPane(canvas))); // Add canvas to scene
        stage.setTitle("ImaginBlast"); // Set window title
        stage.show(); // Display the window
    }
    
    /**
     * RESET GAME METHOD
     * Completely resets the game to Level 1 for a fresh start
     */
    private void resetGame() {
        levelManager.reset();
        setup();
        bossMusicStarted = false;
    }
    
    /**
     * SETUP METHOD
     * Create new collections and place initial enemies and items
     * Called when starting a new game or transitioning between levels
     */
    private void setup() {
        entityManager.resetAll();
        
        // Create the player character
        Player player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        player.resetHealth();
        entityManager.setPlayer(player);
        
        // Only create enemies and items if there is a current level (not final boss)
        if (levelManager.getCurrentLevel() != null) {
            for (int i = 0; i < MAX_BOMBS; i++) {
                entityManager.addEnemy(createEnemyForCurrentLevel());
            }
            
            for (int i = 0; i < MAX_ITEMS; i++) {
                entityManager.addItem(createItemForCurrentLevel());
            }
        }
    }
    
    /**
     * RUN METHOD
     * Main game loop (called every frame)
     * Updates game objects, checks collisions, and renders everything
     * 
     * Reference: https://gameprogrammingpatterns.com/state.html
     * 
     * @param gc Graphics context for drawing
     */
    private void run(GraphicsContext gc) {
        
        switch (stateManager.getCurrentState()) {
            case START_SCREEN:
                gameRenderer.updateButtonHover(inputHandler.getMouseX(), inputHandler.getMouseY());
                uiManager.drawStartScreen();
                break;
                
            case QUEST_SCREEN:
                gameRenderer.playQuestMusic();
                uiManager.drawQuestScreen(levelManager.getQuest());
                break;
                
            case PLAYING:
                gameRenderer.stopQuestMusic();
                
                // Draw background image for current level
                if (levelManager.getCurrentLevel() != null) {
                    gc.drawImage(levelManager.getCurrentLevel().getBackground(), 0, 0, WIDTH, HEIGHT);
                } else {
                    // Fallback background for when there's no level (shouldn't happen in PLAYING state)
                    gc.setFill(Color.GREEN);
                    gc.fillRect(0, 0, WIDTH, HEIGHT);
                }
                
                // Draw the HUD (health, score, level, etc.)
                gameRenderer.drawHUD(entityManager.getScore(), entityManager.getShots().size(), MAX_SHOTS, levelManager.getCurrentLevel(), entityManager.getPlayer());
                
                // Draw background particle effects
                entityManager.drawParticles(gc); 
                entityManager.updateParticles(gc);
            
                // Update and draw player (WASD movement now handled inside player.update())
                entityManager.updatePlayer(); // Update player state (includes movement)
                entityManager.drawPlayer(gc); // Draw player
                
                // Update and draw enemies
                entityManager.updateEnemies(); // Update enemy states
                entityManager.drawEnemies(gc); // Draw enemies
                entityManager.checkEnemyCollisions(stateManager); // Check enemy-player collisions
                
                // Update and draw items
                entityManager.drawItems(gc);
                entityManager.updateItems(levelManager);
                
                // Update and check player shots
                entityManager.updateShotsWithEnemyCollisions(levelManager); // Update shots and check enemy collisions
                entityManager.drawShots(gc); // Draw player shots
                
                // Replace destroyed enemies with new ones (infinite spawning)
                entityManager.replaceDestroyedEnemies(() -> createEnemyForCurrentLevel());
                
                // Replace collected items with new ones (infinite spawning)
                entityManager.replaceCollectedItems(() -> createItemForCurrentLevel());
            
                // Check game over condition
                if (entityManager.isPlayerDestroyed()) {
                    stateManager.setCurrentState(GameState.GAME_OVER);
                }
                
                // Check level completion
                if (levelManager.getCurrentLevel().isComplete()) {
                    stateManager.setCurrentState(GameState.BOSS_FIGHT);
                    // Clear existing enemies and items for boss fight
                    entityManager.clearEnemies();
                    entityManager.clearItems();
                    entityManager.getShots().clear();
                }
                break;
                
            case BOSS_FIGHT:
                gameRenderer.stopGameplayMusic(); // Stop gameplay music when boss fight starts
                if (!bossMusicStarted) { // Only play boss music once
                    gameRenderer.playBossMusic();
                    bossMusicStarted = true;
                }
                // Update player movement (WASD handled inside player.update)
                entityManager.updatePlayer(); // Update player state (includes movement)
                
                // Update and check player shots against boss
                entityManager.updateShotsWithBossCollisions(levelManager.getBossScreen().boss);
                
                // Update boss screen (boss movement, shooting, etc.)
                levelManager.getBossScreen().update(entityManager.getPlayer(), entityManager.getShots(), entityManager.getEnemyShots());
                
                // Draw boss screen elements (background, boss, health bars, portal, etc.)
                levelManager.getBossScreen().draw(gc, gameRenderer, entityManager.getPlayer(), entityManager.getScore());
                
                // Draw player shots
                entityManager.drawShots(gc);
                
                // Update and draw enemy shots (from boss)
                entityManager.updateEnemyShots();
                entityManager.drawEnemyShots(gc);
                
                // Check if boss is defeated and not already recorded
                if (levelManager.getBossScreen().boss.isDefeated() && !levelManager.isBossDefeated()) {
                    levelManager.setBossDefeated(true);
                }
                
                // Check if player entered the portal (level complete)
                if (levelManager.getBossScreen().isComplete()) {
                    // Check if this is the final boss (no current level)
                    if (levelManager.getCurrentLevel() == null) {
                        gameRenderer.stopQuestMusic(); 
                        stateManager.setCurrentState(GameState.END_SCREEN);
                    } else {
                        stateManager.setCurrentState(GameState.LEVEL_DONE);
                    }
                    gameRenderer.stopBossMusic();
                    bossMusicStarted = false;
                }
                
                // Check if player died during boss fight
                if (entityManager.isPlayerDestroyed()) {
                    stateManager.setCurrentState(GameState.GAME_OVER);
                    gameRenderer.stopBossMusic();
                    bossMusicStarted = false;
                }
                break;
                
            case LEVEL_DONE:
                uiManager.drawLevelDoneScreen(levelManager.getLevelDoneScreen());
                break;
                
            case END_SCREEN:
                gameRenderer.stopBossMusic();
                gameRenderer.stopGameplayMusic();
                gameRenderer.stopQuestMusic();
                uiManager.drawEndScreen(0, true); // true = game won
                break;
                
            case GAME_OVER:
                gameRenderer.stopGameplayMusic();
                gameRenderer.stopQuestMusic();
                gameRenderer.stopBossMusic();
                uiManager.drawGameOverScreen(entityManager.getScore());
                break;
        }
    }
    
    /**
     * CREATE ENEMY FOR CURRENT LEVEL METHOD
     * Creates a random enemy based on the current level's allowed enemy types
     * 
     * This function is called whenever a new enemy needs to be spawned in the game,
     * such as during initial level setup or when replacing destroyed enemies.
     * 
     * Process:
     * 1. Asking the current level what enemy types are allowed to appear
     * 2. Randomly selecting one of those enemy types
     * 3. Delegating the actual creation to the level (since different levels might
     *    want to create the same enemy type with different properties)
     * 
     * This allows each level to have its own "enemy pool"
     * Level 1 might only have squirrels, while Level 2 might have squirrels AND pillbugs
     * 
     * Reference: https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#nextInt-int-
     * 
     * @return A new Enemy instance of a randomly chosen type valid for the current level
     */
    Enemy createEnemyForCurrentLevel() {
        // Get list of possible enemies from current level
        List<Class<? extends Enemy>> possibleEnemies = levelManager.getCurrentLevel().getPossibleEnemies();
        // Pick a random enemy type from the list
        Class<? extends Enemy> randomEnemy = possibleEnemies.get(RAND.nextInt(possibleEnemies.size()));
        // Delegate creation to the level (so level can customize if needed)
        return levelManager.getCurrentLevel().createEnemy(RAND, WIDTH, PLAYER_SIZE, randomEnemy);
    }
    
    /**
     * CREATE ITEM FOR CURRENT LEVEL METHOD
     * Works the same as createEnemyForCurrentLevel, but for items instead
     * Creates a random item based on the current level's allowed item types
     * 
     * @return A new Item instance of a randomly chosen type valid for the current level
     */
    Item createItemForCurrentLevel() {
        // Get list of possible items from current level
        List<Class<? extends Item>> possibleItems = levelManager.getCurrentLevel().getPossibleItems();
        // Pick a random item type from the list
        Class<? extends Item> randomItem = possibleItems.get(RAND.nextInt(possibleItems.size()));
        // Delegate creation to the level (so level can customize if needed)
        return levelManager.getCurrentLevel().createItem(RAND, WIDTH, PLAYER_SIZE, randomItem);
    }
    
    /**
     * MAIN METHOD
     * Application entry point - launches the JavaFX application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        launch();
    }
}