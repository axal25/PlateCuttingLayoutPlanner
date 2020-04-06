package sheet.exceptions;

public class SheetSizeException extends Exception {

    public SheetSizeException(String callingClassName, String callingFunctionName, String widthOrHeightName, int widthOrHeightValue) {
        super(
                new StringBuilder()
                        .append(callingClassName)
                        .append(".")
                        .append(callingFunctionName)
                        .append(" ")
                        .append(
                                new StringBuilder()
                                        .append(widthOrHeightName)
                                        .append(" can't be equal or less than 0 (")
                                        .append(widthOrHeightName)
                                        .append(" <= 0). ")
                                        .append(widthOrHeightName)
                                        .append(" detected: ")
                                        .append(widthOrHeightValue)
                                        .append(".")
                                        .toString()
                        ).toString(),
                new Throwable(
                        new StringBuilder()
                                .append(widthOrHeightName)
                                .append(" can't be equal or less than 0 (")
                                .append(widthOrHeightName)
                                .append(" <= 0). ")
                                .append(widthOrHeightName)
                                .append(" detected: ")
                                .append(widthOrHeightValue)
                                .append(".")
                                .toString()
                )
        );
    }
}
