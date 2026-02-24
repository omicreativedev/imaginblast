package application;

import javafx.scene.input.MouseEvent;

/**
 * Handles all mouse input for the game
 */
public class InputHandler {
    private GameStateManager stateManager;
    private LevelManager levelManager;
    private EntityManager entityManager;
    private int MAX_SHOTS;
    private int WIDTH;
    private int HEIGHT;
    
    // Track mouse position
    private double mouseX;
    
    public InputHandler(GameStateManager stateManager, LevelManager levelManager, 
                        EntityManager entityManager, int MAX_SHOTS, int WIDTH, int HEIGHT) {
        this.stateManager = stateManager;
        this.levelManager = levelManager;
        this.entityManager = entityManager;
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
    
    public void handleMouseClicked(MouseEvent e, Runnable setupCallback) {
        double clickX = e.getX();
        double clickY = e.getY();
        
        switch(stateManager.getCurrentState()) {
        
            case START_SCREEN:
                if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                   clickY >= HEIGHT/2 && clickY <= HEIGHT/2 + 50) {
                    stateManager.setCurrentState(GameState.QUEST_SCREEN);
                    setupCallback.run();
                }
                break;
                
            case QUEST_SCREEN:
                if(clickX >= WIDTH/2 - 100 && clickX <= WIDTH/2 + 100 &&
                   clickY >= HEIGHT/2 + 100 && clickY <= HEIGHT/2 + 150) {
                    levelManager.resetForNewGame();
                    stateManager.setCurrentState(GameState.PLAYING);
                    setupCallback.run();
                }
                break;            
                
            case PLAYING:
            case BOSS_FIGHT:
                // Shoot
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