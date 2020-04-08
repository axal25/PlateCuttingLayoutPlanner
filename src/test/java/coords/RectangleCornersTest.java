package coords;

import coords.exceptions.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import sheet.PieceVariation;
import sheet.StaticLayoutFactory;
import sheet.StaticPieceFactory;
import sheet.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RectangleCornersTest implements StaticPieceFactory.InterfaceTestingStaticPieceFactory, StaticLayoutFactory.InterfaceTestingStaticSheetFactory {

    public final static int[] NW_RECTANGLE_NW_X_COORD = {1, 1, 1,   1,  1,  1,  1,  1,  1,  1};
    public final static int[] NW_RECTANGLE_NW_Y_COORD = {2, 2, 2,   2,  2,  2,  2,  2,  2,  2};
    public final static int[] NW_WIDTH =                {3, 3, 3,   3,  3,  3,  3,  3,  3,  3};
    public final static int[] NW_HEIGHT =               {5, 5, 5,   5,  5,  5,  5,  5,  5,  5};
    public final static int[] X_SPACING =               {0, 1, -1, -1,  1, -3, -4, -2, -2, -3};
    public final static int[] Y_SPACING =               {0, 1, -1,  1, -1, -5, -7, -4, -7, -4};
    public final static int[] SE_WIDTH =                {6, 6, 6,   6,  6,  6,  6,  6,  6,  6};
    public final static int[] SE_HEIGHT =               {9, 9, 9,   9,  9,  9,  9,  9,  9,  9};

    private static Coordinate getNWRectangleCoord(int index) throws BadCoordinateValueException {
        return new Coordinate(NW_RECTANGLE_NW_X_COORD[index], NW_RECTANGLE_NW_Y_COORD[index]);
    }

    private static Coordinate getSERectangleCoord(int index) throws BadCoordinateValueException {
        Coordinate nwRectangleCoord = getNWRectangleCoord(index);
        return new Coordinate(nwRectangleCoord.getX() + NW_WIDTH[index] + X_SPACING[index], nwRectangleCoord.getY() + NW_HEIGHT[index] + Y_SPACING[index]);
    }

    private RectangleCorners getRectangleCorners(int index, ExampleRectanglePosition exampleRectanglePosition) throws BadCoordinateValueException, SheetSizeException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetAmountExceededLimitException, CloneNotSupportedException {
        try {
            StaticLayoutFactory.initLayoutFactor(15, 15);
        } catch(Exception e) {}
        int[] widthArray = null;
        int[] heightArray = null;
        GetRectangleCoordSAM getRectangleCoordInstance = null;
        switch(exampleRectanglePosition) {
            case NW:
                widthArray = NW_WIDTH;
                heightArray = NW_HEIGHT;
                getRectangleCoordInstance = (innerIndex) -> {
                    try {
                        return getNWRectangleCoord(innerIndex);
                    } catch (BadCoordinateValueException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
                break;
            case SE:
                widthArray = SE_WIDTH;
                heightArray = SE_HEIGHT;
                getRectangleCoordInstance = (innerIndex) -> {
                    try {
                        return getSERectangleCoord(innerIndex);
                    } catch (BadCoordinateValueException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
                break;
            default:
                throw new UnsupportedOperationException("Unsupported ExampleRectanglePosition");
        }
        PieceVariation pieceVariation = new PieceVariation(StaticPieceFactory.getPieceFactory().getSheetPiece(widthArray[index], heightArray[index], 0));
        pieceVariation.setNorthWesternCoord(getRectangleCoordInstance.getRectangleCoord(index));
        return new RectangleCorners(pieceVariation);
    }

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
    @DisplayName("Constructor 1")
    void rectangleCornersConstructor1() throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(0, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(0, ExampleRectanglePosition.SE);

        assertEquals(1, nwRectangleCorners.getNWCoord().getX());
        assertEquals(2, nwRectangleCorners.getNWCoord().getY());
        assertEquals(4, nwRectangleCorners.getSECoord().getX());
        assertEquals(7, nwRectangleCorners.getSECoord().getY());

        assertEquals(4, seRectangleCorners.getNWCoord().getX());
        assertEquals(7, seRectangleCorners.getNWCoord().getY());
        assertEquals(10, seRectangleCorners.getSECoord().getX());
        assertEquals(16, seRectangleCorners.getSECoord().getY());
    }

    @Test
    @Order(2)
    @DisplayName("Constructor 2")
    void rectangleCornersConstructor2() throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(1, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(1, ExampleRectanglePosition.SE);

        assertEquals(1, nwRectangleCorners.getNWCoord().getX());
        assertEquals(2, nwRectangleCorners.getNWCoord().getY());
        assertEquals(4, nwRectangleCorners.getSECoord().getX());
        assertEquals(7, nwRectangleCorners.getSECoord().getY());

        assertEquals(5, seRectangleCorners.getNWCoord().getX());
        assertEquals(8, seRectangleCorners.getNWCoord().getY());
        assertEquals(11, seRectangleCorners.getSECoord().getX());
        assertEquals(17, seRectangleCorners.getSECoord().getY());
    }

    @Test
    @Order(3)
    @DisplayName("Constructor 3")
    void rectangleCornersConstructor3() throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(2, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(2, ExampleRectanglePosition.SE);

        assertEquals(1, nwRectangleCorners.getNWCoord().getX());
        assertEquals(2, nwRectangleCorners.getNWCoord().getY());
        assertEquals(4, nwRectangleCorners.getSECoord().getX());
        assertEquals(7, nwRectangleCorners.getSECoord().getY());

        assertEquals(3, seRectangleCorners.getNWCoord().getX());
        assertEquals(6, seRectangleCorners.getNWCoord().getY());
        assertEquals(9, seRectangleCorners.getSECoord().getX());
        assertEquals(15, seRectangleCorners.getSECoord().getY());
    }

    @Test
    @Order(4)
    @DisplayName("(1) NOT overlapping - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther1() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isXOfOtherNotOverlappingXY(0);
    }

    void isXOfOtherNotOverlappingXY(int index) throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.SE);

        assertTrue(nwRectangleCorners.isRectangleNorthOf(seRectangleCorners));
        assertTrue(nwRectangleCorners.isRectangleWestOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isRectangleSouthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isRectangleEastOf(seRectangleCorners));

        assertTrue(seRectangleCorners.isRectangleSouthOf(nwRectangleCorners));
        assertTrue(seRectangleCorners.isRectangleEastOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isRectangleNorthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isRectangleWestOf(nwRectangleCorners));
    }

    @Test
    @Order(5)
    @DisplayName("(2) NOT overlapping - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther2() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isXOfOtherNotOverlappingXY(1);
    }

    @Test
    @Order(6)
    @DisplayName("(3) overlapping XY - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther3() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isXOfOtherOverlappingXY(2);
    }

    void isXOfOtherOverlappingXY(int index) throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.SE);

        assertFalse(nwRectangleCorners.isRectangleNorthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isRectangleWestOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isRectangleSouthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isRectangleEastOf(seRectangleCorners));

        assertFalse(seRectangleCorners.isRectangleSouthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isRectangleEastOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isRectangleNorthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isRectangleWestOf(nwRectangleCorners));
    }

    @Test
    @Order(7)
    @DisplayName("(4) overlapping X not Y - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther4() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isXOfOther(3, true, false);
    }

    void isXOfOther(int index, boolean isXOverlapping, boolean isYOverlapping) throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.SE);

        assertEquals(!isYOverlapping, nwRectangleCorners.isRectangleNorthOf(seRectangleCorners));
        assertEquals(!isXOverlapping, nwRectangleCorners.isRectangleWestOf(seRectangleCorners));
        assertEquals(false, nwRectangleCorners.isRectangleSouthOf(seRectangleCorners));
        assertEquals(false, nwRectangleCorners.isRectangleEastOf(seRectangleCorners));

        assertEquals(!isYOverlapping, seRectangleCorners.isRectangleSouthOf(nwRectangleCorners));
        assertEquals(!isXOverlapping, seRectangleCorners.isRectangleEastOf(nwRectangleCorners));
        assertEquals(false, seRectangleCorners.isRectangleNorthOf(nwRectangleCorners));
        assertEquals(false, seRectangleCorners.isRectangleWestOf(nwRectangleCorners));
    }

    @Test
    @Order(8)
    @DisplayName("(5) overlapping X not Y - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther5() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isXOfOther(4, false, true);
    }

    @Test
    @Order(9)
    @DisplayName("isOverlapping")
    void isOverlapping() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isOverlapping(0, false);
        isOverlapping(1, false);
        isOverlapping(2, true);
        isOverlapping(3, false);
        isOverlapping(4, false);
    }

    void isOverlapping(int index, boolean expectedIsOverLapping) throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.SE);

        assertEquals(expectedIsOverLapping, nwRectangleCorners.isRectangleOverlapping(seRectangleCorners));
    }

    @Test
    @Order(9)
    @DisplayName("isInsideOther")
    void isInsideOther() throws BadCoordinateValueException, CloneNotSupportedException, NegativePiecePointsException, PieceCanNotFitIntoLayoutException, SheetAmountExceededLimitException, LayoutFactoryNotInitiatedException, SheetSizeException {
        isInsideOther(5, true);
        isInsideOther(6, true);
        isInsideOther(7, false);
        isInsideOther(8, false);
        isInsideOther(9, false);
    }

    void isInsideOther(int index, boolean expectedIsInsideOther) throws BadCoordinateValueException, CloneNotSupportedException, SheetAmountExceededLimitException, PieceCanNotFitIntoLayoutException, NegativePiecePointsException, LayoutFactoryNotInitiatedException, SheetSizeException {
        RectangleCorners nwRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.NW);
        RectangleCorners seRectangleCorners = getRectangleCorners(index, ExampleRectanglePosition.SE);

        assertEquals(expectedIsInsideOther, nwRectangleCorners.isRectangleInsideOther(seRectangleCorners));
    }
}
