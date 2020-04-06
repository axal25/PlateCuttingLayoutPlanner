package utils.lab1;

import utils.lab1.exceptions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOps {
    public static File newFile(String fileName) throws FileOpenException {
        final String functionName = "tryNewFile(String fileName)";
        if( fileName == null || fileName.isEmpty() ) {
            String cause = "Bad fileName: \"" + fileName + "\"";
            throw new FileOpenException(FileOps.class.getName(), functionName, fileName, cause);
        }
        return new File(fileName);
    }

    public static File openExistingFile(String fileName) throws FileOpenException {
        final String functionName = "openExistingFile(String fileName)";
        File fileToReturn = newFile( fileName );
        if( !fileToReturn.exists() ) {
            String cause = "File to return does NOT exist. fileToReturn.exists() == false";
            throw new FileOpenException(FileOps.class.getName(), functionName, fileName, cause);
        }
        return fileToReturn;
    }

    public static File openExistingFile(String path, String fileName) throws FileOpenException {
        return openExistingFile( path + File.separator + fileName );
    }

    public static File openNewFile(String path, String fileName) throws FileOpenException {
        return openNewFile( path + File.separator + fileName );
    }

    public static File openNewFile(String fileName) throws FileOpenException {
        final String functionName = "openNewFile(String fileName)";
        File fileToReturn = newFile( fileName );
        verifyParentDirectoryPath(fileName);
        if(fileToReturn.exists()) {
            String cause = "File already does exist. fileToReturn.exists() == true";
            throw new FileOpenException(FileOps.class.getName(), functionName, fileName, cause);
        }
        return fileToReturn;
    }

    public static void verifyParentDirectoryPath(String fullFilePath) throws FileOpenException {
        final String functionName = "getFilePath(String fullFilePath)";
        int indexOfLastSlash = fullFilePath.lastIndexOf('/');
        String filePath = null;
        if( indexOfLastSlash == -1 ) filePath = "./";
        else filePath = fullFilePath.substring(0, indexOfLastSlash);
        if( !isDirectory( filePath ) ) throw new FileOpenException(
                FileOps.class.getName(),
                functionName,
                fullFilePath,
                "Its parent directory doesn't exist. fullFilePath = \"" + fullFilePath + "\"."
        );
    }

    public static boolean isDirectory(String fullFilePath) {
        File directory = new File( fullFilePath );
        if( directory.exists() && directory.isDirectory() ) return true;
        else return false;
    }

    public static Long getFileSizeBytes(File file) throws GetFileSizeException {
        final String functionName = "getFileSize(File file)";
        if(file == null) throw new GetFileSizeException(FileOps.class.getName(), functionName, "Function parameter File file == null");
        if(!file.exists()) throw new GetFileSizeException(FileOps.class.getName(), functionName, "Function parameter File file.exists() == false");
        if(!file.isFile()) throw new GetFileSizeException(FileOps.class.getName(), functionName, "Function parameter File file.isFile() == false");
        return file.length();
    }

    public static Long getFileSizeKiloB(File file) throws FileOpenException, GetFileSizeException {
        return sizeBytes2KiloB(getFileSizeBytes(file));
    }

    public static Long getFileSizeMegaB(File file) throws FileOpenException, GetFileSizeException {
        return sizeBytes2MegaB(getFileSizeBytes(file));
    }

    public static Long getFileSizeGigaB(File file) throws FileOpenException, GetFileSizeException {
        return sizeBytes2GigaB(getFileSizeBytes(file));
    }

    public static Long getFileSizeBytes(String fileName) throws FileOpenException, GetFileSizeException {
        return getFileSizeBytes(openExistingFile(fileName));
    }

    public static Long getFileSizeKiloB(String fileName) throws FileOpenException, GetFileSizeException {
        return sizeBytes2KiloB(getFileSizeBytes(fileName));
    }

    public static Long getFileSizeMegaB(String fileName) throws FileOpenException, GetFileSizeException {
        return sizeBytes2MegaB(getFileSizeBytes(fileName));
    }

    public static Long getFileSizeGigaB(String fileName) throws FileOpenException, GetFileSizeException {
        return sizeBytes2GigaB(getFileSizeBytes(fileName));
    }

    public static Long getStringSizeBytes(String string) {
        if( string == null ) return -1L;
        return Long.valueOf(string.getBytes().length);
    }

    public static Long getStringSizeKiloB(String string) {
        if( string == null ) return -1L;
        return sizeBytes2KiloB(getStringSizeBytes(string));
    }

    public static Long getStringSizeMegaB(String string) {
        if( string == null ) return -1L;
        return sizeBytes2MegaB(getStringSizeBytes(string));
    }

    public static Long getStringSizeGigaB(String string) {
        if( string == null ) return -1L;
        return sizeBytes2GigaB(getStringSizeBytes(string));
    }

    public static Long sizeBytes2KiloB(Long fileSizeInBytes) {
        if( fileSizeInBytes == -1 ) return -1L;
        return fileSizeInBytes / (1024L);
    }

    public static Long sizeBytes2MegaB(Long fileSizeInBytes) {
        if( fileSizeInBytes == -1 ) return -1L;
        return fileSizeInBytes / (1024L * 1024L);
    }

    public static Long sizeBytes2GigaB(Long fileSizeInBytes) {
        if( fileSizeInBytes == -1 ) return -1L;
        return fileSizeInBytes / (1024L * 1024L * 1024L);
    }

    public static boolean deleteFile(String fileName) throws FileOpenException {
        File fileToDelete = openExistingFile( fileName );
        return fileToDelete.delete();
    }

    public static File renameFile( File currentFile, File newFile ) throws FileOpenException, RenameFileException {
        final String functionName = "renameFile( File currentFile, File newFile )";
        openExistingFile(currentFile.getAbsolutePath());
        openNewFile( newFile.getAbsolutePath());
        if(!currentFile.renameTo(newFile)) throw new RenameFileException(FileOps.class.getName(), functionName, "Returned value by File currentFile.renameTo(File newFile) == false");
        return newFile;
    }

    public static String renameFile(String currentFullFilePath, String newFullFilePath ) throws FileOpenException, RenameFileException {
        File currentFile = openExistingFile( currentFullFilePath );
        File newFile = openNewFile( newFullFilePath );
        return renameFile( currentFile, newFile ).getAbsolutePath();
    }

    public static File createFile(String newFullFilePath ) throws FileOpenException, IOException, FileWriterCloseException, FileWriterOpenException {
        File newFile = openNewFile( newFullFilePath );
        FileWriter fileWriter = FileWriterOps.openFileWriter( newFile, false );
        fileWriter = FileWriterOps.closeFileWriter( fileWriter );
        return openExistingFile( newFullFilePath );
    }
}
