package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Portal {
    private int posX, posY;
    private int size = 256;
    private Image portalImg = new Image("portal_256x256.png");
    private boolean active = true;
    
    public Portal() {
        this.posX = ImaginBlastMain.WIDTH - size - 50; // Bottom right
        this.posY = ImaginBlastMain.HEIGHT - size - 50;
    }
    
    public void draw(GraphicsContext gc) {
        if (active) {
            gc.drawImage(portalImg, posX, posY, size, size);
        }
    }
    
    public boolean checkCollision(Player player) {
        if (!active) return false;
        
        // Simple rectangle collision
        return (player.posX + player.size > posX &&
                player.posX < posX + size &&
                player.posY + player.size > posY &&
                player.posY < posY + size);
    }
    
    public void deactivate() { active = false; }
}