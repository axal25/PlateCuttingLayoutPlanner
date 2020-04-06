package parser;

import coords.BadCoordinateValueException;
import cutter.Cutter;
import main.MainTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import cutter.Solution;
import sheet.exceptions.*;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputComposerTest {
    public static String[] ARGS = MainTest.ARGS;
    public static String EXPECTED_OUTPUT = MainTest.EXPECTED_OUTPUT;

    @Test
    @Order(1)
    @DisplayName("getOutputString null")
    void OutputComposer_getOutputString_null() throws NegativePiecePointsException, SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException {
        assertEquals("solution == null", OutputComposer.getOutputString(null));
    }

    @Test
    @Order(1)
    @DisplayName("getOutputString new Solution()")
    void OutputComposer_getOutputString_newSolution() {
        Solution solution = new Solution();
        assertEquals(
                "Solution{\n" +
                "\tlayoutVariations=TreeSet<LayoutVariation>{}\n" +
                "}",
                OutputComposer.getOutputString(solution)
        );
    }

    @Test
    @Order(99)
    @DisplayName("getOutputString PDF Example")
    void OutputComposer_getOutputString_PDFExample() throws NegativePiecePointsException, SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException, LayoutFactoryAlreadyInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException {
        Cutter cutter = new Cutter(ARGS);
        String output1 = cutter.cut();
        String output2 = OutputComposer.getOutputString(cutter.getBestSolution());
        assertEquals(output1, output2);
        assertEquals(EXPECTED_OUTPUT, output2);
    }
}
