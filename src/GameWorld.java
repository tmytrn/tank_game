/**
 * Created by tmytrn on 11/25/17.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.event.KeyEvent;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class GameWorld  extends JPanel implements Observer{
    private BufferedImage background;
    private static BufferedImage tank1, tank2, wallImg, bWallImg, shell, player1win, player2win;
    private static BufferedImage[] smallExp, LargeExp;
    private int gameW, gameH;
    private Tank tankOne, tankTwo;
    private Timer reloadTimer;
    private Music musicPlayer;
    private GameClock clock;
    private Controller controlOne, controlTwo;
    private BufferedImage miniMap, map;
    private Graphics2D mapGraphics;
    private TimerTask reload;
    ArrayList<GameObject> gameObjects;
    ArrayList<Walls> gameWalls;
    ArrayList<Tank> gameTanks;
    ArrayList<Bullet> gameBullets;
    ArrayList<BreakableWalls>gameBWalls;
    ArrayList<Explosion> gameExplosions;
    public GameWorld(){
        makeLists();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        initBackground();
        initImages();
        spawnTanks();
        makeControllers();
        makeClock();
        makeMap();
        setReloadTimer();
        musicPlayer = new Music();

    }
    public void makeLists(){
        gameObjects = new ArrayList<>();
        gameTanks = new ArrayList<>();
        gameWalls =new ArrayList<>();
        gameBWalls = new ArrayList<>();
        gameBullets = new ArrayList<>();
        gameExplosions = new ArrayList<>();
    }
    @Override
    public void update(Observable o, Object obj){
        updateTanks(tankOne, controlOne);
        updateTanks(tankTwo, controlTwo);
        updateBullets();
        checkCollision();
        repaint();
    }
    public void setReloadTimer(){
        reloadTimer = new Timer();
        reload = new TimerTask() {
            @Override
            public void run() {
                if(tankOne.getBulletClip() < 1 )
                tankOne.setBulletClip(tankOne.getBulletClip()+1);
                if(tankTwo.getBulletClip() < 1)
                tankTwo.setBulletClip(tankTwo.getBulletClip()+1);
                //System.out.println("reload!");
            }
        };
        reloadTimer.schedule(reload, 15, 150);
    }
    public void updateTanks(Tank tank, Controller controller){
        Set<Integer> keySet = controller.getKeysPressed();
        if(keySet.contains(controller.getKeyLeft()))
            tank.mvLeft();
        if(keySet.contains(controller.getKeyDown()))
            tank.mvDown();
        if(keySet.contains(controller.getKeyRight()))
            tank.mvRight();
        if(keySet.contains(controller.getKeyUp()))
            tank.mvUp();
        if(keySet.contains(controller.getKeyFire())) {
            tank.fire(true);
        }else {
            tank.fire(false);
        }
    }
    public void updateBullets(){
        if(tankOne.getIsFiring()){
            bulletFired(tankOne);
        }
        if(tankTwo.getIsFiring()){
            bulletFired(tankTwo);
        }
    }
    private void initBackground(){
        loadBackground();
        gameW = background.getWidth(this);
        gameH = background.getHeight(this);
        setPreferredSize(new Dimension(gameW, gameH));
        map = new BufferedImage(gameW, gameH, BufferedImage.TYPE_INT_RGB);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        mapGraphics = map.createGraphics();
        g.drawImage(background, 0, 0, null);
        mapGraphics.drawImage(background, 0, 0, null);
        for(int i =0; i < gameExplosions.size(); i++){
            Explosion exp = gameExplosions.get(i);
            if(exp.getIsVisible()){
                g.drawImage(exp.getImage(),exp.getX(), exp.getY(), null);
            }
            else{
                gameExplosions.remove(i);
            }
         }
        for(Walls walls:gameWalls){
            g.drawImage(walls.getImage(), walls.getX(), walls.getY(), null);
            mapGraphics.drawImage(walls.getImage(), walls.getX(), walls.getY(), null);
        }
        for(int i =0; i < gameBWalls.size(); i++){
            BreakableWalls bWalls = gameBWalls.get(i);
            if(!bWalls.getIsVisible()) {
               gameBWalls.remove(bWalls);
               gameObjects.remove(bWalls);
            }
            else{
                g.drawImage(bWalls.getImage(), bWalls.getX(), bWalls.getY(), null);
                mapGraphics.drawImage(bWalls.getImage(), bWalls.getX(), bWalls.getY(), null);
            }
        }

        if(tankOne.getLives() <= 0){
            g.drawImage( player2win, 300, 150, null);

        }
        else if(tankTwo.getLives() <= 0){
            g.drawImage(player1win, 300, 150, null);
            //System.out.println("game over");
            }
        rotateBullets(g);
        rotateTank(g, tankOne);
        rotateTank(g, tankTwo);
        drawMiniMap(g);
        g.dispose();
        mapGraphics.dispose();
    }

    public void drawMiniMap(Graphics g) {
        AffineTransform mi = new AffineTransform();
        mi.scale(.25, .25);
        AffineTransformOp op = new AffineTransformOp(mi, AffineTransformOp.TYPE_BILINEAR);
        miniMap = op.filter(map, miniMap);
        g.drawImage(miniMap, 0, 450, null);
    }
    public void rotateBullets(Graphics g){
        for(int i =0; i < gameBullets.size(); i++) {
            Bullet bullets = gameBullets.get(i);
            if(bullets.getIsVisible()) {
                g.drawImage(bullets.getImage(), bullets.getX(), bullets.getY(), null);
            }else{
                gameBullets.remove(i);
            }
        }
    }
    public void drawStatusBars(Graphics g, Tank tank){
        Color health= new Color(0, 255, 00);//green
        g.setColor(health);
        g.fillRect(tank.getX() +5, tank.getY()-5, tank.getHealth()/2, (tank.dimensionL/5));
        //draw lives
        Color lives = new Color(150, 0, 0);
        g.setColor(lives);
        for(int i =0; i < tank.getLives(); i++) {
            g.fillRect(tank.getX() - 5, tank.getY() + (i*10), 5, 5);

        }
    }
    public void rotateTank(Graphics g, Tank tank){
        double trX = tank.getOffset();
        double trY = tank.getOffset();
        Graphics2D g2d = (Graphics2D) g;
        if(tank.getIsVisible()){
            //rotate tank
            AffineTransform tr = AffineTransform.getRotateInstance(Math.toRadians(tank.getDirection()), trX, trY);
            AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
            mapGraphics.drawImage(op.filter(tank.getImage(), null) ,tank.getX(), tank.getY(), null);
            g2d.drawImage(op.filter(tank.getImage(), null), tank.getX(), tank.getY(), null);
            drawStatusBars(g, tank);
        }
       // g2d.drawImage(tankTwo.getImage(), 0, 0, this);
    }
    public void makeMap() {
        //Walls map2[][] = new Walls[19][33];
        BreakableWalls bWall;
        Walls gWall;
        Map map = new Map();
        int[][]map1 = map.getMap();
        for (int y = 0; y < 19; y++) {
            for (int x = 0; x < 33; x++) {
                if (map1[y][x] == 1) {
                    gWall = new Walls(x*32, y*32, wallImg, 32, 32);
                    gameWalls.add(gWall);
                   gameObjects.add(gWall);
                } else if (map1[y][x] == 2) {
                    //gameObjects.add(new BreakableWalls(x*32, y*32, 32, 32));
                    bWall = new BreakableWalls(x*32, y*32, bWallImg, 32, 32);
                    gameBWalls.add(bWall);
                    gameObjects.add(bWall);
                }
            }
        }
    }
    public void makeControllers(){
        controlTwo = new Controller( KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT , KeyEvent.VK_ENTER);
        controlOne = new Controller( KeyEvent.VK_S, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        addKeyListener(controlOne);
        addKeyListener(controlTwo);
        controlOne.addObserver(this);
        controlTwo.addObserver(this);
    }
    public void spawnTanks(){
        tankOne = new Tank( 2*32, 2*32, tank1, 90, 64, 64, 3, 100, 1);
        tankTwo = new Tank( 28*32, 16*32, tank2, 180, 64, 64, 3, 100, 2);
        gameTanks.add(tankOne);
        gameTanks.add(tankTwo);
    }
    private void makeClock(){
        clock = new GameClock();
        clock.addObserver(this);
        clock.addObserver(tankOne);
        clock.addObserver(tankTwo);
    }
    private void bulletFired(Tank tank){
        if(tank.getBulletClip() > 0 && gameBullets.size() < 15 ) {
            Bullet bullet = new Bullet(tank, tank.getX() + tank.getOffset(), tank.getY() + tank.getOffset(), shell, 10, 10, 7, tank.getDirection(), tank.getID());
            clock.addObserver(bullet);
            gameBullets.add(bullet);
            tank.setBulletClip(tank.getBulletClip()-1);
            gameBullets.add(bullet);
        }
    }
    private void loadBackground(){
        //uses image url to load the png into an Image data type
        try {
            background = ImageIO.read(new File("src/resources/Background.png"));
            //wall = ImageIO.read(new File("src/resources/Wall1.png"));
        }
        catch(Exception e){
            System.out.println("Could not find background image file");
        }
    }
    public void initImages(){
//        //reads image files from resources and adds them to an image array
        try{
            tank1 = ImageIO.read(new File ("src/resources/TANKONE_FRAME_0.png"));
            tank2 = ImageIO.read(new File ("src/resources/TANKTWO_FRAME_0.png"));
            wallImg = ImageIO.read(new File ("src/resources/Wall1.png"));
            bWallImg = ImageIO.read(new File ("src/resources/Wall.png"));
            shell = ImageIO.read(new File ("src/resources/SHELL_FRAME_0.png"));
            player1win = ImageIO.read(new File("src/resources/PLAYER1_WIN.png"));
            player2win = ImageIO.read(new File("src/resources/PLAYER2_WIN.png"));
            smallExp = new BufferedImage[7];
            LargeExp = new BufferedImage[7];
            for(int i =0; i < 6; i++ ){
                smallExp[i] = ImageIO.read(new File("src/resources/small_exp/SMALL_EXPLOSION_" + (i+1) + ".png"));
                LargeExp[i] = ImageIO.read(new File("src/resources/large_exp/LARGE_EXPLOSION_" + (i+1) + ".png"));
            }
        }
        catch(Exception e ){
            e.printStackTrace();
        }
    }
    public void checkCollision(){
        Rectangle tankHitBox = tankOne.getBounds();
        Rectangle tank2HitBox = tankTwo.getBounds();
        for(GameObject obj: gameObjects) {
            Rectangle box = obj.getBounds();
          if(tankHitBox.intersects(box)){
             //System.out.println("collision detected");
             tankOne.collide(obj);
          }
          if(tank2HitBox.intersects(box)){
            // System.out.println("collision detected");
              tankTwo.collide(obj);
          }
          if(tank2HitBox.intersects(tankHitBox) || tankHitBox.intersects(tank2HitBox)){
              tankTwo.collide(tankOne);
              tankOne.collide(tankTwo);
          }
          for(Bullet bullets: gameBullets){
              Rectangle b = bullets.getBounds();
              if(b.intersects(box) && (obj instanceof BreakableWalls)){//bullet vs walls
                  gameExplosions.add(new Explosion(bullets.getX(), bullets.getY(), smallExp));
                  //musicPlayer.playSmallExp();
                  //System.out.println("explosion coordinate: " + bullets.getX() + bullets.getY());
                  bullets.collide((BreakableWalls)obj);
                  if(((BreakableWalls) obj).getHealth() < 0){
                      gameExplosions.add(new Explosion(obj.getX(), obj.getY(), LargeExp));
                      musicPlayer.playLargeExp();
                  }
              }
              else if(b.intersects(box)&& (obj instanceof Walls)){
                  gameExplosions.add(new Explosion(bullets.getX(), bullets.getY(), smallExp));
                  //musicPlayer.playSmallExp();
                  bullets.collide(obj);
              }
              else if(b.intersects(tank2HitBox) && (bullets.getID() != 2)){//bullet vs tank2
                  gameExplosions.add(new Explosion(bullets.getX(), bullets.getY(), smallExp));
                  musicPlayer.playSmallExp();
                  bullets.collide(tankTwo);
                  checkTankDeath(tankTwo);
              }else if(b.intersects(tankHitBox)&& (bullets.getID() != 1)){//bullet vs tank1
                  gameExplosions.add(new Explosion(bullets.getX(), bullets.getY(), smallExp));
                  musicPlayer.playSmallExp();
                  bullets.collide(tankOne);
                  checkTankDeath(tankOne);
              }
          }
        }
    }
    private void checkTankDeath(Tank tank){
        if(tank.getHealth() <= 0){
            gameExplosions.add(new Explosion(tank.getX(), tank.getY(), LargeExp));
            int life = tank.getLives()-1;
            tank.setLives(life);
            if(tank.getLives() > 0){
            tank.respawn();
            }
            else{
                tank.setIsVisible(false);
            }
        }

    }

}
