package utils.lab1;

import utils.lab1.exceptions.FileReaderCloseException;
import utils.lab1.exceptions.FileReaderOpenException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderOps {
    public static FileReader openFileReader(File file) throws FileReaderOpenException, FileNotFoundException {
        final String functionName = "openFileReader(File file)";
        if( file == null ) throw new FileReaderOpenException(FileReaderOps.class.getName(), functionName, "Function parameter File file == null");
        return new FileReader(file);
    }

    public static FileReader closeFileReader(FileReader fileReader) throws FileReaderCloseException, IOException {
        final String functionName = "closeFileReader(FileReader fileReader)";
        if( fileReader == null ) throw new FileReaderCloseException(FileReaderOps.class.getName(), functionName, "Function parameter FileReader fileReader == null");
        fileReader.close();
        return fileReader;
    }
}
