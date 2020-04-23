package sheet.cutcase.free.piece.exceptions;

import sheet.FreePieceVariation;
import sheet.PieceVariation;

public class CutCaseNullArgumentException extends Exception {
    public CutCaseNullArgumentException(String callingClassName, String callingFunctionName, String argumentType, String cause) {
        super(
                new StringBuilder()
                        .append(callingClassName)
                        .append(".")
                        .append(callingFunctionName)
                        .append(" ")
                        .append(argumentType)
                        .append(" argument is null. Neither ")
                        .append(FreePieceVariation.class.getSimpleName())
                        .append(" nor ")
                        .append(PieceVariation.class.getSimpleName())
                        .append(" arguments can be null.")
                        .append((cause != null && !cause.isEmpty()) ? new StringBuilder().append(" Cause by: ").append(cause) : "")
                        .toString(),
                new Throwable(
                        new StringBuilder()
                        .append(argumentType)
                        .append(" argument is null. Neither ")
                        .append(FreePieceVariation.class.getSimpleName())
                        .append(" nor ")
                        .append(PieceVariation.class.getSimpleName())
                        .append(" arguments can be null.")
                        .append((cause != null && !cause.isEmpty()) ? new StringBuilder().append(" Cause by: ").append(cause) : "")
                        .toString()
                )
        );
    }
}
