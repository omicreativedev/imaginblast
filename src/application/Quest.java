package application;

import javafx.scene.image.Image;

/**
 * QUEST BASE CLASS
 * Defines the quest text for each level
 */
public abstract class Quest {
    protected int levelNumber;
    protected String questText;
    protected Image background;
    
    public Quest(int levelNumber, String questText) {
        this.levelNumber = levelNumber;
        this.questText = questText;
    }
    
    public abstract void draw();
    
    public String getQuestText() { return questText; }
    public int getLevelNumber() { return levelNumber; }
    public Image getBackground() { return background; }
}