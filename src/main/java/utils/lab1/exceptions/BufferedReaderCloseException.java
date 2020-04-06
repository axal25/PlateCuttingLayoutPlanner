package utils.lab1.exceptions;

public class BufferedReaderCloseException extends Exception {
    
    private static final long serialVersionUID = -7185543553654273116L;

    public BufferedReaderCloseException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + BufferedReaderCloseException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
