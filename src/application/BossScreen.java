package application;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;




public abstract class BossScreen {
    protected Boss boss;
    protected Portal portal;
    protected boolean portalVisible = false;
    protected boolean levelComplete = false;
    
    public abstract void update(Player player, List<Shot> playerShots, List<Shot> enemyShots);
    public abstract void draw(GraphicsContext gc, GameRenderer renderer, Player player, int score);
    public abstract boolean isComplete();
}