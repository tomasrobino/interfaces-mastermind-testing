import java.awt.*;
import javax.swing.*;

public class MasterMindUI {

    // Constants
    private static final int PIN_SIZE = 10;
    private static final int GUESS_SIZE = 35;

    // Circle not clickable
    private static class CirclePanel extends JPanel {
        private final Color color;
        private final int diameter;

        public CirclePanel(Color color, int diameter) {
            this.color = color;
            this.diameter = diameter;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            g.fillOval(0, 0, getWidth(), getHeight());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(diameter, diameter);
        }
    }

    // Circle clickable
    private static class CircleButton extends JButton {
        private final Color color;
        private final int diameter;

        public CircleButton(Color color, int diameter) {
            this.color = color;
            this.diameter = diameter;

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(color);
            g.fillOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(diameter, diameter);
        }
    }

    // Helper functions
    // Create buttons
    private JButton createStyledButton(String text, Color bg, int size) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(size, size));
        b.setBackground(bg);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        return b;
    }

    // Main UI
    public MasterMindUI() {
        // Color variables
        Color bgColor = new Color(194, 243, 213);
        Color[] colors = {
                new Color(240, 17, 17),
                new Color(104, 227, 70),
                new Color(45, 64, 189),
                new Color(191, 83, 40),
                new Color(242, 135, 34),
                new Color(204, 94, 235)
        };
        Color base = new Color(187, 183, 172);

        String[] labels = {"R", "V", "A", "M", "N", "L"};

        // Main frame
        JFrame frame = new JFrame("MasterMind");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Center: Board with 10 rows for 10 rounds
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        centerPanel.setLayout(new GridLayout(10, 1, 0, 0));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Generating rounds
        for (int i = 0; i < 10; i++) {
            // Row container
            JPanel roundPanel = new JPanel();
            roundPanel.setBackground(bgColor);
            roundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

            // Pin panel
            JPanel pinPanel = new JPanel();
            pinPanel.setBackground(bgColor);
            pinPanel.setLayout(new GridLayout(2, 2, 5, 5));

            for (int j = 0; j < 4; j++) {
                pinPanel.add(new CirclePanel(new Color(187, 183, 172), PIN_SIZE));
            }

            // Guess panel
            JPanel guessPanel = new JPanel();
            guessPanel.setBackground(bgColor);
            guessPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            // Generating guess slots
            for (int j = 0; j < 4; j++) {
                CircleButton btn = new CircleButton(base, GUESS_SIZE);

                // Example action
                btn.addActionListener(e -> System.out.println("Guess button clicked"));

                guessPanel.add(btn);
            }

            roundPanel.add(pinPanel);
            roundPanel.add(guessPanel);
            centerPanel.add(roundPanel);
        }

        // Bottom, for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new BorderLayout(10, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Color panel
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(bgColor);
        colorPanel.setLayout(new GridLayout(1, 6, 0, 0));
        for (int i = 0; i < colors.length; i++) {
            JButton btn = createStyledButton(labels[i], colors[i], 50);
            colorPanel.add(btn);
        }

        // Control buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(bgColor);
        controlPanel.setLayout(new GridLayout(1, 2, 10, 0));
        controlPanel.setPreferredSize(new Dimension(200, 50));
        // Check
        JButton checkBtn = createStyledButton("Check", base, 50);
        controlPanel.add(checkBtn);
        // Selected
        JButton selected = createStyledButton("Selected", base, 50);
        controlPanel.add(selected);
        // Adding
        bottomPanel.add(colorPanel, BorderLayout.WEST);
        bottomPanel.add(controlPanel, BorderLayout.EAST);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new MasterMindUI();
    }
}
