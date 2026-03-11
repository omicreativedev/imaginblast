package application;

/**
 * QUEST BASE CLASS
 * Defines the quest text for each level
 */
public abstract class Quest {
    protected int levelNumber;
    protected String questText;
    
    public Quest(int levelNumber, String questText) {
        this.levelNumber = levelNumber;
        this.questText = questText;
    }
    
    public abstract void draw(); // Will be implemented in renderer
    
    public String getQuestText() { return questText; }
    public int getLevelNumber() { return levelNumber; }
}