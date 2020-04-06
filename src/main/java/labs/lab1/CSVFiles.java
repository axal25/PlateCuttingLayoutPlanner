package labs.lab1;

import labs.lab1.exceptions.CopyFileException;
import main.AppMain;
import org.junit.jupiter.api.Assertions;
import utils.lab1.FileOps;
import utils.lab1.exceptions.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CSVFiles {
    // file source: https://data.cityofchicago.org/Public-Safety/Crimes-2001-to-present/ijzp-q8t2
    public final static Long SIZE_LIMIT_IN_BYTES = 5L * 1025L * 1024L;
    public final static String NOT_PACKAGED_RESOURCE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    public final static String PACKAGE = "csv";
    public final static String EXISTING_FILE_NAME = "Crimes_-_2001_to_present.csv";
    public final static String EXISTING_NOT_PACKAGED_FULL_FILE_PATH = NOT_PACKAGED_RESOURCE_PATH + File.separator + PACKAGE + File.separator + EXISTING_FILE_NAME;
    public final static String EXISTING_PACKAGED_FULL_FILE_PATH = URLDecoder.decode(AppMain.class.getResource(File.separator + PACKAGE + File.separator + EXISTING_FILE_NAME).getFile(), StandardCharsets.UTF_8);

    public final static String NOT_EXISTING_RESOURCE_PATH = "NOT/existing/path/to";
    public final static String NOT_EXISTING_FILE_NAME = "notExistingFile.txt";

    public static void main() throws FileReaderCloseException, RenameFileException, BufferedWriterOpenException, GetFileSizeException, IOException, BufferedReaderOpenException, CopyFileException, BufferedReaderCloseException, FileWriterCloseException, FileOpenException, BufferedWriterCloseException, FileInHandlersException, FileWriterOpenException, FileReaderOpenException {
        final String functionName = "main()";
        final String location = CSVFiles.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        makeSureUntouchableSourceFileIsRightSize();
        Exercise1.main();
        Exercise2.main();
        Exercise3.main();

        utils.PrintSystem.outEnd( location );
    }

    public static boolean isNotPackaged() {
        return isNotPackaged1() || isNotPackaged2() || isNotPackaged3();
    }

    private static boolean isNotPackaged1() {
        final String notPackagedCsvFileFullPath = new StringBuilder()
            .append(System.getProperty("user.dir")).append(File.separator)
            .append("target").append(File.separator)
            .append("classes").append(File.separator)
            .append(PACKAGE).append(File.separator)
            .append(EXISTING_FILE_NAME).toString();
        try {
            URL availableCsvFileFullPathUrl = AppMain.class.getResource(new StringBuilder().append(File.separator).append(PACKAGE)
                    .append(File.separator).append(EXISTING_FILE_NAME).toString());
            File availableCsvFileFullPathFile = new File(availableCsvFileFullPathUrl.toURI());
            final String availableCsvFileFullPath = availableCsvFileFullPathFile.getAbsolutePath(); // potrzebne ?
            FileOps.openExistingFile(availableCsvFileFullPath);
//            Assertions.assertEquals(notPackagedCsvFileFullPath, availableCsvFileFullPath);
            if(notPackagedCsvFileFullPath.compareTo(availableCsvFileFullPath) == 0) {
                System.out.println("1 notPackagedCsvFileFullPath == availableCsvFileFullPath == " + availableCsvFileFullPath);
                return true;
            }
            System.out.println("1 notPackagedCsvFileFullPath != availableCsvFileFullPath == " + availableCsvFileFullPath);
            return false;
        } catch (NullPointerException | URISyntaxException | IllegalArgumentException | FileOpenException e) {
            System.out.println("1 NOT FOUND");
            return false;
        }
    }

    private static boolean isNotPackaged2() {
        final String notPackagedCsvFileFullPath = new StringBuilder()
                .append(System.getProperty("user.dir")).append(File.separator)
                .append("target").append(File.separator)
                .append("classes").toString();
        try {
            URL availableCsvFileFullPathUrl = AppMain.class.getProtectionDomain().getCodeSource().getLocation();
            File availableCsvFileFullPathFile = new File(availableCsvFileFullPathUrl.toURI());
            String availableCsvFileFullPath = availableCsvFileFullPathFile.getAbsolutePath(); // potrzebne ?
            availableCsvFileFullPath = FileOps.openExistingFile(availableCsvFileFullPath).getAbsolutePath();
//            Assertions.assertEquals(notPackagedCsvFileFullPath, availableCsvFileFullPath);
            if(notPackagedCsvFileFullPath.compareTo(availableCsvFileFullPath) == 0) {
                System.out.println("2 notPackagedCsvFileFullPath == availableCsvFileFullPath == " + availableCsvFileFullPath);
                return true;
            }
            System.out.println("2 notPackagedCsvFileFullPath != availableCsvFileFullPath == " + availableCsvFileFullPath);
            availableCsvFileFullPath = new StringBuilder().append(availableCsvFileFullPath).append(File.separator)
                    .append(PACKAGE).append(File.separator)
                    .append(EXISTING_FILE_NAME).toString();
            System.out.println("2 availableCsvFileFullPath: " + availableCsvFileFullPath);
            availableCsvFileFullPathFile = FileOps.openExistingFile(availableCsvFileFullPath);
            System.out.println("2 opened file" +
                    " | availableCsvFileFullPath: " + availableCsvFileFullPath +
                    " | availableCsvFileFullPathFile.getAbsolutePath(): " + availableCsvFileFullPathFile.getAbsolutePath());
            return false;
        } catch (NullPointerException | URISyntaxException | IllegalArgumentException | FileOpenException | SecurityException e) {
            System.out.println("2 NOT FOUND");
            return false;
        }
    }

    private static boolean isNotPackaged3() {
        InputStream inputStream = AppMain.class.getResourceAsStream("/file.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return false;
    }

    public static void makeSureUntouchableSourceFileIsRightSize() throws GetFileSizeException, FileReaderOpenException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, RenameFileException, FileOpenException, FileWriterOpenException, CopyFileException {
        if(isNotPackaged()) CrimesFileOp.cutFileToSize(EXISTING_NOT_PACKAGED_FULL_FILE_PATH, EXISTING_NOT_PACKAGED_FULL_FILE_PATH + "_tmp", SIZE_LIMIT_IN_BYTES, false, true, true);
        else CrimesFileOp.cutFileToSize(EXISTING_PACKAGED_FULL_FILE_PATH, EXISTING_PACKAGED_FULL_FILE_PATH + "_tmp", SIZE_LIMIT_IN_BYTES, false, true, true);
    }
}