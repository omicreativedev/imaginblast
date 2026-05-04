package application;

import javafx.scene.canvas.GraphicsContext;

/**
 * Manages all UI screens (start screen, quest screen, level done screen)
 * Tells GameRenderer.java what to draw based on method calls from Main
 */
public class UIManager {
    private StartScreen startScreen;
    private GameRenderer gameRenderer;
    private EndScreen endScreen; //new - For final game completion screen
    
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
    
    public void drawQuestScreen(Quest quest) { //Needs to be edited to get rid of Quest01
    	gameRenderer.drawQuestScreen(quest); //I try to put it as just 'Quest' without the '01', and I get an error
    }
    
    public void drawLevelDoneScreen(LevelDone screen) {
        screen.draw(gameRenderer.getGc());
    }
    
    public void drawGameOverScreen(int score) {
    	gameRenderer.drawGameOver(score);
    }
    
    //new - Draw the end screen after final boss
    public void drawEndScreen(int finalScore, boolean gameWon) {
        endScreen = new EndScreen(finalScore, gameWon);
        endScreen.draw(gameRenderer.getGc());
    }
    
    //new - Handle click on end screen
    public void handleEndScreenClick(double x, double y) {
        if (endScreen != null) {
            endScreen.handleClick(x, y);
        }
    }
    
    //new - Check if OK button was pressed on end screen
    public boolean isEndScreenOkPressed() {
        return endScreen != null && endScreen.isOkPressed();
    }
    
    //new - Reset end screen OK button
    public void resetEndScreenOkPressed() {
        if (endScreen != null) {
            endScreen.setOkPressed(false);
        }
    }
    
    public void resetStartScreen() {
        startScreen = new StartScreen();
    }
    
    // For GameRenderer to access gc if needed
    public GraphicsContext getGc() {
        return gameRenderer.getGc();
    }
}