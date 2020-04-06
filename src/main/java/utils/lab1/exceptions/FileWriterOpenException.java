package utils.lab1.exceptions;

public class FileWriterOpenException extends Exception {
    
    private static final long serialVersionUID = 486344465121924218L;

    public FileWriterOpenException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileWriterOpenException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
