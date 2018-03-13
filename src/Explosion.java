/**
 * Created by tmytrn on 12/12/17.
 */
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.awt.*;
public class Explosion {
    int x, y, imageCounter;
    BufferedImage[] image;
    boolean isVisible;
    public Explosion(int x, int y, BufferedImage[] image){
        this.x = x;
        this.y = y;
        this.image = image;
        this.imageCounter = -1;
        this.isVisible = true;
    }
    public BufferedImage getImage(){
        if(imageCounter < 5) {
            imageCounter++;
        }
        if(imageCounter==5){
            this.isVisible = false;
        }
        return image[imageCounter];
    }
    public boolean getIsVisible(){
        return this.isVisible;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
