package utils.lab1;

import org.junit.jupiter.api.*;
import utils.lab1.exceptions.BufferedWriterCloseException;
import utils.lab1.exceptions.BufferedWriterOpenException;
import utils.lab1.exceptions.FileOpenException;
import utils.lab1.exceptions.FileWriterOpenException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BufferedWriterOpsTest {
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
    @Order(18)
    void openBufferedWriter() throws BufferedWriterOpenException, FileOpenException, IOException, FileWriterOpenException {
        BufferedWriter[] bufferedWriterList = getOpenedBufferedWriters();
        assertNotNull(bufferedWriterList[0]);
        assertNotNull(bufferedWriterList[1]);
        assertNull(bufferedWriterList[2]);
    }

    BufferedWriter[] getOpenedBufferedWriters() throws IOException, FileOpenException, BufferedWriterOpenException, FileWriterOpenException {
        FileWriter[] fileWriterList = FileWriterOpsTest.getOpenedFileWriters();
        BufferedWriter[] bufferedWriterList = new BufferedWriter[3];
        bufferedWriterList[0] = BufferedWriterOps.openBufferedWriter(fileWriterList[0]);
        bufferedWriterList[1] = BufferedWriterOps.openBufferedWriter(fileWriterList[1]);
        assertThrows(BufferedWriterOpenException.class, () -> BufferedWriterOps.openBufferedWriter(null));
        bufferedWriterList[2] = null;
        return bufferedWriterList;
    }

    @Test
    @Order(19)
    void closeBufferedWriter() throws BufferedWriterCloseException, BufferedWriterOpenException, FileOpenException, IOException, FileWriterOpenException {
        BufferedWriter[] bufferedWriterList = getClosedBufferedWriters();
        assertNotNull(bufferedWriterList[0]);
        assertNotNull(bufferedWriterList[1]);
        assertNull(bufferedWriterList[2]);
    }

    BufferedWriter[] getClosedBufferedWriters() throws BufferedWriterOpenException, FileOpenException, IOException, BufferedWriterCloseException, FileWriterOpenException {
        BufferedWriter[] bufferedWriterList = getOpenedBufferedWriters();
        bufferedWriterList[0] = BufferedWriterOps.closeBufferedWriter(bufferedWriterList[0]);
        bufferedWriterList[1] = BufferedWriterOps.closeBufferedWriter(bufferedWriterList[1]);
        assertThrows(BufferedWriterCloseException.class, () -> BufferedWriterOps.closeBufferedWriter(null));
        bufferedWriterList[2] = null;
        return bufferedWriterList;
    }
}
