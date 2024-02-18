// Strike.java
package environment;

import java.awt.*;
import data.Vector;
import main.GamePanel;
import sound.Sound;
import state.StateManager;

public class Strike {
    private int lifetime;
    Vector start, end;
    boolean split = false;
    boolean both = false;
    boolean dead = false;
    boolean isVisible = true;
    float a, r = 0;
    float h, d;
    Strike s1, s2;
    int n, max;
    Color color;

    public Strike(Vector start, float h, int n, int max, Color strikeColor) {
        this.start = new Vector(start.x, start.y); // Set y coordinate to the y coordinate of the end vector of the previous branch
        this.d = (float) (Math.random() * h / 2) + h;
        this.h = h;
        this.n = n;
        this.max = max;
        this.lifetime = 400;
        this.color = strikeColor; // Use strikeColor instead of color


        a = (float) (Math.random() * Math.PI / 2 + Math.PI / 4);
        end = new Vector(start.x + (float) Math.cos(a) * this.d, start.y + (float) Math.sin(a) * this.d);
    }

    public void update(float spd) {
        GamePanel gp = (GamePanel) StateManager.get("gp");
        if(r < d) {
            r += spd;
        } else {
            r = d;
            if(!split && n < max) {
                split = true;
//                gp.sound.playSE(25);
                if(Math.random() > 0.5) {
                    both = true;
                    s1 = new Strike(end, h, n + 1, max, color); // Use color instead of strikeColor
                    s2 = new Strike(end, h, n + 1, max, color); // Use color instead of strikeColor
                }  else {
                    both = false;
                    s1 = new Strike(end, h, n + 1, max, color); // Use color instead of strikeColor
                }
            } if(n >= max) {
                dead = true;
            } else {
                if(both) dead = s1.dead && s2.dead;
                else dead = s1.dead;
            }
        }
        if(split) {
            s1.update(spd);
            if(both) {
                s2.update(spd);
            }
        }
        lifetime--;
        // Randomly toggle visibility on and off
        if (Math.random() < 0.1) {
            isVisible = !isVisible;
        }
    }

    public boolean isDead() {
        return lifetime <= 0;
    }

    public void show(Graphics2D g2) {
        // Only draw the strike if it is visible
        if (isVisible) {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(r / d * 5));
            g2.drawLine((int) start.x, (int) start.y, (int) (start.x + Math.cos(a) * r), (int) (start.y + Math.sin(a) * r));
            if (split) {
                s1.show(g2);
                if (both) {
                    s2.show(g2);
                }
            }
        }
    }

    public int getY() {
        return (int) start.y;
    }
}