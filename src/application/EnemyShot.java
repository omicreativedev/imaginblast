package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnemyShot extends Shot {
    
    // Override size for enemy shots - 3 times larger
    private static final int ENEMY_SHOT_SIZE = 18; // 6 * 3 = 18
    
    public EnemyShot(int posX, int posY) {
        super(posX, posY);
        this.speed = 8;
    }
    
    @Override
    public void update() {
        posY += speed; // Moves down toward player
    }
    
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        // Use ENEMY_SHOT_SIZE instead of Shot.SIZE
        gc.fillOval(posX, posY, ENEMY_SHOT_SIZE, ENEMY_SHOT_SIZE);
    }
    
    // Override collision size if needed
    @Override
    public boolean isOffScreen() {
        return posY > ImaginBlastMain.HEIGHT;
    }
}