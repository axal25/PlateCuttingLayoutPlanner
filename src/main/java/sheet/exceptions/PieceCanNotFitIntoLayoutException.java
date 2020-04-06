package sheet.exceptions;

public class PieceCanNotFitIntoLayoutException extends Exception {
    public PieceCanNotFitIntoLayoutException(String callingClassName, String callingFunctionName, String cause) {
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
