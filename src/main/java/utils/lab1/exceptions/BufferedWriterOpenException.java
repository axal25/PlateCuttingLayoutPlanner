package utils.lab1.exceptions;

public class BufferedWriterOpenException extends Exception {
    
    private static final long serialVersionUID = 4925470271877268450L;

    public BufferedWriterOpenException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + BufferedWriterOpenException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
