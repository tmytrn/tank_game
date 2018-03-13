import java.awt.image.BufferedImage;

/**
 * Created by tmytrn on 11/5/17.
 */
public abstract class Movable extends GameObject {

    double direction;

    static final int shiftDirection = 3;
    public Movable(){

    }
    public Movable(int x, int y, BufferedImage image, int dimensionW, int dimensionL, int speed){
        super(x, y, image, dimensionW, dimensionL, speed);
    }
    public void move( int direction ) {
        int newXCoordinate = ( int ) ( this.speed * direction * Math.cos( Math.toRadians( this.direction ) ) );
        int newYCoordinate = ( int ) ( this.speed * direction * Math.sin( Math.toRadians( this.direction  ) ) );
        this.setCoordinates( newXCoordinate + this.getX(), newYCoordinate + this.getY() );
    }

    public synchronized void mvRight(){
        double newDirection = this.getDirection() + shiftDirection;
        setDirection( newDirection );
        if(newDirection >= 360){
            newDirection =0;
        }
        setDirection( newDirection );
    }
    public synchronized void mvLeft(){
        double newDirection = this.getDirection() - shiftDirection;
        if ( newDirection  <= -360 ) {
            newDirection = 0;
        }
        setDirection( newDirection );
    }
    public synchronized void mvDown(){
        this.move(-1);
    }
    public synchronized void mvUp(){
        this.move(1);
    }
    public double getDirection(){
        return this.direction;
    }
    public void setDirection(double newDirection){
        this.direction = newDirection;
    }


}

