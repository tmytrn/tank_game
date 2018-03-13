import java.awt.*;
import java.util.Observable;
import java.awt.image.*;

/**
 * Created by tmytrn on 11/5/17.
 */

public class Walls extends GameObject {
    //Image wallImg[];


    public Walls(int x, int y, BufferedImage image, int dimensionW, int dimensionL){
        super(x, y, image, dimensionW, dimensionL, 0);
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
