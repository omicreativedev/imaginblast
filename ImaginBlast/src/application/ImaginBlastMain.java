package application;

/* Much of this code was started from this tutorial, and this github page:
Gaspared. (2019, November 29). Gaspared/space-invaders: A simple space invaders game in javafx. for more information visit my YouTube channel. GitHub. https://github.com/Gaspared/Space-Invaders 
Tutorial Part 1: https://www.youtube.com/watch?v=0szmaHH1hno
Tutorial Part 2: https://www.youtube.com/watch?v=dzcQgv9hqXI&t=87s
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImaginBlastMain extends Application {
	
	//variables
	private static final Random RAND = new Random();
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static final int PLAYER_SIZE = 60;
	
	// Images
	static final Image BG_IMG = new Image("800x600_bg.png"); 
	static final Image PLAYER_IMG = new Image("frog_player_128x128.png"); 
	static final Image SQUIRREL_IMG = new Image("squirrel_enemy_front_128x128.png");
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final Image ACORN_IMG = new Image("acorn_cap_64x64.png");
	
	//explosion animation things
	static final int EXPLOSION_W = 128;
	static final int EXPLOSION_ROWS = 3;
	static final int EXPLOSION_COL = 3;
	static final int EXPLOSION_H = 128;
	static final int EXPLOSION_STEPS = 15;

	//stream and game variables
	final int MAX_ENEMIES = 10,  MAX_SHOTS = MAX_ENEMIES * 2;
	final int MAX_ITEMS = 3;
	boolean gameOver = false;
	boolean levelComplete = false;
	public GraphicsContext gc;
	
	PlayerClass player;
	List<Shot> shots;
	List<Enemy> squirrels;
	List<Item> acornCaps;
	
	public double mouseX;
	public int score;

	//startup
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(WIDTH, HEIGHT);	
		gc = canvas.getGraphicsContext2D();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		canvas.setCursor(Cursor.MOVE);
		canvas.setOnMouseMoved(e -> mouseX = e.getX());
		canvas.setOnMouseClicked(e -> {
			if(shots.size() < MAX_SHOTS) shots.add(player.shoot());
			if(gameOver) { 
				gameOver = false;
				setup();
			}
			// making sure levelComplete works
			if(levelComplete) { 
				levelComplete = false;
				setup();
			}
		});
		
		setup();
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setTitle("ImaginBlast");
		stage.show();
	}

	//setup the game
	private void setup() {
		shots = new ArrayList<>();
		squirrels = new ArrayList<>();
		acornCaps = new ArrayList<>();
		player = new PlayerClass(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
		score = 0;
		IntStream.range(0, MAX_ENEMIES).mapToObj(i -> this.newSquirrel()).forEach(squirrels::add);
		IntStream.range(0, MAX_ITEMS).mapToObj(i -> this.newAcorn()).forEach(acornCaps::add);
	}

	
	//shot collision
	public boolean shot_colide(Shot shot, Creature enemy) {
		int distance = distance(shot.posX + Shot.size / 2, shot.posY + Shot.size / 2, 
				enemy.posX + enemy.size / 2, enemy.posY + enemy.size / 2);
		return distance  < enemy.size / 2 + enemy.size / 2;
	} 
	
	//Player collision
	public boolean player_colide(PlayerClass player, Creature other) {
		int d;
		d = distance(player.posX + player.size / 2, player.posY + player.size /2, 
							other.posX + other.size / 2, other.posY + other.size / 2);
		return d < other.size / 2 + player.size / 2 ;
	}
	
	//Item collision
	public boolean item_colide(PlayerClass player, Item other) {
		int d;
		d = distance(player.posX + player.size / 2, player.posY + player.size /2, 
							other.posX + other.size / 2, other.posY + other.size / 2);
		return d < other.size / 2 + player.size / 2 ;
	}
	
	
	
	//run Graphics
	private void run(GraphicsContext gc) {
		// background
		gc.setFill(new ImagePattern(BG_IMG));
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		// score text
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + score, 60,  20);
		
		// health text
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.RED);
		gc.fillText("Health: " + player.hp, 180,  20);
		
		// acorn caps text
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.BROWN);
		gc.fillText("Acorns: " + player.col_items, 280,  20);
	
		// game over screen
		if(gameOver) {
			gc.setFont(Font.font(35));
			gc.setFill(Color.YELLOW);
			gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT /2.5);
		//	return;
		}
		
		// run the player and let it move
		player.update();
		player.draw(gc);
		player.posX = (int) mouseX;
		
		// Stream of enemy squirrels
		squirrels.stream().peek(Enemy::update).forEach(e -> {
			//make the enemies move
			e.draw(gc);
			e.update(gc);
			
			//if they bump the player
			if((player_colide(player, e) && !e.hitPlayer)&& !gameOver) {
				player.hp -=10;
				e.hitPlayer = true;
				
				//if they do so and the player dies
				if(player.hp<=0) {
					player.explode();
				}
			}
			
		});

		

		// Stream of acorn caps
		acornCaps.stream().forEach(i -> { // check if peek is necessary
			i.draw(gc);
			i.update(gc);
			
			//if the player collects the item
			if((item_colide(player, i) && !i.collected)&& !gameOver) {
				player.col_items++;
				i.collected = true;
			}
		});

		
		// draw shots unless they go off screen
		for (int i = shots.size() - 1; i >=0 ; i--) {
			Shot shot = shots.get(i);
			if(shot.posY < 0 || shot.toRemove)  { 
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw(gc);
			
			// if a shot hits a squirrel, increase score
			for (Enemy squirrel : squirrels) {
				if(shot_colide(shot, squirrel) && !squirrel.exploding) {
					score++;
					squirrel.explode();
					shot.toRemove = true;
				}
			}
		}
		
		
		// when a squirrel dies, add a new one to replace it
		for (int i = squirrels.size() - 1; i >= 0; i--){  
			if(squirrels.get(i).destroyed)  {
				squirrels.set(i, newSquirrel());
			}
		}
		
		// when an acorn is collected or goes off screen, add a new one to replace it
		for (int i = acornCaps.size() - 1; i >= 0; i--){  
			if(acornCaps.get(i).gone)  {
				acornCaps.set(i, newAcorn());
			}
		}
	
	// game end condition
		gameOver = player.destroyed;
	}
	
	// making new squirrels
	Enemy newSquirrel() {
		return new Enemy(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, SQUIRREL_IMG);
	}
	
	// making new acorn caps
	Item newAcorn() {
		return new Item(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, ACORN_IMG);
	}
	
	// does the math to check for collision
	int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	
	// main
	public static void main(String[] args) {
		launch();
	}
}

