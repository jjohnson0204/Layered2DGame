package state;

import main.GamePanel;
import ui.UI;

import java.awt.*;

public class PlayState extends State {
    private final Graphics2D g2;
    private final GamePanel gp;
    boolean leftPressed = false;
    boolean rightPressed = false;
    int health = 0;
    public PlayState() {
        super("play", "playing");
        this.gp = (GamePanel) StateManager.get("gp");
        this.g2 = gp.g2;
    }
    public PlayState(GamePanel gp, Graphics2D g2) {
        super("play", "playing");
        this.gp = gp;
        this.g2 = g2;
    }

    public void start() {
        System.out.println("Starting Play State");


        StateManager.on("keyup:p", (lastState) -> {
            System.out.println("At State key:p");
            stop();
            StateManager.trigger("pause:start");
        });
        StateManager.on("keyup:escape", (lastState) -> {
            System.out.println("At State key:escape");
            stop();
            StateManager.trigger("gameOver:start");
        });
        StateManager.on("playing", (lastState) ->{
            String text = "Health: " + health;
            int x = UI.getXforCenteredText(text);
            int y = gp.screenHeight/2;
            g2.setColor(new Color(0, 0, 0,255 ));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            if(leftPressed) {
                StateManager.trigger("health:decrease");
            }
            if(rightPressed) {
                StateManager.trigger("health:increase");
            }
            changeDayState();
        });

        StateManager.on("keyhold:right", (lastState) -> {
            health++;
        });
        StateManager.on("keyhold:left", (lastState) -> {
            health--;
        });
        StateManager.on("update", update);
        System.out.println("Play State started");
    }

    public void stop() {
//        StateManager.off("update");
        StateManager.off("keyup:p");
        StateManager.off("keyup:escape");
        StateManager.off("keyup:right");
        StateManager.off("keyup:left");
        StateManager.off("playing");
        sleep();
        StateManager.off("update", update);
        System.out.println("Stopping Play State");
    }
    public void changeDayState() {
        LightingState lightingState = (LightingState) StateManager.get("lighting");

        // Get the dayCounter from the LightingState
        int dayCounter = lightingState.getDayCounter();

        // Change the day state based on the dayCounter
        if (dayCounter >= 0 && dayCounter < 6) {
            lightingState.setDay();
        } else if (dayCounter >= 6 && dayCounter < 12) {
            lightingState.setDusk();
        } else if (dayCounter >= 12 && dayCounter < 18) {
            lightingState.setNight();
        } else if (dayCounter >= 18 && dayCounter < 24) {
            lightingState.setDawn();
        }
    }
}
