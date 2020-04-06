package sheet.exceptions;

public class PieceVariationsNotInitiatedException extends Exception {
    public PieceVariationsNotInitiatedException(String callingClassName, String callingFunctionName, String cause) {
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
