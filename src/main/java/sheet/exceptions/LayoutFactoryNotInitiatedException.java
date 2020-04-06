package sheet.exceptions;

public class LayoutFactoryNotInitiatedException extends Exception {
    public LayoutFactoryNotInitiatedException(String callingClassName, String callingFunctionName, String cause) {
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
