package sheet.cutcase.free.piece.exceptions;

import coords.Coordinate;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

public class BadAmountOfCoordinatesFoundException extends Exception {
    public BadAmountOfCoordinatesFoundException(Coordinate[] coordinates, int... expectedAmountsOfCoordinates) {
        super(generateMessage(coordinates, expectedAmountsOfCoordinates), new Throwable(generateMessage(coordinates, expectedAmountsOfCoordinates)));
    }

    public BadAmountOfCoordinatesFoundException(TreeSet<Coordinate> coordinates, int... expectedAmountsOfCoordinates) {
        super(generateMessage(coordinates, expectedAmountsOfCoordinates), new Throwable(generateMessage(coordinates, expectedAmountsOfCoordinates)));
    }

    private static String generateMessage(Coordinate[] coordinates, int... expectedAmountsOfCoordinates) {
        return generateGeneralizedMessage(Arrays.asList(coordinates), expectedAmountsOfCoordinates);
    }

    private static String generateMessage(TreeSet<Coordinate> coordinates, int... expectedAmountsOfCoordinates) {
        return generateGeneralizedMessage(coordinates, expectedAmountsOfCoordinates);
    }

    private static String generateGeneralizedMessage(Collection coordinates, int ... expectedAmountsOfCoordinates) {
        return String.format("Expected %s coordinates but found %d coordinates. Coordinates: %s.",
                stringInterpretation(expectedAmountsOfCoordinates), coordinates.size(), coordinates);
    }

    private static String stringInterpretation(int... expectedAmountsOfCoordinates) {
        StringBuilder stringInterpretation = new StringBuilder();
        for (int i = 0; i < expectedAmountsOfCoordinates.length; i++) {
            stringInterpretation.append(expectedAmountsOfCoordinates[i]);
            if (i != expectedAmountsOfCoordinates.length - 1) stringInterpretation.append(" or ");
        }
        return stringInterpretation.toString();
    }
}
