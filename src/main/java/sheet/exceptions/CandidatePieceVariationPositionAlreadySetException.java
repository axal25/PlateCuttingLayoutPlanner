package sheet.exceptions;

public class CandidatePieceVariationPositionAlreadySetException extends Exception {
    public CandidatePieceVariationPositionAlreadySetException(String callingClassName, String callingFunctionName, String cause) {
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
