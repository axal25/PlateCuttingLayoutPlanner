package parser;

public class BadAmountOfInputArgsException extends Exception {
    public BadAmountOfInputArgsException(String callingClassName, String callingFunctionName, String cause) {
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
