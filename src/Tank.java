/**
 * Created by tmytrn on 11/5/17.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class Tank extends Movable {
    // id, health, and fire are unique to tanks
    int id, health, fire, safeX, safeY, bulletClip, spawnX, spawnY, lives;
    boolean isFiring, collisionDetected;

    public Tank(int x, int y, BufferedImage image, double direction, int dimensionW, int dimensionL, int fire, int health, int id){

        super(x, y,image, dimensionW, dimensionL, 3 );
        saveSpawnCoordinates();
        this.id = id;
        this.health = health;
        this.fire = fire;
        this.direction = direction;
        saveNonCollidingCoordinates();
        bulletClip = 0;
        this.isFiring = false;
        collisionDetected = false;
        this.lives =3;
    }
    public void saveNonCollidingCoordinates(){
        //nCPoint = point;
        safeX = this.x;
        safeY = this.y;
        //System.out.println("saving coordinates");
    }
    @Override
    public void update(Observable o, Object obj ){
        if(!collisionDetected) {
            saveNonCollidingCoordinates();
        }
        else if(collisionDetected){
            collisionDetected = false;
        }
    }
    @Override
    public void collide(GameObject object) {
        collisionDetected = true;
        setX(safeX);
        setY(safeY);
    }
    @Override
    public void collide(Bullet bullet) {
        this.health = health - bullet.damage;
    }

    @Override
    public void collide(Tank tank) {
        //if(this.hitBox.intersects(tank.hitBox)){
            collisionDetected = true;
            setX(safeX);
            setY(safeY);
        //}
    }

    @Override
    public void collide(Walls wall) {
          // if(this.hitBox.intersects(wall.hitBox)){
               collisionDetected = true;
               //point = nCPoint;
               setX(safeX);
               setY(safeY);
    }

    @Override
    public void collide(BreakableWalls breakableWall) {
                collisionDetected = true;
                setX(safeX);
                setY(safeY);
    }
    @Override
    public void collide(PowerUp powerUp ) {

    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, 64, 64);
    }
    public void fire(boolean b){
        isFiring = b;

    }
    public void saveSpawnCoordinates(){
        spawnX = this.x;
        spawnY = this.y;
    }
    public void respawn(){
        setCoordinates(spawnX, spawnY);
        setHealth(100);
    }
    public int getLives(){
        return this.lives;
    }
    public void setLives(int n){
        this.lives = n;
    }
    public int getID(){
        return this.id;
    }
    public void setX(int newX){
        this.x = newX;
    }
    public void setY(int newY){
        this.y = newY;
    }
    public boolean getIsFiring(){
        return this.isFiring;
    }
    public int getBulletClip(){
        return this.bulletClip;
    }
    public void setBulletClip(int n){
        this.bulletClip = n;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getOffset(){
        return this.dimensionW/2;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth( int newHealth){
        this.health = newHealth;
    }

}
