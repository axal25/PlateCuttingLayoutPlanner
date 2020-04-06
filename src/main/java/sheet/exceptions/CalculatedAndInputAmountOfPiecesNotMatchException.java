package sheet.exceptions;

public class CalculatedAndInputAmountOfPiecesNotMatchException extends Exception {
    public CalculatedAndInputAmountOfPiecesNotMatchException(String callingClassName, String callingFunctionName, String cause) {
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
