package labs.lab1;

import labs.lab1.exceptions.CopyFileException;
import utils.lab1.*;
import utils.lab1.exceptions.*;

import java.io.*;

public class CrimesFileOp {
    public static final Long OVERHEAD_PER_HOW_MANY_BYTES = 180L;
    public static final Long FILE_SIZE_OVERHEAD_IN_BYTES_PER_X_BYTES = 1L;

    /** returns new File (output) created from old one (input) limited by size in Bytes copying only full lines
     *
     * 1) If the both files have the same full path (are one and the same file)
     * and size limit is larger than source file size
     * -> return input file without any change
     * 2) Creates output file from input
     * 2.1) If input file size is smaller than size limit copies whole file
     * 2.2) If input file size is larger than size limit copies whole lines allowing for output file to be under size limit
     * @param doReplaceInputFileWithOutput is output file to replace input file,
     *                                     is input file to be deleted and output file to be renamed,
     *                                     if true <code>doDeleteInputFile</code> must be true
     * @param doDeleteInputFile is input file to be delete,
     *                          must be true if <code>doReplaceInputFileWithOutput</code> is true
     **/
    public static File cutFileToSize(String inputFileName, String outputFileName, Long sizeLimitInBytes, boolean doAppend, boolean doDeleteInputFile, boolean doReplaceInputFileWithOutput) throws FileOpenException, GetFileSizeException, BufferedReaderOpenException, BufferedWriterOpenException, RenameFileException, IOException, FileWriterOpenException, FileReaderOpenException, CopyFileException {
        File inputFile = FileOps.openExistingFile( inputFileName );
        File newCopiedFile = copyFile(inputFile, outputFileName, sizeLimitInBytes, doAppend, doDeleteInputFile, doReplaceInputFileWithOutput);
        return newCopiedFile;
    }

    public static File cutInputStreamToSize(String inputFileName, String outputFileName, Long sizeLimitInBytes, boolean doAppend) throws InputStreamOpenException {
        InputStream inputStream = InputStreamOps.newInputStream(inputFileName);
        File newCopiedFile = copyFile(inputStream, outputFileName, sizeLimitInBytes, doAppend);
        return newCopiedFile;
    }

    private static File copyFile(File inputFile, String outputFileName, Long sizeLimitInBytes, boolean doAppend, boolean doDeleteInputFile, boolean doReplaceInputFileWithOutput) throws FileOpenException, BufferedReaderOpenException, IOException, BufferedWriterOpenException, RenameFileException, FileWriterOpenException, FileReaderOpenException, GetFileSizeException, CopyFileException {
        final String functionName = "copyFile(File inputFile, String outputFileName, Long sizeLimitInBytes, boolean doAppend, boolean doDeleteInputFile, boolean doReplaceInputFileWithOutput)";
        if(isToStayUnmodified(inputFile, outputFileName, sizeLimitInBytes)) return inputFile;
        if(!doDeleteInputFile && doReplaceInputFileWithOutput) throw new CopyFileException(
                CrimesFileOp.class.getName(),
                functionName,
                "If you want to rename new file to old file name (swap new file for old one), you need to also delete original file." +
                        " doDeleteInputFile = " + doDeleteInputFile + ", doReplaceInputFileWithOutput = " + doReplaceInputFileWithOutput
        );
        File outputFile = FileOps.openNewFile(outputFileName);
        outputFile = doCopyFile(inputFile, outputFile, sizeLimitInBytes, doAppend);
        outputFile = doRenameAndOrDelete(inputFile, outputFile, doDeleteInputFile, doReplaceInputFileWithOutput);
        return outputFile;
    }

    private static File copyFile(InputStream inputStream, String outputFileName, Long sizeLimitInBytes, boolean doAppend) {
        return null;
    }

    private static boolean isToStayUnmodified(File inputFile, String outputFileName, Long sizeLimitInBytes) {
        try {
            File outputFile = FileOps.openExistingFile(outputFileName);
            Long fileSizeInBytes = FileOps.getFileSizeBytes(inputFile);
            if(sizeLimitInBytes >= fileSizeInBytes && inputFile.getAbsolutePath().compareTo(outputFile.getAbsolutePath()) == 0) return true;
        } catch(FileOpenException | GetFileSizeException e) {}
        return false;
    }

    private static File doCopyFile(File inputFile, File outputFile, Long sizeLimitInBytes, boolean doAppend) throws IOException, BufferedReaderOpenException, BufferedWriterOpenException, FileWriterOpenException, FileReaderOpenException, GetFileSizeException {
        try (
                FileReader originalFileReader = FileReaderOps.openFileReader( inputFile );
                FileWriter newFileWriter = FileWriterOps.openFileWriter(outputFile, doAppend);
                BufferedReader originalBufferedReader = BufferedReaderOps.openBufferedReader( originalFileReader );
                BufferedWriter newBufferedWriter = BufferedWriterOps.openBufferedWriter( newFileWriter );
                ){
            String curLine = originalBufferedReader.readLine();
            Long currentFileSizeBytes = FileOps.getFileSizeBytes( outputFile.getAbsoluteFile() );
            Long currentLineSizeBytes = FileOps.getStringSizeBytes( curLine );
            Long appendedFileSizeBytes = currentFileSizeBytes + currentLineSizeBytes;
            Long fileSizeOverhead = (appendedFileSizeBytes / OVERHEAD_PER_HOW_MANY_BYTES) * FILE_SIZE_OVERHEAD_IN_BYTES_PER_X_BYTES;

            while( curLine != null &&  sizeLimitInBytes > (appendedFileSizeBytes + fileSizeOverhead)) {
                newBufferedWriter.write( curLine );
                newBufferedWriter.newLine();
                curLine = originalBufferedReader.readLine();
                currentFileSizeBytes = appendedFileSizeBytes;
                currentLineSizeBytes = FileOps.getStringSizeBytes( curLine );
                appendedFileSizeBytes = currentFileSizeBytes + currentLineSizeBytes;
                fileSizeOverhead = (appendedFileSizeBytes / OVERHEAD_PER_HOW_MANY_BYTES) * FILE_SIZE_OVERHEAD_IN_BYTES_PER_X_BYTES;
            }
        } catch (IOException | GetFileSizeException e) {
            throw e;
        }
        return outputFile;
    }

    private static File doRenameAndOrDelete(File inputFile, File outputFile, boolean doDeleteInputFile, boolean doReplaceInputFileWithOutput) throws FileOpenException, RenameFileException, CopyFileException {
        if(doDeleteInputFile) {
            FileOps.deleteFile(inputFile.getAbsolutePath());
        }
        if(doReplaceInputFileWithOutput) {
            outputFile = FileOps.renameFile(outputFile, inputFile);
        }
        return outputFile;
    }
}
