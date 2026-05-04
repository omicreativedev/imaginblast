package application;

import javafx.scene.image.Image;

/**
 * QUEST 3
 * Instructions for Level 3
 * Note: File adapted from Quest01.java. See comments for details.
 */
public class Quest03 extends Quest {
    
    public Quest03() {
    	// Instructions on Quest Screen
        super(3, "Collect 1 acorns, 1 donuts, 1 cupcakes, defeat 1 squirrels, 1 pillbugs, and 1 garlic to go to the next level");
        // Background Image
        this.background = new Image("quest_bg_03.png");
    }
    
    @Override
    public void draw() {
        // Drawing handled by renderer
    }
}