package entity;

import main.GamePanel;
import state.StateManager;

import java.awt.*;
import java.util.function.Consumer;

public class Entity {
    // Imported Classes
    public GamePanel gp;

    public String currentState = "";

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
    public  Entity(GamePanel gp) {
        this.gp = gp;

    }

    public Entity() {

    }

    public Entity(String name) {
        setupState(name);
    }

    private void setupState(String name) {
        currentState = name + ":0";
        StateManager.on(name + ":start", (lastState) -> {
            start();
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
        StateManager.trigger(currentState);
    };

    public void draw(Graphics2D g2) {
        // Implement draw logic here
    }
}
