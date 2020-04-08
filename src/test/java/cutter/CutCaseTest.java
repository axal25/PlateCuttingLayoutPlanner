package cutter;

import coords.exceptions.BadCoordinateValueException;
import coords.Coordinate;
import cutter.exceptions.CutCaseNullArgumentException;
import org.junit.jupiter.api.*;
import sheet.*;
import sheet.exceptions.*;

import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CutCaseTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory {
    public static final int[] LAYOUT_VARIATION_WIDTHS = {15, 15, 15, 15, 15};
    public static final int[] LAYOUT_VARIATION_HEIGHTS = {10, 10, 10, 10, 10};

    public static final int[] PIECE_VARIATION_WIDTHS = {15, 15, 7, 7, 7};
    public static final int[] PIECE_VARIATION_HEIGHTS = {10, 1, 5, 5, 5};
    public static final int[] PIECE_VARIATION_POINTS = {0, 0, 0, 0, 0};
    public static final int[] PIECE_VARIATION_XS = {0, 0, 0, 8, 7};
    public static final int[] PIECE_VARIATION_YS = {0, 0, 0, 2, 2};

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
    @DisplayName("getCutCase - exception")
    void assertGetCutCase() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATION_WIDTHS[0], LAYOUT_VARIATION_HEIGHTS[0]);
        Assertions.assertThrows(CutCaseNullArgumentException.class, () -> CutCase.getCutCase(null, null));
        LayoutVariation layoutVariation = getLayoutVariation(0);
        final FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        Assertions.assertThrows(CutCaseNullArgumentException.class, () -> CutCase.getCutCase(freePieceVariation, null));
    }

    public LayoutVariation getLayoutVariation(int index) throws LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, SheetSizeException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, BadCoordinateValueException, PieceVariationsNotInitiatedException, CloneNotSupportedException, CutCaseNullArgumentException {
        try {
            resetLayoutFactory();
            StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATION_WIDTHS[index], LAYOUT_VARIATION_HEIGHTS[index]);
        } catch (LayoutFactoryAlreadyInitiatedException | SheetSizeException e) {}
        return new LayoutVariation();
    }

    public static Piece getPiece(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException {
        return StaticPieceFactory.getPieceFactory().getSheetPiece(
                PIECE_VARIATION_WIDTHS[index],
                PIECE_VARIATION_HEIGHTS[index],
                PIECE_VARIATION_POINTS[index]
        );
    }

    public static PieceVariation getPieceVariation(int index) throws SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, CloneNotSupportedException, BadCoordinateValueException {
        PieceVariation pieceVariation = new PieceVariation( getPiece(index) );
        Coordinate northWesternCoord = new Coordinate(PIECE_VARIATION_XS[index], PIECE_VARIATION_YS[index]);
        pieceVariation.setNorthWesternCoord(northWesternCoord);
        return pieceVariation;
    }

    @Test
    @Order(2)
    @DisplayName("getCutCase - FOUR_OVERLAPPING_CORNERS")
    void getCutCase_FOUR_OVERLAPPING_CORNERS() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        assertGetCutCase(0, CutCase.FOUR_OVERLAPPING_CORNERS);
    }

    CutCase getCutCase(int index) throws SheetSizeException, BadCoordinateValueException, NegativePiecePointsException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATION_WIDTHS[index], LAYOUT_VARIATION_HEIGHTS[index]);
        LayoutVariation layoutVariation = getLayoutVariation(index);
        final FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        final PieceVariation pieceVariation = getPieceVariation(index);
        return CutCase.getCutCase(freePieceVariation, pieceVariation);
    }

    void assertGetCutCase(int index, CutCase expectedCutCase) throws SheetSizeException, BadCoordinateValueException, NegativePiecePointsException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        assertEquals(expectedCutCase, getCutCase(index));
    }

    @Test
    @Order(3)
    @DisplayName("getCutCase - TWO_OVERLAPPING_CORNERS")
    void getCutCase_TWO_OVERLAPPING_CORNERS() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        assertGetCutCase(1, CutCase.TWO_OVERLAPPING_CORNERS);
    }

    @Test
    @Order(4)
    @DisplayName("getCutCase - ONE_OVERLAPPING_CORNER")
    void getCutCase_ONE_OVERLAPPING_CORNER() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        assertGetCutCase(2, CutCase.ONE_OVERLAPPING_CORNER);
    }

    @Test
    @Order(5)
    @DisplayName("getCutCase - TWO_CORNERS_ON_ONE_SIDE")
    void getCutCase_TWO_CORNERS_ON_ONE_SIDE() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        assertGetCutCase(3, CutCase.TWO_CORNERS_ON_ONE_SIDE);
    }

    @Test
    @Order(6)
    @DisplayName("getCutCase - CORNERS_INSIDE")
    void getCutCase_CORNERS_INSIDE() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        assertGetCutCase(4, CutCase.CORNERS_INSIDE);
    }

    @Test
    @Order(7)
    @DisplayName("getCutUpFreePieceVariation - FOUR_OVERLAPPING_CORNERS")
    void getCutUpFreePieceVariation_FOUR_OVERLAPPING_CORNERS() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        TreeSet<FreePieceVariation> returnedCutUpFreePieceVariations = getCutUpFreePieceVariation(0);
        assertNotNull(returnedCutUpFreePieceVariations);
        assertTrue(returnedCutUpFreePieceVariations.isEmpty());
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        cutUpFreePieceVariations.addAll(returnedCutUpFreePieceVariations);
    }

    TreeSet<FreePieceVariation> getCutUpFreePieceVariation(int index) throws SheetSizeException, BadCoordinateValueException, NegativePiecePointsException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, PieceCanNotFitIntoLayoutException, CutCaseNullArgumentException, LayoutFactoryNotInitiatedException, LayoutFactoryAlreadyInitiatedException {
        StaticLayoutFactory.initLayoutFactor(LAYOUT_VARIATION_WIDTHS[index], LAYOUT_VARIATION_HEIGHTS[index]);
        LayoutVariation layoutVariation = getLayoutVariation(index);
        final FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        final PieceVariation pieceVariation = getPieceVariation(index);
        CutCase cutCase = CutCase.getCutCase(freePieceVariation, pieceVariation);
        return cutCase.getCutUpFreePieceVariation(freePieceVariation, pieceVariation);
    }

    @Test
    @Order(8)
    @DisplayName("getCutUpFreePieceVariation - TWO_OVERLAPPING_CORNERS")
    void getCutUpFreePieceVariation_TWO_OVERLAPPING_CORNERS() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        TreeSet<FreePieceVariation> returnedCutUpFreePieceVariations = getCutUpFreePieceVariation(1);
        assertEquals(1, returnedCutUpFreePieceVariations.size());
        assertEquals(
                "FreePieceVariation{piece=FreePiece{id=0, width=15, height=9, points=-135}, orientation=H, northEasternCoord=Coordinate{x=0, y=1}}",
                returnedCutUpFreePieceVariations.first().toString()
        );
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        cutUpFreePieceVariations.addAll(returnedCutUpFreePieceVariations);
    }

    @Test
    @Order(9)
    @DisplayName("getCutUpFreePieceVariation - ONE_OVERLAPPING_CORNER")
    void getCutUpFreePieceVariation_ONE_OVERLAPPING_CORNER() throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException, LayoutFactoryAlreadyInitiatedException {
        TreeSet<FreePieceVariation> returnedCutUpFreePieceVariations = getCutUpFreePieceVariation(2);
        assertEquals(2, returnedCutUpFreePieceVariations.size());
        Iterator iterator = returnedCutUpFreePieceVariations.iterator();
        int i = 0;
        final String[] toStrings = {
                "FreePieceVariation{piece=FreePiece{id=1, width=15, height=5, points=-75}, orientation=H, northEasternCoord=Coordinate{x=0, y=5}}",
                "FreePieceVariation{piece=FreePiece{id=0, width=8, height=10, points=-80}, orientation=V, northEasternCoord=Coordinate{x=7, y=0}}"
        };
        while(iterator.hasNext()) {
            assertEquals(toStrings[i], iterator.next().toString());
            i++;
        }
        TreeSet<FreePieceVariation> cutUpFreePieceVariations = new TreeSet<>();
        cutUpFreePieceVariations.addAll(returnedCutUpFreePieceVariations);
    }
}
