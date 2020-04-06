package utils.lab1.exceptions;

public class BufferedReaderOpenException extends Exception {
    
    private static final long serialVersionUID = 2884361569566243623L;

    public BufferedReaderOpenException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + BufferedReaderOpenException.class.getName() + ": " + "\n\r" +
                        "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
