/**
 * Created by tmytrn on 11/5/17.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.*;

public class Bullet extends Movable {
    int bulletID, damage;
    boolean isMoving;
    Tank tank;

    public Bullet(Tank tank, int x, int y, BufferedImage bulletImg, int dimensionW, int dimensionL, int speed, double direction,  int id){
    super(x, y, bulletImg, dimensionW, dimensionL, speed);
    this.tank = tank;
    this.direction = direction;
    this.bulletID = id;
    isMoving = true;
    damage = 5;
    }
    public void update(Observable o, Object obj){
        if(isMoving)
        move(1);

    }
    @Override
    public void collide(GameObject object) {
        this.destroy();
    }

    @Override
    public void collide(Bullet bullet) {
        this.destroy();
    }

    @Override
    public void collide(Tank tank) {
        if(tank.getID() != this.bulletID){
           tank.setHealth(tank.getHealth()-this.damage);
        }
        this.destroy();
    }

    @Override
    public void collide(Walls wall) {
    this.destroy();
    }
    @Override
    public void collide(BreakableWalls breakableWall) {
    this.destroy();
    breakableWall.setHealth(this.damage);
    //System.out.println("it hit");
    }
    @Override
    public void collide(PowerUp powerUp) {

    }
    public void destroy(){
       // System.out.println("bullet destroyed");
        this.setCoordinates(0, 0);
        isMoving = false;
        this.isVisible = false;
    }
    public int getID(){
        return this.bulletID;
    }
    public boolean getIsVisible(){
        return this.isVisible;
    }

}
