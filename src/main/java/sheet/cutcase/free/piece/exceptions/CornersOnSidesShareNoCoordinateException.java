package sheet.cutcase.free.piece.exceptions;

import coords.Coordinate;

import java.util.Arrays;

public class CornersOnSidesShareNoCoordinateException extends Exception {
    public CornersOnSidesShareNoCoordinateException(Coordinate[] cornersOnOneSide) {
        super(generateMessage(cornersOnOneSide), new Throwable(generateMessage(cornersOnOneSide)));
    }

    public static String generateMessage(Coordinate[] cornersOnOneSide) {
        return String.format("PieceVariation's corners on sides of FreePieceVariation do not share common X or Y.\n\r" +
                "PieceVariation's corners on FreePieceVariation: %s." + Arrays.asList(cornersOnOneSide).toString());
    }
}
