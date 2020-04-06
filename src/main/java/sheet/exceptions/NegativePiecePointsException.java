package sheet.exceptions;

public class NegativePiecePointsException extends Exception {
    public NegativePiecePointsException(String callingClassName, String callingFunctionName, String cause) {
        super(
                new StringBuilder()
                        .append(callingClassName)
                        .append(".")
                        .append(callingFunctionName)
                        .append(" ")
                        .append(cause)
                        .toString(),
                new Throwable(cause)
        );
    }
}
