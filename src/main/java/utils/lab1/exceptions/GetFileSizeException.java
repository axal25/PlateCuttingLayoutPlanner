package utils.lab1.exceptions;

public class GetFileSizeException extends Exception {
    
    private static final long serialVersionUID = 3875291051313506415L;

    public GetFileSizeException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + GetFileSizeException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
                new Throwable(cause)
        );
    }
}
