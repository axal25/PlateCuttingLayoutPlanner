package parser;

import coords.exceptions.BadCoordinateValueException;
import cutter.Cutter;
import cutter.exceptions.LayoutIdDoNotMatchPreparedLayoutsIndexException;
import cutter.exceptions.SolutionLayoutVariationLimitExceededException;
import solution.Solution;
import main.MainTest;
import org.junit.jupiter.api.*;
import parser.exceptions.BadAmountOfInputArgsException;
import parser.exceptions.NullSolutionException;
import sheet.Piece;
import sheet.StaticLayoutFactory;
import sheet.StaticPieceFactory;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

public class OutputComposerTest implements StaticLayoutFactory.InterfaceTestingStaticSheetFactory, StaticPieceFactory.InterfaceTestingStaticPieceFactory, Piece.InterfaceTestingPieceSortStrategy {
    public static String[] ARGS = MainTest.ARGS;
    public static String EXPECTED_OUTPUT = MainTest.EXPECTED_OUTPUT;

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
        this.setPieceSortStrategy(Piece.DEFAULT_PIECE_SORT_STRATEGY);
    }

    @AfterEach
    void tearDown() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

    @Test
    @Order(1)
    @DisplayName("getOutputString null")
    void OutputComposer_getOutputString_null() {
        assertThrows(NullSolutionException.class, () -> OutputComposer.getOutputString(null));
    }

    @Test
    @Order(1)
    @DisplayName("getOutputString new Solution()")
    void OutputComposer_getOutputString_newSolution() throws NullSolutionException, SheetSizeException, CornersOnSidesShareNoCoordinateException, BadCoordinateValueException, NegativePiecePointsException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, LayoutFactoryNotInitiatedException, CornerNotOnSideException, LayoutFactoryAlreadyInitiatedException, NotAllCornersFoundException {
        StaticLayoutFactory.initLayoutFactor(1, 1);
        Solution solution = new Solution();
        assertEquals(
                "0",
                OutputComposer.getOutputString(solution)
        );
    }

    @Test
    @Order(99)
    @DisplayName("getOutputString PDF Example")
    void OutputComposer_getOutputString_PDFExample() throws NegativePiecePointsException, SheetSizeException, BadAmountOfInputArgsException, SheetAmountExceededLimitException, LayoutFactoryAlreadyInitiatedException, PieceCanNotFitIntoLayoutException, LayoutFactoryNotInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NullSolutionException, NotAllCornersFoundException, LayoutIdDoNotMatchPreparedLayoutsIndexException, SolutionLayoutVariationLimitExceededException {
        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, Piece.getPieceSortStrategy());
        Cutter cutter = new Cutter(ARGS);
        String output = cutter.cut();
        System.out.println(String.format("cutter.getCurSolutionsChecked(): %s", cutter.getCurSolutionsChecked()));
        System.out.println(String.format("cutter.getBestSolution(): %s", cutter.getBestSolution().toString()));
        System.out.println(String.format("cutter.getBestSolution().getPointSum(): %s", cutter.getBestSolution().getPointSum()));
        assertEquals(-1, cutter.getBestSolution().getPointSum());
        assertEquals(4, MainTest.getAmountOfPieceVariations(cutter.getBestSolution()));
        assertEquals(EXPECTED_OUTPUT, output);
        assertFalse(true);
    }
}
