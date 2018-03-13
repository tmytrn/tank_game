
import javax.swing.*;

class tankGUI extends JFrame {


    JPanel backgroundLabel;
    //Map newMap = new Map();

    public tankGUI() {
        //load image from resources
        initUI();


    }
    private void initUI(){
        add(new GameWorld());
        pack();
        setTitle("Tank Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }



}

