package visualisation;

import modelli.Tabellone;
import javax.swing.JFrame;

public class VisualFrame extends JFrame {

    public VisualFrame(Tabellone tabellone) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(new TabellonePanel(tabellone));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
