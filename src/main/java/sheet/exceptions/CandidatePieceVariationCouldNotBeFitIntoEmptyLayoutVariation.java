package sheet.exceptions;

public class CandidatePieceVariationCouldNotBeFitIntoEmptyLayoutVariation extends Exception {
    public CandidatePieceVariationCouldNotBeFitIntoEmptyLayoutVariation(String callingClassName, String callingFunctionName, String cause) {
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
