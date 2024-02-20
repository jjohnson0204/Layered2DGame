package environment;

import main.GamePanel;
import state.State;
import state.StateManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Rain extends State {
    private final GamePanel gp;
    private final List<Drop> drops = new ArrayList<>();
    private final Random random = new Random();
    private static final int NUM_DROPS = 10;

    public double speed;
    public double direction;
    public double strength;
    public double limit;
    public double changeRate;
    public static int example = 0;

    public Rain() {
        super("rain", "raining");
        this.gp = (GamePanel) StateManager.get("gp");
    }

    public Rain(GamePanel gp) {
        super("rain", "raining");
        this.gp = gp;
    }


    public void start() {
        System.out.println("Starting Rain State");

        // Set initial values for the variables
        speed = 1.0;
        direction = 1.0;
        strength = 1.0;
        limit = 1.0;
        changeRate = 0.5;

        for (int i = 0; i < NUM_DROPS; i++) {
            drops.add(new Drop(random.nextInt(gp.screenWidth), random.nextInt(gp.screenHeight), random.nextInt(5)));
        }

        StateManager.on("raining", (lastState) -> {
            generateParticles(50);
            updateParticles();
            drawParticles((Graphics2D) StateManager.get("g2"));
        });

        StateManager.on("update", update);
    }

    public void stop() {
        System.out.println("Stopping Rain State");
        StateManager.off("raining");
        StateManager.off("update", update); // remove the update listener
    }

    public void generateParticles(int count) {
        for (int i = 0; i < count / 8; i++) {
            drops.add(new Drop(random.nextInt(gp.screenWidth), random.nextInt(gp.screenHeight), random.nextInt(5)));

        }
    }

    public void updateParticles() {

        Iterator<Drop> iterator = drops.iterator();
        while (iterator.hasNext()) {
            Drop drop = iterator.next();
            drop.update();
            if (drop.getY() > gp.screenHeight) {
                iterator.remove();
            }
        }
    }

    public void drawParticles(Graphics2D g2) {
        for (Drop drop : drops) {
            drop.show(g2);
        }
    }

    // ... other code related to speed, direction, strength, limit, and changeRate ...

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }
    public void setStrength(double strength) {
        this.strength = strength;
    }
    public void setLimit(double limit) {
        this.limit = limit;
    }
    public void setChangeRate(double changeRate) {
        this.changeRate = changeRate;
    }
    public void increaseSpeed(double amount) {
        speed+=amount;
    }
    public void decreaseSpeed(double amount) {
        speed-=amount;
    }
    public void increaseDirection(double amount) {
        direction+=amount;
    }
    public void decreaseDirection(double amount) {
        direction-=amount;
    }
    public void increaseStrength(double amount) {
        strength+=amount;
    }
    public void decreaseStrength(double amount) {
        strength-=amount;
    }
    public void increaseLimit(double amount) {
        limit+=amount;
    }
    public void decreaseLimit(double amount) {
        limit-=amount;
    }
    public void increaseChangeRate(double amount) {
        changeRate+=amount;
    }
    public void decreaseChangeRate(double amount) {
        changeRate-=amount;
    }
    public double getSpeed() {
        return speed;
    }
    public double getDirection() {
        return direction;
    }
    public double getStrength() {
        return strength;
    }
    public double getLimit() {
        return limit;
    }
    public double getChangeRate() {
        return changeRate;
    }

}