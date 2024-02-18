package ui;

import main.GamePanel;
import main.Main;
import state.PlayState;
import state.StateManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {
    // Variables
    GamePanel gp;
    Graphics2D g2;

    DecimalFormat dFormat = new DecimalFormat("#0.00");
    // Fonts
    public Font maruMonica;
    public Font purisaB;

    // Images
    public BufferedImage heart_full;
    public BufferedImage heart_half;
    public BufferedImage heart_blank;
    public BufferedImage crystal_full;
    public BufferedImage crystal_blank;
    public BufferedImage coinImage;
    public BufferedImage keyImage;
    public BufferedImage equipped_menu;
    public BufferedImage weapon;
    public static BufferedImage ability1;
    public static BufferedImage ability2;
    public static BufferedImage ability3;

    // Lists
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    // Strings
    public static String currentDialogue = "";
    public String combinedText = "";

    // Doubles
    public double playTime;

    // Integers
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    public int subState = 0;
    public int counter = 0;
    public int charIndex = 0;
    public UI(GamePanel gp) {
        this.gp = gp;
        try{
            // load a custom font in your project folder
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/MaruMonica.ttf")).deriveFont(30f);
            purisaB = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/PurisaBold.ttf")).deriveFont(30f);
            StateManager.set("font:maruMonica", maruMonica);
            StateManager.set("font:purisaB", purisaB);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/MaruMonica.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/PurisaBold.ttf")));
        }
        catch(IOException | FontFormatException e){
            e.fillInStackTrace();
        }

        // TODO: Load images

        // TODO: Setup Equipped Menu
    }
    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        //Title State
//        if(gp.gameState.equals("title")){
////            drawTitleScreen();
//            StateManager.trigger("title");
//        }
        // Play State
//        if(gp.gameState == gp.playState){
////            drawPlayerLife();
////            drawMonsterLife();
////            drawPlayerEquippedAbilities();
//            drawGameTimer();
////            drawMessage();
//
//        }
//        // Pause State
//        if(gp.gameState == gp.pauseState){
////            drawPlayerLife();
////            drawPauseScreen();
//        }
//        // Dialogue State
//        if(gp.gameState == gp.dialogueState){
////            drawDialogueScreen();
//        }
//        //Character State
//        if(gp.gameState == gp.characterState){
////            drawCharacterScreen();
////            drawInventory(gp.players[gp.selectedPlayerIndex], true);
//        }
//        //Options State
//        if(gp.gameState == gp.optionsState){
//            drawOptionsScreen();
//        }
//        //Game Over State
//        if(gp.gameState == gp.gameOverState){
////            drawGameOverScreen();
//        }
//        //Transition State
//        if(gp.gameState == gp.transitionState){
////            drawTransition();
//        }
//        //Trade State
//        if(gp.gameState == gp.tradeState){
////            drawTradeScreen();
//        }
//        //Sleep State
//        if(gp.gameState == gp.sleepState){
////            drawSleepScreen();
//        }
    }
    public void drawGameTimer(){
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40));
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 5, gp.tileSize * 3, gp.tileSize, gp.tileSize, null);
//        List<Entity> keys = gp.players[gp.selectedPlayerIndex].inventory;
//        keys = keys.stream().filter( entity -> entity.name == "Key").collect(Collectors.toList());
//        g2.drawString("  x = " + keys.size(), 64, 230);
        g2.drawString("  x = ", 64, 230);
        // Time
        playTime +=(double) 1/60;
        g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);
    }
    public void drawTitleScreen(){

        //Title State
        if(titleScreenState == 0){
            g2.setColor(new Color(1, 35, 15));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Lights Epic Adventure";
            int x = getXforCenteredText(text);
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
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Load Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            //Class selection screen
            //BG Color
            g2.setColor(new Color(0, 168, 107)); // rgb color code it's the green from main screen
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight); // means start from top left corner and fill the screen
            //Font color
            g2.setColor(new Color(53, 94, 59));
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,42F));
            //Title of Character Screen
            String text = "Select your character...";
            int x = getXforCenteredText(text);
            int y = gp.tileSize *3;
            g2.drawString(text, x , y);
            //Shadow Color
            g2.setColor(new Color(175, 225, 175));
            g2.drawString(text, x + 5, y + 5);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x- gp.tileSize, y);
            }

            text = "Warrior";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Hunter";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Assassin";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Mage";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 4){
                g2.drawString(">", x- gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 5){
                g2.drawString(">", x- gp.tileSize, y);
            }
        }
    }
    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }

        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Fullscreen ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
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
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
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
    public void options_fullScreenNotification(int frameX, int frameY){

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
            if(gp.keyH.enterPressed){
                subState = 0;
            }
        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Control";
        textX = getXforCenteredText(text);
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
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game \nand \nreturn to title screen:";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                gp.resetGame(true);
            }
        }
        // Yes
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }
    public static void drawSubWindow(int x, int y, int width, int height){
        Graphics2D g2 = (Graphics2D) StateManager.get("g2");
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public static int getXforCenteredText(String text){
        GamePanel gp = (GamePanel) StateManager.get("gp");
        Graphics2D g2 = (Graphics2D) StateManager.get("g2");
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
