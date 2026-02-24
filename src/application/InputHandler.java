package application;

import javafx.scene.input.MouseEvent;
import java.util.List;

/**
 * Handles all mouse input for the game
 */
public class InputHandler {
    private GameStateManager stateManager;
    private LevelManager levelManager;
    private Player player;
    private List<Shot> shots;
    private int MAX_SHOTS;
    private int WIDTH;
    private int HEIGHT;
    
    // Track mouse position
    private double mouseX;
    
    public InputHandler(GameStateManager stateManager, LevelManager levelManager, 
                        Player player, List<Shot> shots, int MAX_SHOTS, int WIDTH, int HEIGHT) {
        this.stateManager = stateManager;
        this.levelManager = levelManager;
        this.player = player;
        this.shots = shots;
        this.MAX_SHOTS = MAX_SHOTS;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    
    public void handleMouseMoved(double mouseX) {
        this.mouseX = mouseX;
    }
    
    public double getMouseX() {
        return mouseX;
    }
    
    public void updateReferences(Player player, List<Shot> shots) {
        this.player = player;
        this.shots = shots;
        // Keep the same mouseX value
    }
    
    public void handleMouseClicked(MouseEvent e, Runnable setupCallback) {
        double clickX = e.getX();
        double clickY = e.getY();
        
        switch(stateManager.getCurrentState()) {
        
            case START_SCREEN:
                // Check if Play button clicked
                if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                   clickY >= HEIGHT/2 && clickY <= HEIGHT/2 + 50) {
                    
                    // Stop START_SCREEN music (commented for now)
                    // if(startMusicPlayer != null) {
                    //     musicPlayer.stop();
                    // }
                    
                    stateManager.setCurrentState(GameState.QUEST_SCREEN);
                    setupCallback.run(); // Call setup to initialize game
                }
                break;
                
            case QUEST_SCREEN:
                // Check if OK button clicked
                if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                   clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                    
                    // Stop quest music (commented for now)
                    // if(questMusicPlayer != null) {
                    //     questMusicPlayer.stop();
                    // }
                    
                    // Start level music (commented for now)
                    // String levelSong = levelManager.getCurrentLevelNum() == 1 ? "level1.wav" : "level2.wav";
                    // Media levelMusic = new Media(new File(levelSong).toURI().toString());
                    // levelMusicPlayer = new MediaPlayer(levelMusic);
                    // levelMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    // levelMusicPlayer.play();
                    
                    levelManager.resetForNewGame();
                    stateManager.setCurrentState(GameState.PLAYING);
                    setupCallback.run();
                }
                break;            
                
            case PLAYING:
                // Shoot
                if(shots.size() < MAX_SHOTS) {
                    Shot newShot = player.shoot();
                    if (newShot != null) {
                        shots.add(newShot);
                    }
                }
                break;
                
            case BOSS_FIGHT:
                // Player can still shoot during boss fight
                if(shots.size() < MAX_SHOTS) {
                    Shot newShot = player.shoot();
                    if (newShot != null) {
                        shots.add(newShot);
                    }
                }
                break;
                
            case LEVEL_DONE:
                // Check if OK button clicked
                levelManager.getLevelDoneScreen().handleClick(clickX, clickY);
                if (levelManager.getLevelDoneScreen().isOkPressed()) {
                    // For now, go to game over (Level 2 would come next)
                    stateManager.setCurrentState(GameState.GAME_OVER);
                    levelManager.getLevelDoneScreen().setOkPressed(false);
                }
                break;
                
            case GAME_OVER:
                // Click to return to start
                stateManager.setCurrentState(GameState.START_SCREEN);
                setupCallback.run();
                break;
        }
    }
}