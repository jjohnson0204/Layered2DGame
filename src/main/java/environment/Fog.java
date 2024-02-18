package environment;

import graphic.Particle;
import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Fog extends State {
    private final Color color;
    private final GamePanel gp;
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();
    private double speed; // New speed attribute

    public Fog() {
        super("fog", "fogging");
        this.color = new Color(255, 255, 255, 100); // White color with 100 alpha for transparency
        this.gp = (GamePanel) StateManager.get("gp");
    }
    public Fog(double speed) { // New speed parameter
        super("fog", "fogging");
        this.color = new Color(255, 255, 255, 100); // White color with 100 alpha for transparency
        this.gp = (GamePanel) StateManager.get("gp");
        this.speed = speed; // Assign speed
    }

    public void start() {
        System.out.println("Starting Fog State");

        StateManager.on("fogging", (lastState) -> {
            // existing code...
            generateParticles(100,4);
            updateParticles();
            drawParticles((Graphics2D) StateManager.get("g2"));
        });
        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Fog State");
        StateManager.off("fogging");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count, int density) {
        //reduce the number of particles
        count = count / 4;
        density = density / 2;
        for (int i = 0; i < count * density; i++) {
            int x = random.nextInt(gp.screenWidth);
            int y = random.nextInt(gp.screenHeight / 2) + gp.screenHeight / 2; // Generate particles in the lower half of the screen
            int vx = random.nextInt(2) - 1; // Slower horizontal velocity
            int vy = random.nextInt(3) + 1; // Slower vertical velocity
            Color color = new Color(255, 255, 255, 100); // White color with 100 alpha for transparency
            int size = 4; // Larger particles
            int life = random.nextInt(100) + 50;
            particles.add(new Particle(x, y, vx, vy, color, size, life));
        }
    }

    public void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            // Add a drift effect
            particle.x += (int) ((random.nextInt(3) - 1) * speed); // Modify by speed
            particle.y += (int) ((random.nextInt(3) - 1) * speed); // Modify by speed
            if (!particle.alive) {
                iterator.remove();
            }
        }
    }

    public void drawParticles(Graphics2D g2) {
        for (Particle particle : particles) {
            g2.setColor(particle.color);
            g2.fillOval(particle.x, particle.y, particle.size, particle.size);
        }
    }
}