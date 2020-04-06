package labs.lab1.exceptions;

public class CopyFileException extends Exception {
    
    private static final long serialVersionUID = -8888932329942197833L;

    public CopyFileException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + CopyFileException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
