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
import javafx.stage.Stage;
import javafx.util.Duration;


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
	boolean gameOver = false;                    // Flag for game over state
	boolean levelComplete = false;               // Flag for level completion
	public GraphicsContext gc;                   // Graphics context for drawing
	
	// Game objects collections
	Creature player;                                // The player character
	List<Shot> shots;                               // List of player shots
	List<Particles> particles;                      // Background star/particle effects
	List<Enemy> Squirrels;                          // List of enemy squirrels
	List<Item> acornCaps;                           // List of acorn items
	GameRenderer renderer;							// Draws game
	
	// Input and score tracking
	public double mouseX;                           // Mouse X position for player movement
	public int score;                               // Player's current score
	public int acornCount = 0;                      // ADD THIS: Track acorns collected
	
	/**
	 * START METHOD
	 * Set up the game window, canvas, input handlers, and animation timeline
	 */
	public void start(Stage stage) throws Exception {
		
		// Create drawing canvas
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();
		renderer = new GameRenderer(gc);
		
		// Set up game loop animation (100ms intervals = 10 fps)
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), _ -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);   // Loop forever
		timeline.play();                               // Start the animation
		
		// Mouse input handling
		canvas.setCursor(Cursor.MOVE);                   // Change cursor to move cursor
		canvas.setOnMouseMoved(e -> mouseX = e.getX());  // Track mouse X position
		canvas.setOnMouseClicked(_ -> {
			
			// Shoot if not at max capacity
			if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
			
			// Restart game if game over
			if(gameOver) { 
				gameOver = false;
				setup();                                 // Reset game state
			}
			
			// ADDED: Handle level completion restart
			if(levelComplete) { 
				levelComplete = false;
				setup();                                 // Reset game state
			}
		});
		
		// Initialize game and set up window
		setup();                                            // Set initial game state
		stage.setScene(new Scene(new StackPane(canvas)));   // Add canvas to scene
		stage.setTitle("ImaginBlast");                      // Set window title
		stage.show();                                       // Display the window
	}

	/**
	 * SETUP METHOD
	 * Create new collections and place initial enemies
	 */
	private void setup() {
		particles = new ArrayList<>();                        // New background effects list
		shots = new ArrayList<>();                       // New player bullets list
		Squirrels = new ArrayList<>();                   // New enemies list
		// ADDED: Initialize acorns list
		acornCaps = new ArrayList<>();                   // New acorns list
		player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG); // Center player
		score = 0;                                       // Reset score
		
		// Create initial set of enemies
		IntStream.range(0, MAX_BOMBS).mapToObj(_ -> this.newSquirrel()).forEach(Squirrels::add);
		
		// ADDED: Create initial set of acorns
		IntStream.range(0, MAX_ITEMS).mapToObj(_ -> this.newAcorn()).forEach(acornCaps::add);
	}

	
	/**
	 * RUN METHOD - Main game loop (called every frame)
	 * Update game objects, check collisions, and render everything
	 */
	private void run(GraphicsContext gc) {
		
		// Rendering code added to GameRenderer.java
		
		renderer.clearScreen();
		
		renderer.drawHUD(score, shots.size(), MAX_SHOTS, acornCount);
		
		if(gameOver) {
			renderer.drawGameOver(score);
		}
		
		// Draw background effects
		particles.forEach(Particles::draw);
	
		// Update and draw player
		player.update();
		player.draw(gc);
		player.posX = (int) mouseX;    // Move player with mouse
		
		// Update and draw enemies, check collisions with player
		Squirrels.stream().peek(Creature::update).forEach(e -> {
			e.draw(gc);
			e.update();
			
			// If enemy hits player, trigger explosion
			if(Collisions.playerCollides(player, e) && !player.exploding) {
				player.explode();
			}
		});
		
		// ADDED: Update and draw acorns, check for player collection
		acornCaps.stream().forEach(i -> {
			i.draw(gc);
			i.update(gc);
			
			// If player collects acorn, mark as collected
		
			if(Collisions.itemCollides(player, i) && !i.collected && !gameOver){
				acornCount++;  // Increment the counter
				// Would increment counter here if Creature had one
				 ((ItemAcorn) i).onCollected();
			}
		});
		
		// Update and check shots
		for (int i = shots.size() - 1; i >=0 ; i--) {
			Shot shot = shots.get(i);
			// Remove shots that are off screen or marked for removal
			if(shot.posY < 0 || shot.toRemove)  { 
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw(gc);
			
			// Check collision with each enemy
			for (Enemy squirrel : Squirrels) {
				if(Collisions.shotCollides(shot, squirrel) && !squirrel.exploding) {
					score++;                              // Increase score
					squirrel.explode();                    // Trigger enemy explosion
					shot.toRemove = true;                  // Mark shot for removal
				}
			}
		}
		
		// Replace destroyed enemies with new ones
		for (int i = Squirrels.size() - 1; i >= 0; i--){  
			if(Squirrels.get(i).destroyed)  {
				Squirrels.set(i, newSquirrel());
			}
		}
		
		// ADDED: Replace collected or off-screen acorns with new ones
		for (int i = acornCaps.size() - 1; i >= 0; i--){  
			if(acornCaps.get(i).gone)  {
				acornCaps.set(i, newAcorn());
			}
		}
	
		// Check game over condition
		gameOver = player.destroyed;
		
		// Randomly create new background stars/effects
		if(RAND.nextInt(10) > 2) {
			particles.add(new Particles(gc)); // Pass gc when creating
		}
		
		// Remove background effects that have fallen off screen
		for (int i = 0; i < particles.size(); i++) {
			
			if(particles.get(i).isOffScreen())
	     // if(particles.get(i).posY > HEIGHT)
				particles.remove(i);
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