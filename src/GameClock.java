/**
 * Created by tmytrn on 11/17/17.
 */
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.ArrayList;



public class GameClock extends Observable implements ActionListener{
    //Thread thread;
    Timer timer;

    public GameClock(){
        //thread = new Thread(this);
        timer = new Timer(10, this);

        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        //method is called every 15 ms
        setChanged();
        notifyObservers();
    }


}
