package application;

// "Oh dear! Oh dear! I shall be too late!"
// ~ The White Rabbit, Alice's Adventures in Wonderland

/**
 * GAME STATE MANAGER CLASS
 * Manages the current game state and handles transitions between states
 * States include: START_SCREEN, QUEST_SCREEN, PLAYING, BOSS_FIGHT, LEVEL_DONE, END_SCREEN, GAME_OVER
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class GameStateManager {
    
    // Current game state (which screen the player is on)
    private GameState currentState;
    
    /**
     * GAME STATE MANAGER CONSTRUCTOR
     * Initializes the game at the start screen
     */
    public GameStateManager() {
        currentState = GameState.START_SCREEN;
    }
    
    /**
     * GET CURRENT STATE METHOD
     * Returns the current game state
     * 
     * @return The active GameState (START_SCREEN, PLAYING, etc.)
     */
    public GameState getCurrentState() {
        return currentState;
    }
    
    /**
     * SET CURRENT STATE METHOD
     * Changes the game to a new state
     * Used for transitions between screens and game phases
     * 
     * @param newState The GameState to transition to
     */
    public void setCurrentState(GameState newState) {
        this.currentState = newState;
    }
    
    /**
     * IS IN STATE CHECK
     * Determines if the game is currently in a specific state
     * Useful for conditional logic in the main game loop
     * 
     * @param state The GameState to check against
     * @return true if current state matches the given state, false otherwise
     */
    public boolean isInState(GameState state) {
        return currentState == state;
    }
}