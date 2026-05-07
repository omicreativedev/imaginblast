package application;

import javafx.scene.image.Image;

// "I'm older than you, and must know better," 
// said the Lory. ~ The Lory, Alice's Adventures in Wonderland

/**
 * QUEST 01 CLASS
 * Quest screen for Level 1
 * Displays objectives for the first level
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Quest01 extends Quest {
    
    /**
     * QUEST 01 CONSTRUCTOR
     * Sets the quest text for Level 1 and loads the background image
     */
    public Quest01() {
        super(1, "Collect 3 acorns and defeat 3 squirrels to go to the next level");
        this.background = new Image("quest_bg_01.png");
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