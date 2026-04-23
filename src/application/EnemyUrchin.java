package application;

import javafx.scene.image.ImageView; //new - Use ImageView instead of Image

/**
 * URCHIN ENEMY CLASS
 * Moves straight down at a set speed
 */
public class EnemyUrchin extends Enemy {
    
    int SPEED = 8;
    
    public EnemyUrchin(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    @Override
    public void update() {
        super.update();
        
        if(!exploding && !destroyed) {
            posY += SPEED;
        }
        
        if(posY > ImaginBlastMain.HEIGHT) {
            destroyed = true;
        }
    }
}