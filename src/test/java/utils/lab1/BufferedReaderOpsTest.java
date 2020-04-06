package utils.lab1;

import org.junit.jupiter.api.*;
import utils.lab1.exceptions.BufferedReaderCloseException;
import utils.lab1.exceptions.BufferedReaderOpenException;
import utils.lab1.exceptions.FileOpenException;
import utils.lab1.exceptions.FileReaderOpenException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BufferedReaderOpsTest {
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
    @Order(8)
    void openBufferedReader() throws FileOpenException, FileNotFoundException, BufferedReaderOpenException, FileReaderOpenException {
        File existingFile = FileOps.openExistingFile(FileOpsTest.EXISTING_FULL_FILE_PATH);
        FileReader fileReader = FileReaderOps.openFileReader(existingFile);
        assertNotNull(BufferedReaderOps.openBufferedReader(fileReader));
        assertThrows(BufferedReaderOpenException.class, () -> BufferedReaderOps.openBufferedReader(null));
    }

    @Test
    @Order(9)
    void closeBufferedReader() throws FileOpenException, IOException, BufferedReaderOpenException, BufferedReaderCloseException, FileReaderOpenException {
        File existingFile = FileOps.openExistingFile(FileOpsTest.EXISTING_FULL_FILE_PATH);
        FileReader fileReader = FileReaderOps.openFileReader(existingFile);
        BufferedReader bufferedReader = BufferedReaderOps.openBufferedReader(fileReader);
        assertNotNull(BufferedReaderOps.closeBufferedReader(bufferedReader));
        assertThrows(BufferedReaderCloseException.class, () -> BufferedReaderOps.closeBufferedReader(null));
    }
}
