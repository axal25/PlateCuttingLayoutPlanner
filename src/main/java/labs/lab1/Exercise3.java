package labs.lab1;

import utils.ExceptionMessageGenerator;
import utils.lab1.FileInHandlers;
import utils.lab1.FileOps;
import utils.lab1.ParseOps;
import utils.lab1.exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// source file: https://data.cityofchicago.org/Administration-Finance/Current-Employee-Names-Salaries-and-Position-Title/xzkq-xp2w

public class Exercise3 {
    public final static String existingFilePath = CSVFiles.NOT_PACKAGED_RESOURCE_PATH;
    public final static String existingFileName = "Current_Employee_Names__Salaries__and_Position_Titles.csv";
    public final static String existingFullFilePath = existingFilePath + "/" + existingFileName;
    public final static int numberOfTopPayedEmployees = 15;
    public final static boolean doLoadingDots = false;
    public static final boolean doPrintMatchInfo = false;

    public static void main() throws FileOpenException, BufferedReaderCloseException, BufferedReaderOpenException, FileInHandlersException, FileReaderCloseException, IOException, FileReaderOpenException {
        System.out.println("Exercise3.main() \\/\\/\\/");

        File f_employees = FileOps.openExistingFile( existingFullFilePath );
        top_X_most_payed_employees(f_employees);

        System.out.println("Exercise3.main() /\\/\\/\\");
    }

    public static void top_X_most_payed_employees(File f_employees) throws IOException, BufferedReaderOpenException, BufferedReaderCloseException, FileInHandlersException, FileReaderCloseException, FileReaderOpenException {
        Employee topXemployees[];

        int name_column_numb = -1;
        int title_column_numb = -1;
        int department_column_numb = -1;
        int salary_column_numb = -1;

        String nameColumnPattern = "Name";
        FileInHandlers fileInHandlers = new FileInHandlers();
        fileInHandlers.open( f_employees );
        BufferedReader br_employees = fileInHandlers.bufferedReader;
        name_column_numb = Exercise1.find_matching_column_number(br_employees, nameColumnPattern);
        fileInHandlers.close();

        String titleColumnPattern = "Job Titles";
        fileInHandlers = new FileInHandlers();
        fileInHandlers.open( f_employees );
        br_employees = fileInHandlers.bufferedReader;
        title_column_numb = Exercise1.find_matching_column_number(br_employees, titleColumnPattern);
        fileInHandlers.close();

        String departmentColumnPattern = "Department";
        fileInHandlers = new FileInHandlers();
        fileInHandlers.open( f_employees );
        br_employees = fileInHandlers.bufferedReader;
        department_column_numb = Exercise1.find_matching_column_number(br_employees, departmentColumnPattern);
        fileInHandlers.close();

        String salaryColumnPattern = "Annual Salary";
        fileInHandlers = new FileInHandlers();
        fileInHandlers.open( f_employees );
        br_employees = fileInHandlers.bufferedReader;
        salary_column_numb = Exercise1.find_matching_column_number(br_employees, salaryColumnPattern);
        fileInHandlers.close();

        if( name_column_numb != -1 && title_column_numb != -1 && department_column_numb != -1 && salary_column_numb != -1)
        {
            System.out.println("Found column numberS of columns: " + nameColumnPattern + ", " + titleColumnPattern + ", " + departmentColumnPattern + ", " + salaryColumnPattern + " = " +
                    name_column_numb + "," + title_column_numb + "," + department_column_numb + "," + salary_column_numb + ".");

            topXemployees = gather_topX_payed_employees(f_employees, name_column_numb, title_column_numb, department_column_numb, salary_column_numb, numberOfTopPayedEmployees);
            if(topXemployees.length == numberOfTopPayedEmployees) // not doing anything because gather_top10_payed_employees always create 10 element array from the start
            {
                System.out.println("Successfully FOUND top " + numberOfTopPayedEmployees + " highest payed employees.");
                System.out.println("Top " + numberOfTopPayedEmployees + " payed employees:");
                Employee.print_tab(topXemployees);
            }
            else
            {
                System.out.println("Could NOT find top " + numberOfTopPayedEmployees + " highest payed employees.");
            }
        }
        else
        {
            System.out.println("Could NOT find number of one or more of the columns.");
            if( name_column_numb == -1 ) System.out.println("Could NOT find column number \"" + nameColumnPattern + "\".");
            if( title_column_numb == -1 ) System.out.println("Could NOT find column number \"" + titleColumnPattern + "\".");
            if( department_column_numb == -1 ) System.out.println("Could NOT find column number \"" + departmentColumnPattern + "\".");
            if( salary_column_numb == -1 ) System.out.println("Could NOT find column number \"" + salaryColumnPattern + "\".");
        }
    }

    public static Employee[] gather_topX_payed_employees(File f, int name_nr, int position_nr, int department_nr, int salary_nr, int amountOfEmployeesToFind) throws IOException, BufferedReaderOpenException, BufferedReaderCloseException, FileInHandlersException, FileReaderCloseException, FileReaderOpenException {
        String functionName = "gather_topX_payed_employees(BufferedReader br, int name_nr, int position_nr, int department_nr, int salary_nr)";
        int size = amountOfEmployeesToFind;
        Employee topX[] = new Employee[size];
        for(int i = 0; i < size; i++) {	topX[i] = new Employee();	}
        String buffer;
        Scanner current_line;
        int column_counter = 0;
        String current_column = null;

        Employee currentEmployee = null;
        double buffer_salary = -1;
        int loadingDotCounter = 0;

        FileInHandlers fileInHandlers = new FileInHandlers();
        fileInHandlers.open( f );
        BufferedReader br = fileInHandlers.bufferedReader;

        System.out.println(Exercise3.class.getName() + " >>> " + functionName + ": processing file.");
        try
        {
            buffer = br.readLine();
            while( buffer !=  null )
            {
                if( doLoadingDots ) {
                    loadingDotCounter++;
                    System.out.print(" . ");
                    if( loadingDotCounter % 20 == 0 ) System.out.println();
                }

                current_line = new Scanner(buffer);
                current_line.useDelimiter(",");
                column_counter = 0;
                currentEmployee = new Employee();
                while( current_line.hasNext() )
                {
                    current_column = current_line.next();
                    column_counter++;
                    if(column_counter == name_nr)
                    {
                        currentEmployee.name = current_column;
                        currentEmployee.name = currentEmployee.name + " " + current_line.next();
                        currentEmployee.name = currentEmployee.name.replace("\"", "");
                        if( doPrintMatchInfo ) System.err.print( "currentEmployee.name = " + currentEmployee.name + ", " );
                    }
                    if(column_counter == position_nr)
                    {
                        currentEmployee.position = current_column;
                        if( doPrintMatchInfo ) System.err.print( "currentEmployee.position = " + currentEmployee.position + ", " );
                    }
                    if(column_counter == department_nr)
                    {
                        currentEmployee.department = current_column;
                        if( doPrintMatchInfo ) System.err.print( "currentEmployee.department = " + currentEmployee.department + ", " );
                    }
                    if(column_counter == salary_nr)
                    {
                        current_column = current_column.replace("$", "");
                        if( current_column.isEmpty() || !ParseOps.isNumeric(current_column) ) {
                            currentEmployee.salary = 0;
                        }
                        else {
                            buffer_salary = Double.parseDouble(current_column);
                            currentEmployee.salary = (int) buffer_salary;
                        }
                        if( doPrintMatchInfo ) System.err.println( "currentEmployee.salary = " + currentEmployee.salary + "." );
                    }
                }
                try_push_new_employee(topX, currentEmployee);

                current_line.close();
                buffer = br.readLine();
            }
        }
        catch(Exception e)
        {
            String exceptionMessage = ExceptionMessageGenerator.getMessage(Exercise3.class.getName(), functionName, e);
            System.out.println(exceptionMessage);
        }
        System.out.println(".");
        fileInHandlers.close();

        return topX;
    }

    public static void try_push_new_employee(Employee[] topX, Employee current)
    {
        if( topX[topX.length-1].salary < current.salary )
        {
            topX[topX.length-1] = current;
        }
        Employee.sort_tab(topX);
    }
}
