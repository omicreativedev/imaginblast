package application;
//Icon made by Freepik from www.flaticon.com
//visit: https://www.youtube.com/user/CbX397/

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
	static final Image PLAYER_IMG = new Image("frog_player_128x128.png"); 
	static final Image SQUIRREL_IMG = new Image("squirrel_enemy_front_128x128.png");
	static final Image EXPLOSION_IMG = new Image("explosion.png");
	static final int EXPLOSION_W = 128;
	static final int EXPLOSION_ROWS = 3;
	static final int EXPLOSION_COL = 3;
	static final int EXPLOSION_H = 128;
	static final int EXPLOSION_STEPS = 15;
	


	
	final int MAX_BOMBS = 10,  MAX_SHOTS = MAX_BOMBS * 2;
	boolean gameOver = false;
	public GraphicsContext gc;
	
	Creature player;
	List<Shot> shots;
	List<Universe> univ;
	List<Enemy> Squirrels;
	
	public double mouseX;
	public int score;

	//start
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
		});
		
		setup();
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setTitle("ImaginBlast");
		stage.show();
	}

	//setup the game
	private void setup() {
		univ = new ArrayList<>();
		shots = new ArrayList<>();
		Squirrels = new ArrayList<>();
		player = new Creature(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
		score = 0;
		IntStream.range(0, MAX_BOMBS).mapToObj(i -> this.newSquirrel()).forEach(Squirrels::add);
	}

	
	//shot collision
	public boolean shot_colide(Shot shot, Creature Rocket) {
		int distance = distance(shot.posX + Shot.size / 2, shot.posY + Shot.size / 2, 
				Rocket.posX + Rocket.size / 2, Rocket.posY + Rocket.size / 2);
		return distance  < Rocket.size / 2 + Rocket.size / 2;
	} 
	
	//bomb collision
	public boolean player_colide(Creature player, Creature other) {
		int d;
		d = distance(player.posX + player.size / 2, player.posY + player.size /2, 
							other.posX + other.size / 2, other.posY + other.size / 2);
		return d < other.size / 2 + player.size / 2 ;
	}
	
	
	
	//run Graphics
	private void run(GraphicsContext gc) {
		gc.setFill(Color.FORESTGREEN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + score, 60,  20);
	
		
		if(gameOver) {
			gc.setFont(Font.font(35));
			gc.setFill(Color.YELLOW);
			gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT /2.5);
		//	return;
		}
		univ.forEach(Universe::draw);
	
		player.update();
		player.draw(gc);
		player.posX = (int) mouseX;
		
		Squirrels.stream().peek(Creature::update).forEach(e -> { // removed ahead of the forEach .peek(Rocket::draw)
			e.draw(gc);
			e.update(gc);
			if(player_colide(player, e) && !player.exploding) {
				player.explode();
			}
		});
		
		
		for (int i = shots.size() - 1; i >=0 ; i--) {
			Shot shot = shots.get(i);
			if(shot.posY < 0 || shot.toRemove)  { 
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw(gc);
			for (Enemy squirrel : Squirrels) {
				if(shot_colide(shot, squirrel) && !squirrel.exploding) {
					score++;
					squirrel.explode();
					shot.toRemove = true;
				}
			}
		}
		
		for (int i = Squirrels.size() - 1; i >= 0; i--){  
			if(Squirrels.get(i).destroyed)  {
				Squirrels.set(i, newSquirrel());
			}
		}
	
		gameOver = player.destroyed;
		if(RAND.nextInt(10) > 2) {
			univ.add(new Universe());
		}
		for (int i = 0; i < univ.size(); i++) {
			if(univ.get(i).posY > HEIGHT)
				univ.remove(i);
		}
	}

	
	//environment
	public class Universe {
		int posX, posY;
		private int h, w, r, g, b;
		private double opacity;
		
		public Universe() {
			posX = RAND.nextInt(WIDTH);
			posY = 0;
			w = RAND.nextInt(5) + 1;
			h =  RAND.nextInt(5) + 1;
			r = RAND.nextInt(100) + 150;
			g = RAND.nextInt(100) + 150;
			b = RAND.nextInt(100) + 150;
			opacity = RAND.nextFloat();
			if(opacity < 0) opacity *=-1;
			if(opacity > 0.5) opacity = 0.5;
		}
		
		public void draw() {
			if(opacity > 0.8) opacity-=0.01;
			if(opacity < 0.1) opacity+=0.01;
			gc.setFill(Color.rgb(r, g, b, opacity));
			gc.fillOval(posX, posY, w, h);
			posY+=20;
		}
	}
	
	
	Enemy newSquirrel() {
		return new Enemy(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, SQUIRREL_IMG);
	}
	
	int distance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	
	
	public static void main(String[] args) {
		launch();
	}
}

