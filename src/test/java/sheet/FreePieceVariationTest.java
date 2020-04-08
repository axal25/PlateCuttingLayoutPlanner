package sheet;

import coords.exceptions.BadCoordinateValueException;
import coords.Coordinate;
import cutter.CutCase;
import cutter.exceptions.CutCaseNullArgumentException;
import org.junit.jupiter.api.*;
import sheet.exceptions.*;

import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FreePieceVariationTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory {
    public static final int[] LAYOUT_VARIATION_WIDTHS =     {   15, 15, 15, 15, 15, 15, 15};
    public static final int[] LAYOUT_VARIATION_HEIGHTS =    {   10, 10, 10, 10, 10, 10, 10};

    public static final int[] PIECE_VARIATION_WIDTHS =      {   7,  7,  7,  15, 5,  5,  7};
    public static final int[] PIECE_VARIATION_HEIGHTS =     {   5,  5,  5,  7,  3,  3,  4}; //5
    public static final int[] PIECE_VARIATION_POINTS =      {   0,  0,  0,  0,  0,  0,  0};
    public static final int[] PIECE_VARIATION_XS =          {   1,  8,  9,  0,  0,  10, 4};
    public static final int[] PIECE_VARIATION_YS =          {   2,  2,  2,  0,  7,  7,  5};

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
    @DisplayName("Constructors")
    void constructors() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException {
        for (int i = 0; i < LAYOUT_VARIATION_WIDTHS.length; i++) {
            constructor(i);
        }
    }

    void constructor(int index) throws CloneNotSupportedException, BadCoordinateValueException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException, PieceVariationsNotInitiatedException, CutCaseNullArgumentException {
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
    @DisplayName("isPieceVariationInsideThisArea")
    void isPieceVariationInsideThisArea() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException {
        isPieceVariationInsideOther(0, true, false);
        isPieceVariationInsideOther(1, true, false);
        isPieceVariationInsideOther(2, false, false);
    }

    void isPieceVariationInsideOther(int index, boolean expectedIsPieceVariationInsideOther, boolean expectedReverseIsPieceVariationInsideOther) throws BadCoordinateValueException, CloneNotSupportedException, PieceVariationsNotInitiatedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException {
        LayoutVariation layoutVariation = getLayoutVariation(index);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = getPieceVariation(index);
        assertEquals(expectedIsPieceVariationInsideOther, pieceVariation.isInsideOther(freePieceVariation));
        assertEquals(expectedReverseIsPieceVariationInsideOther, freePieceVariation.isInsideOther(pieceVariation));
    }

    @Test
    @Order(3)
    @DisplayName("getCutUpFreePieceVariation")
    void getCutUpFreePieceVariation() throws CloneNotSupportedException, BadCoordinateValueException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, PieceVariationsNotInitiatedException, LayoutFactoryNotInitiatedException, SheetSizeException, CutCaseNullArgumentException {
        LayoutVariation layoutVariation = getLayoutVariation(3);
        FreePieceVariation freePieceVariation = layoutVariation.getFreePieceVariations().first();
        PieceVariation pieceVariation = getPieceVariation(3);
        CutCase cutCase = CutCase.getCutCase(freePieceVariation, pieceVariation);

        TreeSet<FreePieceVariation> cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);

        pieceVariation = getPieceVariation(4);
        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);

        pieceVariation = getPieceVariation(5);
        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);
        freePieceVariation = cutFreePieceVariations.first();
        System.out.println("freePieceVariation: " + freePieceVariation);

        pieceVariation = getPieceVariation(6);
//        cutFreePieceVariations = freePieceVariation.getCutUpFreePieceVariation(pieceVariation);

//        Iterator iterator = cutFreePieceVariations.iterator();
//        int i = 0;
//        while(iterator.hasNext()) {
//            System.out.println(i + ". freePieceVariation: " + iterator.next());
//            i++;
//        }
//        assertTrue(false);
    }
}
