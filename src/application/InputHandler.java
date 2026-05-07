package application;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.util.HashSet;
import java.util.Set;

// "I didn't write it, and they can't prove I did," 
// said the Knave. ~ The Knave of Hearts, 
// Alice's Adventures in Wonderland

/**
 * Source(s):
 * Key Events JavaFX by BroCode Tutorial: https://www.youtube.com/watch?v=tq_0im9qc6E
 * Class KeyEvent Documentation: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
 * Class MouseEvent Documentation: https://openjfx.io/javadoc/22/javafx.graphics/javafx/scene/input/MouseEvent.html
 */

/**
 * INPUT HANDLER CLASS
 * Handles all keyboard and mouse input for the game
 * Tracks WASD movement keys, F sprint key, mouse position for aiming, and click events for UI
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class InputHandler {
    
    // Game system references
    private GameStateManager stateManager;
    private LevelManager levelManager;
    private EntityManager entityManager;
    private GameRenderer gameRenderer;
    private UIManager uiManager; // For end screen button handling
    
    // Game configuration
    private int MAX_SHOTS;
    private int WIDTH;
    private int HEIGHT;
    
    // Keyboard tracking (WASD for movement, F for sprint)
    private Set<String> activeKeys = new HashSet<>();
    
    // Mouse tracking for aiming
    private double mouseX;
    private double mouseY;
    
    // KEY STATE CHECKERS
    public boolean isUpPressed()    { return activeKeys.contains("W"); }
    public boolean isDownPressed()  { return activeKeys.contains("S"); }
    public boolean isLeftPressed()  { return activeKeys.contains("A"); }
    public boolean isRightPressed() { return activeKeys.contains("D"); }
    
    // Sprint key ('F' key pressed)
    // Learned that 'shift' key wouldn't work (Oracle, 2015)
    // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
    public boolean isFPressed() { return activeKeys.contains("F"); }
    
    // MOUSE POSITION GETTERS
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
    
    /**
     * INPUT HANDLER CONSTRUCTOR
     * Stores references to all game systems needed for input handling
     * 
     * @param stateManager For changing game states based on input
     * @param levelManager For level progression
     * @param entityManager For player shooting
     * @param gameRenderer For playing sound effects
     * @param uiManager For end screen handling
     * @param MAX_SHOTS Maximum player shots allowed on screen
     * @param WIDTH Screen width for button bounds
     * @param HEIGHT Screen height for button bounds
     */
    public InputHandler(GameStateManager stateManager, LevelManager levelManager, 
            EntityManager entityManager, GameRenderer gameRenderer, UIManager uiManager,
            int MAX_SHOTS, int WIDTH, int HEIGHT) {
        this.stateManager = stateManager;
        this.levelManager = levelManager;
        this.entityManager = entityManager;
        this.gameRenderer = gameRenderer;
        this.uiManager = uiManager;
        this.MAX_SHOTS = MAX_SHOTS;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }
    
    /**
     * HANDLE KEY PRESSED METHOD
     * Registers when movement or sprint keys are pressed
     * 
     * @param e KeyEvent from JavaFX
     */
    public void handleKeyPressed(KeyEvent e) {
        String key = e.getCode().toString();
        
        // Track WASD movement keys and F sprint key
        if (key.equals("W") || key.equals("A") || key.equals("S") || key.equals("D") || key.equals("F")) {
            activeKeys.add(key); 
        }
    }
    
    /**
     * HANDLE KEY RELEASED METHOD
     * Removes key from active set when released
     * 
     * @param e KeyEvent from JavaFX
     */
    public void handleKeyReleased(KeyEvent e) {
        String key = e.getCode().toString();
        activeKeys.remove(key);
    }
    
    /**
     * HANDLE MOUSE MOVED METHOD
     * Tracks mouse position for aiming (NOT for movement)
     * 
     * @param mouseX Current mouse X coordinate
     * @param mouseY Current mouse Y coordinate
     */
    public void handleMouseMoved(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
    
    /**
     * HANDLE MOUSE CLICKED METHOD
     * Processes mouse clicks based on current game state
     * Handles button presses, shooting, and state transitions
     * 
     * @param e MouseEvent from JavaFX
     * @param setupCallback Callback to set up new level (spawn enemies, items)
     * @param resetCallback Callback to reset game for new playthrough
     */
    public void handleMouseClicked(MouseEvent e, Runnable setupCallback, Runnable resetCallback) {
        double clickX = e.getX();
        double clickY = e.getY();
        
        switch (stateManager.getCurrentState()) {
        
            case START_SCREEN:
                // Check if PLAY button was clicked
                if (clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                    clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                    gameRenderer.playButtonClick();
                    gameRenderer.stopStartScreenMusic();
                    stateManager.setCurrentState(GameState.QUEST_SCREEN);
                    resetCallback.run();
                }
                break;
                
            case QUEST_SCREEN:
                // Check if NEXT button was clicked
                if (clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                    clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                    gameRenderer.playButtonClick();
                    
                    // Check if this is the final quest (no regular level content)
                    if (levelManager.getCurrentLevel() == null) {
                        stateManager.setCurrentState(GameState.BOSS_FIGHT);
                    } else {
                        gameRenderer.playGameplayMusic();
                        stateManager.setCurrentState(GameState.PLAYING);
                    }
                    setupCallback.run();
                }
                break;        
                
            case PLAYING:
            case BOSS_FIGHT:
                // Left click creates a new player shot (if under max limit)
                if (entityManager.getShots().size() < MAX_SHOTS) {
                    Shot newShot = entityManager.getPlayer().shoot();
                    if (newShot != null) {
                        entityManager.addShot(newShot);
                        gameRenderer.playPlayerShootSound();
                    }
                }
                break;
            
            case LEVEL_DONE:
                LevelDone doneScreen = levelManager.getLevelDoneScreen();
                if (doneScreen != null) {
                    doneScreen.handleClick(clickX, clickY);
                    if (doneScreen.isOkPressed()) {
                        if (levelManager.getCurrentLevelNum() == 1) {
                            levelManager.advanceToNextLevel();
                            stateManager.setCurrentState(GameState.QUEST_SCREEN);
                            setupCallback.run();
                        } else if (levelManager.getCurrentLevelNum() == 2) {
                            levelManager.advanceToNextLevel();
                            stateManager.setCurrentState(GameState.QUEST_SCREEN);
                            setupCallback.run();
                        } else if (levelManager.getCurrentLevelNum() == 3) {
                            levelManager.advanceToNextLevel();
                            stateManager.setCurrentState(GameState.QUEST_SCREEN);
                            setupCallback.run();
                        } else if (levelManager.getCurrentLevelNum() == 4) {
                            levelManager.loadFinalBoss();
                            stateManager.setCurrentState(GameState.QUEST_SCREEN);
                            setupCallback.run();
                        }
                        doneScreen.setOkPressed(false);
                    }
                }
                // Note: If levelManager.getLevelDoneScreen() returns null
                // (should not happen for regular levels), do nothing
                break;
                
            case END_SCREEN:
                uiManager.handleEndScreenClick(clickX, clickY);
                if (uiManager.isEndScreenOkPressed()) {
                    stateManager.setCurrentState(GameState.START_SCREEN);
                    resetCallback.run();
                    uiManager.resetEndScreenOkPressed();
                }
                break;
                
            case GAME_OVER:
                stateManager.setCurrentState(GameState.START_SCREEN);
                setupCallback.run();
                break;
        }
    }
}