package sheet.cutcase.free.piece.exceptions;

import coords.Coordinate;
import coords.RectangleCorners;

import java.util.Arrays;

public class CornerNotOnSideException extends Exception {
    public CornerNotOnSideException(String xOrY, Coordinate[] cornersOnOneSide, RectangleCorners fpvRectangleCorners) {
        super(
                generateMessage(xOrY, cornersOnOneSide, fpvRectangleCorners),
                new Throwable(generateMessage(xOrY, cornersOnOneSide, fpvRectangleCorners))
        );
    }

    private static String generateMessage(String xOrY, Coordinate[] cornersOnOneSide, RectangleCorners fpvRectangleCorners) {
        return String.format("2 corners of PieceVariation on sides of FreePieceVariation must have same %s as 2 corners of FreePieceVariation.\n\r" +
                        "PieceVariation's corners on FreePieceVariation's sides: %s.\n" +
                        "FreePieceVariation's corners: %s.",
                xOrY, Arrays.asList(cornersOnOneSide).toString(), fpvRectangleCorners.toString());
    }

    public CornerNotOnSideException(RectangleCorners pvRectangleCorners, RectangleCorners fpvRectangleCorners) {
        super(
                generateMessage(pvRectangleCorners, fpvRectangleCorners),
                new Throwable(generateMessage(pvRectangleCorners, fpvRectangleCorners))
        );
    }

    private static String generateMessage(RectangleCorners pvRectangleCorners, RectangleCorners fpvRectangleCorners) {
        return String.format("2 corners of PieceVariation on sides of FreePieceVariation must have same X or Y as 2 corners of FreePieceVariation.\n\r" +
                        "And 2 other corners of PieceVariation on sides must have same X or Y as 2 other corners of FreePieceVariation.\n\r" +
                        "All 4 PieceVariation corners must satisfy this requirement either for X or Y.\n\r" +
                        "Not mixing X for 2 and Y for other 2.\n\r" +
                        "PieceVariation's corners on FreePieceVariation's sides: %s.\n" +
                        "FreePieceVariation's corners: %s.",
                pvRectangleCorners.toString(), fpvRectangleCorners.toString());
    }
}
