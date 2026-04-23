package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView; //new

/**
 * CASSETTE ITEM CLASS
 * Cassettes fall straight down and give points when collected
 */
public class ItemCassette extends Item {
    
    int speed = 8;
    
    public ItemCassette(int posX, int posY, int size, ImageView imageView) {
        super(posX, posY, size, imageView);
    }
    
    public void onCollected() {
        this.collected = true;
    }
    
    @Override
    public void update(GraphicsContext gc) {
        if(collected == true) {
            this.gone = true;
        }
        
        if(!collected && !gone) {
            posY += speed;
        }
        
        if(posY > ImaginBlastMain.HEIGHT) {
            gone = true;
        }
    }
}