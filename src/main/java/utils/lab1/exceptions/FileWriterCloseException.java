package utils.lab1.exceptions;

public class FileWriterCloseException extends Exception {
    
    private static final long serialVersionUID = 432958488650349448L;

    public FileWriterCloseException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileWriterCloseException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
