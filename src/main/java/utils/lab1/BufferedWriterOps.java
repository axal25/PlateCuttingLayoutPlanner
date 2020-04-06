package utils.lab1;

import utils.lab1.exceptions.BufferedWriterCloseException;
import utils.lab1.exceptions.BufferedWriterOpenException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedWriterOps {
    public static BufferedWriter openBufferedWriter(FileWriter fileWriter) throws BufferedWriterOpenException {
        final String functionName = "openBufferedWriter(FileWriter fileWriter)";
        if( fileWriter == null ) throw new BufferedWriterOpenException(BufferedWriterOps.class.getName(), functionName, "Function parameter FileWriter fileWriter == null");
        return new BufferedWriter( fileWriter );
    }

    public static BufferedWriter closeBufferedWriter(BufferedWriter bufferedWriter) throws BufferedWriterCloseException, IOException {
        final String functionName = "closeBufferedWriter(BufferedWriter bufferedWriter)";
        if( bufferedWriter == null ) throw new BufferedWriterCloseException(BufferedWriterOps.class.getName(), functionName, "Function parameter BufferedWriter bufferedWriter == null");
        bufferedWriter.close();
        return bufferedWriter;
    }
}
