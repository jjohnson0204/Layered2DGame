package main;

import environment.Rain;
import input.KeyHandler;
import sound.Sound;
import state.*;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;
    public int tileSize = originalTileSize * scale; // 64x64 tile
    public int maxScreenCol = 20;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 1280
    public int screenHeight = tileSize * maxScreenRow; // 768

    // World settings
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;
    public boolean currentLight;

    //FPS
    int FPS = 90;

    //System
    Thread gameThread;
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui;
    public Sound sound = new Sound();

    //For Full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    public Graphics2D g2;
    public boolean fullScreenOn = false;

    // Game State
    public StateManager stateManager;
    public String gameState = "title";

    //Area
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

    double drawInterval = 1_000_000_000.0/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    TitleState titleState;
    LightingState lightingState;
    OptionsState optionsState;
    GameOverState gameOverState;
    PauseState pauseState;
    PlayState playState;
    EnvironmentState environmentState;
    SleepState sleepState;
    Rain rain;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        tempScreen = new BufferedImage(screenWidth,
                screenHeight,
                BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        //sets these at the start of the game
        StateManager.set("gp", this);
        StateManager.set("screen", tempScreen);
        StateManager.set("g2", g2);

        StateManager.on("update", (lastState) -> {
            // every draw cycle it sets these to a new value
            StateManager.set("delta", delta);
            StateManager.set("timer", timer);
            StateManager.set("drawCount", drawCount);
            StateManager.set("lastTime", lastTime);
            StateManager.set("currentTime", currentTime);
            StateManager.set("gameThread", gameThread);
            StateManager.set("lighting", lightingState);
            StateManager.set("environment", environmentState);
        });
        environmentState = new EnvironmentState();
        gameOverState = new GameOverState();
        lightingState = new LightingState();
        optionsState = new OptionsState();
        pauseState = new PauseState();
        playState = new PlayState();
        sleepState = new SleepState();
        titleState = new TitleState();
        ui = new UI(this);
//        optionsState.start();
        StateManager.trigger("play:start");
        StateManager.trigger("environment:start");
//        StateManager.trigger("lighting:start");

    }

    public void run() {
        // TODO Auto-generated method stub
        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
//                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen();     // draw everything to the screen
                delta--;
                drawCount++;
            }
            if(timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update() {
        // TODO Auto-generated method stub
//        titleState.update();
        StateManager.trigger("update");
        if(keyH.leftPressed) {
            StateManager.trigger("keyhold:left");
        }
        if(keyH.rightPressed) {
            StateManager.trigger("keyhold:right");
        }
        if(keyH.upPressed) {
            StateManager.trigger("keyhold:up");
        }
        if(keyH.downPressed) {
            StateManager.trigger("keyhold:down");
        }
        changeArea();
    }
    public void setupGame() {
        if (fullScreenOn) {
            setFullScreen();
        }
        else {
            Main.window.setSize(screenWidth, screenHeight);
            Main.window.setLocationRelativeTo(null);
            Main.window.setResizable(false);
            Main.window.setVisible(true);
        }

    }
    public void resetGame(boolean restart) {
        sound.stopMusic();
        if (restart) {
            StateManager.trigger("title");
        }
    }
    public void setFullScreen(){
        //Get Local Screen Device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get Full Screen Width and Height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void drawToTempScreen() {
        // TODO Auto-generated method stub
    }
    public void drawToScreen() {
        // TODO Auto-generated method stub
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void changeArea() {
        if (nextArea != currentArea) {
            sound.stopMusic();
            if(nextArea == outside) {
                sound.playMusic(0);
            }
            if(nextArea == indoor) {
                sound.playMusic(18);
            }
            if(nextArea == dungeon) {
                sound.playMusic(19);
                sound.playSE(23);
                sound.playSE(24);
            }
        }
        currentArea = nextArea;
    }
    public int getParticlesCount() {
        return 100;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int  getScreenHeight() {
        return screenHeight;
    }

}