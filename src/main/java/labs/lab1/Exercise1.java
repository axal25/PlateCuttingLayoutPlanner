package labs.lab1;

import labs.lab1.exceptions.CopyFileException;
import utils.ExceptionMessageGenerator;
import utils.lab1.BufferedReaderOps;
import utils.lab1.FileOps;
import utils.lab1.FileReaderOps;
import utils.lab1.exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Exercise1 {
    public static final String EXISTING_FILE_NAME = new StringBuilder()
            .append("exercise1_smaller_").append(CSVFiles.EXISTING_FILE_NAME).toString();
    public static final String EXISTING_PACKAGE = CSVFiles.PACKAGE;
    public static final String EXISTING_NOT_PACKAGED_RESOURCE_PATH = CSVFiles.NOT_PACKAGED_RESOURCE_PATH;
    public static final String EXISTING_NOT_PACKAGED_FULL_FILE_PATH = new StringBuilder().
            append(EXISTING_NOT_PACKAGED_RESOURCE_PATH).append(File.separator)
            .append(EXISTING_PACKAGE).append(File.separator)
            .append(EXISTING_FILE_NAME).toString();
    public static final String[] DISPLAY_TEXT = {
            "    public static void shortenFileCrimes(Long sizeLimitInBytes) throws GetFileSizeException, FileOpenException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, CopyFileException, RenameFileException, FileWriterOpenException, FileReaderOpenException {\n" +
                    "        CSVFiles.makeSureUntouchableSourceFileIsRightSize();\n" +
                    "        try {\n" +
                    "            FileOps.deleteFile(EXISTING_NOT_PACKAGED_FULL_FILE_PATH);\n" +
                    "        } catch(Exception e) {}\n" +
                    "        final String untouchableSource_FullFilePath = CSVFiles.EXISTING_NOT_PACKAGED_FULL_FILE_PATH;\n" +
                    "        final String modifiableSource_FileName = EXISTING_FILE_NAME;\n" +
                    "        final String modifiableSource_FullFilePath = new StringBuilder()\n" +
                    "                .append(EXISTING_NOT_PACKAGED_RESOURCE_PATH).append(File.separator)\n" +
                    "                .append(EXISTING_PACKAGE).append(File.separator).append(modifiableSource_FileName).toString();\n" +
                    "        // just copies the file\n" +
                    "        CrimesFileOp.cutFileToSize(\n" +
                    "                untouchableSource_FullFilePath,\n" +
                    "                modifiableSource_FullFilePath,\n" +
                    "                Long.MAX_VALUE,\n" +
                    "                false,\n" +
                    "                false,\n" +
                    "                false\n" +
                    "        );\n" +
                    "        Long fileSizeBytes = FileOps.getFileSizeBytes(modifiableSource_FullFilePath);\n" +
                    "        System.out.println( \"file size \\\"\" + modifiableSource_FileName + \"\\\"(B)  = \" + fileSizeBytes );\n" +
                    "        Long fileSizeKiloB = FileOps.sizeBytes2KiloB( fileSizeBytes );\n" +
                    "        System.out.println( \"file size \\\"\" + modifiableSource_FileName + \"\\\"(KB)  = \" + fileSizeKiloB );\n" +
                    "        Long fileSizeMegaB = FileOps.sizeBytes2MegaB( fileSizeBytes );\n" +
                    "        System.out.println( \"file size \\\"\" + modifiableSource_FileName + \"\\\"(MB)  = \" + fileSizeMegaB );\n" +
                    "        Long fileSizeGigaB = FileOps.sizeBytes2GigaB( fileSizeBytes );\n" +
                    "        System.out.println( \"file size \\\"\" + modifiableSource_FileName + \"\\\"(GB)  = \" + fileSizeGigaB );\n" +
                    "        // modifies the copied file\n" +
                    "        CrimesFileOp.cutFileToSize(\n" +
                    "                modifiableSource_FullFilePath,\n" +
                    "                modifiableSource_FullFilePath + \"_tmp\",\n" +
                    "                sizeLimitInBytes,\n" +
                    "                false,\n" +
                    "                true,\n" +
                    "                true\n" +
                    "        );\n" +
                    "        System.out.println(\n" +
                    "                \"Changed file: \\n\\r\" +\n" +
                    "                        \"\\\"\" + modifiableSource_FullFilePath + \"\\\" \\n\\r\" +\n" +
                    "                        \"so its size would be equal to \" + sizeLimitInBytes + \" Bytes \\n\\r\" +\n" +
                    "                        \"\\t\" + \"its real size is \" + FileOps.getFileSizeBytes(modifiableSource_FullFilePath) + \" Bytes\"\n" +
                    "        );\n" +
                    "    }"
    };

    public static final boolean doPrintMatchInfo = false;
    public static MySystem System = new MySystem(java.lang.System.out);

    public Exercise1(PrintStream out) {
        System = new MySystem(out);
    }

    public static void main() throws FileOpenException, BufferedReaderCloseException, FileReaderCloseException, IOException, BufferedReaderOpenException, FileReaderOpenException {
        System.out.println("Excercise1.main() \\/\\/\\/");

        System.out.println("Workspace project path (absolute path) [System.getProperty(\"user.dir\")]: " + System.getProperty("user.dir"));

        System.out.println("Should equal to something like: \t\t" +
                "\"/home/jackdaeel/IdeaProjects/JavaProjects/JavaLaboratories\"");

        /** Relative path **/
        // ../../../resources/csv/  Crimes_-_2001_to_present.csv
        //                          Current_Employee_Names__Salaries__and_Position_Titles.csv

        File f_crimes = null;

        f_crimes = FileOps.openExistingFile(CSVFiles.EXISTING_NOT_PACKAGED_FULL_FILE_PATH);
        f_crimes = FileOps.openExistingFile(CSVFiles.NOT_PACKAGED_RESOURCE_PATH, CSVFiles.EXISTING_FILE_NAME);

        int number_of_GTA = count_GTA_in_crimes(f_crimes);
        System.out.println("Count of \"Grand Theft Auto\" crimes = " + number_of_GTA );
        System.out.println("Excercise1.main() /\\/\\/\\");
    }

    public static void shortenFileCrimes(Long sizeLimitInBytes) throws GetFileSizeException, FileOpenException, BufferedWriterOpenException, IOException, BufferedReaderOpenException, CopyFileException, RenameFileException, FileWriterOpenException, FileReaderOpenException {
        CSVFiles.makeSureUntouchableSourceFileIsRightSize();
        System.out.println("CSVFiles.isNotPackaged(): " + CSVFiles.isNotPackaged() + " | " + (CSVFiles.isNotPackaged()?"it is not packaged":" it is packaged"));
        try {
            FileOps.deleteFile(EXISTING_NOT_PACKAGED_FULL_FILE_PATH);
        } catch(Exception e) {}
        final String untouchableSource_FullFilePath = CSVFiles.EXISTING_NOT_PACKAGED_FULL_FILE_PATH;
        final String modifiableSource_FileName = EXISTING_FILE_NAME;
        final String modifiableSource_FullFilePath = new StringBuilder()
                .append(EXISTING_NOT_PACKAGED_RESOURCE_PATH).append(File.separator)
                .append(EXISTING_PACKAGE).append(File.separator).append(modifiableSource_FileName).toString();
        // just copies the file
        CrimesFileOp.cutFileToSize(
                untouchableSource_FullFilePath,
                modifiableSource_FullFilePath,
                Long.MAX_VALUE,
                false,
                false,
                false
        );
        Long fileSizeBytes = FileOps.getFileSizeBytes(modifiableSource_FullFilePath);
        System.out.println( "file size \"" + modifiableSource_FileName + "\"(B)  = " + fileSizeBytes );
        Long fileSizeKiloB = FileOps.sizeBytes2KiloB( fileSizeBytes );
        System.out.println( "file size \"" + modifiableSource_FileName + "\"(KB)  = " + fileSizeKiloB );
        Long fileSizeMegaB = FileOps.sizeBytes2MegaB( fileSizeBytes );
        System.out.println( "file size \"" + modifiableSource_FileName + "\"(MB)  = " + fileSizeMegaB );
        Long fileSizeGigaB = FileOps.sizeBytes2GigaB( fileSizeBytes );
        System.out.println( "file size \"" + modifiableSource_FileName + "\"(GB)  = " + fileSizeGigaB );
        // modifies the copied file
        CrimesFileOp.cutFileToSize(
                modifiableSource_FullFilePath,
                modifiableSource_FullFilePath + "_tmp",
                sizeLimitInBytes,
                false,
                true,
                true
        );
        System.out.println(
                "Changed file: \n\r" +
                        "\"" + modifiableSource_FullFilePath + "\" \n\r" +
                        "so its size would be equal to " + sizeLimitInBytes + " Bytes \n\r" +
                        "\t" + "its real size is " + FileOps.getFileSizeBytes(modifiableSource_FullFilePath) + " Bytes"
        );
    }

    public static int count_GTA_in_crimes(File f_crimes) throws FileReaderCloseException, IOException, BufferedReaderCloseException, BufferedReaderOpenException, FileReaderOpenException {
        int number_of_GTA = 0;
        int crime_type_column_numb = 0;

        java.io.FileReader fr_crimes = null;
        fr_crimes = FileReaderOps.openFileReader(f_crimes);

        BufferedReader br_crimes = null;
        br_crimes = BufferedReaderOps.openBufferedReader( fr_crimes );

        crime_type_column_numb = find_matching_column_number(br_crimes, "Primary Type");
        if( crime_type_column_numb != -1 )
        {
            System.out.println("Found column number of \"Primary Type\" = "+crime_type_column_numb+".");
            number_of_GTA = count_matching_patterns_in_column_X(br_crimes, "MOTOR VEHICLE THEFT", crime_type_column_numb);
            System.out.println("Number of \"MOTOR VIHICLE THEFT\" = "+number_of_GTA+".");
        }
        else
        {
            System.out.println("Cound not find column number of \"Primary Type\"");
        }

        BufferedReaderOps.closeBufferedReader( br_crimes );
        FileReaderOps.closeFileReader( fr_crimes );

        return number_of_GTA;
    }

    public static int find_matching_column_number(BufferedReader br_crimes, String pattern)
    {
        String functionName = "find_matching_column_number(BufferedReader br_crimes, String pattern)";
        int columnNumberToReturn = -1;
        int current_column = 1;
        String new_column = null;
        try
        {
            Scanner line = new Scanner(br_crimes.readLine());
            line.useDelimiter(",");
            while(line.hasNext())
            {
                new_column = line.next();
                if( new_column.compareTo(pattern) == 0 )
                {
                    columnNumberToReturn = current_column;
                }
                current_column++;
            }
        }
        catch(Exception e)
        {
            String exceptionMessage = ExceptionMessageGenerator.getMessage(Exercise1.class.getName(), functionName, e);
            System.out.println(exceptionMessage);
            return -1;
        }

        return columnNumberToReturn;
    }

    public static int count_matching_patterns_in_column_X(BufferedReader br_crimes, String pattern, int column_number)
    {
        String functionName = "count_matching_patterns_in_column_X(BufferedReader br_crimes, String pattern, int column_number)";
        int crimeCounter = 0;
        Scanner current_line = null; //Obecnie przetwarzany wiersz
        String buffer =  null; //Buffer, potrzebny, by current_line = new Scanner( null ) nie wywalal Exception
        int column_counter = 0; //Licznik kolumny w wierszu (porownuje ten licznyk z numerem columny podanym w argumencie metody)
        String current_column = null; //Przechowuje zawartosc obecnie przetwarzanej kolumny (w danym wierszu)
        String stringRegExpPattern = new StringBuilder().append("(.)*").append(pattern).append("(.)*").toString();
        Pattern regExpPattern = Pattern.compile( stringRegExpPattern );
        try
        {
            buffer = br_crimes.readLine();
            while( buffer !=  null )
            {
                current_line = new Scanner(buffer);
                current_line.useDelimiter( Pattern.compile(
                        "(?x)" +                        // ignore white spaces in regex
                        "," +                           // Comma char == ','
                                "(?=" +                 // Followed by - Positive lookahead - Next regex matches
                                "   (?:" +              // Non-capture group
                                "       [^\"]*" +     // 0 or more, NON-quote chars
                                "       \"" +         // 1 quote char
                                "       [^\"]*" +     // 0 or more, NON-quote chars
                                "       \"" +         // 1 quote char
                                "   )*" +               // 0 or more, repetitions of, chain of NON-quote and (even amount of) quote chars
                                "   [^\"]*" +         // 0 or more, NON-quote chars
                                "   $" +                // Followed by END - necessary - every comma would satisfy the condition
                                ")"
                ) );
                // https://regex101.com/r/lS5tT3/243
                // source: https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
                column_counter = 0;
                while( current_line.hasNext() )
                {
                    current_column = current_line.next();
                    column_counter++;
                    if(column_counter == column_number)
                    {
                        if( doPrintMatchInfo ) System.err.print("current_column = \"" + current_column + "\" vs. pattern = \"" + pattern + "\"" );
                        if( regExpPattern.matcher( current_column ).matches() )
                        {
                            if( doPrintMatchInfo ) System.err.println(" +++ MATCHED");
                            crimeCounter++;
                        }
                        else {
                            if( doPrintMatchInfo ) System.err.println(" --- NOT matched");
                        }
                    }

                }
                current_line.close();
                buffer = br_crimes.readLine();
            }
        }
        catch(Exception e)
        {
            String exceptionMessage = ExceptionMessageGenerator.getMessage(Exercise1.class.getName(), functionName, e);
            System.out.println(exceptionMessage);
        }

        return crimeCounter;
    }
}
