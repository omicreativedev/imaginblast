package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * STANDARD SHOT - Default blue oval shot that travels straight up
 * This is the same as the original Shot class
 */
public class ShotStandard extends Shot {
    
    public ShotStandard(int posX, int posY) {
        super(posX, posY);
        this.speed = 10;
    }
    
    @Override
    public void update() {
        posY -= speed;
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(posX, posY, SIZE, SIZE);
    }
}