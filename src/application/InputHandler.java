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
    private UIManager uiManager; //new - For end screen handling
    private int MAX_SHOTS;
    private int WIDTH;
    private int HEIGHT;
    
    // Keyboard tracking W, A, S, D and Shift (for sprinting!)
    private Set<String> activeKeys = new HashSet<>();
    //private Set<String> sprintKey = new HashSet<>();
    
    // Track the mouse position
    private double mouseX;
    private double mouseY;
    
    public boolean isUpPressed()    { return activeKeys.contains("W"); }
    public boolean isDownPressed()  { return activeKeys.contains("S"); }
    public boolean isLeftPressed()  { return activeKeys.contains("A"); }
    public boolean isRightPressed() { return activeKeys.contains("D"); }
    
    //Sprinting = 'F' key pressed
    	//Learned that 'shift' key wouldn't work by: Oracle (2015), https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyEvent.html
    public boolean isFPressed() {return activeKeys.contains("F");}
    
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
    
    public InputHandler(GameStateManager stateManager, LevelManager levelManager, 
            EntityManager entityManager, GameRenderer gameRenderer, UIManager uiManager,
            int MAX_SHOTS, int WIDTH, int HEIGHT) {
    		this.stateManager = stateManager;
    		this.levelManager = levelManager;
    		this.entityManager = entityManager;
    		this.gameRenderer = gameRenderer;
    		this.uiManager = uiManager; //new
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
        
        
        	///NEW CODE that includes 'F' key
        if (key.equals("W") || key.equals("A") || key.equals("S") || key.equals("D") || key.equals("F")) {
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
    public void handleMouseClicked(MouseEvent e, Runnable setupCallback, Runnable resetCallback) {
        double clickX = e.getX();
        double clickY = e.getY();
        
        switch(stateManager.getCurrentState()) {
        
        case START_SCREEN:
            if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
               clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                gameRenderer.playButtonClick();
                gameRenderer.stopStartScreenMusic();
                stateManager.setCurrentState(GameState.QUEST_SCREEN);
                resetCallback.run();
            }
            break;
                
        case QUEST_SCREEN:
            if (clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                gameRenderer.playButtonClick();
                
                // Check if this is the final quest (no current level)
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
            // Left click creates a new player shot
            if(entityManager.getShots().size() < MAX_SHOTS) {
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
            } else {
                // No level done screen (final boss went directly to END_SCREEN)
                // This should not happen, but if it does, do nothing
            }
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