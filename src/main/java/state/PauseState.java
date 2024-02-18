package state;

import main.GamePanel;
import ui.UI;

import java.awt.*;

public class PauseState extends State{
    public PauseState() { super("pause", "paused"); }

    public void start() {
        System.out.println("PauseState starting");
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = gp.g2;

//        StateManager.on("keyup:p", (lastState) -> {
//            System.out.println("At State key:p");
//            StateManager.trigger("pause:start");
//        });
        StateManager.on("keyup:escape", (lastState) -> {
            System.out.println("At State key:esc");
            stop();
            StateManager.trigger("play:start");

        });
        StateManager.on("paused", (lastState) -> {
//            g2.setColor(new Color(155, 0, 0,210 ));
//            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            System.out.println("At State paused");
            g2.setColor(new Color(0, 0, 0,100 ));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            // Window
            int frameX = gp.tileSize * 6;
            int frameY = gp.tileSize * 3;
            int frameWidth = gp.tileSize * 8;
            int frameHeight = gp.tileSize * 5;
            UI.drawSubWindow(frameX, frameY, frameWidth, frameHeight);

            Font maruMonica = (Font) StateManager.get("font:maruMonica");
            if(maruMonica != null) {
                String text;
                int textLength;
                int x;
                int y;
                g2.setFont(maruMonica);
                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));

                text = "Paused";
                //Shadow

                x = UI.getXforCenteredText(text);
                y = gp.screenHeight/2;

                g2.setColor(Color.BLACK);
                g2.drawString(text, x + 5, y + 5);
                //Text
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y);
            }
            StateManager.off("paused");
        });
        StateManager.on("update", update);
        System.out.println("PauseState started");
    }
    public void stop(){
        System.out.println("Stopping Pause State");
        StateManager.off("keyup:p");
        StateManager.off("keyup:escape");
        sleep();
        StateManager.off("update", update);
    }
}
