package utils.lab1;

import utils.lab1.exceptions.FileWriterCloseException;
import utils.lab1.exceptions.FileWriterOpenException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterOps {
    public static FileWriter openFileWriter(File file, boolean doAppend) throws IOException, FileWriterOpenException {
        final String functionName = "openFileWriter(File file, boolean doAppend)";
        if( file == null ) throw new FileWriterOpenException(FileWriterOps.class.getName(), functionName, "Function parameter File file == null");
        return new FileWriter( file, doAppend );
    }

    public static FileWriter closeFileWriter(FileWriter fileWriter) throws FileWriterCloseException, IOException {
        final String functionName = "closeFileWriter(FileWriter fileWriter)";
        if( fileWriter == null ) throw new FileWriterCloseException(FileWriterOps.class.getName(), functionName, "function parameter FileWriter fileWriter == null");
        fileWriter.close();
        return fileWriter;
    }
}
