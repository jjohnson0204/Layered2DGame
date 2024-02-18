package input;

import main.GamePanel;
import state.StateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean enterKeyProcessed = false;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed, spacePressed;
    public boolean shotKeyPressed, skillKeyPressed, burstKeyPressed;

    GamePanel gp;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER && !enterKeyProcessed) {
            StateManager.trigger("keydown:enter");
            enterPressed = true;
            enterKeyProcessed = true; // Set the flag to true after the key is pressed
        }
        if (code == KeyEvent.VK_A) {
            StateManager.trigger("keydown:left");
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            StateManager.trigger("keydown:right");
            rightPressed = true;
        }
        // Handle input for the current state
//        StateManager.getState().handleInput(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            StateManager.trigger("keyup:up");
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            StateManager.trigger("keyup:down");
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            StateManager.trigger("keyup:left");
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            StateManager.trigger("keyup:right");
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            StateManager.trigger("keyup:normal");
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_B) {
            StateManager.trigger("keyup:skill");
            skillKeyPressed = false;
        }
        if (code == KeyEvent.VK_V) {
            StateManager.trigger("keyup:burst");
            burstKeyPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            StateManager.trigger("keyup:enter");
            enterPressed = false;
            enterKeyProcessed = false; // Reset the flag when the key is released
        }
        if (code == KeyEvent.VK_SPACE) {
            StateManager.trigger("keyup:space");
            spacePressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            StateManager.trigger("keyup:escape");
        }
        if (code == KeyEvent.VK_P) {
            StateManager.trigger("keyup:p");
        }
        if (code == KeyEvent.VK_R) {
            StateManager.trigger("keyup:r");
        }
        if (code == KeyEvent.VK_T) {
            StateManager.trigger("keyup:t");
        }
        if (code == KeyEvent.VK_G) {
            StateManager.trigger("keyup:y");
        }
    }
}
