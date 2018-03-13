/**
 * Created by tmytrn on 11/5/17.
 */
import java.util.*;
public class PowerUp extends GameObject {


    @Override
    public void setCoordinates(int x, int y) {
        this.x =x;
        this.y=y;
        //point.setLocation(x,y);
    }

    public void update(Observable o, Object obj){

    }

    @Override
    public void collide(GameObject object) {

    }

    @Override
    public void collide(Bullet bullet) {

    }

    @Override
    public void collide(Tank tank) {

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
}
