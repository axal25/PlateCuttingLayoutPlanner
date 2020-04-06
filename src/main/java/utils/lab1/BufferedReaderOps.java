package utils.lab1;

import utils.lab1.exceptions.BufferedReaderCloseException;
import utils.lab1.exceptions.BufferedReaderOpenException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderOps {
    public static BufferedReader openBufferedReader(FileReader fileReader) throws BufferedReaderOpenException {
        final String functionName = "openBufferedReader(File file)";
        if( fileReader == null ) throw new BufferedReaderOpenException(BufferedReaderOps.class.getName(), functionName, "Function parameter FileReader fileReader == null");
        else return new BufferedReader( fileReader );
    }

    public static BufferedReader closeBufferedReader(BufferedReader bufferedReader) throws IOException, BufferedReaderCloseException {
        final String functionName = "closeBufferedReader(BufferedReader bufferedReader)";
        if( bufferedReader == null ) throw new BufferedReaderCloseException(BufferedReaderOps.class.getName(), functionName, "Function parameter BufferedReader bufferedReader == null");
        bufferedReader.close();
        return bufferedReader;
    }
}
