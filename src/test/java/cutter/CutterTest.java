package cutter;

import coords.exceptions.BadCoordinateValueException;
import cutter.exceptions.LayoutIdDoNotMatchPreparedLayoutsIndexException;
import cutter.exceptions.SolutionLayoutVariationLimitExceededException;
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
import sheet.sort.strategy.PieceSortStrategy;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CutterTest implements Piece.InterfaceTestingPieceSortStrategy, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, StaticPieceFactory.InterfaceTestingStaticPieceFactory {
    public static String[] ARGS = MainTest.ARGS;

    @BeforeEach
    void setUp() {
        this.resetPieceFactory();
        this.resetLayoutFactory();
        this.setPieceSortStrategy(Piece.DEFAULT_PIECE_SORT_STRATEGY);
    }

    @AfterEach
    void tearDown() {
        this.resetPieceFactory();
        this.resetLayoutFactory();
    }

    @Test
    @Order(1)
    @DisplayName("Cutter Constructor")
    void cutterConstructor() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        Cutter cutter = new Cutter(ARGS);
    }

    @Test
    @Order(2)
    @DisplayName("Sort Pieces Default (Piece.DEFAULT_PIECE_SORT_STRATEGY) Strategy")
    void cutterSortDefault() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, this.getPieceSortStrategy());
        this.setPieceSortStrategy(PieceSortStrategy.LONGEST_SIDE_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousLongestSideIsLargerThanOrEqualToCurrent(pieces[i - 1], pieces[i]);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Sort Pieces AREA DESC Strategy")
    void cutterSortAreaDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.AREA_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousAreaIsLargerThanOrEqualToCurrent(pieces[i - 1], pieces[i]);
        }
    }

    void assertPreviousAreaIsLargerThanOrEqualToCurrent(Piece prev, Piece cur) {
        final int prevArea = prev.getHeight() * prev.getWidth();
        final int curArea = cur.getHeight() * cur.getWidth();
        org.hamcrest.MatcherAssert.assertThat(
                prevArea,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curArea)
        );
        assertTrue(prevArea >= curArea);
    }

    @Test
    @Order(4)
    @DisplayName("Sort Pieces LONGEST SIDE DESC Strategy")
    void cutterSortLongestSideDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.LONGEST_SIDE_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousLongestSideIsLargerThanOrEqualToCurrent(pieces[i - 1], pieces[i]);
        }
    }

    void assertPreviousLongestSideIsLargerThanOrEqualToCurrent(Piece prev, Piece cur) {
        final int prevLongestSide = (prev.getHeight() > prev.getWidth()) ? prev.getHeight() : prev.getWidth();
        final int curLongestSide = (cur.getHeight() > cur.getWidth()) ? cur.getHeight() : cur.getWidth();
        org.hamcrest.MatcherAssert.assertThat(
                prevLongestSide,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curLongestSide)
        );
        assertTrue(prevLongestSide >= curLongestSide);
    }

    @Test
    @Order(5)
    @DisplayName("Sort Pieces HEIGHT DESC Strategy")
    void cutterSortHeightDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.HEIGHT_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousHeightOrWidthIsLongerThanOrEqualToCurrent(pieces[i - 1].getHeight(), pieces[i].getHeight());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Sort Pieces WIDTH DESC Strategy")
    void cutterSortWidthDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.WIDTH_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousHeightOrWidthIsLongerThanOrEqualToCurrent(pieces[i - 1].getWidth(), pieces[i].getWidth());
        }
    }

    void assertPreviousHeightOrWidthIsLongerThanOrEqualToCurrent(int prevHeightOrWidth, int curHeightOrWidth) {
        org.hamcrest.MatcherAssert.assertThat(
                prevHeightOrWidth,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curHeightOrWidth)
        );
        assertTrue(prevHeightOrWidth >= curHeightOrWidth);
    }

    @Test
    @Order(7)
    @DisplayName("Sort Pieces POINTS_TO_AREA_RATIO_DESC Strategy")
    void cutterSortPointsToAreaRatioDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.POINTS_TO_AREA_RATIO_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousPointsToAreaRatioIsLargerThanOrEqualToCurrent(pieces[i - 1], pieces[i]);
        }
    }

    void assertPreviousPointsToAreaRatioIsLargerThanOrEqualToCurrent(Piece prevPiece, Piece curPiece) {
        double prevPointsToAreaRatio = getPointsToAreaRatioDesc(prevPiece);
        double curPointsToAreaRatio = getPointsToAreaRatioDesc(curPiece);
        org.hamcrest.MatcherAssert.assertThat(
                prevPointsToAreaRatio,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curPointsToAreaRatio)
        );
        assertTrue(prevPointsToAreaRatio >= curPointsToAreaRatio);
    }

    private double getPointsToAreaRatioDesc(Piece piece) {
        final int pieceArea = piece.getHeight() * piece.getWidth();
        return ((double) piece.getPoints()) / ((double) pieceArea);
    }

    @Test
    @Order(8)
    @DisplayName("Sort Pieces POINTS_TO_LONGEST_SIDE_RATIO_DESC Strategy")
    void cutterSortPointsToLongestSideRationDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException, LayoutIdDoNotMatchPreparedLayoutsIndexException {
        this.setPieceSortStrategy(PieceSortStrategy.POINTS_TO_LONGEST_SIDE_RATIO_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousPointsToLongestSideRatioIsLargerThanOrEqualToCurrent(pieces[i - 1], pieces[i]);
        }
    }

    void assertPreviousPointsToLongestSideRatioIsLargerThanOrEqualToCurrent(Piece prevPiece, Piece curPiece) {
        double prevPointsToLongestSideRatio = getPointsToLongestSideRatioDesc(prevPiece);
        double curPointsToLongestSideRatio = getPointsToLongestSideRatioDesc(curPiece);
        org.hamcrest.MatcherAssert.assertThat(
                prevPointsToLongestSideRatio,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curPointsToLongestSideRatio)
        );
        assertTrue(prevPointsToLongestSideRatio >= curPointsToLongestSideRatio);
    }

    private double getPointsToLongestSideRatioDesc(Piece piece) {
        final int pieceLongestSide = Math.max(piece.getHeight(), piece.getWidth());
        return ((double) piece.getPoints()) / ((double) pieceLongestSide);
    }

    @Test
    @Order(9)
    @DisplayName("Developing test")
    void cutter() throws PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, LayoutFactoryAlreadyInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, SheetSizeException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NullSolutionException, NotAllCornersFoundException, LayoutIdDoNotMatchPreparedLayoutsIndexException, SolutionLayoutVariationLimitExceededException {
//        this.setPieceSortStrategy(Piece.DEFAULT_PIECE_SORT_STRATEGY);
//        Cutter cutter = new Cutter(ARGS);
//        String output = cutter.cut();
//        System.out.println("Solutions ----------------------------------------------------------------");
//        for (Solution solution : cutter.getSolutions()) {
//            System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/");
//            System.out.println(solution.toString(0));
//            System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\");
//        }
//        System.out.println("output: \n\r" + output);
//        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, Piece.getPieceSortStrategy());
//        System.out.println(String.format("cutter.getBestSolution(): %s", cutter.getBestSolution().toString()));
//        if (Piece.getPieceSortStrategy() == PieceSortStrategy.POINTS_TO_LONGEST_SIDE_RATIO_DESC) {
//            org.hamcrest.MatcherAssert.assertThat(
//                    cutter.getBestSolution().getPointSum(),
//                    org.hamcrest.Matchers.greaterThan(0)
//            );
//            org.hamcrest.MatcherAssert.assertThat(
//                    cutter.getBestSolution().getPointSum(),
//                    org.hamcrest.Matchers.greaterThanOrEqualTo(7)
//            );
//            assertEquals(11, cutter.getBestSolution().getPointSum());
//            assertEquals(5, MainTest.getAmountOfPieceVariations(cutter.getBestSolution()));
//        } else {
//            assertEquals(-1, MainTest.getAmountOfPieceVariations(cutter.getBestSolution()));
//            assertEquals(0, MainTest.getAmountOfPieceVariations(cutter.getBestSolution()));
//            assertEquals(-1, cutter.getBestSolution().getPointSum());
//            assertEquals(0, cutter.getBestSolution().getPointSum());
//        }
        assertFalse(true);
    }

    @Test
    @Order(10)
    @DisplayName("Best solution == no cutting")
    void cutterBestSolutionForNoCutting() throws PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, LayoutFactoryAlreadyInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, SheetSizeException, BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NullSolutionException, NotAllCornersFoundException, LayoutIdDoNotMatchPreparedLayoutsIndexException, SolutionLayoutVariationLimitExceededException {
//        this.setPieceSortStrategy(Piece.DEFAULT_PIECE_SORT_STRATEGY);
//        String[] noPointsArgs = {
//                "100", "3",
//                "10",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//                "100", "2", "1",
//        };
//        Cutter cutter = new Cutter(noPointsArgs);
//        String output = cutter.cut();
//        System.out.println("Solutions ----------------------------------------------------------------");
//        for (Solution solution : cutter.getSolutions()) {
//            System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/");
//            System.out.println(solution.toString(0));
//            System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\");
//        }
//        System.out.println("output: \n\r" + output);
//        assertEquals(Piece.DEFAULT_PIECE_SORT_STRATEGY, Piece.getPieceSortStrategy());
//        assertEquals(0, MainTest.getAmountOfPieceVariations(cutter.getBestSolution()));
//        assertEquals(0, cutter.getBestSolution().getPointSum());
        assertFalse(true);
    }

}
