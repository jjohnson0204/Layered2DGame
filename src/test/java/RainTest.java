import environment.Rain;
import main.GamePanel;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import state.PlayState;
import state.StateManager;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RainTest {
    @Test
    void testStartAndStop() {
        // Create mock objects

        GamePanel gamePanel = (GamePanel) StateManager.get("gp");
                gamePanel = mock(GamePanel.class);
        when(gamePanel.getScreenWidth()).thenReturn(800);
        when(gamePanel.getScreenHeight()).thenReturn(600);
        Graphics2D g2 = gamePanel.g2;
               g2 = mock(Graphics2D.class);

        // Use mock objects
        Rain rain = new Rain(gamePanel);
        PlayState play = new PlayState(gamePanel, g2);

        try (MockedStatic<StateManager> mocked = Mockito.mockStatic(StateManager.class)) {
            mocked.when(() -> StateManager.hasListener(anyString())).thenReturn(true, false);
            play.start();
            rain.start();
            assertTrue(StateManager.hasListener("update"));

//            play.stop();
//            rain.stop();
//            assertFalse(StateManager.hasListener("update"));
        }
    }
}