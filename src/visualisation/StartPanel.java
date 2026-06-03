package visualisation;

import java.awt.Dimension;
import javax.swing.JPanel;

public class StartPanel extends JPanel {

    public StartPanel() {
        setPreferredSize(new Dimension(VisualFrame.getScreenSide(), VisualFrame.getScreenSide()));
    }
}
