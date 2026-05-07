package application;

import javafx.scene.image.Image;

// Adapted from Quest01.java

// "I've tried the roots of trees, and I've tried banks, 
// and I've tried hedges," the Pigeon went on, 
// "but those serpents! There's no pleasing them!" 
// ~ The Pigeon, Alice's Adventures in Wonderland

/**
 * QUEST 04 CLASS
 * Quest screen for Level 4
 * Displays objectives for the fourth level
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Quest04 extends Quest {
    
    /**
     * QUEST 04 CONSTRUCTOR
     * Sets the quest text for Level 4 and loads the background image
     */
    public Quest04() {
        super(4, "Collect 1 acorn, 1 donut, 1 cupcake, 1 cassette, defeat \n1 squirrel, 1 pillbug, 1 garlic, and 1 urchin to go to the next level");
        this.background = new Image("quest_bg_04.png");
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