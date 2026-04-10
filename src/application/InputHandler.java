package application;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Source(s):
 * Key Events JavaFX by BroCode Tutorial: https://www.youtube.com/watch?v=tq_0im9qc6E
 * Class KeyEvent Documentation: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
 * Class MouseEvent Documentation: https://openjfx.io/javadoc/22/javafx.graphics/javafx/scene/input/MouseEvent.html
 */

/**
 * Handles input for the game
 */
public class InputHandler {
    private GameStateManager stateManager;
    private LevelManager levelManager;
    private EntityManager entityManager;
    private GameRenderer gameRenderer;
    private int MAX_SHOTS;
    private int WIDTH;
    private int HEIGHT;
    
    // Keyboard tracking W, A, S, D
    private Set<String> activeKeys = new HashSet<>();
    
    // Track the mouse position
    private double mouseX;
    private double mouseY;
    
    public boolean isUpPressed()    { return activeKeys.contains("W"); }
    public boolean isDownPressed()  { return activeKeys.contains("S"); }
    public boolean isLeftPressed()  { return activeKeys.contains("A"); }
    public boolean isRightPressed() { return activeKeys.contains("D"); }
    
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
    
    public InputHandler(GameStateManager stateManager, LevelManager levelManager, 
                        EntityManager entityManager, GameRenderer gameRenderer,
                        int MAX_SHOTS, int WIDTH, int HEIGHT) {
        this.stateManager = stateManager;
        this.levelManager = levelManager;
        this.entityManager = entityManager;
        this.gameRenderer = gameRenderer;
        this.MAX_SHOTS = MAX_SHOTS;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    
    /**
     * HANDLE KEY PRESSED
     * @param e KeyEvent from JavaFX
     */
    public void handleKeyPressed(KeyEvent e) {
        String key = e.getCode().toString();
        
        if (key.equals("W") || key.equals("A") || key.equals("S") || key.equals("D")) {
            activeKeys.add(key);
        }
    }
    
    /**
     * HANDLE KEY RELEASE
     * @param e KeyEvent from JavaFX
     */
    public void handleKeyReleased(KeyEvent e) {
        String key = e.getCode().toString();
        activeKeys.remove(key);
    }
    
    /**
     * HANDLE MOUSE MOVED
     * Mouse position for aiming only !!!not movement!!!
     * @param mouseX Current mouse X coordinate
     * @param mouseY Current mouse Y coordinate
     */
    public void handleMouseMoved(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
    
    /**
     * HANDLE MOUSE CLICKED
     * @param e MouseEvent from JavaFX
     * @param Reset game state when needed
     */
    public void handleMouseClicked(MouseEvent e, Runnable setupCallback) {
        double clickX = e.getX();
        double clickY = e.getY();
        
        switch(stateManager.getCurrentState()) {
        
        case START_SCREEN:
            if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
               clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                gameRenderer.playButtonClick();
                gameRenderer.stopStartScreenMusic();
                stateManager.setCurrentState(GameState.QUEST_SCREEN);
                setupCallback.run();
            }
            break;
                
        case QUEST_SCREEN:
            if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
               clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                gameRenderer.playButtonClick();
                levelManager.resetForNewGame();
                stateManager.setCurrentState(GameState.PLAYING);
                setupCallback.run();
            }
            break;            
                
        case PLAYING:
        case BOSS_FIGHT:
            // Left click creates a new player shot
            if(entityManager.getShots().size() < MAX_SHOTS) {
                Shot newShot = entityManager.getPlayer().shoot();
                if (newShot != null) {
                    entityManager.addShot(newShot);
                }
            }
            break;
                
        case LEVEL_DONE:
            levelManager.getLevelDoneScreen().handleClick(clickX, clickY);
            if (levelManager.getLevelDoneScreen().isOkPressed()) {
                stateManager.setCurrentState(GameState.GAME_OVER);
                levelManager.getLevelDoneScreen().setOkPressed(false);
            }
            break;
                
        case GAME_OVER:
            stateManager.setCurrentState(GameState.START_SCREEN);
            setupCallback.run();
            break;
        }
    }
}