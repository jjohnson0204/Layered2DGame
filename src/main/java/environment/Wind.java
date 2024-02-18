package environment;

import graphic.Particle;
import main.GamePanel;
import state.State;
import state.StateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wind extends State {
    private final GamePanel gp;
    private int speed;
    private double direction; // New property for the wind direction
    public final List<Particle> particles;
    public final Random random;
    public boolean isBlowing = false;

    public Wind() {
        super("wind", "blowing");
        this.gp = (GamePanel) StateManager.get("gp");
        this.speed = 0;
        this.direction = 0.0; // Initialize the direction to 0
        this.particles = new ArrayList<>();
        this.random = new Random();
    }

    public Wind(GamePanel gp) {
        super("wind", "blowing");
        this.gp = gp;
        this.speed = 10;
        this.direction = 10; // Initialize the direction to 0
        this.particles = new ArrayList<>();
        this.random = new Random();
    }


    public void start() {
        System.out.println("Starting Wind State");
        isBlowing = true;
        StateManager.on("blowing", (lastState) -> {
            System.out.println(isBlowing);
           if (isBlowing) {
                // Update the position of the particles based on the wind speed and direction
                for (Particle particle : particles) {
                    particle.move((int) (speed * Math.cos(direction)), (int) (speed * Math.sin(direction))); // Move the particle based on the wind speed and direction
                }
           }
        });

        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Wind State");
        StateManager.off("blowing");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count) {
        for (int i = 0; i < count; i++) {
            // Generate a new particle with a random position
            int x = random.nextInt(gp.getWidth());
            int y = random.nextInt(gp.getHeight());
            particles.add(new Particle(x, y));
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setDirection(double direction) {
        this.direction = direction; // New method to set the wind direction
    }

    public double getDirection() {
        return this.direction;
    }
    public List<Particle> getParticles() {
        return this.particles;
    }
}