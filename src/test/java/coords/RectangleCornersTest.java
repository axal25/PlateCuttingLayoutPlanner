package coords;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RectangleCornersTest {

    public final static int[] NW_RECTANGLE_NW_X_COORD = {1, 1, 1, 1, 1};
    public final static int[] NW_RECTANGLE_NW_Y_COORD = {2, 2, 2, 2, 2};
    public final static int[] NW_WIDTH = {3, 3, 3, 3, 3};
    public final static int[] NW_HEIGHT = {5, 5, 5, 5, 5};
    public final static int[] X_SPACING = {0, 1, -1, -1, 1};
    public final static int[] Y_SPACING = {0, 1, -1, 1, -1};
    public final static int[] SE_WIDTH = {6, 6, 6, 6, 6};
    public final static int[] SE_HEIGHT = {9, 9, 9, 9, 9};

    public static Coordinate getNWRectangleCoord(int index) throws BadCoordinateValueException {
        return new Coordinate(NW_RECTANGLE_NW_X_COORD[index], NW_RECTANGLE_NW_Y_COORD[index]);
    }

    public static Coordinate getSERectangleCoord(int index) throws BadCoordinateValueException {
        Coordinate nwRectangleCoord = getNWRectangleCoord(index);
        return new Coordinate(nwRectangleCoord.getX() + NW_WIDTH[index] + X_SPACING[index], nwRectangleCoord.getY() + NW_HEIGHT[index] + Y_SPACING[index]);
    }

    @Test
    @Order(1)
    @DisplayName("Constructor 1")
    void rectangleCornersConstructor1() throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(0), NW_WIDTH[0], NW_HEIGHT[0]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(0), SE_WIDTH[0], SE_HEIGHT[0]);

        assertEquals(1, nwRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(2, nwRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(4, nwRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(7, nwRectangleCorners.getSouthEasternCoord().getY());

        assertEquals(4, seRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(7, seRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(10, seRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(16, seRectangleCorners.getSouthEasternCoord().getY());
    }

    @Test
    @Order(2)
    @DisplayName("Constructor 2")
    void rectangleCornersConstructor2() throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(1), NW_WIDTH[1], NW_HEIGHT[1]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(1), SE_WIDTH[1], SE_HEIGHT[1]);

        assertEquals(1, nwRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(2, nwRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(4, nwRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(7, nwRectangleCorners.getSouthEasternCoord().getY());

        assertEquals(5, seRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(8, seRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(11, seRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(17, seRectangleCorners.getSouthEasternCoord().getY());
    }

    @Test
    @Order(3)
    @DisplayName("Constructor 3")
    void rectangleCornersConstructor3() throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(2), NW_WIDTH[2], NW_HEIGHT[2]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(2), SE_WIDTH[2], SE_HEIGHT[2]);

        assertEquals(1, nwRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(2, nwRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(4, nwRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(7, nwRectangleCorners.getSouthEasternCoord().getY());

        assertEquals(3, seRectangleCorners.getNorthWesternCoord().getX());
        assertEquals(6, seRectangleCorners.getNorthWesternCoord().getY());
        assertEquals(9, seRectangleCorners.getSouthEasternCoord().getX());
        assertEquals(15, seRectangleCorners.getSouthEasternCoord().getY());
    }

    @Test
    @Order(4)
    @DisplayName("(1) NOT overlapping - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther1() throws BadCoordinateValueException {
        isXOfOtherNotOverlappingXY(0);
    }

    void isXOfOtherNotOverlappingXY(int index) throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(index), NW_WIDTH[index], NW_HEIGHT[index]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(index), SE_WIDTH[index], SE_HEIGHT[index]);

        assertTrue(nwRectangleCorners.isNorthOf(seRectangleCorners));
        assertTrue(nwRectangleCorners.isWestOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isSouthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isEastOf(seRectangleCorners));

        assertTrue(seRectangleCorners.isSouthOf(nwRectangleCorners));
        assertTrue(seRectangleCorners.isEastOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isNorthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isWestOf(nwRectangleCorners));
    }

    @Test
    @Order(5)
    @DisplayName("(2) NOT overlapping - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther2() throws BadCoordinateValueException {
        isXOfOtherNotOverlappingXY(1);
    }

    @Test
    @Order(6)
    @DisplayName("(3) overlapping XY - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther3() throws BadCoordinateValueException {
        isXOfOtherOverlappingXY(2);
    }

    void isXOfOtherOverlappingXY(int index) throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(index), NW_WIDTH[index], NW_HEIGHT[index]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(index), SE_WIDTH[index], SE_HEIGHT[index]);

        assertFalse(nwRectangleCorners.isNorthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isWestOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isSouthOf(seRectangleCorners));
        assertFalse(nwRectangleCorners.isEastOf(seRectangleCorners));

        assertFalse(seRectangleCorners.isSouthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isEastOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isNorthOf(nwRectangleCorners));
        assertFalse(seRectangleCorners.isWestOf(nwRectangleCorners));
    }

    @Test
    @Order(7)
    @DisplayName("(4) overlapping X not Y - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther4() throws BadCoordinateValueException {
        isXOfOther(3, true, false);
    }

    void isXOfOther(int index, boolean isXOverlapping, boolean isYOverlapping) throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(index), NW_WIDTH[index], NW_HEIGHT[index]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(index), SE_WIDTH[index], SE_HEIGHT[index]);

        assertEquals(!isYOverlapping, nwRectangleCorners.isNorthOf(seRectangleCorners));
        assertEquals(!isXOverlapping, nwRectangleCorners.isWestOf(seRectangleCorners));
        assertEquals(false, nwRectangleCorners.isSouthOf(seRectangleCorners));
        assertEquals(false, nwRectangleCorners.isEastOf(seRectangleCorners));

        assertEquals(!isYOverlapping, seRectangleCorners.isSouthOf(nwRectangleCorners));
        assertEquals(!isXOverlapping, seRectangleCorners.isEastOf(nwRectangleCorners));
        assertEquals(false, seRectangleCorners.isNorthOf(nwRectangleCorners));
        assertEquals(false, seRectangleCorners.isWestOf(nwRectangleCorners));
    }

    @Test
    @Order(8)
    @DisplayName("(5) overlapping X not Y - is this NORTH/EAST/WEST/SOUTH of other")
    void isXOfOther5() throws BadCoordinateValueException {
        isXOfOther(4, false, true);
    }

    @Test
    @Order(9)
    @DisplayName("isOverlapping")
    void isOverlapping() throws BadCoordinateValueException {
        isOverlapping(0, false);
        isOverlapping(1, false);
        isOverlapping(2, true);
        isOverlapping(3, false);
        isOverlapping(4, false);
    }

    void isOverlapping(int index, boolean expectedIsOverLapping) throws BadCoordinateValueException {
        RectangleCorners nwRectangleCorners = new RectangleCorners(getNWRectangleCoord(index), NW_WIDTH[index], NW_HEIGHT[index]);
        RectangleCorners seRectangleCorners = new RectangleCorners(getSERectangleCoord(index), SE_WIDTH[index], SE_HEIGHT[index]);

        assertEquals(expectedIsOverLapping, nwRectangleCorners.isOverlapping(seRectangleCorners));
    }
}
