package state;

import main.GamePanel;
import ui.UI;

import java.awt.*;

import static ui.UI.currentDialogue;

public class OptionsState extends State{
    String currentState = "options";
    boolean enterPressed = false;
    public int commandNum = 0;
    public OptionsState() {
        super("options", "options");
    }
    public void start() {
        System.out.println("Starting Options State");
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        StateManager.on("keyup:up", (lastState) -> {
            System.out.println("At State key:up");
            switch (currentState) {
                case "options":
                    if(commandNum == 0) commandNum = 6;
                    break;
                case "endGame":
                    if(commandNum == 0) commandNum = 2;
                    break;
            }
            commandNum = (commandNum - 1);
        });
        StateManager.on("keyup:down", (lastState) -> {
            switch (currentState) {
                case "options":
                    commandNum = (commandNum + 1)%6;
                    break;
                case "endGame":
                    commandNum = (commandNum + 1)%2;
                    break;

            }
        });
        StateManager.on("keyup:enter", (lastState) -> {
            enterPressed = false;
        });
        StateManager.on("keydown:enter", (lastState) -> {
            enterPressed = true;
        });
        StateManager.on("update", (lastState) -> {
            //this is the draw cycle frames
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(32F));

            // Window
            int frameX = gp.tileSize * 6;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 10;
            UI.drawSubWindow(frameX, frameY, frameWidth, frameHeight);

            switch (currentState){
                case "options": options(frameX, frameY); break;
                case "fullScreen": fullscreen(frameX, frameY); break;
                case "control": control(frameX, frameY); break;
                case "endGame": endGame(frameX, frameY); break;
            }
        });

    }
    public void stop() {
        System.out.println("Stopping Options State");
        StateManager.off("keyup:up");
        StateManager.off("keyup:down");
        StateManager.off("keyup:enter");
        StateManager.off("keydown:enter");
        StateManager.off("update", update);
    }
    public void options(int frameX, int frameY){
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = UI.getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Fullscreen ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                currentState = "fullScreen";
            }
        }

        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                currentState = "control";
                commandNum = 0;
                enterPressed = false;
            }
        }

        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                currentState = "endGame";
                commandNum = 0;
                enterPressed = false;
            }
        }

        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                StateManager.trigger("playState");
                commandNum = 0;
                enterPressed = false;
            }
        }

        //Fullscreen Check Box
        textX = (int) (frameX + gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 32;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 32, 32);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 32, 32);
        }

        //Music Volume Slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 32);
        int volumeWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 32);


        //SE Slider
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 32);
        int seWidth = 24 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, seWidth, 32);

//        gp.config.saveConfig();
    }
    public void fullscreen(int frameX, int frameY){
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(enterPressed){
                currentState = "options";
                enterPressed = false;
            }
        }
    }
    public void control(int frameX, int frameY){
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        int textX;
        int textY;

        //Title
        String text = "Control";
        textX = UI.getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Content
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(enterPressed){
                currentState = "options";
                commandNum = 3;
                enterPressed = false;
            }
        }
    }
    public void endGame(int frameX, int frameY){
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game \nand \nreturn to title screen:";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = UI.getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                StateManager.trigger("options:stop");
                StateManager.trigger("title:start");
                enterPressed = false;
            }
        }
        // Yes
        text = "No";
        textX = UI.getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(enterPressed){
                currentState = "options";
                commandNum = 4;
                enterPressed = false;
            }
        }
    }
}
