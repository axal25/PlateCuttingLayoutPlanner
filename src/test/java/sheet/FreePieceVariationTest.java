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
import sheet.cutcase.piece.PieceCutter;
import sheet.cutcase.piece.PieceCutCaseInterface;
import sheet.exceptions.*;
import sheet.sort.strategy.PieceSortStrategy;

import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FreePieceVariationTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory, Piece.InterfaceTestingPieceSortStrategy {

    public static class LayoutVariationTestData {
        public int width, height;
        public LayoutVariationTestData(int width, int height) {
                this.width = width;
                this.height = height;
        }
    }

    public static class PieceVariationTestData {
        public int width, height, points, x, y;
        public PieceVariationTestData(int width, int height, int points, int x, int y) {
            this.width = width;
            this.height = height;
            this.points = points;
            this.x = x;
            this.y = y;
        }
    }

    public static final LayoutVariationTestData[] LAYOUT_VARIATIONS = new LayoutVariationTestData[]{
            new LayoutVariationTestData(15, 10), // 0
            new LayoutVariationTestData(15, 10), // 1
            new LayoutVariationTestData(15, 10), // 2
            new LayoutVariationTestData(15, 10), // 3
            new LayoutVariationTestData(15, 10), // 4
            new LayoutVariationTestData(15, 10), // 5
            new LayoutVariationTestData(15, 10), // 6
            new LayoutVariationTestData(15, 10), // 7
            new LayoutVariationTestData(15, 10), // 8
            new LayoutVariationTestData(15, 10), // 9
            new LayoutVariationTestData(15, 10), // 10
            new LayoutVariationTestData(15, 10), // 11
            new LayoutVariationTestData(15, 10), // 12
            new LayoutVariationTestData(15, 10), // 13
            new LayoutVariationTestData(15, 10), // 14
            new LayoutVariationTestData(15, 10), // 15
            new LayoutVariationTestData(15, 10), // 16
            new LayoutVariationTestData(15, 10), // 17
            new LayoutVariationTestData(15, 10), // 18
            new LayoutVariationTestData(15, 10), // 19
            new LayoutVariationTestData(15, 10), // 20
            new LayoutVariationTestData(15, 10), // 21
            new LayoutVariationTestData(15, 10), // 22
    };

    public static final PieceVariationTestData[] PIECE_VARIATIONS = new PieceVariationTestData[]{
            new PieceVariationTestData(7, 5, 0, 1, 2), // 0
            new PieceVariationTestData(7, 5, 0, 8, 2), // 1
            new PieceVariationTestData(7, 5, 0, 9, 2), // 2
            new PieceVariationTestData(15, 2, 0, 0, 0), // 3
            new PieceVariationTestData(15, 5, 0, 0, 5), // 4
            new PieceVariationTestData(5, 3, 0, 0, 2), // 5
            new PieceVariationTestData(5, 3, 0, 10, 2), // 6
            new PieceVariationTestData(5, 3, 0, 5, 2), // 7
            new PieceVariationTestData(5, 2, 0, 5, 2), // 8
            new PieceVariationTestData(4, 2, 0, 5, 2), // 9
            new PieceVariationTestData(3, 2, 0, 6, 2), // 10
            new PieceVariationTestData(3, 2, 0, 6, 3), // 11
            new PieceVariationTestData(4, 1, 0, 5, 3), // 12
            new PieceVariationTestData(4, 1, 0, 6, 3), // 13
            new PieceVariationTestData(5, 1, 0, 5, 3), // 14
            new PieceVariationTestData(3, 3, 0, 6, 2), // 15
            new PieceVariationTestData(8, 3, 0, 4, 1), // 16
            new PieceVariationTestData(3, 3, 0, 7, 1), // 17
            new PieceVariationTestData(3, 3, 0, 8, 1), // 18
            new PieceVariationTestData(6, 4, 0, 4, 2), // 19
            new PieceVariationTestData(7, 1, 0, 4, 3), // 20
            new PieceVariationTestData(3, 5, 0, 6, 1), // 21
            new PieceVariationTestData(1, 3, 0, 5, 4), // 22
    };

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
    void constructors() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        for (int i = 0; i < LAYOUT_VARIATIONS.length; i++) {
            constructor(i);
        }
    }

    void constructor(int index) throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = getLayoutVariation(index);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        assertEquals(layoutVariation.getLayout().getWidth(), freePieceVariation.getPiece().getWidth());
        assertEquals(layoutVariation.getLayout().getHeight(), freePieceVariation.getPiece().getHeight());
        assertEquals(0, freePieceVariation.getNorthWesternCoord().getX());
        assertEquals(0, freePieceVariation.getNorthWesternCoord().getY());
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(pieceVariation.getPiece().getWidth(), PIECE_VARIATIONS[index].width);
        assertEquals(pieceVariation.getPiece().getHeight(), PIECE_VARIATIONS[index].height);
        assertEquals(pieceVariation.getPiece().getPoints(), PIECE_VARIATIONS[index].points);
        assertEquals(pieceVariation.getNorthWesternCoord().getX(), PIECE_VARIATIONS[index].x);
        assertEquals(pieceVariation.getNorthWesternCoord().getY(), PIECE_VARIATIONS[index].y);
    }

    public static LayoutVariation getLayoutVariation(int index) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, PieceVariationsNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        try {
            StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATIONS[index].width, LAYOUT_VARIATIONS[index].height);
        } catch (LayoutFactoryAlreadyInitiatedException | SheetSizeException e) {}
        return new LayoutVariation();
    }

    public static Piece getPiece(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException {
        return StaticPieceFactory.getPieceFactory().getPiece(
                PIECE_VARIATIONS[index].width,
                PIECE_VARIATIONS[index].height,
                PIECE_VARIATIONS[index].points
        );
    }

    public static PieceVariation getPieceVariation(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, CloneNotSupportedException, BadCoordinateValueException {
        PieceVariation pieceVariation = new PieceVariation(getPiece(index));
        Coordinate northWesternCoord = new Coordinate(PIECE_VARIATIONS[index].x, PIECE_VARIATIONS[index].y);
        pieceVariation.setNorthWesternCoord(northWesternCoord);
        return pieceVariation;
    }

    @Test
    @Order(2)
    @DisplayName("isPieceVariationInsideThisArea")
    void isPieceVariationInsideThisArea() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        isPieceVariationInsideOther(0, true, false);
        isPieceVariationInsideOther(1, true, false);
        isPieceVariationInsideOther(2, false, false);
    }

    void isPieceVariationInsideOther(int index, boolean expectedIsPieceVariationInsideOther, boolean expectedReverseIsPieceVariationInsideOther) throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        LayoutVariation layoutVariation = getLayoutVariation(index);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(expectedIsPieceVariationInsideOther, pieceVariation.isInsideOther(freePieceVariation));
        assertEquals(expectedReverseIsPieceVariationInsideOther, freePieceVariation.isInsideOther(pieceVariation));
    }

    @Test
    @Order(3)
    @DisplayName("FreePieceCutCase - getCutUpFreePieceVariation - FOUR_OVERLAPPING_CORNERS")
    void FreePieceCutCase_getCutUpFreePieceVariation_FOUR_OVERLAPPING_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(7);
        assertEquals(4, pieceVariation.getCornersInsideOf(freePieceVariation).length);
        assertEquals(4, freePieceVariation.getCornersInsideOf(pieceVariation).length);
        assertEquals(FreePieceCutCase.FOUR_OVERLAPPING_CORNERS, FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, pieceVariation));
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        assertEquals(0, cutFreePieceVariations.size());
    }

    private FreePieceVariation setupFreePieceVariationForFurtherTesting() throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, PieceVariationsNotInitiatedException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void FreePieceCutCase_getCutUpFreePieceVariation_TWO_OVERLAPPING_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void FreePieceCutCase_getCutUpFreePieceVariation_ONE_OVERLAPPING_CORNER() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_X_1() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_X_2() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_Y_1() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_TWO_CORNERS_ON_ONE_SIDE_Y_2() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_FOUR_CORNERS_ON_TWO_SIDES_X() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    void CornersOnSides_getCutUpFreePieceVariation_FOUR_CORNERS_ON_TWO_SIDES_Y() throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, BadAmountOfCoordinatesFoundException, CutCaseNullArgumentException, BadCoordinateValueException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    @DisplayName("PieceCutter - getFragmentInsideOther - PV_AROUND_FPV_2_CORNERS")
    void PieceCutCase_getFragmentInsideOther_PV_AROUND_FPV_2_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation uncutPieceVariation = getPieceVariation(16);
        PieceCutter pieceCutter = new PieceCutter(uncutPieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(0, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(2, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ZERO_PV_CORNERS_INSIDE_FPV.PV_AROUND_FPV_2_CORNERS, pieceCutter.getPieceCutCase());
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(cutPieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
    }

    @Test
    @Order(12)
    @DisplayName("PieceCutter - getFragmentInsideOther - FPV_AROUND_PV_2_CORNERS")
    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        int index = 17;
        PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_splitUpOperations(index);
        PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_combinedOperations(index);
    }

    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_splitUpOperations(int index) throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, BadCoordinateValueException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation uncutPieceVariation = getPieceVariation(index);
        PieceCutter pieceCutter = new PieceCutter(uncutPieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(2, pieceCutter.getPvCornersInsideFpv().length);
        assertNull(pieceCutter.getFpvCornersInsidePv());
        assertEquals(1, freePieceVariation.getCornersInsideOf(uncutPieceVariation).length);
        assertEquals(PieceCutCase.FPV_AROUND_PV_2_CORNERS, pieceCutter.getPieceCutCase());
        assertEquals("PieceVariation{piece=Piece{id=4, width=3, height=3, points=0}, orientation=H, northEasternCoord=Coordinate{x=7, y=1}}", uncutPieceVariation.toString());
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=3, height=2, points=-6}, orientation=H, northEasternCoord=Coordinate{x=7, y=2}}", cutPieceVariation.toString());
        FreePieceCutCase freePieceCutCase = FreePieceCutCase.getNewFreePieceCutCase(freePieceVariation, cutPieceVariation);
        assertEquals(FreePieceCutCase.ONE_OVERLAPPING_CORNER, freePieceCutCase);
        System.out.println("freePieceCutCase.getCutUpFreePieceVariation(freePieceVariation, uncutPieceVariation)");
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceCutCase.getCutUpFreePieceVariation(freePieceVariation, cutPieceVariation);
        assert2CutFreePieceVariations(cutFreePieceVariations);
    }

    void PieceCutCase_getFragmentInsideOther_FPV_AROUND_PV_2_CORNERS_combinedOperations(int index) throws SheetSizeException, LayoutFactoryNotInitiatedException, CloneNotSupportedException, NegativePiecePointsException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, BadCoordinateValueException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
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
    @DisplayName("PieceCutter - getFragmentInsideOther - ONE_CORNER_INSIDE_EACH")
    void PieceCutCase_getFragmentInsideOther_ONE_CORNER_INSIDE_EACH() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(18);
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(1, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(1, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ONE_PV_CORNER_INSIDE_FPV.ONE_OR_TWO_FPV_CORNERS_INSIDE_PV, pieceCutter.getPieceCutCase());
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

    @Test
    @Order(14)
    @DisplayName("PieceCutter - getFragmentInsideOther - ONE_CORNER_INSIDE_EACH 2")
    void PieceCutCase_getFragmentInsideOther_ONE_CORNER_INSIDE_EACH_2() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(19);
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(1, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(4, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ALL_FPV_CORNERS_INSIDE_PV, pieceCutter.getPieceCutCase());
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        Iterator iterator = cutFreePieceVariations.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    @Order(15)
    @DisplayName("PieceCutter - getFragmentInsideOther - INTERSECTING_PV_FPV")
    void PieceCutCase_getFragmentInsideOther_INTERSECTING_PV_FPV() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(20);
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(0, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(0, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ZERO_PV_CORNERS_INSIDE_FPV.INTERSECTING_PV_FPV, pieceCutter.getPieceCutCase());
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=5, height=1, points=-5}, orientation=H, northEasternCoord=Coordinate{x=5, y=4}}", freePieceVariation.toString());
    }

    @Test
    @Order(16)
    @DisplayName("PieceCutter - getFragmentInsideOther - INTERSECTING_PV_FPV (2)")
    void PieceCutCase_getFragmentInsideOther_INTERSECTING_PV_FPV_2() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(21);
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(0, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(0, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ZERO_PV_CORNERS_INSIDE_FPV.INTERSECTING_PV_FPV, pieceCutter.getPieceCutCase());
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(cutPieceVariation);
        Iterator iterator = cutFreePieceVariations.iterator();
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
    @Order(17)
    @DisplayName("PieceCutter - getFragmentInsideOther - ONE_OR_TWO_FPV_CORNERS_INSIDE_PV")
    void PieceCutCase_getFragmentInsideOther_ONE_OR_TWO_FPV_CORNERS_INSIDE_PV() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, BadAmountOfCoordinatesFoundException, CornerNotOnSideException, CornersOnSidesShareNoCoordinateException, NotAllCornersFoundException {
        FreePieceVariation freePieceVariation = setupFreePieceVariationForFurtherTesting();
        PieceVariation pieceVariation = getPieceVariation(22);
        PieceCutter pieceCutter = new PieceCutter(pieceVariation, freePieceVariation);
        PieceVariation cutPieceVariation = pieceCutter.getFragmentOfPvInsideFpv();
        assertEquals(1, pieceCutter.getPvCornersInsideFpv().length);
        assertEquals(2, pieceCutter.getFpvCornersInsidePv().length);
        assertEquals(PieceCutCase.ONE_PV_CORNER_INSIDE_FPV.ONE_OR_TWO_FPV_CORNERS_INSIDE_PV, pieceCutter.getPieceCutCase());
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=1, points=-1}, orientation=V, northEasternCoord=Coordinate{x=5, y=4}}" ,cutPieceVariation);
        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(cutPieceVariation);
        Iterator iterator = cutFreePieceVariations.iterator();
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=5, y=2}}", freePieceVariation.toString());
        assertTrue(iterator.hasNext());
        freePieceVariation = (FreePieceVariation) iterator.next();
        System.out.println("freePieceVariation: " + freePieceVariation);
        assertEquals("FreePieceVariation{piece=FreePiece{id=0, width=1, height=3, points=-3}, orientation=V, northEasternCoord=Coordinate{x=9, y=2}}", freePieceVariation.toString());
    }
}
