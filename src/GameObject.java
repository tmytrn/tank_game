import java.awt.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.*;
/**
 * Created by tmytrn on 11/12/17.
 */
public abstract class GameObject extends Observable implements Observer {
    protected  int x, y, dimensionW, dimensionL, speed;
    boolean isVisible, isSolid;
    BufferedImage image;
    int imageIndex = 0;
    public GameObject(){

    }
    public GameObject(int x, int y, BufferedImage image, int dimensionW, int dimensionL, int speed){
        this.x= x;
        this.y=y;
        this.image = image;
        this.dimensionW = dimensionW;
        this.dimensionL = dimensionL;
        this.speed=speed;
        this.isVisible = true;
        this.isSolid = true;

    }
    public BufferedImage getImage(){
        return this.image;
    }
    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){return this.x;}
    public int getY(){
        return this.y;
    }
    public boolean getIsVisible(){
        return this.isVisible;
    }
    public boolean getIsSolid(){
        return this.isSolid;
    }
    public void setIsVisible(boolean b){
        this.isVisible = b;
    }
    public void setIsSolid(boolean b){
        this.isSolid = b;
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, dimensionW, dimensionL);
    }

    public abstract void collide(GameObject object);
    public abstract void collide(Tank tank);
    public abstract void collide(Bullet bullet);
    public abstract void collide(Walls wall);
    public abstract void collide(BreakableWalls breakableWall);
    public abstract void collide(PowerUp powerUp);
    /**
     * make constructor GameObject( int, int, Sprite);
     * make setCoordinates( int x, int y);
     */

}
