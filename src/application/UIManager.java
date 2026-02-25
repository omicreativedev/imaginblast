package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * Manages all UI screens (start screen, quest screen, level done screen)
 * Tells GameRenderer.java what to draw based on method calls from Main
 */
public class UIManager {
    private StartScreen startScreen;
    private GameRenderer renderer;
    
    public UIManager(GameRenderer renderer) {
        this.renderer = renderer;
        this.startScreen = new StartScreen();
    }
    
    // 
    public void drawStartScreen() {
        renderer.drawStartScreen(startScreen);
    }
    
    public void drawQuestScreen(Quest01 quest) {
        renderer.drawQuestScreen(quest);
    }
    
    public void drawLevelDoneScreen(LevelDone01 screen) {
        screen.draw(renderer.getGc());
    }
    
    public void drawGameOverScreen(int score) {
        renderer.drawGameOver(score);
    }
    
    public void resetStartScreen() {
        startScreen = new StartScreen();
    }
    
    // For GameRenderer to access gc if needed
    public GraphicsContext getGc() {
        return renderer.getGc();
    }
}