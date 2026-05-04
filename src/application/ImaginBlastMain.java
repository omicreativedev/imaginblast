package application;

import javafx.scene.paint.Color;

// Hello this is a test to see if pushing works or sinister shenanigans ensue -EV

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

import java.util.Random;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; //new
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

// "How puzzling all these changes are!
// I'm never sure what I'm going to be, from one minute to another."
// ~ Mad Hatter, Alice's Adventures in Wonderland

/**
 * MAIN GAME CLASS
 */
public class ImaginBlastMain extends Application {
	
	private boolean bossMusicStarted = false; //new - Track if boss music already playing
	
	// Constants and global variables
	private static final Random RAND = new Random(); // Random generator
	public static final int WIDTH = 1280; // Game window width
	public static final int HEIGHT = 720; // Game window height
	private static final int PLAYER_SIZE = 60;
	
	//Player images
	static final ImageView PLAYER_IMG = new ImageView(new Image("player_frog_static.png"));
	//Enemy images
	static final ImageView PILLBUG_IMG = new ImageView(new Image("enemy_pillbug.png"));
	static final ImageView SQUIRREL_IMG = new ImageView(new Image("enemy_squirrel.png"));
	static final ImageView GARLIC_IMG = new ImageView(new Image("enemy_garlic.png"));
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final ImageView ACORN_IMG = new ImageView(new Image("item_acorn.png"));
	static final ImageView DONUT_IMG = new ImageView(new Image("item_donut.png"));
	static final ImageView CUPCAKE_IMG = new ImageView(new Image("item_cupcake.png"));
	static final ImageView URCHIN_IMG = new ImageView(new Image("item_urchin.png"));
	static final ImageView CASSETTE_IMG = new ImageView(new Image("item_cassette.png"));
	
	// Explosion animation properties
	static final int EXPLOSION_W = 128; // Width of explosion sprite
	static final int EXPLOSION_ROWS = 3; // Rows in explosion sprite sheet
	static final int EXPLOSION_COL = 3; // Columns in explosion sprite sheet
	static final int EXPLOSION_H = 128; // Height of explosion sprite
	static final int EXPLOSION_STEPS = 15; // Number of steps in explosion animation
	
	// Game balance constants
	final int MAX_BOMBS = 10; // Maximum number of enemies
	final int MAX_SHOTS = MAX_BOMBS * 2; // Maximum number of player shots allowed
	final int MAX_ITEMS = 6; // Maximum number of items
	
	public GraphicsContext gc;
	
	/**
	 * GAME STATE MANAGEMENT
	 * Moved to GameState.java and GameStateManager.java
	 */
	GameStateManager stateManager;
	
	// Game objects collections
	EntityManager entityManager; // Manages game entities 
	GameRenderer gameRenderer; // Draws game
	UIManager uiManager; // What screen are we on?
	boolean questConfirmed = false; // Has player read the quest?
	LevelManager levelManager; // Manages levels
	InputHandler inputHandler; // Handles user input

	/**
	 * START METHOD
	 * Set up the game window, canvas, input handlers, and animation timeline
	 */
	public void start(Stage stage) throws Exception {
		
		// Drawing canvas
		// Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/Canvas.html
		Canvas canvas = new Canvas(WIDTH, HEIGHT); // Create canvas with game dimensions
		gc = canvas.getGraphicsContext2D(); // Get graphics context for drawing
		gameRenderer = new GameRenderer(gc); // Initialize renderer with graphics context

		// Initializations
		stateManager = new GameStateManager();
		levelManager = new LevelManager();
		entityManager = new EntityManager(MAX_SHOTS, WIDTH, HEIGHT, MAX_BOMBS, MAX_ITEMS);
		uiManager = new UIManager(gameRenderer);
		setup();
		inputHandler = new InputHandler(stateManager, levelManager, entityManager, gameRenderer, uiManager, MAX_SHOTS, WIDTH, HEIGHT);
		
		// Connect input handler to entity manager so player can access key states
		entityManager.setInputHandler(inputHandler);
		entityManager.setGameRenderer(gameRenderer);
		
		// KEYBOARD INPUT for WASD movement
		// Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
		canvas.setFocusTraversable(true); // Make canvas focusable to receive key events
		canvas.setOnKeyPressed(e -> inputHandler.handleKeyPressed(e)); // Handle key down
		canvas.setOnKeyReleased(e -> inputHandler.handleKeyReleased(e)); // Handle key up
		
		// MOUSE INPUT for aiming and shooting/scene/input/MouseEvent.html
		canvas.setOnMouseMoved(e -> inputHandler.handleMouseMoved(e.getX(), e.getY())); // Track mouse for aiming only
	    
	    canvas.setOnMouseClicked(e -> { // Handle mouse clicks for shooting and UI
	        inputHandler.handleMouseClicked(e, this::setup, this::resetGame); // Process click callback to setup
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
	 * Create new collections and place initial enemies
	 */
	private void setup() {
	    entityManager.resetAll();
	    
	    Player player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
	    player.resetHealth();
	    entityManager.setPlayer(player);
	    
	    for (int i = 0; i < MAX_BOMBS; i++) {
	        entityManager.addEnemy(createEnemyForCurrentLevel());
	    }
	    
	    for (int i = 0; i < MAX_ITEMS; i++) {
	        entityManager.addItem(createItemForCurrentLevel());
	    }
	}

	/**
	 * RUN METHOD
	 * Main game loop (called every frame)
	 * Update game objects, check collisions, and render everything
	 */
	private void run(GraphicsContext gc) {
	    
		// Reference: https://gameprogrammingpatterns.com/state.html
	    switch(stateManager.getCurrentState()) {
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


	        	if (levelManager.getCurrentLevel() != null) {
	        	    gc.drawImage(levelManager.getCurrentLevel().getBackground(), 0, 0, WIDTH, HEIGHT);
	        	} else {
	        	    // Fallback background for when there's no level (shouldn't happen in PLAYING state)
	        	    gc.setFill(Color.GREEN);
	        	    gc.fillRect(0, 0, WIDTH, HEIGHT);
	        	}
	        	
	            gameRenderer.drawHUD(entityManager.getScore(), entityManager.getShots().size(), MAX_SHOTS, levelManager.getCurrentLevel(), entityManager.getPlayer());
	            
	            // Draw background effects
	            entityManager.drawParticles(gc); 
	            entityManager.updateParticles(gc);
	        
	            // Update and draw player (WASD movement now handled inside player.update())
	            entityManager.updatePlayer(); // Update player state (includes movement)
	            entityManager.drawPlayer(gc); // Draw player
	            
	            // Update and draw enemies
	            entityManager.updateEnemies(); // Update enemy states
	            entityManager.drawEnemies(gc); // Draw enemies
	            entityManager.checkEnemyCollisions(stateManager); // Check enemy collisions
	            
	            // Update and draw items
	            entityManager.drawItems(gc);
	            entityManager.updateItems(levelManager);
	            
	            // Update and check shots
	            entityManager.updateShotsWithEnemyCollisions(levelManager); // Update shots and check enemy collisions
	            entityManager.drawShots(gc); // Draw player shots
	            
	            // Replace destroyed enemies
	            entityManager.replaceDestroyedEnemies(() -> createEnemyForCurrentLevel());
	            
	            // Replace collected items
	            entityManager.replaceCollectedItems(() -> createItemForCurrentLevel());
	        
	            // Check game over condition
	            if(entityManager.isPlayerDestroyed()) { // If player is destroyed
	                stateManager.setCurrentState(GameState.GAME_OVER); // Set game over state
	            }
	            
	            // Check level completion
	            if(levelManager.getCurrentLevel().isComplete()) { // If current level is complete
	                stateManager.setCurrentState(GameState.BOSS_FIGHT); // Set boss fight state
	                // Clear existing enemies and items for boss fight
	                entityManager.clearEnemies(); // Clear all enemies
	                entityManager.clearItems(); // Clear all items
	                entityManager.getShots().clear(); // Clear all shots
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

	            // Update and check player shots
	        	entityManager.updateShotsWithBossCollisions(levelManager.getBossScreen().boss);

	            // Update boss screen
	            levelManager.getBossScreen().update(entityManager.getPlayer(), entityManager.getShots(), entityManager.getEnemyShots());

	            // Draw boss screen stuff
	            levelManager.getBossScreen().draw(gc, gameRenderer, entityManager.getPlayer(), entityManager.getScore());

	            // Draw player shots
	            entityManager.drawShots(gc); // Draw player shots

	            // Update and draw enemy shots
	            entityManager.updateEnemyShots(); // Update enemy shots
	            entityManager.drawEnemyShots(gc); // Draw enemy shots

	            // Check if boss is defeated
	            if (levelManager.getBossScreen().boss.isDefeated() && !levelManager.isBossDefeated()) { // If boss defeated and not already recorded
	            	levelManager.setBossDefeated(true); // Set boss defeated
	            }

	            // Check if player entered portal
	            if (levelManager.getBossScreen().isComplete()) {
	                // Check if this is the final boss (no current level)
	                if (levelManager.getCurrentLevel() == null) {
	                    stateManager.setCurrentState(GameState.END_SCREEN);
	                } else {
	                    stateManager.setCurrentState(GameState.LEVEL_DONE);
	                }
	                gameRenderer.stopBossMusic();
	                bossMusicStarted = false;
	            }

	            // Check if player died
	            if (entityManager.isPlayerDestroyed()) { // If player is destroyed
	                stateManager.setCurrentState(GameState.GAME_OVER); // Set game over state
	                gameRenderer.stopBossMusic(); //new - Stop boss music if player dies
	                bossMusicStarted = false;
	            }
	            break;
	            
	        case LEVEL_DONE:
	        	uiManager.drawLevelDoneScreen(levelManager.getLevelDoneScreen());
	            break;
	            
	        case END_SCREEN:
	            uiManager.drawEndScreen(entityManager.getScore(), true); // true = game won
	            break;
	            
	        case GAME_OVER:
	        	gameRenderer.stopGameplayMusic(); //new - Stop gameplay music when game over
	        	gameRenderer.stopQuestMusic(); //new - Stop quest music if playing
	        	gameRenderer.stopBossMusic(); //new - Stop boss music if playing
	        	uiManager.drawGameOverScreen(entityManager.getScore());
	            break;
	            
	          
	    }
	}

	/**
	 * CREATES A RANDOM ENEMY FOR THE CURRENT LEVEL
	 * 
	 * This function is called whenever a new enemy needs to be spawned in the game,
	 * such as during initial level setup or when replacing destroyed enemies.
	 * 
	 * Asking the current level what enemy types are allowed to appear (can be many!)
	 * Randomly selecting one of those enemy types
	 * Delegating the actual creation to the level (since different levels might
	 *    want to create the same enemy type with different properties)
	 * 
	 * This allows each level to have its own "enemy pool"
	 * Level 1 might only have squirrels, while Level 2 might have squirrels AND birds.
	 * Reference: https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#nextInt-int-
	 * 
	 * @return A new Enemy instance of a randomly chosen type valid for the current level
	 */
	Enemy createEnemyForCurrentLevel() {
	    // Get list of possible enemies from current level
	    List<Class<? extends Enemy>> possibleEnemies = levelManager.getCurrentLevel().getPossibleEnemies();
	    // Pick a random enemy type
	    Class<? extends Enemy> randomEnemy = possibleEnemies.get(RAND.nextInt(possibleEnemies.size()));
	    // Create that enemy type
	    return levelManager.getCurrentLevel().createEnemy(RAND, WIDTH, PLAYER_SIZE, randomEnemy);
	}

	/**
	 * ITEM CREATION
	 * Works the same as enemies (above), except for items ^^^
	 */
	Item createItemForCurrentLevel() {
	    // Get list of possible items from current level
	    List<Class<? extends Item>> possibleItems = levelManager.getCurrentLevel().getPossibleItems();
	    // Pick a random item type
	    Class<? extends Item> randomItem = possibleItems.get(RAND.nextInt(possibleItems.size()));
	    // Create that item type
	    return levelManager.getCurrentLevel().createItem(RAND, WIDTH, PLAYER_SIZE, randomItem);
	}
	
	/**
	 * MAIN
	 */
	public static void main(String[] args) {
		launch();
	}
}