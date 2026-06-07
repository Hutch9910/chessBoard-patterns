package visualisation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import input.MouseInput;

public class StartPanel extends JPanel {

    public StartPanel() {
        setPreferredSize(new Dimension(VisualFrame.getScreenSide(), VisualFrame.getScreenSide()));
        // JButton button = new JButton();

        // MouseInput mouseInput = new MouseInput(null);

        // button.addActionListener(mouseInput);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D g2 = (Graphics2D) g;


    }



    
}
