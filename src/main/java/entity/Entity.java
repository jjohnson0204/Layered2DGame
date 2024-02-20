package entity;

import main.GamePanel;
import state.StateManager;

import java.awt.*;
import java.util.function.Consumer;

public abstract class Entity {
    // Imported Classes
    public GamePanel gp;

    public String currentEntity = "";

    // Attributes
    // Character Attributes
    public String name;
    public int defaultSpeed;
    public double speed;
    public double worldX, worldY;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public boolean alive = true;

    public Entity() {

    }

    public Entity(String name) {
        setupEntity(name);
        setupEntity(name, name + ":0");
    }

    public Entity(String name, String runningEntity) {
        setupEntity(name, runningEntity);
    }

    private void setupEntity(String name) {
        currentEntity = name + ":0";
        StateManager.on(name + ":start", (lastState) -> {
            start();
        });
        StateManager.on(name+":stop", (lastState) -> {
            stop();
        });
    }

    private void setupEntity(String name, String runningEntity) {
        currentEntity = runningEntity;
        StateManager.on(name + ":start", (lastState) -> {
            start();
            currentEntity = runningEntity;
        });
        StateManager.on(name+":stop", (lastState) -> {
            stop();
        });
    }

    public void start() {
        // Implement start logic here
    }

    public void stop() {
        // Implement stop logic here
    }

    public Consumer<String> update = (String lastState) -> {
        StateManager.trigger(currentEntity);
    };

    public void draw(Graphics2D g2) {
        // Implement draw logic here
    }
}
