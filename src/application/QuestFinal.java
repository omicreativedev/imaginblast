package application;

// Adapted from Quest01.java

// "I shall sit here," the Footman remarked, 
// "till tomorrow..." 
// ~ The Fish Footman, Alice's Adventures in Wonderland

/**
 * QUEST FINAL CLASS
 * Quest screen for the Final Boss (Bunny)
 * Displays the final objective before battling the last boss
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class QuestFinal extends Quest {
    
    /**
     * QUEST FINAL CONSTRUCTOR
     * Sets the quest text for the final boss and uses default background
     * No background image needed - GameRenderer handles the final quest screen
     */
    public QuestFinal() {
        super(5, "Defeat the Final Boss Bunny!");
        // No background image - GameRenderer uses default
    }
    
    /**
     * OVERRIDE DRAW METHOD
     * Drawing is handled by GameRenderer, not used here
     */
    @Override
    public void draw() {
        // Drawing handled by GameRenderer
    }
}