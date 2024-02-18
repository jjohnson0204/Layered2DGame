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

public class Snow extends State {
    private final GamePanel gp;
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();

    public Snow() {
        super("snow", "snowing");
        this.gp = (GamePanel) StateManager.get("gp");
    }

    public Snow(GamePanel gp) {
        super("snow", "snowing");
        this.gp = gp;
    }

    public void start() {
        System.out.println("Starting Snow State");

        StateManager.on("snowing", (lastState) -> {
            generateParticles(100);
            updateParticles();
            drawParticles((Graphics2D) StateManager.get("g2"));
        });

        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Snow State");
        StateManager.off("snowing");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(gp.screenWidth);
            int y = random.nextInt(gp.screenHeight);
            int vx = random.nextInt(3) - 1; // Snowflakes sway side to side
            int vy = random.nextInt(3) + 1; // Snowflakes fall slower than rain
            Color color = Color.WHITE;
            int size = 2;
            int life = random.nextInt(100) + 50;
            particles.add(new Particle(x, y, vx, vy, color, size, life));
        }
    }

    public void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (!particle.alive) {
                iterator.remove();
                particles.add(generateParticle());
            }
        }
    }

    private Particle generateParticle() {
        int x = random.nextInt(gp.screenWidth);
        int y = random.nextInt(gp.screenHeight);
        int vx = random.nextInt(3) - 1;
        int vy = random.nextInt(3) + 1;
        Color color = Color.WHITE;
        int size = 2;
        int life = random.nextInt(100) + 50;
        return new Particle(x, y, vx, vy, color, size, life);
    }

    public void drawParticles(Graphics2D g2) {
        for (Particle particle : particles) {
            particle.draw(g2);
        }
    }
}