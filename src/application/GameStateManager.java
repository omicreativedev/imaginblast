package application;

/**
 * Manages the current game state and transitions
 * Moved from ImaginBlastMain.java
 */

public class GameStateManager {
    private GameState currentState;
    
    public GameStateManager() {
        currentState = GameState.START_SCREEN;
    }
    
    public GameState getCurrentState() {
        return currentState;
    }
    
    public void setCurrentState(GameState newState) {
        this.currentState = newState;
    }
    
    public boolean isInState(GameState state) {
        return currentState == state;
    }
}