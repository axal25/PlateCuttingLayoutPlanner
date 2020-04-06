package utils.lab1;

import labs.lab1.CSVFiles;
import org.junit.jupiter.api.*;
import utils.lab1.exceptions.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileOpsTest {
    public final static String EXISTING_NOT_PACKAGED_RESOURCE_PATH = CSVFiles.NOT_PACKAGED_RESOURCE_PATH;
    public final static String EXISTING_PACKAGE = CSVFiles.PACKAGE;
    public final static String EXISTING_FILE_NAME = CSVFiles.EXISTING_FILE_NAME;
    /**
     * In CSVFiles class:
     * String EXISTING_NOT_PACKAGED_FULL_FILE_PATH =
     *      EXISTING_NOT_PACKAGED_RESOURCE_PATH + File.separator +
     *          EXISTING_PACKAGE + File.separator +
     *          EXISTING_FILE_NAME;
     */
    public final static String EXISTING_FULL_FILE_PATH = CSVFiles.EXISTING_NOT_PACKAGED_FULL_FILE_PATH;

    public final static String NOT_EXISTING_RESOURCE_PATH = CSVFiles.NOT_EXISTING_RESOURCE_PATH;
    public final static String NOT_EXISTING_FILE_NAME = CSVFiles.NOT_EXISTING_FILE_NAME;
    public final static String NOT_EXISTING_FULL_FILE_PATH = NOT_EXISTING_RESOURCE_PATH + File.separator + "not_" + EXISTING_PACKAGE + File.separator + NOT_EXISTING_FILE_NAME;

    public final static String EXISTING_PATH_NOT_EXISTING_FILE_FULL_PATH = EXISTING_NOT_PACKAGED_RESOURCE_PATH + File.separator + EXISTING_PACKAGE + File.separator + NOT_EXISTING_FILE_NAME;
    public final static String NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH = EXISTING_NOT_PACKAGED_RESOURCE_PATH + File.separator + EXISTING_PACKAGE + File.separator + "JustForTests_" + NOT_EXISTING_FILE_NAME;

    @BeforeEach
    void setUp() {
        try{
            FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        } catch(Exception e) {};

    }

    @AfterEach
    void tearDown() {
        try{
            FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        } catch(Exception e) {};
    }

    @Test
    @Order(1)
    void tryNewFile() throws FileOpenException {
        assertNotNull(FileOps.newFile(NOT_EXISTING_FULL_FILE_PATH));
        assertNotNull(FileOps.newFile(EXISTING_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.newFile(null));
        assertThrows(FileOpenException.class, () -> FileOps.newFile(""));
    }

    @Test
    @Order(2)
    void openExistingFile1() throws FileOpenException {
        assertNotNull(FileOps.openExistingFile(EXISTING_NOT_PACKAGED_RESOURCE_PATH + File.separator + EXISTING_PACKAGE, EXISTING_FILE_NAME));
        assertThrows(FileOpenException.class, () -> FileOps.openExistingFile(NOT_EXISTING_RESOURCE_PATH, NOT_EXISTING_FILE_NAME));
    }

    @Test
    @Order(3)
    void openExistingFile2() throws FileOpenException {
        assertNotNull(FileOps.openExistingFile(EXISTING_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.openExistingFile(NOT_EXISTING_FULL_FILE_PATH));
    }

    @Test
    @Order(4)
    void openNewFile1() throws FileOpenException {
        assertNotNull(FileOps.openNewFile(EXISTING_NOT_PACKAGED_RESOURCE_PATH, NOT_EXISTING_FILE_NAME));
        assertThrows(FileOpenException.class, () -> FileOps.openNewFile(NOT_EXISTING_RESOURCE_PATH, NOT_EXISTING_FILE_NAME));
        assertThrows(FileOpenException.class, () -> FileOps.openNewFile(EXISTING_NOT_PACKAGED_RESOURCE_PATH + File.separator + EXISTING_PACKAGE, EXISTING_FILE_NAME));
    }

    @Test
    @Order(5)
    void openNewFile2() throws FileOpenException {
        assertNotNull(FileOps.openNewFile(EXISTING_PATH_NOT_EXISTING_FILE_FULL_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.openNewFile(EXISTING_FULL_FILE_PATH));
    }

    @Test
    @Order(10)
    void getFileSizeBytes1() throws FileOpenException, GetFileSizeException {
        File existingFile = FileOps.openExistingFile(EXISTING_FULL_FILE_PATH);
        File notExistingFile = FileOps.openNewFile(EXISTING_PATH_NOT_EXISTING_FILE_FULL_PATH);

        assertNotEquals(-1, FileOps.getFileSizeBytes(existingFile));
        assertThrows(GetFileSizeException.class, () -> FileOps.getFileSizeBytes(notExistingFile));
        assertThrows(GetFileSizeException.class, () -> FileOps.getFileSizeBytes((File) null));
    }

    @Test
    @Order(11)
    void getFileSizeBytes2() throws GetFileSizeException, FileOpenException {
        assertNotEquals(-1, FileOps.getFileSizeBytes(EXISTING_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.getFileSizeBytes(NOT_EXISTING_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.getFileSizeBytes(""));
        assertThrows(FileOpenException.class, () -> FileOps.getFileSizeBytes((String) null));
    }

    @Test
    @Order(12)
    void sizeBytes2KiloB() {
        sizeBytes2KiloB(1024L);
        sizeBytes2KiloB(2048L);
        sizeBytes2KiloB(3072L);
        sizeBytes2KiloB(4096L);
    }

    void sizeBytes2KiloB(Long sizeInBytes) {
        Long expectedSizeInKiloB = (sizeInBytes/(1024L));
        Long actualSizeInKiloB = FileOps.sizeBytes2KiloB(sizeInBytes);
        org.hamcrest.MatcherAssert.assertThat(
                0L,
                org.hamcrest.Matchers.lessThan(actualSizeInKiloB)
        );
        assertTrue(actualSizeInKiloB > 0L);
        assertEquals(expectedSizeInKiloB, actualSizeInKiloB);
        assertEquals(-1, FileOps.sizeBytes2KiloB(-1L));
    }

    @Test
    @Order(13)
    void sizeBytes2MegaB() {
        sizeBytes2MegaB(1024L*1024L);
        sizeBytes2MegaB(2048L*1024L);
        sizeBytes2MegaB(3072L*1024L);
        sizeBytes2MegaB(4096L*1024L);
    }

    void sizeBytes2MegaB(Long sizeInBytes) {
        Long expectedSizeInMegaB = (sizeInBytes/(1024L*1024L));
        Long actualSizeInMegaB = FileOps.sizeBytes2MegaB(sizeInBytes);
        org.hamcrest.MatcherAssert.assertThat(
                0L,
                org.hamcrest.Matchers.lessThan(actualSizeInMegaB)
        );
        assertTrue(actualSizeInMegaB > 0L);
        assertEquals(expectedSizeInMegaB, actualSizeInMegaB);
        assertEquals(-1, FileOps.sizeBytes2MegaB(-1L));
    }

    @Test
    @Order(14)
    void sizeBytes2GigaB() {
        sizeBytes2GigaB(1024L*1024L*1024L);
        sizeBytes2GigaB(2048L*1024L*1024L);
        sizeBytes2GigaB(3072L*1024L*1024L);
        sizeBytes2GigaB(4096L*1024L*1024L);
    }

    void sizeBytes2GigaB(Long sizeInBytes) {
        Long expectedSizeInGigaB = (sizeInBytes/(1024L*1024L*1024L));
        Long actualSizeInGigaB = FileOps.sizeBytes2GigaB(sizeInBytes);
        assertEquals(expectedSizeInGigaB, actualSizeInGigaB);
        assertEquals(-1L, FileOps.sizeBytes2GigaB(-1L));
    }

    @Test
    @Order(22)
    void getStringSizeBytes() {
        assertEquals(13L, FileOps.getStringSizeBytes("Bla4bla8bla13"));
        assertEquals(-1L, FileOps.getStringSizeBytes(null));
        assertEquals(0L, FileOps.getStringSizeBytes(""));
    }

    @Test
    @Order(15)
    void getFilePath() {
        assertDoesNotThrow(() -> FileOps.verifyParentDirectoryPath(EXISTING_FULL_FILE_PATH));
        assertDoesNotThrow(() -> FileOps.verifyParentDirectoryPath(EXISTING_PATH_NOT_EXISTING_FILE_FULL_PATH));
        assertDoesNotThrow(() -> FileOps.verifyParentDirectoryPath(EXISTING_FILE_NAME));
        assertThrows(FileOpenException.class, () -> FileOps.verifyParentDirectoryPath(NOT_EXISTING_FULL_FILE_PATH));
    }

    @Test
    @Order(16)
    void isDirectory() {
        assertTrue(FileOps.isDirectory(EXISTING_NOT_PACKAGED_RESOURCE_PATH));
        assertFalse(FileOps.isDirectory(NOT_EXISTING_RESOURCE_PATH));
        assertFalse(FileOps.isDirectory(NOT_EXISTING_FULL_FILE_PATH));
    }

    @Test
    @Order(21)
    void deleteFile() throws FileOpenException, FileWriterCloseException, IOException, FileWriterOpenException {
        assertNotNull(FileOps.createFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
        assertTrue(FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(NOT_EXISTING_FULL_FILE_PATH));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(""));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(null));
    }

    @Test
    @Order(23)
    void renameFile1() throws FileOpenException, FileWriterCloseException, IOException, RenameFileException, FileWriterOpenException {
        final String renamedFullFilePath = NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_renamed1.txt";
        try{
            FileOps.deleteFile(renamedFullFilePath);
        } catch(FileOpenException e) {}
        File newFile = FileOps.createFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        assertNotNull(newFile);
        File renamedFile = FileOps.openNewFile(renamedFullFilePath);
        assertNotNull(renamedFile);
        File returnedRenamedFile = FileOps.renameFile(newFile, renamedFile);
        assertNotNull(returnedRenamedFile);
        assertTrue(FileOps.deleteFile(renamedFullFilePath));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
    }

    @Test
    @Order(24)
    void renameFile2() throws FileOpenException, FileWriterCloseException, IOException, RenameFileException, FileWriterOpenException {
        String renamedFullFilePath = NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH + "_renamed2.txt";
        try{
            FileOps.deleteFile(renamedFullFilePath);
        } catch(FileOpenException e) {}
        File file = FileOps.createFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH);
        assertNotNull(file);
        String returnedRenamedFullFilePath = FileOps.renameFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH, renamedFullFilePath);
        assertNotNull(returnedRenamedFullFilePath);
        assertTrue(FileOps.deleteFile(renamedFullFilePath));
        assertThrows(FileOpenException.class, () -> FileOps.deleteFile(NOT_EXISTING_CREATED_JUST_FOR_TESTS_FULL_FILE_PATH));
    }
}