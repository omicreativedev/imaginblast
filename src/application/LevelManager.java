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
    
    // Load Level 3
    private void loadLevel3() {
        currentLevel = new Level03();
        currentQuest = new Quest03();
        currentBossScreen = new BossScreen03();
        currentLevelDoneScreen = new LevelDone03();
    }
    
    // Load Level 4
    private void loadLevel4() {
        currentLevel = new Level04();
        currentQuest = new Quest04();
        currentBossScreen = new BossScreen04();
        currentLevelDoneScreen = new LevelDone04();
    }
    
    // Load Final Boss (no level, just quest and boss screen)
    public void loadFinalBoss() {
        currentQuest = new QuestFinal();
        currentBossScreen = new BossScreenFinal();
        currentLevelDoneScreen = null; // Will use EndScreen instead
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
    
    // Advance to next level (only for levels 1-4, not for final boss)
    public void advanceToNextLevel() {
        currentLevelNum++;
        
        if (currentLevelNum == 2) {
            loadLevel2();
        } else if (currentLevelNum == 3) {
            loadLevel3();
        } else if (currentLevelNum == 4) {
            loadLevel4();
        }
        // currentLevelNum never goes to 5 - final boss is handled separately
        
        bossDefeated = false;
    }
}