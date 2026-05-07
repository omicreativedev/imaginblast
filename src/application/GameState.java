package application;

// "We're all mad here." ~ Cheshire Cat, Alice's Adventures in Wonderland

/**
 * GAME STATE ENUM
 * Defines all possible states the game can be in
 * Used by GameStateManager to control game flow and transitions
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public enum GameState {
    START_SCREEN,    // Title screen with PLAY button
    QUEST_SCREEN,    // Level objective display before playing
    PLAYING,         // Normal gameplay (enemies, items, collecting)
    BOSS_FIGHT,      // Boss battle arena
    LEVEL_DONE,      // Level completion screen after boss
    GAME_OVER,       // Player death screen
    END_SCREEN       // Final victory screen after final boss
}