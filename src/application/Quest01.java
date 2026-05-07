package application;

import javafx.scene.image.Image;

//Adapted from Quest01.java

/**
 * QUEST 1
 * Instructions for Level 1
 */
public class Quest01 extends Quest {
    
    public Quest01() {
    	// Instructions on Quest Screen
        super(1, "Collect 3 acorns and defeat 3 squirrels to go to the next level");
        // Background Image
        this.background = new Image("quest_bg_01.png");
    }
    
    @Override
    public void draw() {
        // Drawing handled by renderer
    }
}