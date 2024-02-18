package state;

import main.GamePanel;
import ui.UI;

import java.awt.*;
import java.lang.invoke.SwitchPoint;
import java.util.function.Consumer;

public class GameOverState extends State{
    public int commandNum = 0;
    public GameOverState () {
        super("gameOver", "gameOverState");
    }
    public void start() {
        System.out.println("Starting Game Over State");
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;

        StateManager.on("keyup:up", (lastState) -> {
            System.out.println("At State key:up");
            if(commandNum == 0) commandNum = 2;
            commandNum = (commandNum - 1);
        });
        StateManager.on("keyup:down", (lastState) -> {
            commandNum = (commandNum + 1) % 2;
        });
        StateManager.on("keyup:enter", (lastState) -> {
//            commandNum = (commandNum + 1) % 2;
            if(commandNum == 1){
                // Quit
                StateManager.trigger("gameOver:stop");
                StateManager.trigger("title:start");
            }
            if(commandNum == 0){
                // TODO ensure this player is rest back to same state as before death.
                // Retry
                StateManager.trigger("gameOver:stop");
                StateManager.trigger("play:start");
            }
            commandNum = 0;
        });
        StateManager.on("gameOverState", (lastState) -> {
            currentState = "gameOverState";
            g2.setColor(new Color(0, 0, 0,210 ));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            Font maruMonica = (Font) StateManager.get("font:maruMonica");
            if(maruMonica != null) {
                String text;
                int textLength;
                int x;
                int y;
                g2.setFont(maruMonica);
                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));

                text = "Game Over";
                //Shadow
                g2.setColor(Color.BLACK);
                x = UI.getXforCenteredText(text);
                y = gp.tileSize * 4;
                g2.drawString(text, x, y);
                //Main
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y);

                //Retry
                g2.setFont(g2.getFont().deriveFont(50F));
                text = "Retry";
                x = UI.getXforCenteredText(text);
                y += gp.tileSize * 4;
                g2.drawString(text, x, y);
                if(commandNum == 0){
                    g2.drawString(">", x - 40, y);
                }

                //Back to Title Screen
                text = "Quit";
                x = UI.getXforCenteredText(text);
                y += 55;
                g2.drawString(text, x, y);
                if(commandNum == 1){
                    g2.drawString(">", x - 40, y);
                }
            }

        });
        StateManager.on("update", update);
    }
    public void stop() {
        System.out.println("Stopping Game Over State");
        StateManager.off("keyup:up");
        StateManager.off("keyup:down");
        StateManager.off("keyup:enter");
        StateManager.off("gameOverState");
        sleep();
        StateManager.off("update", update);
    }
}
