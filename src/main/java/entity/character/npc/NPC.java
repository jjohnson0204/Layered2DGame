package entity.character.npc;

import entity.character.Character;
import main.GamePanel;

import java.awt.*;

public class NPC extends Character {
    // NPC-specific attributes
    private String dialog;

    public NPC(String name) {
        super("npc");
    }

    public NPC(GamePanel gp, Graphics2D g2) {
        super(gp, g2);
    }

    // NPC-specific methods
    public void speak() {
        System.out.println(this.dialog);
    }

    // getters and setters for the new attributes
    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
}