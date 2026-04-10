package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * Manages all UI screens (start screen, quest screen, level done screen)
 * Tells GameRenderer.java what to draw based on method calls from Main
 */
public class UIManager {
    private StartScreen startScreen;
    private GameRenderer gameRenderer;
    
    public UIManager(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.startScreen = new StartScreen();
    }
    
    // Example. When Main calls drawStartScreen()
    // This will tell GameRenderer.java to do
    // public void drawStartScreen(StartScreen startScreen)
    public void drawStartScreen() {
    	gameRenderer.drawStartScreen(startScreen);
    }
    
    public void drawQuestScreen(Quest01 quest) { //Needs to be edited to get rid of Quest01
    	gameRenderer.drawQuestScreen(quest); //I try to put it as just 'Quest' without the '01', and I get an error
    }
    
    public void drawLevelDoneScreen(LevelDone screen) {
        screen.draw(gameRenderer.getGc());
    }
    
    public void drawGameOverScreen(int score) {
    	gameRenderer.drawGameOver(score);
    }
    
    public void resetStartScreen() {
        startScreen = new StartScreen();
    }
    
   
    
    
    // For GameRenderer to access gc if needed
    public GraphicsContext getGc() {
        return gameRenderer.getGc();
    }
}