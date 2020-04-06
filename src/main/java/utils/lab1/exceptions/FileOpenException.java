package utils.lab1.exceptions;

public class FileOpenException extends Exception {
    
    private static final long serialVersionUID = 6586638654386504449L;

    public FileOpenException(String callingClassName, String callingFunctionName, String fileName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileOpenException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause + "\n\r" +
                "File name: " + fileName,
                new Throwable(cause)
        );
    }
}
