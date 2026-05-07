package application;

// "They were learning to draw," the Dormouse continued, yawning, 
// "and they drew all manner of things—everything that begins with an M—" 
// ~ The Dormouse, Alice's Adventures in Wonderland

/**
 * LEVEL MANAGER CLASS
 * Manages all level-specific content and game progression
 * Handles loading levels, quests, boss screens, and transitions between them
 * Reference: https://textbooks.cs.ksu.edu/cc210/13-inheritance/06-java/06-abstract-classes/
 */
public class LevelManager {
    
    // Current progression state
    private int currentLevelNum;           // Current level number (1-4, then final boss)
    private Level currentLevel;            // Active level (enemy spawns, etc.)
    private Quest currentQuest;            // Active quest screen for this level
    private BossScreen currentBossScreen;  // Active boss screen for this level
    private LevelDone currentLevelDoneScreen; // Level completion screen
    private boolean bossDefeated;          // True when current boss has been defeated
    
    /**
     * LEVEL MANAGER CONSTRUCTOR
     * Initializes the game at Level 1
     */
    public LevelManager() {
        currentLevelNum = 1;
        loadLevel1();
        bossDefeated = false;
    }
    
    /**
     * LOAD LEVEL 1 METHOD
     * Loads Level 01, Quest 01, BossScreen 01, and LevelDone 01
     */
    private void loadLevel1() {
        currentLevel = new Level01();
        currentQuest = new Quest01();
        currentBossScreen = new BossScreen01();
        currentLevelDoneScreen = new LevelDone01();
    }
    
    /**
     * LOAD LEVEL 2 METHOD
     * Loads Level 02, Quest 02, BossScreen 02, and LevelDone 02
     */
    private void loadLevel2() {
        currentLevel = new Level02();
        currentQuest = new Quest02();
        currentBossScreen = new BossScreen02();
        currentLevelDoneScreen = new LevelDone02();
    }
    
    /**
     * LOAD LEVEL 3 METHOD
     * Loads Level 03, Quest 03, BossScreen 03, and LevelDone 03
     */
    private void loadLevel3() {
        currentLevel = new Level03();
        currentQuest = new Quest03();
        currentBossScreen = new BossScreen03();
        currentLevelDoneScreen = new LevelDone03();
    }
    
    /**
     * LOAD LEVEL 4 METHOD
     * Loads Level 04, Quest 04, BossScreen 04, and LevelDone 04
     */
    private void loadLevel4() {
        currentLevel = new Level04();
        currentQuest = new Quest04();
        currentBossScreen = new BossScreen04();
        currentLevelDoneScreen = new LevelDone04();
    }
    
    /**
     * LOAD FINAL BOSS METHOD
     * Loads the final boss quest and boss screen (Bunny)
     * No regular level content - just the boss fight
     * Level completion screen is null (EndScreen will be used instead)
     */
    public void loadFinalBoss() {
        currentLevel = null;
        currentQuest = new QuestFinal();
        currentBossScreen = new BossScreenFinal();
        currentLevelDoneScreen = null; // EndScreen will handle victory
    }
    
    /**
     * GET CURRENT LEVEL METHOD
     * 
     * @return The active Level object (or null for final boss)
     */
    public Level getCurrentLevel() { 
        return currentLevel; 
    }
    
    /**
     * GET QUEST METHOD
     * 
     * @return The active Quest screen object
     */
    public Quest getQuest() { 
        return currentQuest; 
    }
    
    /**
     * GET BOSS SCREEN METHOD
     * 
     * @return The active BossScreen object
     */
    public BossScreen getBossScreen() { 
        return currentBossScreen; 
    }
    
    /**
     * GET LEVEL DONE SCREEN METHOD
     * 
     * @return The active LevelDone screen object (or null for final boss)
     */
    public LevelDone getLevelDoneScreen() { 
        return currentLevelDoneScreen; 
    }
    
    /**
     * IS BOSS DEFEATED CHECK
     * 
     * @return true if current boss has been defeated, false otherwise
     */
    public boolean isBossDefeated() { 
        return bossDefeated; 
    }
    
    /**
     * GET CURRENT LEVEL NUMBER METHOD
     * 
     * @return Current level number (1-4, final boss is handled separately)
     */
    public int getCurrentLevelNum() { 
        return currentLevelNum; 
    }
    
    /**
     * SET BOSS DEFEATED METHOD
     * 
     * @param defeated true when boss is defeated, false when resetting
     */
    public void setBossDefeated(boolean defeated) { 
        bossDefeated = defeated; 
    }
    
    /**
     * RESET FOR NEW GAME METHOD
     * Resets progression and loads Level 1 for a brand new game
     */
    public void resetForNewGame() {
        bossDefeated = false;
        currentLevelNum = 1;
        loadLevel1();
    }
    
    /**
     * RESET METHOD
     * Resets progression to Level 1 (alias for resetForNewGame)
     */
    public void reset() {
        currentLevelNum = 1;
        loadLevel1();
        bossDefeated = false;
    }
    
    /**
     * ADVANCE TO NEXT LEVEL METHOD
     * Moves to the next level (1->2, 2->3, 3->4)
     * Final boss is handled separately by ImaginBlastMain
     * currentLevelNum never goes to 5
     */
    public void advanceToNextLevel() {
        currentLevelNum++;
        
        if (currentLevelNum == 2) {
            loadLevel2();
        } else if (currentLevelNum == 3) {
            loadLevel3();
        } else if (currentLevelNum == 4) {
            loadLevel4();
        }
        // Note: Level 5 does not exist - final boss is loaded separately
        
        bossDefeated = false;
    }
}