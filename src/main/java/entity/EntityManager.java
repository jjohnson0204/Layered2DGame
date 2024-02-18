package entity;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final List<Entity> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public void start() {
        for (Entity entity : entities) {
            entity.start();
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity entity : entities) {
            entity.draw(g2);
        }
    }
}