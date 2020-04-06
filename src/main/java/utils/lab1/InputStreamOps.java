package utils.lab1;

import main.AppMain;
import utils.lab1.exceptions.InputStreamOpenException;

import java.io.InputStream;

public class InputStreamOps {
    public static InputStream newInputStream(String fileName) throws InputStreamOpenException {
        final String functionName = "newInputStream(String fileName)";
        if(fileName == null || fileName.isEmpty()) {
            String cause = "Bad fileName: \"" + fileName + "\"";
            throw new InputStreamOpenException(FileOps.class.getName(), functionName, fileName, cause);
        }
        InputStream inputStream = AppMain.class.getResourceAsStream(fileName);
        if(inputStream == null) {
            String cause = "inputStream == " + inputStream;
            throw new InputStreamOpenException(FileOps.class.getName(), functionName, fileName, cause);
        }
        return inputStream;
    }
}
