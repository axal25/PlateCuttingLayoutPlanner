package labs.lab1;

import labs.lab1.CrimesFileOp;
import labs.lab1.exceptions.CopyFileException;
import org.junit.jupiter.api.*;
import utils.lab1.FileOps;
import utils.lab1.FileOpsTest;
import utils.lab1.exceptions.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrimesFileOpTest {

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
    @Order(25)
    void cutFileToSize1() throws FileOpenException, GetFileSizeException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, RenameFileException, FileWriterOpenException, FileReaderOpenException, CopyFileException {
        File newFile = FileOps.openNewFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        Long sizeLimitInBytes = 100L*1024L;
        File returnedFile = CrimesFileOp.cutFileToSize(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, sizeLimitInBytes, false, false, false);
        assertNotNull(returnedFile);
        assertEquals(newFile, returnedFile);
        Long returnedFileSizeBytes = FileOps.getFileSizeBytes(returnedFile);
        org.hamcrest.MatcherAssert.assertThat(
                sizeLimitInBytes,
                org.hamcrest.Matchers.greaterThanOrEqualTo(returnedFileSizeBytes)
        );
        assertTrue( returnedFileSizeBytes <= sizeLimitInBytes);
        assertTrue(FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
    }

    @Test
    @Order(26)
    void cutFileToSize2() throws FileOpenException, GetFileSizeException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, RenameFileException, FileWriterOpenException, FileReaderOpenException, CopyFileException {
        File returnedFile = CrimesFileOp.cutFileToSize(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, Long.MAX_VALUE, false, false, false);
        assertNotNull(returnedFile);
        Long returnedFileSizeKiloB = FileOps.sizeBytes2KiloB(FileOps.getFileSizeBytes(returnedFile));
        org.hamcrest.MatcherAssert.assertThat(
                Long.MAX_VALUE,
                org.hamcrest.Matchers.greaterThan( returnedFileSizeKiloB )
        );
        assertTrue( returnedFileSizeKiloB < Double.MAX_VALUE );
        assertNotEquals(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        assertEquals(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, returnedFile.getAbsolutePath());
    }

    @Test
    @Order(27)
    void cutFileToSize3() throws FileWriterOpenException, GetFileSizeException, FileReaderOpenException, BufferedReaderOpenException, BufferedWriterOpenException, FileOpenException, RenameFileException, IOException, CopyFileException {
        try{
            FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_tmp");
        } catch(Exception e) {};
        assertNotNull(CrimesFileOp.cutFileToSize(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, 150L, false, false, false));
        assertNotNull(CrimesFileOp.cutFileToSize(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_tmp", 100L, false, true, true));
    }


    @Test
    @Order(28)
    void cutFileToSize4() throws GetFileSizeException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, RenameFileException, FileOpenException, FileWriterOpenException, FileReaderOpenException, CopyFileException {
        for(Long i = (CrimesFileOp.OVERHEAD_PER_HOW_MANY_BYTES-1); i <= (1024L*1024L); i+=(CrimesFileOp.OVERHEAD_PER_HOW_MANY_BYTES*10L)) cutFileToSize4(i);
    }

    void cutFileToSize4(Long copyFileSizeLimitInKiloB) throws GetFileSizeException, FileReaderOpenException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, RenameFileException, FileOpenException, FileWriterOpenException, CopyFileException {
        File copyFile = CrimesFileOp.cutFileToSize(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, copyFileSizeLimitInKiloB, false, false, false);
        String cutFullFilePath = FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_cut.txt";
        try{
            FileOps.deleteFile(cutFullFilePath);
        } catch(Exception e) {};
        Long cutFileSizeLimitInKiloB = copyFileSizeLimitInKiloB * 2;
        org.hamcrest.MatcherAssert.assertThat(
                cutFileSizeLimitInKiloB,
                org.hamcrest.Matchers.greaterThan( copyFileSizeLimitInKiloB )
        );
        assertTrue( copyFileSizeLimitInKiloB < cutFileSizeLimitInKiloB );
        File returnedFile = CrimesFileOp.cutFileToSize(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, cutFullFilePath, cutFileSizeLimitInKiloB, false, true, true);
        assertNotNull( returnedFile );
        assertNotNull(FileOps.openNewFile(cutFullFilePath));
        assertThrows(FileOpenException.class, () -> FileOps.openExistingFile(cutFullFilePath));
        assertEquals(copyFile.getAbsolutePath(), returnedFile.getAbsolutePath());
        Long returnedFileSizeKiloB = FileOps.sizeBytes2KiloB(FileOps.getFileSizeBytes( returnedFile ));
        org.hamcrest.MatcherAssert.assertThat(
                copyFileSizeLimitInKiloB,
                org.hamcrest.Matchers.greaterThan(returnedFileSizeKiloB)
        );
        assertTrue(returnedFileSizeKiloB < copyFileSizeLimitInKiloB);
        assertTrue(FileOps.deleteFile(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(cutFullFilePath));
    }

    @Test
    @Order(29)
    void cutFileToSize_doRenameAndOrDelete() throws GetFileSizeException, FileReaderOpenException, CopyFileException, BufferedWriterOpenException, BufferedReaderOpenException, FileWriterOpenException, IOException, RenameFileException, FileOpenException {
        assertNotNull(CrimesFileOp.cutFileToSize(FileOpsTest.EXISTING_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, Long.MAX_VALUE, false, false, false));
        assertNotNull(CrimesFileOp.cutFileToSize(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, Long.MAX_VALUE, false, false, true));
        assertThrows(FileOpenException.class, () -> CrimesFileOp.cutFileToSize(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, 512L, false, true, true));
        assertThrows(CopyFileException.class, () -> CrimesFileOp.cutFileToSize(FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, FileOpsTest.NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_tmp", 512L, false, false, true));
    }
}