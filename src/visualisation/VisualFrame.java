package visualisation;

import models.Board;
import javax.swing.JFrame;

public class VisualFrame extends JFrame {

    // Constructors
    public VisualFrame(Board board) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(new BoardPanel(board));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
