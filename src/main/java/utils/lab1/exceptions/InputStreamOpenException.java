package utils.lab1.exceptions;

public class InputStreamOpenException extends Exception {

    private static final long serialVersionUID = 2139844883014623475L;

    public InputStreamOpenException(String name, String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + InputStreamOpenException.class.getName() + ": " + "\n\r" +
                        "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
