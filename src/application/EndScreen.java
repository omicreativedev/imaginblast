package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndScreen {
    private int finalScore;
    private boolean gameWon;
    private boolean okPressed = false;
    
    public EndScreen(int finalScore, boolean gameWon) {
        this.finalScore = finalScore;
        this.gameWon = gameWon;
    }
    
    public void draw(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, ImaginBlastMain.WIDTH, ImaginBlastMain.HEIGHT);
        
        // Semi-transparent overlay
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        if (gameWon) {
            gc.setFill(Color.GOLD);
            gc.setFont(Font.font(36));
            gc.fillText("YOU WIN!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 80);
            
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(24));
            gc.fillText("Congratulations! You defeated the Final Boss!", 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 30);
            
            //gc.setFill(Color.YELLOW);
            //gc.setFont(Font.font(20));
            //gc.fillText("Final Score: " + finalScore, 
                        //ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 20);
        } else {
            gc.setFill(Color.RED);
            gc.setFont(Font.font(36));
            gc.fillText("GAME OVER", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 80);
            
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(24));
            gc.fillText("You were defeated by the Final Boss!", 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 30);
            
            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font(20));
            gc.fillText("Final Score: " + finalScore, 
                        ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 20);
        }
        
        // OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 80, 200, 50);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(18));
        gc.fillText("PLAY AGAIN", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 110);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 80, 200, 50);
    }
    
    public void handleClick(double x, double y) {
        if (x >= ImaginBlastMain.WIDTH/2 - 100 && x <= ImaginBlastMain.WIDTH/2 + 100 &&
            y >= ImaginBlastMain.HEIGHT/2 + 80 && y <= ImaginBlastMain.HEIGHT/2 + 130) {
            okPressed = true;
        }
    }
    
    public boolean isOkPressed() { return okPressed; }
    public void setOkPressed(boolean pressed) { this.okPressed = pressed; }
}