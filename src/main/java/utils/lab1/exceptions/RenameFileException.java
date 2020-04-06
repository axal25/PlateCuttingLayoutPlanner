package utils.lab1.exceptions;

public class RenameFileException extends Exception {
    
    private static final long serialVersionUID = 7799588742538640952L;

    public RenameFileException(String callingClassName, String callingFunctionName, String cause) {
        super(callingClassName + " >>> " + callingFunctionName + " >>> " + RenameFileException.class.getName() + ": " + "\n\r" +
                "Exception cause(custom): " + cause,
            new Throwable(cause)
        );
    }
}
