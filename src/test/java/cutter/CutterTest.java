package cutter;

import coords.exceptions.BadCoordinateValueException;
import cutter.exceptions.CutCaseNullArgumentException;
import main.MainTest;
import org.junit.jupiter.api.*;
import parser.exceptions.BadAmountOfInputArgsException;
import sheet.Piece;
import sheet.StaticLayoutFactory;
import sheet.StaticPieceFactory;
import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CutterTest implements Piece.InterfaceTestingPieceSortStrategy, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, StaticPieceFactory.InterfaceTestingStaticPieceFactory {
    public static String[] ARGS = MainTest.ARGS;

    @BeforeEach
    void setUp() {
        this.resetPieceFactory();
        this.resetLayoutFactory();
    }

    @AfterEach
    void tearDown() {
        this.resetPieceFactory();
        this.resetLayoutFactory();
    }

    @Test
    @Order(1)
    @DisplayName("Cutter Constructor")
    void cutterConstructor() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        Cutter cutter = new Cutter(ARGS);
    }

    @Test
    @Order(2)
    @DisplayName("Sort Pieces Default (LONGEST SIDE DESC) Strategy")
    void cutterSortDefault() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        assertEquals(PieceSortStrategy.LONGEST_SIDE_DESC, this.getPieceSortStrategy());
        this.setPieceSortStrategy(PieceSortStrategy.LONGEST_SIDE_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousLongestSideIsLargerThanOrEqualToCurrent(pieces[i-1], pieces[i]);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Sort Pieces AREA DESC Strategy")
    void cutterSortAreaDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.AREA_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousAreaIsLargerThanOrEqualToCurrent(pieces[i-1], pieces[i]);
        }
    }

    void assertPreviousAreaIsLargerThanOrEqualToCurrent(Piece prev, Piece cur) {
        final int prevArea = prev.getHeight()*prev.getWidth();
        final int curArea = cur.getHeight()*cur.getWidth();
        org.hamcrest.MatcherAssert.assertThat(
                prevArea,
                org.hamcrest.Matchers.greaterThanOrEqualTo(curArea)
        );
        assertTrue(prevArea >= curArea);
    }

    @Test
    @Order(4)
    @DisplayName("Sort Pieces LONGEST SIDE DESC Strategy")
    void cutterSortLongestSideDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.LONGEST_SIDE_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousLongestSideIsLargerThanOrEqualToCurrent(pieces[i-1], pieces[i]);
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
    void cutterSortHeightDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.HEIGHT_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousHeightOrWidthIsLongerThanOrEqualToCurrent(pieces[i-1].getHeight(), pieces[i].getHeight());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Sort Pieces WIDTH DESC Strategy")
    void cutterSortWidthDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.WIDTH_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousHeightOrWidthIsLongerThanOrEqualToCurrent(pieces[i-1].getWidth(), pieces[i].getWidth());
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
    void cutterSortPointsToAreaRatioDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.POINTS_TO_AREA_RATIO_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousPointsToAreaRatioIsLargerThanOrEqualToCurrent(pieces[i-1], pieces[i]);
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
        return ((double) piece.getPoints())/((double) pieceArea);
    }

    @Test
    @Order(8)
    @DisplayName("Sort Pieces POINTS_TO_LONGEST_SIDE_RATIO_DESC Strategy")
    void cutterSortPointsToLongestSideRationDesc() throws CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryAlreadyInitiatedException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, SheetSizeException, LayoutFactoryNotInitiatedException, PieceCanNotFitIntoLayoutException {
        this.setPieceSortStrategy(PieceSortStrategy.POINTS_TO_LONGEST_SIDE_RATIO_DESC);
        Cutter cutter = new Cutter(ARGS);
        Piece[] pieces = cutter.getPieces();
        for (int i = 1; i < pieces.length; i++) {
            assertPreviousPointsToLongestSideRatioIsLargerThanOrEqualToCurrent(pieces[i-1], pieces[i]);
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
        return ((double) piece.getPoints())/((double) pieceLongestSide);
    }

    @Test
    @Order(9)
    @DisplayName("Developing test")
    void cutter() throws PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, NegativePiecePointsException, BadAmountOfInputArgsException, LayoutFactoryAlreadyInitiatedException, CalculatedAndInputAmountOfPiecesNotMatchException, LayoutFactoryNotInitiatedException, SheetSizeException, CandidatePieceVariationCouldNotBeFitIntoEmptyLayoutVariation, BadCoordinateValueException, CandidatePieceVariationPositionAlreadySetException, CloneNotSupportedException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
        Cutter cutter = new Cutter(ARGS);
        cutter.cut();
        for (Solution solution : cutter.getSolutions()) {
            System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/");
            System.out.println(solution.toString(0));
            System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\");
        }
    }
}
