package application;

import javafx.scene.image.Image;

//Adapted from Quest01.java

/**
 * QUEST 2
 * Instructions for Level 2
 * Note: File adapted from Quest01.java. See comments for details.
 */
public class Quest02 extends Quest {
    
    public Quest02() {
    	// Instructions on Quest Screen
        super(2, "Collect 2 acorns, 2 donuts, defeat 2 squirrels, and \ndefeat 2 pillbugs to go to the next level");
        // Background Image
        this.background = new Image("quest_bg_02.png");
    }
    
    @Override
    public void draw() {
        // Drawing handled by renderer
    }
}