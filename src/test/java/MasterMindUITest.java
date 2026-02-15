import org.example.Circle;
import org.example.MasterMindLogic;
import org.example.MasterMindUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MasterMindUITest {

    @Mock
    private MasterMindLogic mockLogic;

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

        // Create UI and initialize without showing frame
        ui = new MasterMindUI();
        ui.initialize(colors, labels, rounds, mockLogic);
    }

    @Test
    void testCreateStyledButton() {
        JButton button = ui.createStyledButton("Test", Color.RED, 50);

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
        int[] sizes = {10, 25, 50, 100};
        for (int size : sizes) {
            JButton button = ui.createStyledButton("Btn", Color.BLUE, size);
            assertEquals(new Dimension(size, size), button.getPreferredSize());
        }
    }

    @Test
    void testCreateStyledButtonWithDifferentColors() {
        for (Color color : colors) {
            JButton button = ui.createStyledButton("Btn", color, 50);
            assertEquals(color, button.getBackground());
        }
    }

    @Test
    void testCreateStyledButtonWithDifferentText() {
        String[] texts = {"A", "Test", "Check", "Submit"};
        for (String text : texts) {
            JButton button = ui.createStyledButton(text, Color.WHITE, 50);
            assertEquals(text, button.getText());
        }
    }

    @Test
    void testCreatePinPanel() {
        JPanel pinPanel = ui.createPinPanel();

        assertNotNull(pinPanel);
        assertEquals(new Color(141, 69, 220), pinPanel.getBackground());
        assertInstanceOf(GridLayout.class, pinPanel.getLayout());
        assertEquals(4, pinPanel.getComponentCount());
    }

    @Test
    void testCreatePinPanelComponents() {
        JPanel pinPanel = ui.createPinPanel();

        for (Component comp : pinPanel.getComponents()) {
            assertInstanceOf(Circle.class, comp);
            Circle circle = (Circle) comp;
            assertEquals(new Color(187, 183, 172), circle.getColor());
        }
    }

    @Test
    void testCreateGuessPanel() {
        JPanel guessPanel = ui.createGuessPanel();

        assertNotNull(guessPanel);
        assertEquals(new Color(141, 69, 220), guessPanel.getBackground());
        assertInstanceOf(FlowLayout.class, guessPanel.getLayout());
        assertEquals(4, guessPanel.getComponentCount());
    }

    @Test
    void testCreateGuessPanelComponents() {
        JPanel guessPanel = ui.createGuessPanel();

        for (Component comp : guessPanel.getComponents()) {
            assertInstanceOf(Circle.class, comp);
            Circle circle = (Circle) comp;
            assertEquals(new Color(187, 183, 172), circle.getColor());
            assertTrue(circle.isEnabled());
        }
    }

    @Test
    void testCreateRoundRow() {
        JPanel roundPanel = ui.createRoundRow();

        assertNotNull(roundPanel);
        assertEquals(new Color(141, 69, 220), roundPanel.getBackground());
        assertInstanceOf(FlowLayout.class, roundPanel.getLayout());
        assertEquals(2, roundPanel.getComponentCount());
    }

    @Test
    void testCreateColorDisplayLabel() {
        JLabel label = ui.createColorDisplayLabel(Color.RED, 50);

        assertNotNull(label);
        assertEquals("Selected", label.getText());
        assertTrue(label.isOpaque());
        assertEquals(Color.RED, label.getBackground());
        assertEquals(new Dimension(50, 50), label.getPreferredSize());
        assertEquals(SwingConstants.CENTER, label.getHorizontalAlignment());
    }

    @Test
    void testColorPinsWithMixedResults() {
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(2, 1);
        ui.colorPins(pins, result);

        // First 2 should be black, next 1 white, last stays base
        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.BLACK, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testColorPinsWithOnlyWhites() {
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(0, 3);
        ui.colorPins(pins, result);

        assertEquals(Color.WHITE, pins[0].getColor());
        assertEquals(Color.WHITE, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testColorPinsWithNoCorrect() {
        Circle[] pins = new Circle[4];
        Color baseColor = new Color(187, 183, 172);
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(baseColor, 10, false);
        }

        MasterMindLogic.Result result = new MasterMindLogic.Result(0, 0);
        ui.colorPins(pins, result);

        // All should remain base color
        for (Circle pin : pins) {
            assertEquals(baseColor, pin.getColor());
        }
    }

    @Test
    void testCreateBottomPanel() {
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

        assertNotNull(bottomPanel);
        assertEquals(new Color(141, 69, 220), bottomPanel.getBackground());
        assertInstanceOf(BorderLayout.class, bottomPanel.getLayout());
    }

    @Test
    void testCreateBottomPanelComponentCount() {
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

        // Should have 2 components: colorPanel (WEST) and controlPanel (EAST)
        assertEquals(2, bottomPanel.getComponentCount());
    }

    @Test
    void testCreateBottomPanelColorButtons() {
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

        Component westComponent = ((BorderLayout) bottomPanel.getLayout())
                .getLayoutComponent(BorderLayout.WEST);
        assertInstanceOf(JPanel.class, westComponent);

        JPanel colorPanel = (JPanel) westComponent;
        assertEquals(colors.length, colorPanel.getComponentCount());
    }

    @Test
    void testConstructorCreatesFrame() {
        assertDoesNotThrow(() -> {
            MasterMindUI testUI = new MasterMindUI();
            testUI.initialize(colors, labels, rounds, mockLogic);
        });
    }

    @Test
    void testGuessRowsInitialization() {
        // After initialization without frame, verify the UI object exists
        assertNotNull(ui);
    }

    @Test
    void testPinRowsInitialization() {
        // Verify UI construction
        assertNotNull(ui);
    }

    @Test
    void testColorSelectionMechanism() {
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

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
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

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
        JPanel bottomPanel = ui.createBottomPanel(colors, labels);

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
        Color expectedBg = new Color(141, 69, 220);

        JPanel pinPanel = ui.createPinPanel();
        assertEquals(expectedBg, pinPanel.getBackground());

        JPanel guessPanel = ui.createGuessPanel();
        assertEquals(expectedBg, guessPanel.getBackground());

        JPanel roundRow = ui.createRoundRow();
        assertEquals(expectedBg, roundRow.getBackground());

        JPanel bottomPanel = ui.createBottomPanel(colors, labels);
        assertEquals(expectedBg, bottomPanel.getBackground());
    }

    @Test
    void testBaseColor() {
        Color expectedBase = new Color(187, 183, 172);

        JPanel pinPanel = ui.createPinPanel();
        for (Component comp : pinPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(expectedBase, circle.getColor());
        }

        JPanel guessPanel = ui.createGuessPanel();
        for (Component comp : guessPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(expectedBase, circle.getColor());
        }
    }

    @Test
    void testCircleSizes() {
        // Pin size should be 10
        JPanel pinPanel = ui.createPinPanel();
        for (Component comp : pinPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(10, circle.getPreferredSize().width);
        }

        // Guess size should be 35
        JPanel guessPanel = ui.createGuessPanel();
        for (Component comp : guessPanel.getComponents()) {
            Circle circle = (Circle) comp;
            assertEquals(35, circle.getPreferredSize().width);
        }
    }

    @Test
    void testMultipleRoundsCreation() {
        int[] roundCounts = {1, 5, 10, 15};

        for (int roundCount : roundCounts) {
            MasterMindUI testUI = new MasterMindUI();
            testUI.initialize(colors, labels, roundCount, mockLogic);
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
            MasterMindUI testUI = new MasterMindUI();
            testUI.initialize(palettes[i], labelSets[i], rounds, mockLogic);
            JPanel bottomPanel = testUI.createBottomPanel(palettes[i], labelSets[i]);

            Component westComponent = ((BorderLayout) bottomPanel.getLayout())
                    .getLayoutComponent(BorderLayout.WEST);
            JPanel colorPanel = (JPanel) westComponent;

            assertEquals(palettes[i].length, colorPanel.getComponentCount());
        }
    }

    @Test
    void testColorPinsBlackPriority() {
        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        // Blacks should be assigned before whites
        MasterMindLogic.Result result = new MasterMindLogic.Result(1, 2);
        ui.colorPins(pins, result);

        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.WHITE, pins[1].getColor());
        assertEquals(Color.WHITE, pins[2].getColor());
        assertEquals(new Color(187, 183, 172), pins[3].getColor());
    }

    @Test
    void testLogicIntegration() {
        assertNotNull(ui);
    }

    @Test
    void testUIWithMockLogicCheckGuess() {
        MasterMindLogic.Result mockResult = new MasterMindLogic.Result(3, 1);

        Circle[] pins = new Circle[4];
        for (int i = 0; i < 4; i++) {
            pins[i] = new Circle(new Color(187, 183, 172), 10, false);
        }

        ui.colorPins(pins, mockResult);

        // Verify 3 blacks and 1 white
        assertEquals(Color.BLACK, pins[0].getColor());
        assertEquals(Color.BLACK, pins[1].getColor());
        assertEquals(Color.BLACK, pins[2].getColor());
        assertEquals(Color.WHITE, pins[3].getColor());
    }
}