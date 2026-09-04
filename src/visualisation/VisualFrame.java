package visualisation;

import models.Board;
import javax.swing.JFrame;

public class VisualFrame extends JFrame {

    // Constructors
    public VisualFrame(Board board) {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.add(new BoardPanel(board));
        this.pack();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
