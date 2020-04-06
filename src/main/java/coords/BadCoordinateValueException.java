package coords;

public class BadCoordinateValueException extends Exception {
    public BadCoordinateValueException(String callingClassName, String callingFunctionName, String xOrYName, int xOrYValue) {
        super(
                new StringBuilder()
                        .append(callingClassName)
                        .append(".")
                        .append(callingFunctionName)
                        .append(" ")
                        .append(
                                new StringBuilder()
                                        .append("Coordinate ")
                                        .append(xOrYName)
                                        .append(" value can't be lower than 0. (")
                                        .append(xOrYName)
                                        .append(" < 0). ")
                                        .append(xOrYName)
                                        .append(" detected: ")
                                        .append(xOrYValue)
                                        .append(".")
                                        .toString()
                        ).toString(),
                new Throwable(
                        new StringBuilder()
                                .append("Coordinate ")
                                .append(xOrYName)
                                .append(" value can't be lower than 0. (")
                                .append(xOrYName)
                                .append(" < 0). ")
                                .append(xOrYName)
                                .append(" detected: ")
                                .append(xOrYValue)
                                .append(".")
                                .toString()
                )
        );
    }
}
