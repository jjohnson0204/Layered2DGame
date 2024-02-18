package environment;

import main.GamePanel;
import state.StateManager;

import java.awt.*;
import java.util.Random;

public class Drop {
    static GamePanel gp;
    private Color color;
    private float x, y, z;
    private static int HEIGHT = 0; // replace with actual height
    private static int WIDTH; // replace with actual width

    public Drop(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        GamePanel gp = (GamePanel) StateManager.get("gp");
        HEIGHT = gp.screenHeight;
        WIDTH = gp.screenWidth;

        Random random = new Random();
        if (random.nextInt(2) == 0) {
            color = new Color(173, 216, 230, 20); // transparent light blue
        } else {
            color = new Color(255, 255, 255, 20); // transparent white
        }
    }

    public void update() {
        float spd = map(z, 0, 5, 10, 4);
        y = y + spd;
        x = x + spd / 5;
        if(y > HEIGHT + 10) {
            y = -10;
            x = (float) Math.random() * WIDTH;
        }
    }

    public void show(Graphics2D g2) {
        float t = map(z, 0, 5, 1, 5); // reverse the mapping
        Color transparentLightBlue = new Color(173, 216, 230, 35); // light blue with 100 alpha
        g2.setColor(color); // set the color to transparent light blue
        g2.setStroke(new BasicStroke(t));
        g2.drawLine((int)x, (int)y, (int)x, (int)(y + t * 5)); // make the drop 5 times longer
    }

    private float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }
    public float getY() {
        return y;
    }
}