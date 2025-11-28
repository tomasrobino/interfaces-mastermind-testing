
import java.awt.*;
import javax.swing.*;

public class MasterMindUI {

    public MasterMindUI() {
        // Background color
        Color bgColor = new Color(0x8FEBB8);

        // Colors and labels for the 6 buttons
        Color[] colors = { new Color(240, 17, 17), // Rojo (R)
                new Color(104, 227, 70), // Verde (V)
                new Color(45, 64, 189), // Azul (A)
                new Color(191, 83, 40), // Marrón (M)
                new Color(242, 135, 34), // Naranja (N)
                new Color(204, 94, 235) // Lila (L)
        };
        String[] labels = { "R", "V", "A", "M", "N", "L" };

        // Main frame
        JFrame frame = new JFrame("MasterMind");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Pins
        JPanel pinPanel = new JPanel();
        pinPanel.setBackground(bgColor);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bgColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        for (int i = 0; i < colors.length; i++) {
            JButton btn = new JButton(labels[i]);
            btn.setPreferredSize(new Dimension(50, 50)); // square buttons
            btn.setBackground(colors[i]); // sets the background
            btn.setOpaque(true); // ensures background shows
            btn.setBorderPainted(false); // removes the default border
            btn.setFocusPainted(false); // removes the grey focus rectangle
            btn.setContentAreaFilled(true); // ensures the background is filled
            bottomPanel.add(btn);
        }

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Show frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MasterMindUI();
    }
}
