package utils.lab1.exceptions;

public class FileInHandlersException extends Exception {
    
    private static final long serialVersionUID = 3726604256564587584L;

    public FileInHandlersException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + FileInHandlersException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
