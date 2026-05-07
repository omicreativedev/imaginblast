package application;

import javafx.scene.image.Image;

// Adpted from Quest01.java

// "I shall sit here," the Footman remarked, 
// "till tomorrow..." 
// ~ The Frog Footman, Alice's Adventures in Wonderland

/**
 * QUEST 02 CLASS
 * Quest screen for Level 2
 * Displays objectives for the second level
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Quest02 extends Quest {
    
    /**
     * QUEST 02 CONSTRUCTOR
     * Sets the quest text for Level 2 and loads the background image
     */
    public Quest02() {
        super(2, "Collect 2 acorns, 2 donuts, defeat 2 squirrels, and \ndefeat 2 pillbugs to go to the next level");
        this.background = new Image("quest_bg_02.png");
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