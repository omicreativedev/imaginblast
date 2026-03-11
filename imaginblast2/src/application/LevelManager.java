package application;

/**
 * Manages all level-specific content and progression
 */
public class LevelManager {
    private int currentLevelNum;
    private Level01 currentLevel;
    private Quest01 quest01;
    private BossScreen01 bossScreen;
    private LevelDone01 levelDoneScreen;
    private boolean bossDefeated;
    
    public LevelManager() {
        currentLevelNum = 1;
        currentLevel = new Level01();
        quest01 = new Quest01();
        bossScreen = new BossScreen01();
        levelDoneScreen = new LevelDone01();
        bossDefeated = false;
    }
    
    // Getters
    public Level01 getCurrentLevel() { return currentLevel; }
    public Quest01 getQuest() { return quest01; }
    public BossScreen01 getBossScreen() { return bossScreen; }
    public LevelDone01 getLevelDoneScreen() { return levelDoneScreen; }
    public boolean isBossDefeated() { return bossDefeated; }
    public int getCurrentLevelNum() { return currentLevelNum; }
    
    // Setters
    public void setBossDefeated(boolean defeated) { bossDefeated = defeated; }
    
    public void resetForNewGame() {
        bossDefeated = false;
        bossScreen = new BossScreen01();
        // Reset other level-specific things if needed
    }
    
    public void reset() {
        currentLevelNum = 1;
        currentLevel = new Level01();
        quest01 = new Quest01();
        bossScreen = new BossScreen01();
        levelDoneScreen = new LevelDone01();
        bossDefeated = false;
    }
    
    // For future Level 2
    public void advanceToNextLevel() {
        currentLevelNum++;
        // In future: switch to load revelant level classes
        // currentLevel = new Level02();
        // quest01 = new Quest02();
        // bossScreen = new BossScreen02();
        // levelDoneScreen = new LevelDone02();
    }
}