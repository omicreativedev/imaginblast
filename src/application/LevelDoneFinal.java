package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LevelDoneFinal extends LevelDone {
    
    public LevelDoneFinal() {
        super(5);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.BLACK);
        gc.fillRect(ImaginBlastMain.WIDTH/4, ImaginBlastMain.HEIGHT/4, 
                    ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(24));
        gc.fillText("Final Boss Defeated!", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 - 50);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(18));
        gc.fillText("Click OK to see your victory screen", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2);
        
        gc.setFill(Color.GREEN);
        gc.fillRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
        gc.setFill(Color.BLACK);
        gc.fillText("OK", ImaginBlastMain.WIDTH/2, ImaginBlastMain.HEIGHT/2 + 130);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(ImaginBlastMain.WIDTH/2 - 100, ImaginBlastMain.HEIGHT/2 + 100, 200, 50);
    }
    
    @Override
    public void handleClick(double x, double y) {
        if (x >= ImaginBlastMain.WIDTH/2 - 100 && x <= ImaginBlastMain.WIDTH/2 + 100 &&
            y >= ImaginBlastMain.HEIGHT/2 + 100 && y <= ImaginBlastMain.HEIGHT/2 + 150) {
            okPressed = true;
        }
    }
}