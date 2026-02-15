
import org.example.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CircleTest {

    @Spy
    private Circle clickableCircle;

    @Spy
    private Circle nonClickableCircle;

    @Mock
    private Graphics graphicsMock;

    private Color testColor;
    private int testDiameter;

    @BeforeEach
    void setUp() {
        testColor = Color.RED;
        testDiameter = 35;
        clickableCircle = spy(new Circle(testColor, testDiameter, true));
        nonClickableCircle = spy(new Circle(testColor, testDiameter, false));
    }

    @Test
    void testConstructorWithClickableTrue() {
        Circle circle = new Circle(Color.BLUE, 50, true);

        assertNotNull(circle);
        assertEquals(Color.BLUE, circle.getColor());
        assertTrue(circle.isEnabled());
        assertFalse(circle.isContentAreaFilled());
        assertFalse(circle.isBorderPainted());
        assertFalse(circle.isFocusPainted());
        assertFalse(circle.isOpaque());
    }

    @Test
    void testConstructorWithClickableFalse() {
        Circle circle = new Circle(Color.GREEN, 40, false);

        assertNotNull(circle);
        assertEquals(Color.GREEN, circle.getColor());
        assertFalse(circle.isEnabled());
        assertFalse(circle.isContentAreaFilled());
        assertFalse(circle.isBorderPainted());
        assertFalse(circle.isFocusPainted());
        assertFalse(circle.isOpaque());
    }

    @Test
    void testConstructorWithDifferentColors() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                Color.ORANGE, Color.PINK, Color.MAGENTA, Color.CYAN};

        for (Color color : colors) {
            Circle circle = new Circle(color, 30, true);
            assertEquals(color, circle.getColor());
        }
    }

    @Test
    void testConstructorWithDifferentDiameters() {
        int[] diameters = {10, 20, 35, 50, 100};

        for (int diameter : diameters) {
            Circle circle = new Circle(Color.BLACK, diameter, true);
            Dimension size = circle.getPreferredSize();
            assertEquals(diameter, size.width);
            assertEquals(diameter, size.height);
        }
    }

    @Test
    void testGetColor() {
        assertEquals(testColor, clickableCircle.getColor());
        assertEquals(testColor, nonClickableCircle.getColor());
    }

    @Test
    void testSetCircleColor() {
        Color newColor = Color.BLUE;
        clickableCircle.setCircleColor(newColor);

        assertEquals(newColor, clickableCircle.getColor());
        verify(clickableCircle, times(1)).repaint();
    }

    @Test
    void testSetCircleColorNull() {
        clickableCircle.setCircleColor(null);
        assertNull(clickableCircle.getColor());
        verify(clickableCircle, times(1)).repaint();
    }

    @Test
    void testGetPreferredSize() {
        Dimension preferredSize = clickableCircle.getPreferredSize();

        assertNotNull(preferredSize);
        assertEquals(testDiameter, preferredSize.width);
        assertEquals(testDiameter, preferredSize.height);
    }

    @Test
    void testGetPreferredSizeConsistency() {
        Dimension size1 = clickableCircle.getPreferredSize();
        Dimension size2 = clickableCircle.getPreferredSize();

        assertEquals(size1.width, size2.width);
        assertEquals(size1.height, size2.height);
    }

    @Test
    void testButtonPropertiesContentAreaFilled() {
        assertFalse(clickableCircle.isContentAreaFilled());
        assertFalse(nonClickableCircle.isContentAreaFilled());
    }

    @Test
    void testButtonPropertiesBorderPainted() {
        assertFalse(clickableCircle.isBorderPainted());
        assertFalse(nonClickableCircle.isBorderPainted());
    }

    @Test
    void testButtonPropertiesFocusPainted() {
        assertFalse(clickableCircle.isFocusPainted());
        assertFalse(nonClickableCircle.isFocusPainted());
    }

    @Test
    void testButtonPropertiesOpaque() {
        assertFalse(clickableCircle.isOpaque());
        assertFalse(nonClickableCircle.isOpaque());
    }

    @Test
    void testClickableProperty() {
        assertTrue(clickableCircle.isEnabled());
        assertFalse(nonClickableCircle.isEnabled());
    }

    @Test
    void testActionListenerOnClickable() {
        ActionListener listener = mock(ActionListener.class);
        clickableCircle.addActionListener(listener);

        assertNotNull(clickableCircle.getActionListeners());
        assertEquals(1, clickableCircle.getActionListeners().length);
    }

    @Test
    void testMultipleActionListeners() {
        ActionListener listener1 = mock(ActionListener.class);
        ActionListener listener2 = mock(ActionListener.class);

        clickableCircle.addActionListener(listener1);
        clickableCircle.addActionListener(listener2);

        assertEquals(2, clickableCircle.getActionListeners().length);
    }

    @Test
    void testRemoveActionListener() {
        ActionListener listener = mock(ActionListener.class);
        clickableCircle.addActionListener(listener);
        assertEquals(1, clickableCircle.getActionListeners().length);

        clickableCircle.removeActionListener(listener);
        assertEquals(0, clickableCircle.getActionListeners().length);
    }

    @Test
    void testCircleWithSmallDiameter() {
        Circle smallCircle = new Circle(Color.BLACK, 5, true);
        assertEquals(5, smallCircle.getPreferredSize().width);
        assertEquals(5, smallCircle.getPreferredSize().height);
    }

    @Test
    void testCircleWithLargeDiameter() {
        Circle largeCircle = new Circle(Color.BLACK, 200, true);
        assertEquals(200, largeCircle.getPreferredSize().width);
        assertEquals(200, largeCircle.getPreferredSize().height);
    }

    @Test
    void testColorChangeDoesNotAffectDiameter() {
        Dimension sizeBefore = clickableCircle.getPreferredSize();
        clickableCircle.setCircleColor(Color.MAGENTA);
        Dimension sizeAfter = clickableCircle.getPreferredSize();

        assertEquals(sizeBefore.width, sizeAfter.width);
        assertEquals(sizeBefore.height, sizeAfter.height);
    }

    @Test
    void testCircleEquality() {
        Circle circle1 = new Circle(Color.RED, 30, true);
        Circle circle2 = new Circle(Color.RED, 30, true);

        // Different objects
        assertNotSame(circle1, circle2);

        // Same properties
        assertEquals(circle1.getColor(), circle2.getColor());
        assertEquals(circle1.getPreferredSize(), circle2.getPreferredSize());
        assertEquals(circle1.isEnabled(), circle2.isEnabled());
    }

    @Test
    void testCircleWithCustomColor() {
        Color customColor = new Color(123, 45, 67);
        Circle circle = new Circle(customColor, 25, true);

        assertEquals(customColor, circle.getColor());
    }

    @Test
    void testSetCircleColorTriggersRepaint() {
        // Create a real circle (not spy) to test repaint behavior
        Circle realCircle = new Circle(Color.RED, 30, true);
        Circle spyCircle = spy(realCircle);

        spyCircle.setCircleColor(Color.BLUE);

        verify(spyCircle, times(1)).repaint();
    }
}