package sheet;

import coords.exceptions.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LayoutVariationTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, Piece.InterfaceTestingPieceSortStrategy {
    public static final int[] PIECE_VARIATION_WIDTHS = {6, 5, 3, 4, 3, 5, 2, 4, 2,};
    public static final int[] PIECE_VARIATION_HEIGHTS = {2, 3, 5, 3, 4, 2, 5, 2, 4,};
    public static final int[] PIECE_VARIATION_POINTS = {0, 0, 0, 0, 0, 0, 0, 0, 0,};

    @BeforeEach
    void setUp() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
        this.setPieceSortStrategy(PieceSortStrategy.LONGEST_SIDE_DESC);
    }

    @AfterEach
    void tearDown() {
        this.resetLayoutFactory();
        this.resetPieceFactory();
    }

    @Test
    @Order(1)
    @DisplayName("findSameSizePieceVariation")
    void findSameSizePieceVariation() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(0));
        pieceVariation = layoutVariation.findSameSizePieceVariation(pieceVariation);
        assertNull(pieceVariation);
        pieceVariation = new PieceVariation(getPiece(1));
        pieceVariation = layoutVariation.findSameSizePieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=5, width=5, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    public static Piece getPiece(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException {
        return StaticPieceFactory.getPieceFactory().getPiece(
                PIECE_VARIATION_WIDTHS[index],
                PIECE_VARIATION_HEIGHTS[index],
                PIECE_VARIATION_POINTS[index]
        );
    }

    private static LayoutVariation setupLayoutForFurtherTesting() throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, PieceVariationsNotInitiatedException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = FreePieceVariationTest.getLayoutVariation(3);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = FreePieceVariationTest.getPieceVariation(3);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=10, points=-150}, orientation=H, northEasternCoord=Coordinate{x=0, y=0}}", freePieceVariation.toString());

        layoutVariation.add(pieceVariation);
        freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=8, points=-120}, orientation=H, northEasternCoord=Coordinate{x=0, y=2}}", freePieceVariation.toString());

        pieceVariation = FreePieceVariationTest.getPieceVariation(4);
        layoutVariation.add(pieceVariation);
        freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=3, points=-45}, orientation=H, northEasternCoord=Coordinate{x=0, y=2}}", freePieceVariation.toString());

        pieceVariation = FreePieceVariationTest.getPieceVariation(5);
        layoutVariation.add(pieceVariation);
        freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=10, height=3, points=-30}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());

        pieceVariation = FreePieceVariationTest.getPieceVariation(6);
        layoutVariation.add(pieceVariation);
        freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=3, points=-15}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());

        return layoutVariation;
    }

    @Test
    @Order(1)
    @DisplayName("findSameSizePieceVariation - rotated")
    void findSameSizePieceVariation_rotated() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(2));
        pieceVariation = layoutVariation.findSameSizePieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=5, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(2)
    @DisplayName("findSameWidthOrHeightPieceVariation - same height")
    void findSameWidthOrHeightPieceVariation_height() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(3));
        pieceVariation = layoutVariation.findSameWidthOrHeightPieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=4, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(3)
    @DisplayName("findSameWidthOrHeightPieceVariation - same height - rotated")
    void findSameWidthOrHeightPieceVariation_height_rotated() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(4));
        pieceVariation = layoutVariation.findSameWidthOrHeightPieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=4, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(4)
    @DisplayName("findSameWidthOrHeightPieceVariation - same width")
    void findSameWidthOrHeightPieceVariation_width() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(5));
        pieceVariation = layoutVariation.findSameWidthOrHeightPieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=5, height=2, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(5)
    @DisplayName("findSameWidthOrHeightPieceVariation - same width - rotated")
    void findSameWidthOrHeightPieceVariation_width_rotated() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(6));
        pieceVariation = layoutVariation.findSameWidthOrHeightPieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=5, height=2, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(6)
    @DisplayName("findSmallerSizePieceVariation")
    void findSmallerSizePieceVariation() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(7));
        pieceVariation = layoutVariation.findSmallerSizePieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=4, height=2, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }

    @Test
    @Order(7)
    @DisplayName("findSmallerSizePieceVariation - rotated")
    void findSmallerSizePieceVariation_rotated() throws SheetSizeException, CornersOnSidesShareNoCoordinateException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, CornerNotOnSideException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = setupLayoutForFurtherTesting();
        PieceVariation pieceVariation = new PieceVariation(getPiece(8));
        pieceVariation = layoutVariation.findSmallerSizePieceVariation(pieceVariation);
        assertNotNull(pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=4, height=2, points=0}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", pieceVariation.toString());
    }
}
