package graphic;

import java.util.ArrayList;
import java.util.List;

public class ParticlePool {
    private final List<Particle> pool = new ArrayList<>();

    public Particle getParticle() {
        for (Particle particle : pool) {
            if (!particle.alive) {
                return particle;
            }
        }

        Particle newParticle = new Particle();
        pool.add(newParticle);
        return newParticle;
    }
}