package utils.lab1;

import org.junit.jupiter.api.*;
import utils.lab1.exceptions.FileOpenException;
import utils.lab1.exceptions.FileReaderCloseException;
import utils.lab1.exceptions.FileReaderOpenException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileReaderOpsTest {
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
    @Order(6)
    void openFileReader() throws FileOpenException, FileNotFoundException, FileReaderOpenException {
        File existingFile = FileOps.openExistingFile(FileOpsTest.EXISTING_FULL_FILE_PATH);
        File notExistingFile = FileOps.openNewFile(FileOpsTest.EXISTING_PATH_NOT_EXISTING_FILE_FULL_PATH);
        assertNotNull(FileReaderOps.openFileReader(existingFile));
        assertThrows(FileReaderOpenException.class, () -> FileReaderOps.openFileReader(null));
        assertThrows(FileNotFoundException.class, () -> FileReaderOps.openFileReader(notExistingFile));
    }

    @Test
    @Order(7)
    void closeFileReader() throws FileOpenException, IOException, FileReaderCloseException, FileReaderOpenException {
        File existingFile = FileOps.openExistingFile(FileOpsTest.EXISTING_FULL_FILE_PATH);
        FileReader fileReader = FileReaderOps.openFileReader(existingFile);
        assertNotNull(FileReaderOps.closeFileReader(fileReader));
        assertThrows(FileReaderCloseException.class, () -> FileReaderOps.closeFileReader(null));
    }
}
