

import org.example.MasterMindLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;


class MasterMindLogicTest {
    private Color[] palette;
    private String[] labels;
    private int secretLength;

    @BeforeEach
    void setUp() {
        palette = new Color[]{
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.ORANGE,
                Color.PINK
        };
        labels = new String[]{"R", "G", "B", "Y", "O", "P"};
        secretLength = 4;
    }

    @Test
    void testConstructorInitializesCorrectly() {
        MasterMindLogic testLogic = new MasterMindLogic(palette, secretLength, labels);
        assertNotNull(testLogic);
        assertNotNull(testLogic.getSECRET());
    }

    @Test
    void testGenerateSecretCreatesCorrectLength() {
        MasterMindLogic testLogic = new MasterMindLogic(palette, secretLength, labels);
        Color[] secret = testLogic.generateSecret(secretLength);
        assertEquals(secretLength, secret.length);
    }

    @Test
    void testGenerateSecretUsesColorsFromPalette() {
        MasterMindLogic testLogic = new MasterMindLogic(palette, secretLength, labels);
        Color[] secret = testLogic.generateSecret(secretLength);

        for (Color color : secret) {
            boolean found = false;
            for (Color paletteColor : palette) {
                if (color.equals(paletteColor)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGenerateSecretWithDifferentLengths() {
        for (int length = 1; length <= 6; length++) {
            MasterMindLogic testLogic = new MasterMindLogic(palette, length, labels);
            Color[] secret = testLogic.generateSecret(length);
            assertEquals(length, secret.length);
        }
    }

    @Test
    void testCheckGuessWithWhitePins() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        // Method override so that is returns a known secret: RED, GREEN, BLUE, YELLOW
        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {

                return new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            }
        };

        // Guess with colors in wrong positions: GREEN, RED, YELLOW, BLUE
        Color[] guess = {Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        assertEquals(0, result.blacks, "Should have 0 black pins");
        assertEquals(4, result.whites, "Should have 4 white pins");
    }

    @Test
    void testCheckGuessMixedBlackAndWhite() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {
                // Known secret: RED, GREEN, BLUE, YELLOW
                return new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            }
        };

        // Guess: RED (correct), BLUE (wrong color, right position elsewhere), GREEN (wrong position), YELLOW (correct)
        // Actually: RED (black), YELLOW (white), GREEN (white), YELLOW (black)
        Color[] guess = {Color.RED, Color.YELLOW, Color.GREEN, Color.YELLOW};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        assertEquals(2, result.blacks, "Should have 2 black pins");
        assertEquals(1, result.whites, "Should have 1 white pin");
    }

    @Test
    void testCheckGuessDuplicateColors() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {
                // Known secret with duplicates: RED, RED, BLUE, YELLOW
                return new Color[]{Color.RED, Color.RED, Color.BLUE, Color.YELLOW};
            }
        };

        // Guess: RED, GREEN, RED, YELLOW
        Color[] guess = {Color.RED, Color.GREEN, Color.RED, Color.YELLOW};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        assertEquals(2, result.blacks, "Should have 2 black pins (positions 0 and 3)");
        assertEquals(1, result.whites, "Should have 1 white pin (RED at position 2)");
    }

    @Test
    void testCheckGuessAllSameColorNoMatch() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {
                // Known secret: RED, GREEN, BLUE, YELLOW (all different)
                return new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            }
        };

        // Guess all same color that appears once
        Color[] guess = {Color.RED, Color.RED, Color.RED, Color.RED};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        assertEquals(1, result.blacks, "Should have 1 black pin");
        assertEquals(0, result.whites, "Should have 0 white pins");
    }

    @Test
    void testShowSecretReturnsCorrectFormat() {
        MasterMindLogic testLogic = new MasterMindLogic(palette, secretLength, labels);
        String secret = testLogic.showSecret();

        assertNotNull(secret);
        assertEquals(secretLength, secret.length());

        // Verify all characters are valid labels
        for (char c : secret.toCharArray()) {
            boolean found = false;
            for (String label : labels) {
                if (label.charAt(0) == c) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Secret contains invalid label: " + c);
        }
    }

    @Test
    void testShowSecretConsistency() {
        MasterMindLogic testLogic = new MasterMindLogic(palette, secretLength, labels);
        String secret1 = testLogic.showSecret();
        String secret2 = testLogic.showSecret();

        assertEquals(secret1, secret2, "showSecret should return same value when called multiple times");
    }

    @Test
    void testResultClassCreation() {
        MasterMindLogic.Result result = new MasterMindLogic.Result(2, 1);
        assertEquals(2, result.blacks);
        assertEquals(1, result.whites);
    }

    @Test
    void testResultClassWithZeros() {
        MasterMindLogic.Result result = new MasterMindLogic.Result(0, 0);
        assertEquals(0, result.blacks);
        assertEquals(0, result.whites);
    }

    @Test
    void testResultClassWithMaxValues() {
        MasterMindLogic.Result result = new MasterMindLogic.Result(4, 0);
        assertEquals(4, result.blacks);
        assertEquals(0, result.whites);

        result = new MasterMindLogic.Result(0, 4);
        assertEquals(0, result.blacks);
        assertEquals(4, result.whites);
    }

    @Test
    void testCheckGuessPartialDuplicates() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {
                // Secret: RED, RED, GREEN, BLUE
                return new Color[]{Color.RED, Color.RED, Color.GREEN, Color.BLUE};
            }
        };

        // Guess: GREEN, RED, RED, GREEN
        Color[] guess = {Color.GREEN, Color.RED, Color.RED, Color.GREEN};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        // Position 1: RED matches position 1 = 1 black
        // Position 0: GREEN is in position 2 = 1 white
        // Position 2: RED is in position 0 = 1 white
        assertEquals(1, result.blacks);
        assertEquals(2, result.whites);
    }

    @Test
    void testCheckGuessWithSingleColor() {
        Color[] testPalette = {Color.RED};
        String[] testLabels = {"R"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels);
        Color[] guess = {Color.RED, Color.RED, Color.RED, Color.RED};

        MasterMindLogic.Result result = testLogic.checkGuess(guess);
        assertEquals(4, result.blacks);
        assertEquals(0, result.whites);
    }

    @Test
    void testMultipleGuessesSameLogic() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE};
        String[] testLabels = {"R", "G", "B"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 3, testLabels);

        Color[] guess1 = {Color.RED, Color.GREEN, Color.BLUE};
        Color[] guess2 = {Color.BLUE, Color.RED, Color.GREEN};

        MasterMindLogic.Result result1 = testLogic.checkGuess(guess1);
        MasterMindLogic.Result result2 = testLogic.checkGuess(guess2);

        // Results should be consistent with the same secret
        assertNotNull(result1);
        assertNotNull(result2);
    }

    @Test
    void testEdgeCaseEmptyWhiteList() {
        Color[] testPalette = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        String[] testLabels = {"R", "G", "B", "Y"};

        MasterMindLogic testLogic = new MasterMindLogic(testPalette, 4, testLabels) {
            @Override
            public Color[] generateSecret(int length) {
                return new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            }
        };

        // All correct positions
        Color[] guess = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        MasterMindLogic.Result result = testLogic.checkGuess(guess);

        assertEquals(4, result.blacks);
        assertEquals(0, result.whites);
    }
}