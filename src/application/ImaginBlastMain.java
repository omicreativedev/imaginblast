package application;

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
 */

import java.util.Random;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

//Music
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

/**
 * MAIN GAME CLASS
 */
public class ImaginBlastMain extends Application {
	
	// Constants and global variables
	private static final Random RAND = new Random(); // Random generator
	public static final int WIDTH = 1280; // Game window width
	public static final int HEIGHT = 720; // Game window height
	private static final int PLAYER_SIZE = 60;
	
	// Image resources for game elements
	static final Image PLAYER_IMG = new Image("frog_player_128x128.png");
	static final Image SQUIRREL_IMG = new Image("squirrel_enemy_front_128x128.png");
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final Image ACORN_IMG = new Image("acorn_cap_64x64.png");
	
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
	
	public GraphicsContext gc; // Graphics context for drawing
	
	/**
	 * GAME STATE MANAGEMENT
	 * Moved to GameState.java and GameStateManager.java
	 */
	GameStateManager stateManager; // Manages game states i.e. what's the current state?
	
	// Game objects collections
	EntityManager entityManager; // Manages game entities 
	GameRenderer renderer; // Draws game
	UIManager uiManager; // What screen are we on?
	boolean questConfirmed = false; // Has player read the quest???
	LevelManager levelManager; // Moved to LevelManager.java
	InputHandler inputHandler; // Handles user input like mouseclicks (later WASD)
	
	// Music -- for later
	// MediaPlayer startMusicPlayer;
	// MediaPlayer questMusicPlayer;
	// MediaPlayer levelMusicPlayer;
	// MediaPlayer bossMusicPlayer;
	// MediaPlayer winMusicPlayer;
	// MediaPlayer loseMusicPlayer;

	/**
	 * START METHOD
	 * Set up the game window, canvas, input handlers, and animation timeline
	 */
	public void start(Stage stage) throws Exception {
		
		// Drawing canvas
		Canvas canvas = new Canvas(WIDTH, HEIGHT); // Create canvas with game dimensions
		gc = canvas.getGraphicsContext2D(); // Get graphics context for drawing
		renderer = new GameRenderer(gc); // Initialize renderer with graphics context

		// Other initializations
		stateManager = new GameStateManager(); // Initialize state manager
		levelManager = new LevelManager(); // Initialize level manager
		entityManager = new EntityManager(MAX_SHOTS, WIDTH, HEIGHT, MAX_BOMBS, MAX_ITEMS); // Initialize entity manager
		uiManager = new UIManager(renderer); // Initialize UI manager
		setup(); // setup will now use EntityManager.java
		inputHandler = new InputHandler(stateManager, levelManager, entityManager, MAX_SHOTS, WIDTH, HEIGHT); // Initialize input handler

	    canvas.setOnMouseMoved(e -> inputHandler.handleMouseMoved(e.getX())); // Handle mouse movement
	    
	    canvas.setOnMouseClicked(e -> { // Handle mouse clicks
	        inputHandler.handleMouseClicked(e, this::setup); // Process click callback to setup
	    });
	    
		// Set up game loop animation (100ms intervals = 10 fps)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), _ -> run(gc))); // Create animation timeline
		timeline.setCycleCount(Timeline.INDEFINITE); // Loop forever
		timeline.play(); // Start the animation

		//Media startMusic = new Media(new File("start.wav").toURI().toString());
		//musicPlayer = new MediaPlayer(startMusic);
		//musicPlayer.play();
		
		stage.setScene(new Scene(new StackPane(canvas))); // Add canvas to scene
		stage.setTitle("ImaginBlast"); // Set window title
		stage.show(); // Display the window
	}

	/**
	 * SETUP METHOD
	 * Create new collections and place initial enemies
	 */
	private void setup() {
	    entityManager.resetAll(); // Reset all entities
	    levelManager.reset(); // Reset level manager
	    
	    // Create player
	    Player player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG); // Create player at bottom center
	    player.resetHealth(); // Reset player health
	    entityManager.setPlayer(player); // Set player in entity manager
	    
	    // Create initial set of enemies
	    for (int i = 0; i < MAX_BOMBS; i++) {
	    	entityManager.addEnemy(createEnemyForCurrentLevel());
	    }
	    
	    // Create initial set of items for current level
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
	    
	    switch(stateManager.getCurrentState()) {
	        case START_SCREEN:
	        	uiManager.drawStartScreen();
	            break;
	            
	        case QUEST_SCREEN:
	        	uiManager.drawQuestScreen(levelManager.getQuest());
	            break;
	            
	        case PLAYING:
	            renderer.clearScreen();
	            
	            
	            // Get the first item goal's class to display
	            Class<? extends Item> itemType = levelManager.getCurrentLevel().getItemGoals().keySet().iterator().next();
	            int itemsSoFar = levelManager.getCurrentLevel().itemsCollected.getOrDefault(itemType, 0);
	            
	            renderer.drawHUD(entityManager.getScore(), entityManager.getShots().size(), MAX_SHOTS, itemsSoFar, entityManager.getPlayer());
	            
	            // Draw background effects
	            entityManager.drawParticles(gc);
	            entityManager.updateParticles(gc);
	        
	            // Update and draw player
	            entityManager.updatePlayer(); // Update player state
	            entityManager.drawPlayer(gc); // Draw player
	            entityManager.movePlayer((int) inputHandler.getMouseX()); // Move player to mouse X position
	            
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
	            
	        case BOSS_FIGHT: // Boss fight state
	            // Update player movement
	        	entityManager.updatePlayer(); // Update player state
	        	entityManager.movePlayer((int) inputHandler.getMouseX()); // Move player to mouse X position

	            // UPDATE AND CHECK PLAYER SHOTS - just like in PLAYING state
	        	entityManager.updateShotsWithBossCollisions(levelManager.getBossScreen().boss); // Update shots and check boss collisions

	            // Update boss screen (handles boss movement, enemy shots, portal)
	            levelManager.getBossScreen().update(entityManager.getPlayer(), entityManager.getShots(), entityManager.getEnemyShots()); // Update boss screen

	            // Draw everything
	            levelManager.getBossScreen().draw(gc, renderer, entityManager.getPlayer(), entityManager.getScore()); // Draw boss screen

	            // Draw player shots
	            entityManager.drawShots(gc); // Draw player shots

	            // Update and draw enemy shots (just like in PLAYING state)
	            entityManager.updateEnemyShots(); // Update enemy shots
	            entityManager.drawEnemyShots(gc); // Draw enemy shots

	            // Check if boss is defeated
	            if (levelManager.getBossScreen().boss.isDefeated() && !levelManager.isBossDefeated()) { // If boss defeated and not already recorded
	            	levelManager.setBossDefeated(true); // Set boss defeated flag
	            }

	            // Check if player entered portal
	            if (levelManager.getBossScreen().isComplete()) { // If boss screen is complete
	                stateManager.setCurrentState(GameState.LEVEL_DONE); // Set level done state
	            }

	            // Check if player died
	            if (entityManager.isPlayerDestroyed()) { // If player is destroyed
	                stateManager.setCurrentState(GameState.GAME_OVER); // Set game over state
	            }
	            break;
	            
	        case LEVEL_DONE: // Level done state
	            // Draw level complete screen
	        	uiManager.drawLevelDoneScreen(levelManager.getLevelDoneScreen()); // Draw level done screen
	            break;
	            
	        case GAME_OVER: // Game over state
	            //stateManager.setCurrentState(GameState.START_SCREEN);
	            //entityManager.resetAll();
	            //startScreen = new StartScreen();
	        	uiManager.drawGameOverScreen(entityManager.getScore()); // Draw game over screen with final score
	            break;
	    }
	}

	/**
	 * ENEMY CREATION - Delegates to current level
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
	 * ITEM CREATION - Delegates to current level
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