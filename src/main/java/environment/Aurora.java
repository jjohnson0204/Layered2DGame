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

public class Aurora extends State {
    private final GamePanel gp;
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();

    public Aurora() {
        super("aurora", "glowing");
        this.gp = (GamePanel) StateManager.get("gp");
    }

    public Aurora(GamePanel gp) {
        super("aurora", "glowing");
        this.gp = gp;
    }

    public void start() {
        System.out.println("Starting Aurora State");

        StateManager.on("glowing", (lastState) -> {
            generateParticles(100);
            updateParticles();
            drawParticles((Graphics2D) StateManager.get("g2"));
        });
        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Aurora State");
        StateManager.off("glowing");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count) {
        for (int i = 0; i < count; i++) {
            particles.add(generateParticle());
        }
    }

    public void updateParticles() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update();
            if (!particle.alive) {
                iterator.remove();
            }
        }
    }

    private Particle generateParticle() {
        int x = random.nextInt(gp.screenWidth);
        int y = random.nextInt(gp.screenHeight);
        int vx = (int) (Math.cos(y / (double) gp.screenHeight * Math.PI * 2) * 2);
        int vy = (int) (Math.sin(y / (double) gp.screenHeight * Math.PI * 2) * 2);
        Color color = generateAuroraColor();
        int size = random.nextInt(3) + 1;
        int life = random.nextInt(100) + 50;
        return new Particle(x, y, vx, vy, color, size, life);
    }

    private Color generateAuroraColor() {
        int type = random.nextInt(5);
        switch (type) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.BLUE;
            case 4:
                return new Color(128, 0, 128); // Purple
            default:
                return Color.WHITE;
        }
    }

    public void drawParticles(Graphics2D g2) {
        for (Particle particle : particles) {
            particle.draw(g2);
        }
    }
}