package application;

/* 
 * ACKNOWLEDGEMENTS AND SOURCES:
 * Much of this code was started from this tutorial, and this github page:
 * Gaspared. (2019, November 29). Gaspared/space-invaders: A simple space invaders game in javafx. 
 * for more information visit my YouTube channel. GitHub. https://github.com/Gaspared/Space-Invaders 
 * Tutorial Part 1: https://www.youtube.com/watch?v=0szmaHH1hno
 * Tutorial Part 2: https://www.youtube.com/watch?v=dzcQgv9hqXI&t=87s
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
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
	
	// CONSTANTS AND GLOBAL VARIABLES
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
	final int MAX_ITEMS = 3;                     // Maximum number of acorns
	
	// Game state variables
	// boolean gameOver = false;
	// boolean levelComplete = false;
	
	public GraphicsContext gc;                   // Graphics context for drawing
	
	// New Game State System
	enum GameState {
	    START_SCREEN,
	    QUEST_SCREEN,
	    PLAYING,
	    GAME_OVER
	}

	GameState currentState = GameState.START_SCREEN;  // Start at beginning
	
	// Game objects collections
	Creature player;                                // The player character
	List<Shot> shots;                               // List of player shots
	List<Particles> particles;                      // Background star/particle effects
	List<Enemy> Squirrels;                          // List of enemy squirrels
	List<Item> acornCaps;                           // List of acorn items
	GameRenderer renderer;							// Draws game
	StartScreen startScreen;						// Draws Start Screen
	// MediaPlayer musicPlayer;						// Music - Uncomment when ready to add music
	Quest01 quest01;              					// The quest for level 1
	
	boolean questConfirmed = false;					// Track if player has read the quest
	
	Level01 currentLevel;							// Will become just "Level" later
	
	// Input and score tracking
	public double mouseX;                           // Mouse X position for player movement
	public int score;                               // Player's current score

	
	/**
	 * START METHOD
	 * Set up the game window, canvas, input handlers, and animation timeline
	 */
	public void start(Stage stage) throws Exception {
		
		// Create drawing canvas
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		renderer = new GameRenderer(gc);
		gc.setFill(Color.GREEN);  // Temporary fill
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Set up game loop animation (100ms intervals = 10 fps)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), _ -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);   // Loop forever
		timeline.play();                               // Start the animation
		
		// Mouse input handling
		canvas.setCursor(Cursor.DEFAULT);                   // Change cursor to move cursor
		canvas.setOnMouseMoved(e -> mouseX = e.getX());  // Track mouse X position
		
		canvas.setOnMouseClicked(e -> {
		    
		    double clickX = e.getX();
		    double clickY = e.getY();
		    
		    switch(currentState) {
		    
		        case START_SCREEN:
		            // Check if Play button clicked
		            if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
		               clickY >= HEIGHT/2 && clickY <= HEIGHT/2 + 50) {
		            	
		                // Stop "start screen" music
		                // if(musicPlayer != null) {
		                //     musicPlayer.stop();
		                // }
		            	
		                currentState = GameState.QUEST_SCREEN; // next screen
		                setup(); // Initialize game
		            }
		            break;
		            
		        case QUEST_SCREEN:
		            // Check if OK button clicked
		            // We'll need button coordinates
		            // x: WIDTH/2-100 to WIDTH/2+100, y: HEIGHT/2+100 to HEIGHT/2+150
		            if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
		               clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
		            	questConfirmed = true;  // Player accepted the quest
		                currentState = GameState.PLAYING;  // next screen
		                setup(); // Start the game
		            }
		            break;		            
		            
		        case PLAYING:
		            // Shoot
		            if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
		            break;
		            
		        case GAME_OVER:
		            // Click to return to start
		            currentState = GameState.START_SCREEN;  // next screen
		            setup(); // Reset for next game
		            break;
		    }
		});
			
			
	
		
		// Initialize game and set up window
		setup();                                            // Set initial game state
		
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
		particles = new ArrayList<>();                   // New background effects list
		shots = new ArrayList<>();                       // New player bullets list
		Squirrels = new ArrayList<>();                   // New enemies list
		acornCaps = new ArrayList<>();                   // New acorns list
		player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG); // Center player
		score = 0;                                       // Reset score
		
		startScreen = new StartScreen();
		quest01 = new Quest01();
		currentLevel = new Level01();
		
		// Create initial set of enemies
		IntStream.range(0, MAX_BOMBS).mapToObj(_ -> this.newSquirrel()).forEach(Squirrels::add);
		
		// Create initial set of acorns
		IntStream.range(0, MAX_ITEMS).mapToObj(_ -> this.newAcorn()).forEach(acornCaps::add);
	}

	
	/**
	 * RUN METHOD - Main game loop (called every frame)
	 * Update game objects, check collisions, and render everything
	 */
	private void run(GraphicsContext gc) {
	    
	    switch(currentState) {
	        case START_SCREEN:
	            // Only draw start screen
	            renderer.drawStartScreen(startScreen);
	            break;
	            
	        case QUEST_SCREEN:
	            renderer.drawQuestScreen(quest01);
	            break;
	            
	        case PLAYING:
	            // Full game rendering and logic
	            renderer.clearScreen();
	            
	            int acornsSoFar = currentLevel.itemsCollected.getOrDefault(ItemAcorn.class, 0);
	            
	            renderer.drawHUD(score, shots.size(), MAX_SHOTS, acornsSoFar);
	            
	            // Draw background effects
	            particles.forEach(Particles::draw);
	        
	            // Update and draw player
	            player.update();
	            player.draw(gc);
	            player.posX = (int) mouseX;
	            
	            // Update and draw enemies
	            Squirrels.stream().peek(Creature::update).forEach(e -> {
	                e.draw(gc);
	                e.update();
	                
	                if(Collisions.playerCollides(player, e) && !player.exploding) {
	                    player.explode();
	                }
	            });
	            
	            // Update and draw acorns
	            acornCaps.forEach(i -> {
	                i.draw(gc);
	                i.update(gc);
	                
	                if(Collisions.itemCollides(player, i) && !i.collected) {
	                    currentLevel.registerItemCollected(i);
	                    ((ItemAcorn) i).onCollected();
	                }
	            });
	            
	            // Update and check shots
	            for (int i = shots.size() - 1; i >=0 ; i--) {
	                Shot shot = shots.get(i);
	                if(shot.posY < 0 || shot.toRemove) { 
	                    shots.remove(i);
	                    continue;
	                }
	                shot.update();
	                shot.draw(gc);
	                
	                for (Enemy squirrel : Squirrels) {
	                    if(Collisions.shotCollides(shot, squirrel) && !squirrel.exploding) {
	                        score++;
	                        currentLevel.registerEnemyDefeated(squirrel);
	                        squirrel.explode();
	                        shot.toRemove = true;
	                    }
	                }
	            }
	            
	            // Replace destroyed enemies
	            for (int i = Squirrels.size() - 1; i >= 0; i--){  
	                if(Squirrels.get(i).destroyed) {
	                    Squirrels.set(i, newSquirrel());
	                }
	            }
	            
	            // Replace collected acorns
	            for (int i = acornCaps.size() - 1; i >= 0; i--){  
	                if(acornCaps.get(i).gone) {
	                    acornCaps.set(i, newAcorn());
	                }
	            }
	        
	            // Check game over condition
	            if(player.destroyed) {
	                currentState = GameState.GAME_OVER;
	            }
	            
	            // Check level completion
	            if(currentLevel.isComplete()) {
	                // For now, just go to game over. Boss level later.
	                currentState = GameState.GAME_OVER;
	            }
	            
	            // Particles
	            if(RAND.nextInt(10) > 2) {
	                particles.add(new Particles(gc));
	            }
	            
	            for (int i = 0; i < particles.size(); i++) {
	                if(particles.get(i).isOffScreen())
	                    particles.remove(i);
	            }
	            break;
	            
	        case GAME_OVER:
	            // Only draw game over screen
	            renderer.drawGameOver(score);
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