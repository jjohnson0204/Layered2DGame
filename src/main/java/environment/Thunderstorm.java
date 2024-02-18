package environment;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.util.Random;

public class Thunderstorm extends State {
    private GamePanel gp;
    private StateManager stateManager;
    private Rain rain;
    private Wind wind;
    private Lightning lightning;
    private Random random;
    private boolean isStorming;
    private int lightningCounter;

    public Thunderstorm() {
        super("thunderstorm", "storming");
        this.gp = (GamePanel) StateManager.get("gp");
        this.random = new Random();
        this.rain = new Rain(gp);
        this.wind = new Wind(gp);
        this.lightning = new Lightning(gp, 2.0f, Color.yellow);
        this.isStorming = false;
    }

    private Rain getRain() {
        if (this.rain == null) {
            this.rain = new Rain(gp);
        }
        return this.rain;
    }
    private Wind getWind() {
        if (this.wind == null) {
            this.wind = new Wind(gp);
        }
        return this.wind;
    }
    private Lightning getLightning() {
        if (this.lightning == null) {
            this.lightning = new Lightning(gp, 2.0f, Color.black);
        }
        return this.lightning;
    }

    @Override
    public void start() {
        System.out.println("Starting Thunderstorm State");

        isStorming = true;
        StateManager.on("storming", (lastState) -> {
            // Add logic for when the thunderstorm state starts
            if (isStorming) {
                // Start the wind and lightning

                // Generate some particles for the wind
                wind.generateParticles(50);

                // Set the wind speed and direction
                wind.setSpeed(random.nextInt(5) - 2); // Random wind speed between -2 and 2
                wind.setDirection(0.1); // Slightly towards the right

                // Start the rain last


                // Set the Rain instance's properties
                rain.speed = (5.0);
                rain.direction = (1.0);
                rain.strength = (0.5); // Reduce the strength to make the rain less thick
                rain.limit = (1.0);
                rain.changeRate = (2.5);

                // Generate fewer particles for the rain
                rain.generateParticles(5);

                // Trigger the raining and blowing events
//                StateManager.trigger("raining");
//                StateManager.trigger("blowing");
//                StateManager.trigger("flashing");
                // Start the lightning flashes
                getLightning().isFlashing = true;

            } else {
                // If the storm is stopping, stop the rain, wind, and lightning
//                getLightning().stop();

                // Stop the lightning flashes
                StateManager.off("raining");
                StateManager.off("blowing");
                lightning.isFlashing = false;
                System.out.println("Lightning is flashing");
            }
        });
        lightning.start();
        rain.start();
        wind.start();
        StateManager.on("update", update);
    }

    @Override
    public void stop() {
        System.out.println("Stopping Thunderstorm State");
        lightning.stop();
        rain.stop();
        wind.stop();
        StateManager.off("storming");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count) {
        getRain().generateParticles(count);
        getWind().setSpeed(random.nextInt(5) - 2); // Random wind speed between -2 and 2
    }

    public void updateParticles() {
        getRain().updateParticles();
    }

    public void draw(Graphics2D g2) {
        getRain().drawParticles(g2);
        getWind().draw(g2);
//        if (StateManager.getState().equals("flashing")) {
//            getLightning().drawParticles(g2);
//        }
    }
    public int getLightningCounter() {
        return lightningCounter;
    }
}