// Lightning.java
package environment;

import main.GamePanel;
import sound.Sound;
import state.State;
import state.StateManager;
import data.Vector;
import environment.Strike;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lightning extends State {
    private final GamePanel gp;
    private final List<Strike> strikes = new ArrayList<>();
    private final Random random = new Random();
    private static final int NUM_STRIKES = 2;
    private final float speed;
    private final float strikeSpeed;
    private final Color strikeColor;
    public boolean isFlashing = false;

    public Lightning(float strikeSpeed, Color strikeColor) {
        super("lightning", "flashing");
        this.gp = (GamePanel) StateManager.get("gp");
        this.speed = 1.0f;
        this.strikeSpeed = strikeSpeed;
        this.strikeColor = strikeColor;
    }

    public Lightning(GamePanel gp, float strikeSpeed, Color strikeColor) {
        super("lightning", "flashing");
        this.gp = gp;
        this.speed = 1.0f;
        this.strikeSpeed = strikeSpeed;
        this.strikeColor = strikeColor;
    }

    public void start() {
        System.out.println("Starting Lightning State");

        for (int i = 0; i < NUM_STRIKES; i++) {
            strikes.add(new Strike(new Vector(random.nextInt(gp.screenWidth), random.nextInt(gp.screenHeight)), 200, 0, 5, strikeColor));
        }

        StateManager.on("flashing", (lastState) -> {
            System.out.println(isFlashing);
            if(isFlashing)
                drawParticles((Graphics2D) StateManager.get("g2"));
        });

        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Lightning State");
        StateManager.off("flashing");
        StateManager.off("update", update);
    }

    public void drawParticles(Graphics2D g2) {
        g2.setColor(new Color(150, 150, 150));
        for (int i = strikes.size() - 1; i >= 0; i--) {
            Strike strike = strikes.get(i);
            strike.update(strikeSpeed);
            strike.show(g2);
            if (strike.isDead()) {
                strikes.remove(i);
            }
        }
        if (strikes.size() < NUM_STRIKES) {
            strikes.add(new Strike(new Vector(random.nextInt(gp.screenWidth), random.nextInt(gp.screenHeight)), 100, 0, 5, strikeColor));
        }
    }
}