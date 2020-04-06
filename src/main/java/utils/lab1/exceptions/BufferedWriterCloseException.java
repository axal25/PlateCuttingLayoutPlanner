package utils.lab1.exceptions;

public class BufferedWriterCloseException extends Exception {
    
    private static final long serialVersionUID = 8122649378840291928L;

    public BufferedWriterCloseException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + BufferedWriterCloseException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
