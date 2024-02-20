package entity.character.player;

import entity.character.Character;
import main.GamePanel;
import state.StateManager;

import java.awt.*;

public class Player extends Character {
    // Class Imports
    GamePanel gp;
    Graphics2D g2;

    // Attributes
    private int score;
    private int lives;

    public Player() {
        super("player");
        this.gp = (GamePanel) StateManager.get("gp");
        this.g2 = gp.g2;
    }

    public Player(GamePanel gp, Graphics2D g2) {
        super(gp, g2);
        this.gp = gp;
        this.g2 = g2;
    }

    public Player(String name) {
        super("player");
    }

    // Player-specific methods
    public void increaseScore(int increment) {
        this.score += increment;
    }

    public void loseLife() {
        this.lives--;
    }

    // getters and setters for the new attributes
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}