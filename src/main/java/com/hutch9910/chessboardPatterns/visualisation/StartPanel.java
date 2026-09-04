package com.hutch9910.chessboardPatterns.visualisation;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class StartPanel extends JPanel {

    private static final int SLOT_COUNT = 8;
    private Color currentColor = Color.BLACK;
    private final SequencePanel sequencePanel;
    private PieceComponent draggingComponent;

    public StartPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(VisualFrame.getScreenSide(), VisualFrame.getScreenSide()));
        setBackground(new Color(0xFFF3E8));

        // Title
        JLabel title = new JLabel("ChessBoard Patterns", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 32f));

        title.setBackground(Color.black);
        title.setOpaque(true);
        
        title.setBorder(BorderFactory.createEmptyBorder(28,0,18,0));
        add(title, BorderLayout.NORTH);

        // Center column with controls stacked vertically
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Instruction label and sequence box
        JLabel instr = new JLabel("Drag and drop:");
        instr.setAlignmentX(Component.CENTER_ALIGNMENT);
        instr.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
        center.add(instr);

        sequencePanel = new SequencePanel();
        sequencePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(sequencePanel);

        // color selector row (dots)
        JPanel colorRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        colorRow.setOpaque(false);
        Color[] colors = {Color.BLACK, Color.RED, new Color(0xFF69B4), Color.BLUE, Color.GREEN, Color.YELLOW};
        for (Color c : colors) {
            colorRow.add(new ColorDot(c));
        }
        center.add(colorRow);

        // palette row (piece types)
        JPanel paletteRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        paletteRow.setOpaque(false);
        for (TypeOfPiece t : TypeOfPiece.values()) {
            paletteRow.add(new PieceButton(t));
        }
        center.add(paletteRow);

        add(center, BorderLayout.CENTER);

        // Start button at bottom
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        JButton startBtn = new JButton("START SIMULATION");
        startBtn.setFont(startBtn.getFont().deriveFont(Font.BOLD, 14f));
        startBtn.setPreferredSize(new Dimension(220, 44));
        startBtn.setFocusPainted(false);
        startBtn.setBackground(Color.WHITE);
        startBtn.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        bottom.add(startBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    // small circular color selector
    private class ColorDot extends JComponent {
        private final Color color;
        public ColorDot(Color color) {
            this.color = color;
            setPreferredSize(new Dimension(16,16));
            setToolTipText("Select team color");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentColor = color;
                    // visually indicate selection by repainting sequence/palette if needed
                    repaint();
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.fillOval(0,0,getWidth(),getHeight());
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(0,0,getWidth()-1,getHeight()-1);
        }
    }

    private String typeToLabel(TypeOfPiece t) {
        switch (t) {
            case ANTELOPE: return "A";
            case DABBABA: return "Da";
            case DROMEDARY: return "Dr";
            case ELEPHANT: return "E";
            case FERZ: return "F";
            case KNIGHT: return "K";
            case WAZIR: return "W";
            case ZEBRA: return "Z";
            default: return t.name();
        }
    }

    // Palette item (small labeled square)
    private class PieceButton extends JLabel {
        private final TypeOfPiece type;
        public PieceButton(TypeOfPiece type) {
            super(typeToLabel(type), CENTER);
            this.type = type;
            setOpaque(true);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            setPreferredSize(new Dimension(44,44));
            setTransferHandler(new PieceExportTransferHandler());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    getTransferHandler().exportAsDrag(PieceButton.this, e, TransferHandler.COPY);
                }
            });
        }
    }

    // Sequence outer box containing fixed slots
    private class SequencePanel extends JPanel {
        private final SlotPanel[] slots = new SlotPanel[SLOT_COUNT];
        public SequencePanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            JPanel outer = new JPanel();
            outer.setPreferredSize(new Dimension(100,20));
            outer.setBackground(Color.WHITE);
            outer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            outer.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 12));
            for (int i=0;i<SLOT_COUNT;i++) {
                slots[i] = new SlotPanel();
                outer.add(slots[i]);
            }
            add(outer, BorderLayout.CENTER);
            setTransferHandler(new PieceImportTransferHandler());
        }
        public SlotPanel[] getSlots() { return slots; }
    }

    // individual slot that can hold a piece (or be empty)
    private class SlotPanel extends JPanel {
        private PieceComponent pieceComp;
        public SlotPanel() {
            setPreferredSize(new Dimension(50, 50));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            setLayout(new BorderLayout());
        }
        public boolean isEmpty() { return pieceComp == null; }
        public void setPiece(TypeOfPiece type, Color color) {
            removeAll();
            pieceComp = new PieceComponent(type, color);
            add(pieceComp, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
        public void clearPiece() {
            removeAll();
            pieceComp = null;
            revalidate();
            repaint();
        }
        public PieceComponent getPieceComponent() { return pieceComp; }
    }

    // visual component for a placed piece (small labeled square)
    private class PieceComponent extends JPanel {
        private final TypeOfPiece type;
        private final Color color;
        public PieceComponent(TypeOfPiece type, Color color) {
            this.type = type;
            this.color = color;
            setOpaque(false);
            setTransferHandler(new PieceExportTransferHandler());
            // setPreferredSize(new Dimension(48,36));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    draggingComponent = PieceComponent.this;
                    getTransferHandler().exportAsDrag(PieceComponent.this, e, TransferHandler.MOVE);
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            g2.setColor(color);
            g2.fillRect(2,2,w-4,h-4);
            g2.setColor(Color.WHITE);
            String label = typeToLabel(type);
            int sw = g2.getFontMetrics().stringWidth(label);
            g2.drawString(label, (w-sw)/2, h/2 + 5);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect(0,0,w-1,h-1);
        }
    }

    // Transferable wrapper for string payload
    private static class StringSelection implements Transferable {
        private final String data;
        public StringSelection(String data) { this.data = data; }
        @Override public DataFlavor[] getTransferDataFlavors() { return new DataFlavor[]{DataFlavor.stringFlavor}; }
        @Override public boolean isDataFlavorSupported(DataFlavor flavor) { return DataFlavor.stringFlavor.equals(flavor); }
        @Override public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);
            return data;
        }
    }

    // Export handler: "TYPE;rgb"
    private class PieceExportTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            String typeName;
            Color color;
            if (c instanceof PieceButton) {
                typeName = ((PieceButton)c).type.name();
                color = currentColor;
            } else if (c instanceof PieceComponent) {
                PieceComponent pc = (PieceComponent)c;
                typeName = pc.type.name();
                color = pc.color;
            } else return null;
            return new StringSelection(typeName + ";" + color.getRGB());
        }
        @Override public int getSourceActions(JComponent c) { return COPY_OR_MOVE; }
    }

    // Import handler: place into first empty slot or append into first available; if moving, clear origin slot
    private class PieceImportTransferHandler extends TransferHandler {
        @Override public boolean canImport(TransferSupport support) { return support.isDataFlavorSupported(DataFlavor.stringFlavor); }
        @Override public boolean importData(TransferSupport support) {
            if (!canImport(support)) return false;
            try {
                String payload = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                String[] p = payload.split(";");
                TypeOfPiece type = TypeOfPiece.valueOf(p[0]);
                Color color = new Color(Integer.parseInt(p[1]));
                // if moving, remove origin piece from its slot
                if (draggingComponent != null) {
                    Container parent = draggingComponent.getParent();
                    while (parent != null && !(parent instanceof SlotPanel)) parent = parent.getParent();
                    if (parent instanceof SlotPanel) ((SlotPanel)parent).clearPiece();
                    draggingComponent = null;
                }
                // find first empty slot
                for (SlotPanel s : sequencePanel.getSlots()) {
                    if (s.isEmpty()) { s.setPiece(type, color); return true; }
                }
                // if none empty, place in first slot (overwrite)
                sequencePanel.getSlots()[0].setPiece(type, color);
                return true;
            } catch (UnsupportedFlavorException | IOException ex) { ex.printStackTrace(); return false; }
        }
    }

}
