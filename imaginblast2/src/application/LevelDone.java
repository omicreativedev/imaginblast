package application;

import javafx.scene.canvas.GraphicsContext;

public abstract class LevelDone {
    protected int levelCompleted;
    protected boolean okPressed = false;
    
    public LevelDone(int levelCompleted) {
        this.levelCompleted = levelCompleted;
    }
    
    public abstract void draw(GraphicsContext gc);
    public abstract void handleClick(double x, double y);
    
    public boolean isOkPressed() { return okPressed; }
    public void setOkPressed(boolean pressed) { this.okPressed = pressed; } // ADD THIS
    public int getNextLevel() { return levelCompleted + 1; }
}