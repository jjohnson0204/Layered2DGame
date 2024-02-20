package events;

import javax.swing.*;
import java.awt.event.*;

public class EventHandler extends JFrame {
    JButton button;

    public EventHandler() {
        button = new JButton("Click me");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked");
            }
        });
        add(button);
        setSize(200, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EventHandler();
    }
}
