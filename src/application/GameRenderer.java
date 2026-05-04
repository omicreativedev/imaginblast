package application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Map;


/**
 * GAME RENDERER CLASS
 * Separates rendering logic from game logic
 * Handles all drawing operations for different game states
 */
public class GameRenderer {
   
    private GraphicsContext gc;
    private Image startupBgGif;
    
    // AudioClip works for Music but some isn't working
    private AudioClip startScreenMusic;
    private AudioClip buttonClickSound;
    private AudioClip gameplayMusic;
    // Media Player is working for some encoding better.
    private MediaPlayer bossMusic;
    private MediaPlayer itemCollectSound;
    private MediaPlayer playerShootSound;
    private MediaPlayer questMusic;
    private MediaPlayer portalSound;
    private MediaPlayer playerDamageSound;
    private MediaPlayer explodeSound;
    
    private boolean isHoveringPlayButton = false;  
    
    /**
     * CONSTRUCTOR
     * @param gc GraphicsContext from the Canvas - all drawing goes through this
     */
    public GameRenderer(GraphicsContext gc) {
        this.gc = gc;
        this.startupBgGif = new Image("bg_blinking_startup.gif");
        
      
    // Music for start up screen
    String musicUrl = getClass().getResource("/wav_start_screen_music.wav").toString();
    this.startScreenMusic = new AudioClip(musicUrl);
    this.startScreenMusic.setCycleCount(1);
    this.startScreenMusic.play();
    
    // Sound effect for clicking buttons
    String clickUrl = getClass().getResource("/wav_button_click.wav").toString();
    this.buttonClickSound = new AudioClip(clickUrl);
    
    // Music for the play screens (all screens currently using the same music)
    String gameplayUrl = getClass().getResource("/wav_play_screen_music.wav").toString();
    this.gameplayMusic = new AudioClip(gameplayUrl);
    this.gameplayMusic.setCycleCount(AudioClip.INDEFINITE);
    
    // Music for the boss screens
    try {
        String bossMusicUrl = getClass().getResource("/mp3_boss_music.mp3").toString();
        Media bossMedia = new Media(bossMusicUrl);
        this.bossMusic = new MediaPlayer(bossMedia);
        this.bossMusic.setCycleCount(MediaPlayer.INDEFINITE);
        this.bossMusic.setRate(1.0);
        // Preload the audio
        this.bossMusic.play();
        this.bossMusic.pause();
        this.bossMusic.seek(javafx.util.Duration.ZERO);
    } catch (Exception e) {
        System.out.println("Could not load boss music: " + e.getMessage());
    }
    
    // Collecting item sound effects
    try {
        String itemCollectUrl = getClass().getResource("/wav_item_collect.wav").toString();
        Media itemCollectMedia = new Media(itemCollectUrl);
        this.itemCollectSound = new MediaPlayer(itemCollectMedia);
    } catch (Exception e) {
        System.out.println("Could not load item collect sound: " + e.getMessage());
    }
    
    // Player shooting sound
    try {
        String playerShootUrl = getClass().getResource("/wav_player_shoot.wav").toString();
        Media playerShootMedia = new Media(playerShootUrl);
        this.playerShootSound = new MediaPlayer(playerShootMedia);
    } catch (Exception e) {
        System.out.println("Could not load player shoot sound: " + e.getMessage());
    }
    
    // Quest background music
    try {
        String questMusicUrl = getClass().getResource("/wav_quest_music_fixed.wav").toString();
        Media questMedia = new Media(questMusicUrl);
        this.questMusic = new MediaPlayer(questMedia);
        this.questMusic.setCycleCount(MediaPlayer.INDEFINITE);
    } catch (Exception e) {
        System.out.println("Could not load quest music: " + e.getMessage());
    }
    
    // Explosion sound
    try {
        String explodeUrl = getClass().getResource("/wav_explode.wav").toString();
        Media explodeMedia = new Media(explodeUrl);
        this.explodeSound = new MediaPlayer(explodeMedia);
    } catch (Exception e) {
        System.out.println("Could not load explosion sound: " + e.getMessage());
    }
    
    // Portal sound
    try {
        String portalSoundUrl = getClass().getResource("/wav_going_through_portal.wav").toString();
        Media portalMedia = new Media(portalSoundUrl);
        this.portalSound = new MediaPlayer(portalMedia);
    } catch (Exception e) {
        System.out.println("Could not load portal sound: " + e.getMessage());
    }
    
    // Player damage sound
    try {
        String playerDamageUrl = getClass().getResource("/wav_player_take_damage.wav").toString();
        Media playerDamageMedia = new Media(playerDamageUrl);
        this.playerDamageSound = new MediaPlayer(playerDamageMedia);
    } catch (Exception e) {
        System.out.println("Could not load player damage sound: " + e.getMessage());
    }
} 
    
    /**
     * Change the button color of the button when hover
     * 
     * @param mouseX Current mouse X coordinate
     * @param mouseY Current mouse Y coordinate
     */
    public void updateButtonHover(double mouseX, double mouseY) {
        int buttonX = ImaginBlastMain.WIDTH/2 - 100;
        int buttonY = ImaginBlastMain.HEIGHT/2 + 100;
        int buttonWidth = 200;
        int buttonHeight = 50;
        
        isHoveringPlayButton = (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                                mouseY >= buttonY && mouseY <= buttonY + buttonHeight);
    }
    
    /**
     * Clear the screen with forest green background
     * Called at the beginning of each frame in PLAYING state
     * This is the base layer that everything else draws on top of
     */
    public void clearScreen() {
        gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
    }
    
    /**
     * GETTER
     * Provides access to the GraphicsContext for other classes
     * @return The GraphicsContext instance
     */
    public GraphicsContext getGc() {
        return gc;
    }
    
    /**
     * drawHUD
     * Shows player information during normal gameplay PLAYING state
     * 
     * @param score Current player score
     * @param shotsSize Number of active player shots on screen
     * @param maxShots Maximum allowed player shots
     * @param acornCount Number of acorns collected in current level
     * @param player Player object (for health display)
     */
    public void drawHUD(int score, int shotsSize, int maxShots, Level level, Player player) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(16));
        
        // SCORE (top left)
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 10, 45); // Halfway between 20 and 70
        
        // AMMO
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 150, 45); // Halfway between 20 and 70
        
        // PLAYER HEALTH
        gc.setFill(Color.RED);
        gc.fillRect(300, 30, 200, 20); // Halfway between 5 and 55
        gc.setFill(Color.LIMEGREEN);
        double healthPercent = (double)player.hp / player.maxHp;
        gc.fillRect(300, 30, 200 * healthPercent, 20); // Halfway between 5 and 55
        gc.setFill(Color.WHITE);
        gc.fillText("HP: " + player.hp + "/" + player.maxHp, 400, 47); // Halfway between 22 and 72
        
        // ITEM GOALS (top center-right)
        int yOffset = 45; // Halfway between 20 and 70
        gc.setFill(Color.GOLD);
        gc.fillText("ITEMS:", 550, yOffset);
        yOffset += 20;
        for (Map.Entry<Class<? extends Item>, Integer> goal : level.getItemGoals().entrySet()) {
            String itemName = goal.getKey().getSimpleName().replace("Item", "");
            int collected = level.itemsCollected.getOrDefault(goal.getKey(), 0);
            gc.setFill(Color.WHITE);
            gc.fillText(itemName + ": " + collected + "/" + goal.getValue(), 570, yOffset);
            yOffset += 20;
        }
        
        // ENEMY GOALS (top right)
        yOffset = 45; // Halfway between 20 and 70
        gc.setFill(Color.RED);
        gc.fillText("ENEMIES:", 750, yOffset);
        yOffset += 20;
        for (Map.Entry<Class<? extends Enemy>, Integer> goal : level.getEnemyGoals().entrySet()) {
            String enemyName = goal.getKey().getSimpleName().replace("Enemy", "");
            int defeated = level.enemiesDefeated.getOrDefault(goal.getKey(), 0);
            gc.setFill(Color.WHITE);
            gc.fillText(enemyName + ": " + defeated + "/" + goal.getValue(), 770, yOffset);
            yOffset += 20;
        }
    }
    
    
    /**
     * drawStartScreen
     * Draws the start screen with PLAY button
     * 
     * @param startScreen The StartScreen object-not heavily used yet...
     */
    public void drawStartScreen(StartScreen startScreen) {

    	gc.drawImage(startupBgGif, 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
    	
        // Center text alignment for menu elements
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Semi-transparent black overlay box (centered, half screen size)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Calculate center positions for easier reference
        double centerX = ImaginBlastMain.WIDTH / 2;
        double boxTop = ImaginBlastMain.HEIGHT / 4;
       
        // Load and resize the logo
        final Image logo = new Image("logo_black_bg.png");
        double logoWidth = 400; // Desired width - adjust as needed
        double logoHeight = logoWidth * (logo.getHeight() / logo.getWidth()); // Maintain aspect ratio
        double logoX = centerX - (logoWidth / 2);
        double logoY = boxTop + 40; // Position from top of black box
        gc.drawImage(logo, logoX, logoY, logoWidth, logoHeight);
        
        // FrogArmy text below the logo
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(24));
        gc.fillText("FrogArmy", centerX, logoY + logoHeight + 30);
        
        // Instructions (How to Play) - positioned above the button
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Instructions: Move your mouse cursor to move, left-click on mouse to shoot!", 
                    centerX, ImaginBlastMain.HEIGHT/2 + 40);
        
        // PLAY GAME button - green rectangle
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        
        // On Mouse over Button
        if (isHoveringPlayButton) {
            gc.setFill(Color.LIMEGREEN);
        } else {
            gc.setFill(Color.GREEN);
        }
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);  
                
        // Button text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("PLAY", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);

        // Button border to make it visually distinct
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
    }
    
    /**
     * drawQuestScreen
     * Shows the level objective/quest text before gameplay begins
     * 
     * @param quest The Quest01 object containing quest text
     */
    public void drawQuestScreen(Quest quest) {
        // Draw quest background - ADD THIS LINE
        gc.drawImage(quest.getBackground(), 0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Center text alignment
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Semi-transparent black overlay box (same style as start screen)
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Quest title
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Level " + quest.getLevelNumber() + " Quest", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        // Quest description
        gc.setFont(Font.font(18));
        gc.setFill(Color.WHITE);
        gc.fillText(quest.getQuestText(), ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        // OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        
        gc.setFill(Color.BLACK);
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
    }
    
    /**
     * DRAW BOSS SCREEN (COMPLETE)
     * Renders the entire boss fight scene
     * This method is called from BossScreen classes
     * 
     * @param boss The boss entity
     * @param score Current player score
     * @param shotsSize Number of active player shots
     * @param maxShots Maximum allowed player shots
     * @param player The player entity
     * @param portal The exit portal
     */
    public void drawBossScreen(Boss boss, int score, int shotsSize, int maxShots, Player player, Portal portal) {
        // Clear screen (background can be different for boss)
        clearScreen(); // Or use a different background for each boss
        
        // Draw HUD elements (simplified for boss - no acorn count)
        drawBossHUD(score, shotsSize, maxShots, player, boss);
        
        // Draw boss (delegates to Boss's own draw method)
        boss.draw(gc);
        
        // Draw portal if visible (delegates to Portal's draw method)
        portal.draw(gc);
    }

    /**
     * DRAW BOSS HEADS-UP DISPLAY (HUD) -- this is the toolbar with scores
     * Special HUD for boss fights (no item count, includes boss health)
     * Called by drawBossScreen()
     * 
     * @param score Current player score
     * @param shotsSize Number of active player shots
     * @param maxShots Maximum allowed player shots
     * @param player The player entity (for player health)
     * @param boss The boss entity (for boss health)
     */
    public void drawBossHUD(int score, int shotsSize, int maxShots, Player player, Boss boss) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(20));
        
        // PLAYER INFO (left side)
        // Score display
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        
        // Ammo display
        gc.fillText("Ammo: " + (maxShots - shotsSize) + "/" + maxShots, 200, 20);
        
        // Player Health Bar (below score)
        // Background
        gc.setFill(Color.RED);
        gc.fillRect(60, 40, 200, 20);
        
        // Foreground (current health percentage)
        gc.setFill(Color.LIMEGREEN);
        double playerHealthPercent = (double)player.hp / player.maxHp;
        playerHealthPercent = Math.max(0, Math.min(1, playerHealthPercent));
        gc.fillRect(60, 40, 200 * playerHealthPercent, 20);
        
        // Player health text
        gc.setFill(Color.WHITE);
        gc.fillText("Player: " + player.hp + "/" + player.maxHp, 160, 57);
        
        // BOSS INFO (top center)
        // Boss Health Bar background
        gc.setFill(Color.DARKRED);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400, 30);
        
        // Boss Health Bar foreground (current health percentage)
        gc.setFill(Color.CRIMSON);
        double bossHealthPercent = (double)boss.getHealth() / boss.getMaxHealth();
        bossHealthPercent = Math.max(0, Math.min(1, bossHealthPercent));
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 200, 20, 400 * bossHealthPercent, 30);
        
        // Boss health text (label and numbers)
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("BOSS", ImaginBlastMain.WIDTH/2, 40);
        gc.fillText(boss.getHealth() + "/" + boss.getMaxHealth(), ImaginBlastMain.WIDTH/2, 60);
    }

    /**
     * DRAW LEVEL COMPLETE SCREEN
     * Renders the screen shown after boss is defeated and portal is entered
     * Delegates drawing to the LevelDone object
     * 
     * @param levelDone The LevelDone screen object
     */
    public void drawLevelDoneScreen(LevelDone levelDone) {
        levelDone.draw(gc); // Let LevelDone handle its own drawing
    }

    /**
     * DRAW PORTAL (UTILITY METHOD)
     * Separate method for drawing just the portal if needed
     * Currently not heavily used - portal draws itself in most cases
     * 
     * @param portal The portal to draw
     */
    public void drawPortal(Portal portal) {
        portal.draw(gc);
    }
    
    
    /**
     * STOP MUSIC ON START SCREEN
     */
    public void stopStartScreenMusic() {
        if (startScreenMusic != null) {
            startScreenMusic.stop();
        }
    }
    
    /**
     * PLAY BUTTON CLICK SOUND
     */
    public void playButtonClick() {
        if (buttonClickSound != null) {
            buttonClickSound.play();
        }
    }
    
    //new - Play gameplay music
    public void playGameplayMusic() {
        if (gameplayMusic != null) {
            gameplayMusic.play();
        }
    }
    
    //new - Stop gameplay music
    public void stopGameplayMusic() {
        if (gameplayMusic != null) {
            gameplayMusic.stop();
        }
    }
    
  //new - Play boss music (MediaPlayer version)
    public void playBossMusic() {
        System.out.println("playBossMusic called, bossMusic = " + bossMusic); // DEBUG
        if (bossMusic != null) {
            System.out.println("Boss music status: " + bossMusic.getStatus()); // DEBUG
            bossMusic.stop();
            bossMusic.play();
            System.out.println("After play, status: " + bossMusic.getStatus()); // DEBUG
        } else {
            System.out.println("bossMusic is NULL!"); // DEBUG
        }
    }
    
    //new - Stop boss music
    public void stopBossMusic() {
        if (bossMusic != null) {
            bossMusic.stop();
        }
    }
    
    
  //new - Play explosion sound
    public void playExplodeSound() {
        if (explodeSound != null) {
            explodeSound.stop(); // Reset to beginning
            explodeSound.play();
        }
    }
    
    //new - Play item collect sound (MediaPlayer version)
    public void playItemCollectSound() {
        if (itemCollectSound != null) {
            itemCollectSound.stop(); //new - Reset to beginning
            itemCollectSound.play();
        }
    }
    
    //new - Play player shoot sound (MediaPlayer version)
    public void playPlayerShootSound() {
        if (playerShootSound != null) {
            playerShootSound.stop(); //new - Reset to beginning
            playerShootSound.play();
        }
    }
    
    //new - Play quest music (MediaPlayer version)
    public void playQuestMusic() {
        if (questMusic != null) {
            questMusic.play();
        }
    }
    
    //new - Stop quest music
    public void stopQuestMusic() {
        if (questMusic != null) {
            questMusic.stop();
        }
    }
    
    //new - Play portal sound (MediaPlayer version)
    public void playPortalSound() {
        if (portalSound != null) {
            portalSound.stop(); //new - Reset to beginning
            portalSound.play();
        }
    }
    
    //new - Play player damage sound (MediaPlayer version)
    public void playPlayerDamageSound() {
        if (playerDamageSound != null) {
            playerDamageSound.stop(); //new - Reset to beginning
            playerDamageSound.play();
        }
    }
    
    /**
     * DRAW GAME OVER SCREEN
     * Renders the game over screen with final score
     * Called when player health reaches zero
     * 
     * @param score Final player score to display
     */
    public void drawGameOver(int score) {
        // Black background
        gc.setFill(Color.BLACK); 
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Center all text
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(35));
        
        // Game over message with score and replay instructions
        gc.setFill(Color.YELLOW);
        gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", 
                    ImaginBlastMain.WIDTH / 2, ImaginBlastMain.HEIGHT / 2.5);
    }
}