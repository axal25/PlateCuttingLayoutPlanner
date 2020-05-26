package sheet.exceptions;

import coords.Coordinate;
import coords.Coordinates;

public class NotAllCornersFoundException extends Exception {
    public NotAllCornersFoundException(Coordinate[] coordinates, int expectedAmountOfCoordinates) {
        super(
                String.format(
                        "Expected %d coordinates but found %d coordinates. Coordinates: %s.",
                        expectedAmountOfCoordinates,
                        coordinates.length,
                        Coordinates.toString(coordinates)
                ),
                null
        );
    }
}
