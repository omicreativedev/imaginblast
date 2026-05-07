package application;

import javafx.scene.image.Image;

// Adapted from Quest01.java

// "Speak English!" said the Eaglet. 
// "I don't know the meaning of half those long words." 
// ~ The Eaglet, Alice's Adventures in Wonderland

/**
 * QUEST 03 CLASS
 * Quest screen for Level 3
 * Displays objectives for the third level
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class Quest03 extends Quest {
    
    /**
     * QUEST 03 CONSTRUCTOR
     * Sets the quest text for Level 3 and loads the background image
     */
    public Quest03() {
        super(3, "Collect 1 acorn, 1 donut, 1 cupcake, defeat 1 squirrel, \n1 pillbug, and 1 garlic to go to the next level");
        this.background = new Image("quest_bg_03.png");
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