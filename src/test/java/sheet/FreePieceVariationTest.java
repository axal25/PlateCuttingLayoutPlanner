package sheet;

import coords.Coordinate;
import coords.exceptions.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import sheet.cutcase.free.piece.CornersOnSidesFreePieceCutCase;
import sheet.cutcase.free.piece.FreePieceCutCase;
import sheet.cutcase.free.piece.exceptions.BadAmountOfCoordinatesFoundException;
import sheet.cutcase.free.piece.exceptions.CornerNotOnSideException;
import sheet.cutcase.free.piece.exceptions.CornersOnSidesShareNoCoordinateException;
import sheet.cutcase.free.piece.exceptions.CutCaseNullArgumentException;
import sheet.cutcase.piece.PieceCutCase;
import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FreePieceVariationTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, Piece.InterfaceTestingPieceSortStrategy {
    //   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18
    public static final int[] LAYOUT_VARIATION_WIDTHS = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
    public static final int[] LAYOUT_VARIATION_HEIGHTS = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

    public static final int[] PIECE_VARIATION_WIDTHS = {7, 7, 7, 15, 15, 5, 5, 5, 5, 4, 3, 3, 4, 4, 5, 3, 8, 3, 3,};
    public static final int[] PIECE_VARIATION_HEIGHTS = {5, 5, 5, 2, 5, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 3, 3, 3, 3,};
    public static final int[] PIECE_VARIATION_POINTS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
    public static final int[] PIECE_VARIATION_XS = {1, 8, 9, 0, 0, 0, 10, 5, 5, 5, 6, 6, 5, 6, 5, 6, 4, 7, 8,};
    public static final int[] PIECE_VARIATION_YS = {2, 2, 2, 0, 5, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 2, 1, 1, 1,};

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
    @DisplayName("Constructors")
    void constructors() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        for (int i = 0; i < LAYOUT_VARIATION_WIDTHS.length; i++) {
            constructor(i);
        }
    }

    void constructor(int index) throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        LayoutVariation layoutVariation = getLayoutVariation(index);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals(layoutVariation.getLayout().getWidth(), freePieceVariation.getPiece().getWidth());
        assertEquals(layoutVariation.getLayout().getHeight(), freePieceVariation.getPiece().getHeight());
        assertEquals(0, freePieceVariation.getNorthWesternCoord().getX());
        assertEquals(0, freePieceVariation.getNorthWesternCoord().getY());
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(pieceVariation.getPiece().getWidth(), PIECE_VARIATION_WIDTHS[index]);
        assertEquals(pieceVariation.getPiece().getHeight(), PIECE_VARIATION_HEIGHTS[index]);
        assertEquals(pieceVariation.getPiece().getPoints(), PIECE_VARIATION_POINTS[index]);
        assertEquals(pieceVariation.getNorthWesternCoord().getX(), PIECE_VARIATION_XS[index]);
        assertEquals(pieceVariation.getNorthWesternCoord().getY(), PIECE_VARIATION_YS[index]);
    }

    public static LayoutVariation getLayoutVariation(int index) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, PieceVariationsNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        try {
            StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATION_WIDTHS[index], LAYOUT_VARIATION_HEIGHTS[index]);
        } catch (LayoutFactoryAlreadyInitiatedException | SheetSizeException e) {}
        return new LayoutVariation();
    }

    public static Piece getPiece(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException {
        return StaticPieceFactory.getPieceFactory().getPiece(
                PIECE_VARIATION_WIDTHS[index],
                PIECE_VARIATION_HEIGHTS[index],
                PIECE_VARIATION_POINTS[index]
        );
    }

    public static PieceVariation getPieceVariation(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, CloneNotSupportedException, BadCoordinateValueException {
        PieceVariation pieceVariation = new PieceVariation(getPiece(index));
        Coordinate northWesternCoord = new Coordinate(PIECE_VARIATION_XS[index], PIECE_VARIATION_YS[index]);
        pieceVariation.setNorthWesternCoord(northWesternCoord);
        return pieceVariation;
    }

    @Test
    @Order(2)
    @DisplayName("isPieceVariationInsideThisArea")
    void isPieceVariationInsideThisArea() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        isPieceVariationInsideOther(0, true, false);
        isPieceVariationInsideOther(1, true, false);
        isPieceVariationInsideOther(2, false, false);
    }

    void isPieceVariationInsideOther(int index, boolean expectedIsPieceVariationInsideOther, boolean expectedReverseIsPieceVariationInsideOther) throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        LayoutVariation layoutVariation = getLayoutVariation(index);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(expectedIsPieceVariationInsideOther, pieceVariation.isInsideOther(freePieceVariation));
        assertEquals(expectedReverseIsPieceVariationInsideOther, freePieceVariation.isInsideOther(pieceVariation));
    }

    @Test
    @Order(3)
    @DisplayName("FreePieceCutCase - getCutUpFreePieceVariation - FOUR_OVERLAPPING_CORNERS")
    void FreePieceCutCase_getCutUpFreePieceVariation_FOUR_OVERLAPPING_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(7);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(4, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.FOUR_OVERLAPPING_CORNERS, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(0, cutFreePieceVariations.size());
    }

    private FreePieceVariation setupFreePieceVariationForFurtherTesting() throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, PieceVariationsNotInitiatedException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        LayoutVariation layoutVariation = getLayoutVariation(3);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = getPieceVariation(3);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=10, points=-150}, orientation=H, northEasternCoord=Coordinate{x=0, y=0}}", freePieceVariation.toString());

        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=8, points=-120}, orientation=H, northEasternCoord=Coordinate{x=0, y=2}}", freePieceVariation.toString());

        pieceVariation = getPieceVariation(4);
        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=15, height=3, points=-45}, orientation=H, northEasternCoord=Coordinate{x=0, y=2}}", freePieceVariation.toString());

        pieceVariation = getPieceVariation(5);
        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=10, height=3, points=-30}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());

        pieceVariation = getPieceVariation(6);
        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=3, points=-15}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());

        return freePieceVariation;
    }

    @Test
    @Order(4)
    @DisplayName("FreePieceCutCase - getCutUpFreePieceVariation - TWO_OVERLAPPING_CORNERS")
    void FreePieceCutCase_getCutUpFreePieceVariation_TWO_OVERLAPPING_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(8);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(2, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.TWO_OVERLAPPING_CORNERS, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(1, cutFreePieceVariations.size());
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
    }

    @Test
    @Order(5)
    @DisplayName("FreePieceCutCase - getCutUpFreePieceVariation - ONE_OVERLAPPING_CORNER")
    void FreePieceCutCase_getCutUpFreePieceVariation_ONE_OVERLAPPING_CORNER() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(9);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(1, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.ONE_OVERLAPPING_CORNER, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(2, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(6)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - TWO_CORNERS_ON_ONE_SIDE (on same X - 1)")
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_X_1() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(10);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.TWO_CORNERS_ON_ONE_SIDE, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(3, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(7)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - TWO_CORNERS_ON_ONE_SIDE (on same X - 2)")
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_X_2() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(11);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.TWO_CORNERS_ON_ONE_SIDE, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(3, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(8)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - TWO_CORNERS_ON_ONE_SIDE (on same Y - 1)")
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_Y_1() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(12);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.TWO_CORNERS_ON_ONE_SIDE, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(3, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(9)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - TWO_CORNERS_ON_ONE_SIDE (on same Y - 2)")
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_Y_2() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(13);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.TWO_CORNERS_ON_ONE_SIDE, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(3, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(10)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - FOUR_CORNERS_ON_TWO_SIDES (on same X)")
    void CornersOnSides_getCutUpFreePieceVariation_FOUR_CORNERS_ON_TWO_SIDES_X() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(14);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.FOUR_CORNERS_ON_TWO_SIDES, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        for (FreePieceVariation cutFreePieceVariation : cutFreePieceVariations)
            System.out.println("cutFreePieceVariation: " + cutFreePieceVariation);
        assertEquals(2, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertFalse(iterator.hasNext());
    }

    @Test
    @Order(11)
    @DisplayName("CornersOnSides - getCutUpFreePieceVariation - FOUR_CORNERS_ON_TWO_SIDES (on same Y)")
    void CornersOnSides_getCutUpFreePieceVariation_FOUR_CORNERS_ON_TWO_SIDES_Y() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(15);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(0, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.CORNERS_ON_SIDES, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        assertEquals(CornersOnSidesFreePieceCutCase.FOUR_CORNERS_ON_TWO_SIDES, CornersOnSidesFreePieceCutCase.getNewCornersOnSidesFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        for (FreePieceVariation cutFreePieceVariation : cutFreePieceVariations)
            System.out.println("cutFreePieceVariation: " + cutFreePieceVariation);
        assertEquals(2, cutFreePieceVariations.size());
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
        assertFalse(iterator.hasNext());
    }

    @Test
    @Order(11)
    @DisplayName("PieceCutCase - getFragmentInsideOther - PV_AROUND_FPV_2_CORNERS")
    void PieceCutCase_getFragmentInsideOther_PV_AROUND_FPV_2_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();

        PieceVariation pieceVariation = getPieceVariation(16);
        assertEquals(0, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(2, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(PieceCutCase.PV_AROUND_FPV_2_CORNERS, PieceCutCase.getNewPieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
    }

    @Test
    @Order(12)
    @DisplayName("PieceCutCase - getFragmentInsideOther - FPV_AROUND_PV_2_CORNERS")
    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        int index = 17;
        PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_splitUpOperations(index);
        PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_combinedOperations(index);
    }

    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_splitUpOperations(int index) throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, BadCoordinateValueException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(2, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(1, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        PieceCutCase pieceCutCase = PieceCutCase.getNewPieceCutCase(freePieceVariation, pieceVariation);
        assertEquals(PieceCutCase.FPV_AROUND_PV_2_CORNERS, pieceCutCase);
        System.out.println("pieceVariation: " + pieceVariation);
        assertEquals("PieceVariation{piece=Piece{id=4, width=3, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=7, y=1}}", pieceVariation.toString());
        pieceVariation = pieceCutCase.getFragmentInsideOther(pieceVariation, freePieceVariation);
        System.out.println("pieceVariation: " + pieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=3, height=2, points=-6}, orientation=H, northEasternCoord=Coordinate{x=7, y=2}}", pieceVariation.toString());
        FreePieceCutCase freePieceCutCase = FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation);
        assertEquals(FreePieceCutCase.ONE_OVERLAPPING_CORNER, freePieceCutCase);
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceCutCase.getCutUpFreePieceVariation(freePieceVariation, pieceVariation);
        assert2CutFreePieceVariations(cutFreePieceVariations);
    }

    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_combinedOperations(int index) throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, BadCoordinateValueException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(2, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(1, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assert2CutFreePieceVariations(cutFreePieceVariations);
    }

    void assert2CutFreePieceVariations(TreeSet<FreePieceVariation> cutFreePieceVariations) {
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        FreePieceVariation freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=2, height=3, points=-6}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
    }

    @Test
    @Order(13)
    @DisplayName("PieceCutCase - getFragmentInsideOther - ONE_CORNER_INSIDE_EACH")
    void PieceCutCase_getFragmentInsideOther_ONE_CORNER_INSIDE_EACH() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(18);
        assertEquals(1, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(1, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(PieceCutCase.ONE_CORNER_INSIDE_EACH, PieceCutCase.getNewPieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=3, height=3, points=-9}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
    }
}
