package state;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LightingState extends State {
    private final GamePanel gp;
    private final Graphics2D g2;
    private BufferedImage bloom;
    private BufferedImage darknessFilter;
    private RadialGradientPaint paint;
    private int dayCounter;
    private float filterAlpha = 0f;

    public final int day =  0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;
    private float[] fraction;
    private Color[] color;

    public LightingState() {
        super("lighting","illuminate");
        this.gp = (GamePanel) StateManager.get("gp");
        this.g2 = gp.g2;
    }
    public LightingState(GamePanel gp, Graphics2D g2) {
        super("lighting","illuminate");
        this.gp = gp;
        this.g2 = g2;
    }
    public void start() {
        System.out.println("Starting Lighting State");

        StateManager.on("illuminate", (lastState) -> {
            // Add logic for when the lighting state starts
            // Start the lighting
            // Generate some particles for the lighting
            setLightingSource();
        });
    }
    public void stop() {
        System.out.println("Stopping Lighting State");
        // Add logic for when the lighting state stops
        // Stop the lighting
        StateManager.off("illuminate");
        StateManager.off("update", update);
    }
    public void generateParticles(int numParticles) {
        // Generate some particles for the lighting
    }
    public void updateParticles() {
        // Update the particles for the lighting
        this.update();
    }
    public void draw() {
        // Draw the particles for the lighting
        this.draw(g2);
    }

    public void setLightingSource() {
        // Create a buffered image
        BufferedImage darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Get the center x and y of the light circle
        int centerX = (gp.tileSize) / 2;
        int centerY = (gp.tileSize) / 2;

        // Create a gradation effect within the light circle
        Color[] color = new Color[12];
        float[] fraction = new float[12];

        color[0] = new Color(0, 0, 0.1f, 0.1f);
        color[1] = new Color(0, 0, 0.1f, 0.42f);
        color[2] = new Color(0, 0, 0.1f, 0.52f);
        color[3] = new Color(0, 0, 0.1f, 0.61f);
        color[4] = new Color(0, 0, 0.1f, 0.69f);
        color[5] = new Color(0, 0, 0.1f, 0.76f);
        color[6] = new Color(0, 0, 0.1f, 0.82f);
        color[7] = new Color(0, 0, 0.1f, 0.87f);
        color[8] = new Color(0, 0, 0.1f, 0.91f);
        color[9] = new Color(0, 0, 0.1f, 0.92f);
        color[10] = new Color(0, 0, 0.1f, 0.93f);
        color[11] = new Color(0, 0, 0.1f, 0.94f);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        // Create a gradation paint settings for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, 32, fraction, color);

        // Set the gradient data on g2
        g2.setPaint(gPaint);


        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();
    }
    public void resetDay() {
        dayState = day;
        filterAlpha = 0f;
    }
    public void update() {
        // Check the state of the day
        if(dayState == day){
            dayCounter++;
            if(dayCounter > 600) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk) {
            filterAlpha += 0.0001f;
            if(filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night) {
            dayCounter++;
            if (dayCounter > 600) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn) {
            filterAlpha -= 0.0001f;
            if (filterAlpha < 0f) {
                filterAlpha = 0;
                dayState = day;
            }
        }

    }
    public void applyBloomEffect(int x, int y) {
        // Reduce the resolution of the bloom effect
        int bloomWidth = gp.screenWidth / 2;
        int bloomHeight = gp.screenHeight / 2;

        // Clear the existing Graphics2D object
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, bloomWidth, bloomHeight);
        g2.setComposite(AlphaComposite.SrcOver);

        // Create a radial gradient that goes from a bright color at the center to a transparent color at the edges
        // Adjust the alpha value of the center color to change the brightness of the bloom effect
        float brightness = 1.0f; // Ensure this value is between 0.0f and 1.0f
        Color[] colors = {new Color(1f, 1f, 1f, brightness), new Color(1f, 1f, 1f, 0f)};
        float[] fractions = {0f, 1f};
        RadialGradientPaint paint = new RadialGradientPaint(x / 2, y / 2, 100, fractions, colors);

        // Apply the gradient to the Graphics2D object
        g2.setPaint(paint);
        g2.fillRect(0, 0, bloomWidth, bloomHeight);

        // Draw the bloom effect on top of your existing graphics

        g2.drawImage(bloom, 0, 0, null);
    }
    public void draw(Graphics2D g2) {
        if (gp.currentArea == gp.outside) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if (gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug
        String situation = "";

        switch (dayState) {
            case day: situation  = "Day"; break;
            case dusk: situation = "Dusk"; break;
            case night: situation = "Night"; break;
            case dawn: situation = "Dawn"; break;
        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.drawString(situation, 1025, 700);
    }
    public void moveLightSource(int x, int y) {
        // Update the position of the light source
        // This could be based on player position or other game events
        // You would need to update the RadialGradientPaint to reflect the new position
        RadialGradientPaint gPaint = new RadialGradientPaint(x, y, 32, fraction, color);
        g2.setPaint(gPaint);
    }
    public void setDay() {
        this.dayState = this.day;
    }

    public void setDusk() {
        this.dayState = this.dusk;
    }

    public void setNight() {
        this.dayState = this.night;
    }

    public void setDawn() {
        this.dayState = this.dawn;
    }

    public int getDayCounter() {
        return this.dayCounter;
    }
}