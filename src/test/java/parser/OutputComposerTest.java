package parser;

import coords.exceptions.BadCoordinateValueException;
import cutter.Cutter;
import cutter.Solution;
import cutter.exceptions.CutCaseNullArgumentException;
import main.MainTest;
import org.junit.jupiter.api.*;
import parser.exceptions.BadAmountOfInputArgsException;
import sheet.StaticLayoutFactory;
import sheet.StaticPieceFactory;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputComposerTest implements StaticLayoutFactory.InterfaceTestingStaticSheetFactory, StaticPieceFactory.InterfaceTestingStaticPieceFactory {
    public static String[] ARGS = MainTest.ARGS;
    public static String EXPECTED_OUTPUT = MainTest.EXPECTED_OUTPUT;

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

    @AfterEach
    void tearDown() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

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
    void OutputComposer_getOutputString_PDFExample() throws NegativePiecePointsException, SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException, LayoutFactoryAlreadyInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        Cutter cutter = new Cutter(ARGS);
        String output1 = cutter.cut();
        String output2 = OutputComposer.getOutputString(cutter.getBestSolution());
        assertEquals(output1, output2);
//        assertEquals(EXPECTED_OUTPUT, output2);
    }
}
