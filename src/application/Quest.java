package application;

import javafx.scene.image.Image;

/**
 * QUEST BASE CLASS
 * Defines the quest text for each level
 */
public abstract class Quest {
    protected int levelNumber;
    protected String questText;
    protected Image background; //new - Background image for quest screen
    
    public Quest(int levelNumber, String questText) {
        this.levelNumber = levelNumber;
        this.questText = questText;
    }
    
    public abstract void draw(); // Will be implemented in renderer
    
    public String getQuestText() { return questText; }
    public int getLevelNumber() { return levelNumber; }
    public Image getBackground() { return background; } //new - Getter for background
}