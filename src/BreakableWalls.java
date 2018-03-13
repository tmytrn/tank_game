import java.awt.image.BufferedImage;
import java.util.Observable;
import java.awt.*;

/**
 * Created by tmytrn on 11/5/17.
 */
public class BreakableWalls extends GameObject {
    int health;

    public BreakableWalls(int x, int y, BufferedImage image, int dimensionW, int dimensionL){
        this.x = x;
        this.y = y;
        this.dimensionW = dimensionW;
        this.dimensionL = dimensionL;
        this.image = image;
        isSolid = true;
        isVisible = true;
        health = 20;
    }
    public void update(Observable o, Object obj){

    }
    @Override
    public void collide(GameObject object) {

    }
    @Override
    public void collide(Bullet bullet) {
        this.setHealth( health - bullet.damage);

    }
    @Override
    public void collide(Tank tank) {
        tank.x = x;
    }
    @Override
    public void collide(Walls wall) {

    }

    @Override
    public void collide(BreakableWalls breakableWall) {

    }
    @Override
    public void collide(PowerUp powerUp) {

    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth( int damage){
        this.health = health - damage;
        if(health < 0){
            this.setIsSolid(false);
            this.setIsVisible(false);
           // System.out.println("wall destroyed");
        }
    }
}
