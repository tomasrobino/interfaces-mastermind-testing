import org.example.Circle;
import org.example.MasterMindLogic;
import org.example.MasterMindUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MasterMindUITest {

    @Mock
    private MasterMindLogic mockLogic;

    @Spy
    private MasterMindUI ui;

    private Color[] colors;
    private String[] labels;
    private int rounds;

    @BeforeEach
    void setUp() {
        colors = new Color[]{
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.ORANGE,
                Color.PINK
        };
        labels = new String[]{"R", "G", "B", "Y", "O", "P"};
        rounds = 10;
    }

    @Test
    void testCreateStyledButton() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JButton button = testUI.createStyledButton("Test", Color.RED, 50);

        assertNotNull(button);
        assertEquals("Test", button.getText());
        assertEquals(Color.RED, button.getBackground());
        assertEquals(new Dimension(50, 50), button.getPreferredSize());
        assertTrue(button.isContentAreaFilled());
        assertFalse(button.isBorderPainted());
        assertFalse(button.isFocusPainted());
    }

    @Test
    void testCreateStyledButtonWithDifferentSizes() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        int[] sizes = {10, 25, 50, 100};
        for (int size : sizes) {
            JButton button = testUI.createStyledButton("Btn", Color.BLUE, size);
            assertEquals(new Dimension(size, size), button.getPreferredSize());
        }
    }

    @Test
    void testCreateStyledButtonWithDifferentColors() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        for (Color color : colors) {
            JButton button = testUI.createStyledButton("Btn", color, 50);
            assertEquals(color, button.getBackground());
        }
    }

    @Test
    void testCreateStyledButtonWithDifferentText() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        String[] texts = {"A", "Test", "Check", "Submit"};
        for (String text : texts) {
            JButton button = testUI.createStyledButton(text, Color.WHITE, 50);
            assertEquals(text, button.getText());
        }
    }

    @Test
    void testCreatePinPanel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel pinPanel = testUI.createPinPanel();

        assertNotNull(pinPanel);
        assertEquals(new Color(141, 69, 220), pinPanel.getBackground());
        assertInstanceOf(GridLayout.class, pinPanel.getLayout());
        assertEquals(4, pinPanel.getComponentCount());
    }

    @Test
    void testCreatePinPanelComponents() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel pinPanel = testUI.createPinPanel();

        for (Component comp : pinPanel.getComponents()) {
            assertInstanceOf(Circle.class, comp);
            Circle circle = (Circle) comp;
            assertEquals(new Color(187, 183, 172), circle.getColor());
        }
    }

    @Test
    void testCreateGuessPanel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel guessPanel = testUI.createGuessPanel();

        assertNotNull(guessPanel);
        assertEquals(new Color(141, 69, 220), guessPanel.getBackground());
        assertInstanceOf(FlowLayout.class, guessPanel.getLayout());
        assertEquals(4, guessPanel.getComponentCount());
    }

    @Test
    void testCreateGuessPanelComponents() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel guessPanel = testUI.createGuessPanel();

        for (Component comp : guessPanel.getComponents()) {
            assertInstanceOf(Circle.class, comp);
            Circle circle = (Circle) comp;
            assertEquals(new Color(187, 183, 172), circle.getColor());
            assertTrue(circle.isEnabled());
        }
    }

    @Test
    void testCreateRoundRow() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel roundPanel = testUI.createRoundRow();

        assertNotNull(roundPanel);
        assertEquals(new Color(141, 69, 220), roundPanel.getBackground());
        assertInstanceOf(FlowLayout.class, roundPanel.getLayout());
        assertEquals(2, roundPanel.getComponentCount());
    }

    @Test
    void testCreateColorDisplayLabel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JLabel label = testUI.createColorDisplayLabel(Color.RED, 50);

        assertNotNull(label);
        assertEquals("Selected", label.getText());
        assertTrue(label.isOpaque());
        assertEquals(Color.RED, label.getBackground());
        assertEquals(new Dimension(50, 50), label.getPreferredSize());
        assertEquals(SwingConstants.CENTER, label.getHorizontalAlignment());
    }

    @Test
    void testColorPinsWithMixedResults() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(2, 1);
        testUI.colorPins(pins, result);

        // First 2 should be black, next 1 white, last stays base
        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.BLACK, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testColorPinsWithOnlyWhites() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(0, 3);
        testUI.colorPins(pins, result);

        assertEquals(Color.WHITE, pins[0].getColor());
        assertEquals(Color.WHITE, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testColorPinsWithNoCorrect() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Circle[] pins = new Circle[4];
        Color baseColor = new Color(187, 183, 172);
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(baseColor, 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(0, 0);
        testUI.colorPins(pins, result);

        // All should remain base color
        for (Circle pin : pins) {
            assertEquals(baseColor, pin.getColor());
        }
    }

    @Test
    void testCreateBottomPanel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        assertNotNull(bottomPanel);
        assertEquals(new Color(141, 69, 220), bottomPanel.getBackground());
        assertInstanceOf(BorderLayout.class, bottomPanel.getLayout());
    }

    @Test
    void testCreateBottomPanelComponentCount() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        // Should have 2 components: colorPanel (WEST) and controlPanel (EAST)
        assertEquals(2, bottomPanel.getComponentCount());
    }

    @Test
    void testCreateBottomPanelColorButtons() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        Component westComponent = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.WEST);
        assertInstanceOf(JPanel.class, westComponent);

        JPanel colorPanel = (JPanel) westComponent;
        assertEquals(colors.length, colorPanel.getComponentCount());
    }

    @Test
    void testConstructorCreatesFrame() {
        // This test creates an actual UI - in headless mode it might not fully work
        // but we can verify it doesn't throw exceptions
        assertDoesNotThrow(() -> {
            new MasterMindUI(colors, labels, rounds, mockLogic);
        });
    }

    @Test
    void testGuessRowsInitialization() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        // After construction, we should have 'rounds' guess rows
        // We can't directly access private fields without reflection,
        // but we can verify the UI was constructed
        assertNotNull(testUI);
    }

    @Test
    void testPinRowsInitialization() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        // Verify UI construction
        assertNotNull(testUI);
    }

    @Test
    void testColorSelectionMechanism() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        Component westComponent = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.WEST);
        JPanel colorPanel = (JPanel) westComponent;

        // Verify we have the right number of color buttons
        assertEquals(colors.length, colorPanel.getComponentCount());

        for (int i = 0; i < colorPanel.getComponentCount(); i++) {
            Component comp = colorPanel.getComponent(i);
            assertInstanceOf(JButton.class, comp);
            JButton btn = (JButton) comp;
            assertEquals(labels[i], btn.getText());
            assertEquals(colors[i], btn.getBackground());
        }
    }

    @Test
    void testCheckButtonInControlPanel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        Component eastComponent = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.EAST);
        assertInstanceOf(JPanel.class, eastComponent);

        JPanel controlPanel = (JPanel) eastComponent;
        assertEquals(2, controlPanel.getComponentCount());

        // First component should be check button
        Component firstComp = controlPanel.getComponent(0);
        assertInstanceOf(JButton.class, firstComp);
        JButton checkBtn = (JButton) firstComp;
        assertEquals("Check", checkBtn.getText());
    }

    @Test
    void testSelectedLabelInControlPanel() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);

        Component eastComponent = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.EAST);
        JPanel controlPanel = (JPanel) eastComponent;

        // Second component should be selected label
        Component secondComp = controlPanel.getComponent(1);
        assertInstanceOf(JLabel.class, secondComp);
        JLabel selectedLabel = (JLabel) secondComp;
        assertEquals("Selected", selectedLabel.getText());
    }

    @Test
    void testBackgroundColors() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Color expectedBg = new Color(141, 69, 220);

        JPanel pinPanel = testUI.createPinPanel();
        assertEquals(expectedBg, pinPanel.getBackground());

        JPanel guessPanel = testUI.createGuessPanel();
        assertEquals(expectedBg, guessPanel.getBackground());

        JPanel roundRow = testUI.createRoundRow();
        assertEquals(expectedBg, roundRow.getBackground());

        JPanel bottomPanel = testUI.createBottomPanel(colors, labels);
        assertEquals(expectedBg, bottomPanel.getBackground());
    }

    @Test
    void testBaseColor() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Color expectedBase = new Color(187, 183, 172);

        JPanel pinPanel = testUI.createPinPanel();
        for (Component comp : pinPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(expectedBase, circle.getColor());
        }

        JPanel guessPanel = testUI.createGuessPanel();
        for (Component comp : guessPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(expectedBase, circle.getColor());
        }
    }

    @Test
    void testCircleSizes() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);

        // Pin size should be 10
        JPanel pinPanel = testUI.createPinPanel();
        for (Component comp : pinPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(10, circle.getPreferredSize().width);
        }

        // Guess size should be 35
        JPanel guessPanel = testUI.createGuessPanel();
        for (Component comp : guessPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(35, circle.getPreferredSize().width);
        }
    }

    @Test
    void testMultipleRoundsCreation() {
        int[] roundCounts = {1, 5, 10, 15};

        for (int roundCount : roundCounts) {
            MasterMindUI testUI = new MasterMindUI(colors, labels, roundCount, mockLogic);
            assertNotNull(testUI);
        }
    }

    @Test
    void testDifferentPaletteSizes() {
        Color[][] palettes = {
                {Color.RED, Color.BLUE},
                {Color.RED, Color.BLUE, Color.GREEN},
                {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE}
        };

        String[][] labelSets = {
                {"R", "B"},
                {"R", "B", "G"},
                {"R", "B", "G", "Y", "O"}
        };

        for (int i = 0; i < palettes.length; i++) {
            MasterMindUI testUI = new MasterMindUI(palettes[i], labelSets[i], rounds, mockLogic);
            JPanel bottomPanel = testUI.createBottomPanel(palettes[i], labelSets[i]);

            Component westComponent = ((BorderLayout) bottomPanel.getLayout())
                    .getLayoutComponent(BorderLayout.WEST);
            JPanel colorPanel = (JPanel) westComponent;

            assertEquals(palettes[i].length, colorPanel.getComponentCount());
        }
    }

    @Test
    void testColorPinsBlackPriority() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        // Blacks should be assigned before whites
        MasterMindLogic.Result result = new MasterMindLogic.Result(1, 2);
        testUI.colorPins(pins, result);

        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.WHITE, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testLogicIntegration() {
        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        assertNotNull(testUI);
    }

    @Test
    void testUIWithMockLogicCheckGuess() {
        MasterMindLogic.Result mockResult = new MasterMindLogic.Result(3, 1);

        MasterMindUI testUI = new MasterMindUI(colors, labels, rounds, mockLogic);
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        testUI.colorPins(pins, mockResult);

        // Verify 3 blacks and 1 white
        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.BLACK, pins[1].getColor());
        assertEquals(Color.BLACK, pins[2].getColor());
        assertEquals(Color.WHITE, pins[3].getColor());
    }
}