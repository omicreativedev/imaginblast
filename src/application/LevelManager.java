package application;

/**
 * Manages all level-specific content and progression
 */
public class LevelManager {
    private int currentLevelNum;
    private Level currentLevel;
    private Quest currentQuest;
    private BossScreen currentBossScreen;
    private LevelDone currentLevelDoneScreen;
    private boolean bossDefeated;
    
    public LevelManager() {
        currentLevelNum = 1;
        loadLevel1();
        bossDefeated = false;
    }
    
    // Load Level 1
    private void loadLevel1() {
        currentLevel = new Level01();
        currentQuest = new Quest01();
        currentBossScreen = new BossScreen01();
        currentLevelDoneScreen = new LevelDone01();
    }
    
    // Load Level 2
    private void loadLevel2() {
        currentLevel = new Level02();
        currentQuest = new Quest02();
        currentBossScreen = new BossScreen02();
        currentLevelDoneScreen = new LevelDone02();
    }
    
    // Getters
    public Level getCurrentLevel() { return currentLevel; }
    public Quest getQuest() { return currentQuest; }
    public BossScreen getBossScreen() { return currentBossScreen; }
    public LevelDone getLevelDoneScreen() { return currentLevelDoneScreen; }
    public boolean isBossDefeated() { return bossDefeated; }
    public int getCurrentLevelNum() { return currentLevelNum; }
    
    // Setters
    public void setBossDefeated(boolean defeated) { bossDefeated = defeated; }
    
    public void resetForNewGame() {
        bossDefeated = false;
        currentLevelNum = 1;
        loadLevel1();
    }
    
    public void reset() {
        currentLevelNum = 1;
        loadLevel1();
        bossDefeated = false;
    }
    
    // Advance to next level
    public void advanceToNextLevel() {
        currentLevelNum++;
        
        if (currentLevelNum == 2) {
            loadLevel2();
        }
        
        bossDefeated = false;
    }
}