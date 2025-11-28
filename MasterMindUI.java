import java.awt.*;
import javax.swing.*;

public class MasterMindUI {

    // CirclePanel → used for PINS (not clickable)
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

    // CircleButton → used for GUESS SLOTS (clickable)
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

    public MasterMindUI() {

        Color bgColor = new Color(194, 243, 213);

        // Bottom color buttons
        Color[] colors = {
                new Color(240, 17, 17),
                new Color(104, 227, 70),
                new Color(45, 64, 189),
                new Color(191, 83, 40),
                new Color(242, 135, 34),
                new Color(204, 94, 235)
        };

        String[] labels = {"R", "V", "A", "M", "N", "L"};

        JFrame frame = new JFrame("MasterMind");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // CENTER: Board with 10 rows
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        centerPanel.setLayout(new GridLayout(10, 1, 0, 0));
        frame.add(centerPanel, BorderLayout.CENTER);

        int pinSize = 10;     // small scoring pins
        int guessSize = 35;   // big guess circles

        for (int i = 0; i < 10; i++) {

            // Row container
            JPanel roundPanel = new JPanel();
            roundPanel.setBackground(bgColor);
            roundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

            // PIN PANEL
            JPanel pinPanel = new JPanel();
            pinPanel.setBackground(bgColor);
            pinPanel.setLayout(new GridLayout(2, 2, 5, 5));

            for (int j = 0; j < 4; j++) {
                pinPanel.add(new CirclePanel(new Color(187, 183, 172), pinSize));
            }

            // GUESS PANEL
            JPanel guessPanel = new JPanel();
            guessPanel.setBackground(bgColor);
            guessPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            for (int j = 0; j < 4; j++) {
                CircleButton btn = new CircleButton(new Color(187, 183, 172), guessSize);

                // Example action
                btn.addActionListener(e -> System.out.println("Guess button clicked"));

                guessPanel.add(btn);
            }

            roundPanel.add(pinPanel);
            roundPanel.add(guessPanel);
            centerPanel.add(roundPanel);
        }

        // BOTTOM
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // COLORS
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(bgColor);
        colorPanel.setLayout(new GridLayout(0, 6, 0, 0));

        for (int i = 0; i < colors.length; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setPreferredSize(new Dimension(50, 50));
            btn.setBackground(colors[i]);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            colorPanel.add(btn);
        }
        bottomPanel.add(colorPanel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MasterMindUI();
    }
}
