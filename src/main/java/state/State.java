// State.java
package state;


import java.awt.*;
import java.util.function.Consumer;

// DEPRECATED: Do not use unless you find a purpose for it.
public abstract class State {

    public String currentState = "";
    public State(String name) {
        currentState = name + ":0";
        StateManager.on(name + ":start", (lastState) -> {
            start();
        });
        StateManager.on(name+":stop", (lastState) -> {
            stop();
        });
    }
    public State(String name, String runningState) {
        currentState = runningState;
        StateManager.on(name + ":start", (lastState) -> {
            start();
            currentState = runningState;
        });
        StateManager.on(name+":stop", (lastState) -> {
            stop();
        });
    }
    public void start() {
    }
    public void stop() {
    }

    public void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
    }
    public Consumer<String> update = (String lastState) -> {
        StateManager.trigger(currentState);
    };

    public void draw(Graphics2D g2) {
    }
}