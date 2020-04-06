package menu;

import recruitment.mayeryn.Mayeryn;
import utils.ExceptionMessageGenerator;

import java.util.Scanner;

public class Menu {
    public static final Option[] options = getOptions();
    public static final int exitOption = -1;

    public static void main() {
        menuLoop();
    }

    public static void printSelectOptionMessage() {
        System.out.println("Please select option ( from \"" + exitOption + "\" to \"" + (options.length-1) + "\" )" );
    }

    public static void printListOfOptions() {
        System.out.println("Option \"" + exitOption + "\". to EXIT menu loop");
        for (int i = 0; i < options.length; i++) {
            System.out.println( "Option \"" + i + "\". " + options[i].toString() );
        }
    }

    public static void printBadOptionMessage( String selectedOption ) {
        System.out.println("You've selected bad option. Your selected option was \"" + selectedOption + "\"");
    }

    public static void menuLoop() {
        Scanner scanner = new Scanner( System.in );
        while( true ) {
            printSelectOptionMessage();
            printListOfOptions();
            String string = scanner.next().trim();
            if( isInt( string ) ) {
                int optionNumber = Integer.parseInt( string );
                if( optionNumber == exitOption ) {
                    break;
                }
                if( optionNumber >= options.length || optionNumber < 0 ) printBadOptionMessage( string );
                else options[ optionNumber ].getOptionFunctionalInterface().runOption();
            }
            else {
                printBadOptionMessage( string );
            }

            scanner.nextLine();
        }
        scanner.close();
    }

    private static boolean isInt( String string ) {
        if( string == null || string.isEmpty() ) {
            return false;
        }
        else {
            try {
                System.out.println("param String string = " + string);
                Integer.parseInt(string);
                return true;
            }
            catch( Exception e ) {
                System.out.println( ExceptionMessageGenerator.getMessage(Menu.class.getName(), "isInt( String string )", e) );
                return false;
            }
        }
    }

    private static Option[] getOptions() {
        Option[] optionsToBe = new Option[4];

        // info.ToDo.printWhatIsLefttoDo();
        optionsToBe[0] = new Option(
                info.ToDo.class.getName(),
                "printWhatIsLeftToDo()",
                info.ToDo::printWhatIsLefttoDo
                );

        // java8.func.lambda.LambdaExamples.main();
        optionsToBe[2] = new Option(
                java8.func.lambda.LambdaExamples.class.getName(),
                "main()",
                java8.func.lambda.LambdaExamples::main
        );

        // recruitment.exercises.mayeryn.Mayeryn.main();
        optionsToBe[3] = new Option(
                Mayeryn.class.getName(),
                "main()",
                Mayeryn::main
        );

        return optionsToBe;
    }

    private static void differentWayOfSettingAnOption() {
        Option[] optionsToBe = new Option[1];

        // info.ToDo.printWhatIsLefttoDo();
        optionsToBe[0] = new Option();
        optionsToBe[0].setClassName( info.ToDo.class.getName() );
        optionsToBe[0].setFunctionName( "printWhatIsLeftToDo()" );
        /**
         *
         * OptionFunctionalInterface optionFunctionalInterface = null;
         * optionFunctionalInterface = info.ToDo::printWhatIsLeftToDo;
         * // optionFunctionalInterface = () -> { info.ToDo.printWhatIsLeftToDo(); };
         * optionsToBe[0].setOptionFunctionalInterface( optionFunctionalInterface );
         *
         **/
        optionsToBe[0].setOptionFunctionalInterface( info.ToDo::printWhatIsLefttoDo );

        // info.ToDo.printWhatIsLefttoDo();
        optionsToBe[0] = new Option(
                info.ToDo.class.getName(),
                "printWhatIsLeftToDo()",
                info.ToDo::printWhatIsLefttoDo
        );
    }
}
