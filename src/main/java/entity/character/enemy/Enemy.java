package entity.character.enemy;

import entity.character.Character;
import main.GamePanel;
import state.StateManager;

import java.awt.*;

public class Enemy extends Character {
    // Class Imports
    GamePanel gp;
    Graphics2D g2;

    // Enemy-specific attributes
    private int damage;
    public Enemy(String name) {
        super("enemy");
    }

    public Enemy(GamePanel gp, Graphics2D g2) {
        super(gp, g2);
        this.gp = (GamePanel) StateManager.get("gp");
        this.g2 = gp.g2;
    }

    // Enemy-specific methods
    public void attack(Character target) {
        int targetLife = target.getLife();
        target.setLife(targetLife - this.damage);
    }

    // getters and setters for the new attributes
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}