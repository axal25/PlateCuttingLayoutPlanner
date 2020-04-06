package utils.lab1;

import org.junit.jupiter.api.*;
import utils.lab1.exceptions.FileOpenException;
import utils.lab1.exceptions.FileWriterCloseException;
import utils.lab1.exceptions.FileWriterOpenException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileWriterOpsTest {
    @BeforeEach
    void setUp() {
        try{
            FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        } catch(Exception e) {};

    }

    @AfterEach
    void tearDown() {
        try{
            FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        } catch(Exception e) {};
    }

    @Test
    @Order(17)
    void openFileWriter() throws IOException, FileOpenException, FileWriterOpenException {
        FileWriter[] fileWritersList = getOpenedFileWriters();
        assertNotNull(fileWritersList[0]);
        assertNotNull(fileWritersList[1]);
        assertNull(fileWritersList[2]);
    }

    static FileWriter[] getOpenedFileWriters() throws FileOpenException, IOException, FileWriterOpenException {
        try{
            FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        } catch(FileOpenException e) {}
        File existingFile = FileOps.openExistingFile(FileOpsTest.EXISTING_FULL_FILE_PATH);
        File notExistingFile = FileOps.openNewFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        FileWriter[] fileWritersList = new FileWriter[3];
        fileWritersList[0] = FileWriterOps.openFileWriter(existingFile, true);
        fileWritersList[1] = FileWriterOps.openFileWriter(notExistingFile, true);
        assertThrows(FileWriterOpenException.class, () -> FileWriterOps.openFileWriter(null, true));
        fileWritersList[2] = null;
        return fileWritersList;
    }

    @Test
    @Order(20)
    void closeFileWriter() throws IOException, FileOpenException, FileWriterCloseException, FileWriterOpenException {
        FileWriter[] fileWriterList = FileWriterOpsTest.getOpenedFileWriters();
        fileWriterList[0] = FileWriterOps.closeFileWriter(fileWriterList[0]);
        fileWriterList[1] = FileWriterOps.closeFileWriter(fileWriterList[1]);
        fileWriterList[2] = null;
        assertNotNull(FileWriterOps.closeFileWriter(fileWriterList[0]));
        assertNotNull(FileWriterOps.closeFileWriter(fileWriterList[1]));
        assertThrows(FileWriterCloseException.class, () -> FileWriterOps.closeFileWriter(null));
        assertNull(fileWriterList[2]);
    }
}
