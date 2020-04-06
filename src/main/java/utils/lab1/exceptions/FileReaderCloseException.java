package utils.lab1.exceptions;

public class FileReaderCloseException extends Exception {
    
    private static final long serialVersionUID = 4819949929058773033L;

    public FileReaderCloseException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileReaderCloseException.class.getName() + ": " + "\n\r" +
                        "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
