import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Music extends JFrame implements Runnable{
    private String smallExp, largeExp;
    private Clip smallSound, largeSound, gameSound;
    public Music(){//String... files
            String game = "src/resources/audio/lost_woods.wav";
            smallExp = "src/resources/audio/Explosion_small.wav";
            largeExp = "src/resources/audio/Explosion_large.wav";
            gameSound = createSound(game);
            gameSound.loop(5);
    }

    private Clip createSound(String fileName){
        try{
            File soundFile = new File(fileName);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            return clip;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private synchronized void playSound(Clip clip){
        clip.start();
    }
    public synchronized void playSmallExp(){
        smallSound = createSound(smallExp);
        playSound(smallSound);
    }
    public synchronized void playLargeExp(){
        largeSound = createSound(largeExp);
        playSound(largeSound);
    }
    @Override
    public void run() {
        //playSound(musicFiles.get(currentSongIndex));
    }
}
