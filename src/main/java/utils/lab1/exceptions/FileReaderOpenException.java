package utils.lab1.exceptions;

public class FileReaderOpenException extends Exception {
    
    private static final long serialVersionUID = -7886892959407074221L;

    public FileReaderOpenException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileReaderOpenException.class.getName() + ": " + "\n\r" +
                        "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
