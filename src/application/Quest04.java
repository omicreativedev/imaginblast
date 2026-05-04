package application;

import javafx.scene.image.Image;

/**
 * QUEST 4
 * Instructions for Level 4
 * Note: File adapted from Quest01.java. See comments for details.
 */
public class Quest04 extends Quest {
    
    public Quest04() {
    	// Instructions on Quest Screen
        super(4, "Collect 1 acorns, 1 donuts, 1 cupcakes, 1 cassettes, defeat \n1 squirrels, 1 pillbugs, and 1 garlic, 1 urchins to go to the next level");
        // Background Image
        this.background = new Image("quest_bg_04.png");
    }
    
    @Override
    public void draw() {
        // Drawing handled by renderer
    }
}