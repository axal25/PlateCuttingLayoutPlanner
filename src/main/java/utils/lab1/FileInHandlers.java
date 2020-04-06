package utils.lab1;

import utils.ExceptionMessageGenerator;
import utils.lab1.exceptions.*;

import java.io.*;

public class FileInHandlers {
    public File file;
    public FileReader fileReader;
    public BufferedReader bufferedReader;

    public FileInHandlers() {
        set(null, null, null);
    }

    public FileInHandlers(File file, FileReader fileReader, BufferedReader bufferedReader) {
        set(file, fileReader, bufferedReader);
    }

    public FileInHandlers set(File file, FileReader fileReader, BufferedReader bufferedReader) {
        this.file = file;
        this.fileReader = fileReader;
        this.bufferedReader = bufferedReader;
        return this;
    }

    public FileInHandlers open(File file) throws FileNotFoundException, BufferedReaderOpenException, FileReaderOpenException {
        if( file != null ) {
            fileReader = FileReaderOps.openFileReader( file );
            bufferedReader = BufferedReaderOps.openBufferedReader( fileReader );
            set( file, fileReader, bufferedReader );
        }
        return this;
    }

    public FileInHandlers close(File file, FileReader fileReader, BufferedReader bufferedReader ) throws FileInHandlersException, FileReaderCloseException, IOException, BufferedReaderCloseException {
        final String functionName = "close(File file, FileReader fileReader, BufferedReader bufferedReader )";
        if( file == null ) throw new FileInHandlersException( this.getClass().getName(), functionName, "file == null");
        if( fileReader == null ) throw new FileInHandlersException( this.getClass().getName(), functionName, "fileReader == null");
        if( bufferedReader == null ) throw new FileInHandlersException( this.getClass().getName(), functionName, "bufferedReader == null");
        fileReader = FileReaderOps.closeFileReader( fileReader );
        bufferedReader = BufferedReaderOps.closeBufferedReader( bufferedReader );
        set( file, fileReader, bufferedReader );
        return this;
    }

    public FileInHandlers close() throws BufferedReaderCloseException, FileInHandlersException, FileReaderCloseException, IOException {
        return this.close( this.file, this.fileReader, this.bufferedReader );
    }
}
