package state;

import main.GamePanel;
import main.Main;
import ui.UI;

import java.awt.*;
import java.util.function.Consumer;

public class TitleState extends State {
    public int commandNum = 0;
    public TitleState() {
        super("title", "title:0");
    }
    public void start() {
        System.out.println("Starting Title State");
//        Graphics2D g2 = (Graphics2D) StateManager.get("g2");
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;
        StateManager.on("keyup:up", (lastState) -> {
            System.out.println("At State key:up");
            switch (currentState) {
                case "title:0":
                    if(commandNum == 0) commandNum = 3;
                    break;
                case "title:1":
                    if(commandNum == 0) commandNum = 6;
                    break;
            }
            commandNum = (commandNum - 1);
        });
        StateManager.on("keyup:down", (lastState) -> {
            switch (currentState) {
                case "title:0":
                    commandNum = (commandNum + 1)%3;
                    break;
                case "title:1":
                    commandNum = (commandNum + 1)%6;
                    break;
            }
        });
        StateManager.on("keydown:enter", (lastState) -> {
            System.out.println("At State key:enter");
            if(currentState.equals("title:0")){
                if(commandNum == 0){
                    currentState = "title:1";
                }
                if(commandNum == 1){
                    //Load Game
                }
                if(commandNum == 2){
                    System.exit(0);
                }
            }
            if(currentState.equals("title:1")){
                if(commandNum == 0){
                    //Fighter
                }
                if(commandNum == 1){
                    //Warrior
                }
                if(commandNum == 2){
                    //Hunter
                }
                if(commandNum == 3){
                    //Assassin
                }
                if(commandNum == 4){
                    //Mage
                }
                if(commandNum == 5){
                    currentState = "title:0";
                }
            }
            commandNum = 0;
        });



        StateManager.on("update", (lastState) -> {
            switch (currentState) {
                case "title:0":
                    title0();
                    break;
                case "title:1":
                    title1();
                    break;
            }
        });
    }

    public void stop(){
        StateManager.off("title:0");
        StateManager.off("title:1");
        StateManager.off("title:2");
        sleep();
        StateManager.off("update", update);
    }

    public void title0() {
            GamePanel gp = (GamePanel) StateManager.get("gp");
            Graphics2D g2 = gp.g2;

            g2.setColor(new Color(1, 35, 15));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Lights Epic Adventure";
            int x = UI.getXforCenteredText(text);
            int y = gp.tileSize * 3;

            //Shadow Color
            g2.setColor(new Color(43, 103, 84));
            g2.drawString(text, x + 5, y + 5);

            //Main Color
            g2.setColor(new Color(0, 168, 107));
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth/2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(Main.window.getIconImage(), x,y,gp.tileSize * 2,gp.tileSize *2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "New Game";
            x = UI.getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Load Game";
            x = UI.getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Quit";
            x = UI.getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }
    }
    public void title1() {
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;

        g2.setColor(new Color(0, 168, 107)); // rgb color code it's the green from main screen
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight); // means start from top left corner and fill the screen
        //Font color
        g2.setColor(new Color(53, 94, 59));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
        //Title of Character Screen
        String text = "Select your character...";
        int x = UI.getXforCenteredText(text);
        int y = gp.tileSize *3;
        g2.drawString(text, x , y);
        //Shadow Color
        g2.setColor(new Color(175, 225, 175));
        g2.drawString(text, x + 5, y + 5);

        text = "Fighter";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x- gp.tileSize, y);
        }

        text = "Warrior";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x- gp.tileSize, y);
        }
        text = "Hunter";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x- gp.tileSize, y);
        }
        text = "Assassin";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 3){
            g2.drawString(">", x- gp.tileSize, y);
        }
        text = "Mage";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 4){
            g2.drawString(">", x- gp.tileSize, y);
        }
        text = "Back";
        x = UI.getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if(commandNum == 5){
            g2.drawString(">", x- gp.tileSize, y);
        }
    }
}
