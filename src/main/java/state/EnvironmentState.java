package state;

import environment.*;
import main.GamePanel;

import java.awt.*;

public class EnvironmentState extends State{
    private final Aurora aurora;
    private final Fog fog;
    private  Fog fogSlow;
    private  Fog fogMedium;
    private  Fog fogFast;
    private final Lightning lightning;
    private final Rain rain;
    private final Snow snow;
    private final Thunderstorm thunderstorm;
    private final Wind wind;

    private GamePanel gp;
    private StateManager stateManager;

    public EnvironmentState() {
        super("environment", "environment:0");
        this.gp = (GamePanel) StateManager.get("gp");
        this.aurora = new Aurora();
        this.fog = new Fog(7);
//        this.fogSlow = new Fog(.5);
//        this.fogMedium = new Fog(1.0);
//        this.fogFast = new Fog(1.5);
        this.lightning = new Lightning(gp, 2.0f, Color.YELLOW);
        this.wind = new Wind(gp);
        this.rain = new Rain(gp);
        this.snow = new Snow();
        this.thunderstorm = new Thunderstorm();


//        // Set initial values for the Rain object's properties
//        StateManager.data.putIfAbsent("rain:speed", 5.0);
//        StateManager.data.putIfAbsent("rain:direction", 1.0);
//        StateManager.data.putIfAbsent("rain:strength", 1.0);
//        StateManager.data.putIfAbsent("rain:limit", 1.0);
//        StateManager.data.putIfAbsent("rain:changeRate", 0.5);

        StateManager.set("aurora", aurora);
        StateManager.set("fog", fog);
//        StateManager.set("fogSlow", fogSlow);
//        StateManager.set("fogMedium", fogMedium);
//        StateManager.set("fogFast", fogFast);
        StateManager.set("lightning", lightning);
        StateManager.set("rain", rain);
        StateManager.set("snow", snow);
        StateManager.set("thunderstorm", thunderstorm);
        StateManager.set("wind", wind);

        double rainSpeed = (double) StateManager.data.getOrDefault("rain:speed", 0.0);

    }

    @Override
    public void start() {
//        StateManager.set("rain:limit", 1.0);
//        StateManager.set("rain:speed", 1000.0);
//        StateManager.set("rain:changeRate", 2.5);
//        StateManager.set("rain:direction", 0.0);
//        StateManager.set("rain:strength", 1.0);

//        StateManager.trigger("rain:start");
//        StateManager.trigger("wind:start");
//        StateManager.trigger("snow:start");
        StateManager.trigger("fog:start");
//        StateManager.trigger("thunderstorm:start");
//        StateManager.trigger("aurora:start");
//        StateManager.trigger("lightning:start");
    }

    public void generateParticles(int count) {
        aurora.generateParticles(count);
        fog.generateParticles(count, 3);
//        fogSlow.generateParticles(count, 2);
//        fogMedium.generateParticles(count, 2);
//        fogFast.generateParticles(count, 2);
        rain.generateParticles(count);
        snow.generateParticles(count);
        thunderstorm.generateParticles(count);
        wind.generateParticles(count);
    }

    public void updateParticles() {
        aurora.updateParticles();
        fog.updateParticles();
//        fogSlow.updateParticles();
//        fogMedium.updateParticles();
//        fogFast.updateParticles();
        rain.updateParticles();
        snow.updateParticles();
        thunderstorm.updateParticles();
    }

    public void draw(Graphics2D g2) {
        aurora.drawParticles(g2);
        fog.drawParticles(g2);
//        fogSlow.drawParticles(g2);
//        fogMedium.drawParticles(g2);
//        fogFast.drawParticles(g2);
        lightning.drawParticles(g2);
        rain.drawParticles(g2);
        snow.drawParticles(g2);
        thunderstorm.draw(g2);
        wind.draw(g2);
    }
}