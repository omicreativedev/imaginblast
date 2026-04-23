package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LevelDone04 extends LevelDone {
    
    public LevelDone04() {
        super(4);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER); 
        // Draw black box overlay
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        // Draw completion text
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Congratulations!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 100);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("You have completed all 3 levels!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(16));
        gc.fillText("Click OK to return to the Start Screen", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 20);
        
        // Draw OK button
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 50, 200, 50);
        
        gc.setFill(Color.BLACK);
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 80);
        
        // Button border
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 50, 200, 50);
    }
    
    @Override
    public void handleClick(double x, double y) {
        if (x >= ImaginBlastMain.WIDTH/2 - 100 && x <= ImaginBlastMain.WIDTH/2 + 100 &&
            y >= ImaginBlastMain.HEIGHT/2 + 50 && y <= ImaginBlastMain.HEIGHT/2 + 100) {
            okPressed = true;
        }
    }
}