import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class Controller extends Observable implements KeyListener{
    int keyLeft, keyRight, keyUp, keyDown, keyFire;
    int speed, x, y;
    private final Set<Integer> keysPressed;
    boolean[] keys;
    //int[]keys = {keyLeft, keyRight, keyDown, keyUp, keyFire};

    public Controller( int down, int up, int left, int right, int fire){
        keysPressed = new HashSet<>();
        this.keyDown = down;
        this.keyLeft = left;
        this.keyUp = up;
        this.keyRight = right;
        this.keyFire = fire;
        //this.keysPressed = new boolean[5];
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public  void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        this.keysPressed.add(key);
        setChanged();
        notifyObservers();
    }

    @Override
    public  void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        keysPressed.remove(key);
        setChanged();
        notifyObservers();

    }
    public Set getKeysPressed(){
        return this.keysPressed;
    }
    public int getKeyLeft(){
        return  this.keyLeft;
    }
    public int getKeyRight(){
        return this.keyRight;
    }
    public int getKeyUp(){
        return this.keyUp;
    }
    public int getKeyDown(){
        return this.keyDown;
    }
    public int getKeyFire(){
        return this.keyFire;
    }






}
