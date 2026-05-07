package application;

import javafx.scene.image.Image;

// "I mean what I say," the Mock Turtle 
// replied in an offended tone.
// ~ The Mock Turtle, Alice's Adventures in Wonderland

/**
 * QUEST BASE CLASS
 * Abstract base class that defines the quest text for each level
 * Extended by Quest01, Quest02, Quest03, Quest04, and QuestFinal
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public abstract class Quest {
    
    // Quest properties
    protected int levelNumber;   // Which level this quest belongs to (1-4, then final)
    protected String questText;  // The objective text displayed to the player
    protected Image background;  // Background image for the quest screen
    
    /**
     * QUEST CONSTRUCTOR
     * Creates a new quest for a specific level with given text
     * 
     * @param levelNumber The level number (1-4, then final boss)
     * @param questText The quest objective text to display
     */
    public Quest(int levelNumber, String questText) {
        this.levelNumber = levelNumber;
        this.questText = questText;
    }
    
    /**
     * ABSTRACT DRAW METHOD
     * Each quest type implements its own drawing logic
     * Currently not heavily used - rendering is handled by GameRenderer
     */
    public abstract void draw();
    
    /**
     * GET QUEST TEXT METHOD
     * 
     * @return The quest objective text
     */
    public String getQuestText() { 
        return questText; 
    }
    
    /**
     * GET LEVEL NUMBER METHOD
     * 
     * @return The level number this quest belongs to
     */
    public int getLevelNumber() { 
        return levelNumber; 
    }
    
    /**
     * GET BACKGROUND METHOD
     * 
     * @return The background image for the quest screen
     */
    public Image getBackground() { 
        return background; 
    }
}