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
 */



import java.util.Random;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
	private static final Random RAND = new Random();
	public static final int WIDTH = 1280;                  // Game window width
	public static final int HEIGHT = 720;                  // Game window height
	private static final int PLAYER_SIZE = 60;             // Size of player
	
	// Image resources for game elements
	static final Image PLAYER_IMG = new Image("frog_player_128x128.png");
	static final Image SQUIRREL_IMG = new Image("squirrel_enemy_front_128x128.png");
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final Image ACORN_IMG = new Image("acorn_cap_64x64.png");
	
	// Explosion animation properties
	static final int EXPLOSION_W = 128;          // Width of explosion sprite
	static final int EXPLOSION_ROWS = 3;         // Rows in explosion sprite sheet
	static final int EXPLOSION_COL = 3;          // Columns in explosion sprite sheet
	static final int EXPLOSION_H = 128;          // Height of explosion sprite
	static final int EXPLOSION_STEPS = 15;       // Number of steps in explosion animation
	
	// Game balance constants
	final int MAX_BOMBS = 10;                    // Maximum number of enemies
	final int MAX_SHOTS = MAX_BOMBS * 2;         // Maximum number of player shots allowed
	final int MAX_ITEMS = 6;                     // Maximum number of acorns

	
	public GraphicsContext gc;                   // Graphics context for drawing
	
	/**
	 * GAME STATE MANAGEMENT
	 * Moved to GameState.java and GameStateManager.java
	 */
	GameStateManager stateManager;
	
	// Game objects collections
	EntityManager entityManager;
	GameRenderer renderer;							// Draws game
	UIManager uiManager; 
	boolean questConfirmed = false;					// Track if player has read the quest
	LevelManager levelManager;						// Moved to LevelManager.java
	InputHandler inputHandler; 
	
	// Music
	// MediaPlayer startMusicPlayer;
	// MediaPlayer questMusicPlayer;
	// MediaPlayer levelMusicPlayer;
	// MediaPlayer bossMusicPlayer;
	// MediaPlayer winMusicPlayer;
	// MediaPlayer loseMusicPlayer;
	
	
	// Input and score tracking
	


	/**
	 * START METHOD
	 * Set up the game window, canvas, input handlers, and animation timeline
	 */
	public void start(Stage stage) throws Exception {
		
		// Create drawing canvas
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		renderer = new GameRenderer(gc);


		stateManager = new GameStateManager();
		levelManager = new LevelManager();
		entityManager = new EntityManager(MAX_SHOTS, WIDTH, HEIGHT, MAX_BOMBS, MAX_ITEMS);
		uiManager = new UIManager(renderer); 
		setup();  // setup will now use EntityManager.java
		inputHandler = new InputHandler(stateManager, levelManager, entityManager, MAX_SHOTS, WIDTH, HEIGHT);
		

	    canvas.setOnMouseMoved(e -> inputHandler.handleMouseMoved(e.getX()));
	    // canvas.setOnMouseClicked(e -> inputHandler.handleMouseClicked(e, this::setup));
	    canvas.setOnMouseClicked(e -> {
	        inputHandler.handleMouseClicked(e, this::setup);
	
	    });
	    
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Set up game loop animation (100ms intervals = 10 fps)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), _ -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);   // Loop forever
		timeline.play();                               // Start the animation
		
	


		//Media startMusic = new Media(new File("start.wav").toURI().toString());
		//musicPlayer = new MediaPlayer(startMusic);
		//musicPlayer.play();
		
		stage.setScene(new Scene(new StackPane(canvas)));   // Add canvas to scene
		stage.setTitle("ImaginBlast");                      // Set window title
		stage.show();                                       // Display the window
	}

	/**
	 * SETUP METHOD
	 * Create new collections and place initial enemies
	 */
	private void setup() {
	    entityManager.resetAll();
	    levelManager.reset(); 
	    
	    // Create player
	    Player player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
	    player.resetHealth();
	    entityManager.setPlayer(player);
	    
	   
	    
	    // Create initial set of enemies
	    for (int i = 0; i < MAX_BOMBS; i++) {
	        entityManager.addEnemy(newSquirrel());
	    }
	    
	    // Create initial set of acorns
	    for (int i = 0; i < MAX_ITEMS; i++) {
	        entityManager.addAcorn(newAcorn());
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
	            // Only draw start screen
	        	uiManager.drawStartScreen();
	            break;
	            
	        case QUEST_SCREEN:
	        	uiManager.drawQuestScreen(levelManager.getQuest());
	            break;
	            
	        case PLAYING:
	            // Full game rendering and logic
	            renderer.clearScreen();
	            
	            int acornsSoFar = levelManager.getCurrentLevel().itemsCollected.getOrDefault(ItemAcorn.class, 0);
	            
	            renderer.drawHUD(entityManager.getScore(), entityManager.getShots().size(), MAX_SHOTS, acornsSoFar, entityManager.getPlayer());
	            
	            // Draw background effects
	            entityManager.drawParticles(gc);
	            entityManager.updateParticles(gc);
	        
	            // Update and draw player
	            entityManager.updatePlayer();
	            entityManager.drawPlayer(gc);
	            entityManager.movePlayer((int) inputHandler.getMouseX());
	            
	            // Update and draw enemies
	            entityManager.updateEnemies();
	            entityManager.drawEnemies(gc);
	            entityManager.checkEnemyCollisions(stateManager);
	            
	            // Update and draw acorns
	            entityManager.drawAcorns(gc);
	            entityManager.updateAcorns(levelManager);
	            
	            // Update and check shots
	            entityManager.updateShotsWithEnemyCollisions(levelManager);
	            entityManager.drawShots(gc);
	            
	            // Replace destroyed enemies
	            entityManager.replaceDestroyedEnemies(() -> newSquirrel());
	            
	            // Replace collected acorns
	            entityManager.replaceCollectedAcorns(() -> newAcorn());
	        
	            
	         // Check game over condition
	            if(entityManager.isPlayerDestroyed()) {
	                stateManager.setCurrentState(GameState.GAME_OVER);
	            }
	            
	            // Check level completion
	            if(levelManager.getCurrentLevel().isComplete()) {
	              
	                stateManager.setCurrentState(GameState.BOSS_FIGHT);
	                // Clear existing enemies and items for boss fight
	                entityManager.clearEnemies();
	                entityManager.clearAcorns();
	                entityManager.getShots().clear();
	            }
	            
	
	    
	            break;
	            
	        case BOSS_FIGHT:
	            // Update player movement
	        	entityManager.updatePlayer();
	        	entityManager.movePlayer((int) inputHandler.getMouseX());

	            // UPDATE AND CHECK PLAYER SHOTS - just like in PLAYING state
	        	entityManager.updateShotsWithBossCollisions(levelManager.getBossScreen().boss);

	            // Update boss screen (handles boss movement, enemy shots, portal)
	            levelManager.getBossScreen().update(entityManager.getPlayer(), entityManager.getShots(), entityManager.getEnemyShots());

	            // Draw everything
	            levelManager.getBossScreen().draw(gc, renderer, entityManager.getPlayer(), entityManager.getScore());

	            // Draw player shots
	            entityManager.drawShots(gc);

	            // Update and draw enemy shots (just like in PLAYING state)
	            entityManager.updateEnemyShots();
	            entityManager.drawEnemyShots(gc);

	            // Check if boss is defeated
	            if (levelManager.getBossScreen().boss.isDefeated() && !levelManager.isBossDefeated()) {
	            	levelManager.setBossDefeated(true);
	            }

	            // Check if player entered portal
	            if (levelManager.getBossScreen().isComplete()) {
	                stateManager.setCurrentState(GameState.LEVEL_DONE);
	            }

	            // Check if player died
	            if (entityManager.isPlayerDestroyed()) {
	                stateManager.setCurrentState(GameState.GAME_OVER);
	            }
	            break;
	            
	             
	        case LEVEL_DONE:
	            // Draw level complete screen
	        	uiManager.drawLevelDoneScreen(levelManager.getLevelDoneScreen());
	            break;
	            
	        case GAME_OVER:
	            //stateManager.setCurrentState(GameState.START_SCREEN);
	            //entityManager.resetAll();
	            //startScreen = new StartScreen();
	        	uiManager.drawGameOverScreen(entityManager.getScore());
	            break;
	    }
	}

	
	
	/**
	 * ENEMY CREATION METHOD
	 * Creates a new enemy squirrel at a random X position at the top of screen
	 */
	Enemy newSquirrel() {
		return new EnemySquirrel(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, SQUIRREL_IMG);
	}
	
	/**
	 * ADDED: ACORN CREATION METHOD
	 * Creates a new acorn collectible at a random X position at the top of screen
	 */
	Item newAcorn() {
		return new ItemAcorn(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, ACORN_IMG);
	}
	
	
	
	/**
	 * MAIN METHOD - Application entry point
	 * Start JavaFX application
	 */
	public static void main(String[] args) {
		launch(); 
	}
}