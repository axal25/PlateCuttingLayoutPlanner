package coords;

import coords.exceptions.BadCoordinateValueException;
import org.junit.jupiter.api.*;
import sheet.Layout;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoordinateTest {

    @Test
    @Order(1)
    @DisplayName("Coordinate Constructor Correct Values")
    void coordinateConstructorCorrectValues() throws BadCoordinateValueException {
        coordinateConstructorCorrectValues(0, 0);
        coordinateConstructorCorrectValues(1, 2);
        coordinateConstructorCorrectValues(3, 4);
        coordinateConstructorCorrectValues(Layout.MAX_PLATE_WIDTH, Layout.MAX_PLATE_HEIGHT);
    }

    Coordinate coordinateConstructorCorrectValues(int x, int y) throws BadCoordinateValueException {
        final Coordinate coordinate = new Coordinate(x, y);
        assertNotNull(coordinate);
        assertEquals(x, coordinate.getX());
        assertEquals(y, coordinate.getY());
        return coordinate;
    }

    @Test
    @Order(2)
    @DisplayName("Coordinate Constructor Exception X")
    void coordinateConstructorExceptionX()  {
        coordinateConstructorExceptions(-1, 0);
    }

    @Test
    @Order(3)
    @DisplayName("Coordinate Constructor Exception Y")
    void coordinateConstructorExceptionY()  {
        coordinateConstructorExceptions(0, -1);
    }

    void coordinateConstructorExceptions(int x, int y) {
        assertThrows(BadCoordinateValueException.class, () -> new Coordinate(x, y));
    }

    @Test
    @Order(4)
    @DisplayName("equals")
    void equals() throws BadCoordinateValueException {
        assertNewSameValueObjectEquals(1, 1);
        assertNewSameValueObjectEquals(2, 2);
        assertNewSameValueObjectEquals(6, 6);
        assertNewSameValueObjectEquals(3, 6);
        assertNewSameValueObjectEquals(9, 15);
        assertNewSameValueObjectEquals(15,  1);
    }

    void assertNewSameValueObjectEquals(int x, int y) throws BadCoordinateValueException {
        Coordinate coord1 = coordinateConstructorCorrectValues(x, y);
        Coordinate coord2 = coordinateConstructorCorrectValues(x, y);
        Coordinate coord3 = coordinateConstructorCorrectValues(x, y);
        assertEquals(coord1, coord1);
        assertEquals(coord1, coord2);
        assertEquals(coord1, coord3);
        assertTrue(coord1.hashCode() == coord1.hashCode());
        assertTrue(coord1.hashCode() == coord2.hashCode());
        assertTrue(coord1.hashCode() == coord3.hashCode());
        assertEquals(coord2, coord2);
        assertEquals(coord2, coord1);
        assertEquals(coord2, coord3);
        assertTrue(coord2.hashCode() == coord2.hashCode());
        assertTrue(coord2.hashCode() == coord1.hashCode());
        assertTrue(coord2.hashCode() == coord3.hashCode());
        assertEquals(coord3, coord3);
        assertEquals(coord3, coord1);
        assertEquals(coord3, coord2);
        assertTrue(coord3.hashCode() == coord3.hashCode());
        assertTrue(coord3.hashCode() == coord1.hashCode());
        assertTrue(coord3.hashCode() == coord2.hashCode());
        assertTrue(coord1 == coord1);
        assertTrue(coord2 == coord2);
        assertTrue(coord3 == coord3);
        assertTrue(coord1 != coord2);
        assertTrue(coord1 != coord3);
        assertTrue(coord2 != coord1);
        assertTrue(coord2 != coord3);
    }

    @Test
    @Order(4)
    @DisplayName("equals")
    void notEquals() throws BadCoordinateValueException {
        assertNewDifferentValueObjectNotEquals(1, 1);
        assertNewDifferentValueObjectNotEquals(2, 2);
        assertNewDifferentValueObjectNotEquals(6, 6);
        assertNewDifferentValueObjectNotEquals(3, 6);
        assertNewDifferentValueObjectNotEquals(9, 15);
        assertNewDifferentValueObjectNotEquals(15,  1);
    }

    void assertNewDifferentValueObjectNotEquals(int x, int y) throws BadCoordinateValueException {
        Coordinate coord1 = coordinateConstructorCorrectValues(x, y);
        Coordinate coord2 = coordinateConstructorCorrectValues(x+1, y+2);
        Coordinate coord3 = coordinateConstructorCorrectValues(x+3, y+4);
        assertEquals(coord1, coord1);
        assertNotEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
        assertEquals(coord2, coord2);
        assertNotEquals(coord2, coord1);
        assertNotEquals(coord2, coord3);
        assertEquals(coord3, coord3);
        assertNotEquals(coord3, coord1);
        assertNotEquals(coord3, coord2);
        assertTrue(coord1 == coord1);
        assertTrue(coord2 == coord2);
        assertTrue(coord3 == coord3);
        assertTrue(coord1 != coord2);
        assertTrue(coord1 != coord3);
        assertTrue(coord2 != coord1);
        assertTrue(coord2 != coord3);
    }

    @Test
    @Order(5)
    @DisplayName("isBetweenCoords")
    void isBetweenCoords() throws BadCoordinateValueException {
        isBetweenCoords(0, 0, 15, 15, 5, 5);
        isBetweenCoords(0, 0, 0, 10, 0, 5);
        isBetweenCoords(0, 0, 10, 0, 5, 0);
    }

    void isBetweenCoords(int startX, int startY, int stopX, int stopY, int betweenX, int betweenY) throws BadCoordinateValueException {
        Coordinate coord1 = coordinateConstructorCorrectValues(startX, startY);
        Coordinate coord2 = coordinateConstructorCorrectValues(betweenX, betweenY);
        Coordinate coord3 = coordinateConstructorCorrectValues(stopX, stopY);
        assertTrue(coord2.isBetweenCoords(coord1, coord3));
        assertTrue(coord2.isBetweenCoords(coord3, coord1));
        assertFalse(coord1.isBetweenCoords(coord2, coord3));
        assertFalse(coord1.isBetweenCoords(coord3, coord2));
        assertFalse(coord1.isBetweenCoords(coord1, coord1));
        assertFalse(coord1.isBetweenCoords(coord1, coord2));
        assertFalse(coord1.isBetweenCoords(coord2, coord1));
        assertFalse(coord1.isBetweenCoords(coord1, coord3));
        assertFalse(coord1.isBetweenCoords(coord3, coord1));
    }
}
