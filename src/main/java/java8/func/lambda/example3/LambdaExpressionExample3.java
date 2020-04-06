package java8.func.lambda.example3;

public class LambdaExpressionExample3 {

    public interface FunctionalInterface3 {
        void printMessage( String message );
    }

    public static void main() {
        final String functionName = "main()";
        final String location = LambdaExpressionExample3.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        example1();
        example2();
        example3();

        utils.PrintSystem.outEnd( location );
    }

    public static void example1() {
        final String functionName = "example1()";
        final String location = LambdaExpressionExample3.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        FunctionalInterface1 functionalInterface1 = () -> {
            final String lambdaFunctionName = "FunctionalInterface1 functionalInterface1 = () -> {...};";
            final String lambdaLocation = FunctionalInterface1.class.getName() + " >>> " + lambdaFunctionName;
            utils.PrintSystem.outBegin( lambdaLocation );

            System.out.println( "void printLocation();" );

            utils.PrintSystem.outEnd( lambdaLocation );
        };

        functionalInterface1.printLocation();

        utils.PrintSystem.outEnd( location );
    }

    public static void example2() {
        final String functionName = "example2()";
        final String location = LambdaExpressionExample3.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        FunctionalInterface2 functionalInterface2Add = (int subject1, int subject2) -> subject1 + subject2;
        FunctionalInterface2 functionalInterface2Mul = (int subject1, int subject2) -> subject1 * subject2;
        int subject1 = 1;
        int subject2 = 2;
        int addResult1 = performOperation( subject1, subject2, functionalInterface2Add );
        int mulResult1 = performOperation( subject1, subject2, functionalInterface2Mul );
        System.out.println( "addResult1 = performOperation( " + subject1 + ", " + subject2 + ", functionalInterface2Add ) == " + addResult1 );
        System.out.println( "mulResult1 = performOperation( " + subject1 + ", " + subject2 + ", functionalInterface2Mul ) == " + mulResult1 );
        int addResult2 = functionalInterface2Add.operation( subject1, subject2 );
        int mulResult2 = functionalInterface2Mul.operation( subject1, subject2 );
        System.out.println( "addResult2 = functionalInterface2Add.operation( " + subject1 + ", " + subject2 + " ) == " + addResult2 );
        System.out.println( "mulResult2 = functionalInterface2Mul.operation( " + subject1 + ", " + subject2 + " ) == " + mulResult2 );

        utils.PrintSystem.outEnd( location );
    }

    /**
     * public interface FunctionalInterface2 {
     *     int operation( int subject1, int subject2 );
     * }
     *
     * @param subject1 - subject 1 of the operation - int operation( int subject1, int subject2 );
     * @param subject2 - subject 2 of the operation - int operation( int subject1, int subject2 );
     * @param functionalInterface2 - functional interface containing operation method
     * @return returns int result of the operation method
     */
    public static int performOperation(int subject1, int subject2, FunctionalInterface2 functionalInterface2) {
        return functionalInterface2.operation( subject1, subject2 );
    }

    public static void example3() {
        final String functionName = "example3()";
        final String location = LambdaExpressionExample3.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        FunctionalInterface3 functionalInterface3 = ( String message ) -> {
            final String lambdaFunctionName = "FunctionalInterface3 functionalInterface3 = ( String message ) -> {...};";
            final String lambdaLocation = FunctionalInterface1.class.getName() + " >>> " + lambdaFunctionName;
            utils.PrintSystem.outBegin( lambdaLocation );

            System.out.println( "Original (outter) implementation() " + message );

            utils.PrintSystem.outEnd( lambdaLocation );
        };

        LambdaExpressionExample3 lambdaExpressionExample3 = new LambdaExpressionExample3();
        String message = "{ A message from outter LambdaExpressionExample3 }";
        lambdaExpressionExample3.nonStaticPerformPrintMessage( message, functionalInterface3 );
        lambdaExpressionExample3.nonStaticPerformPrintMessageOverrideFunctionalInterface3Implementation( message, functionalInterface3 );

        utils.PrintSystem.outEnd( location );
    }

    public static String wrapInnerMessageAroundOutter(String outterMessage) {
        String innerMessage = "{ " + "\n\r" +
                "\t\t" + outterMessage + "\n\r" +
                "\t\t" + "A message from inner LambdaExpression" + "\n\r" +
                "}";
        return innerMessage;
    }

    public void nonStaticPerformPrintMessage( String message, FunctionalInterface3 functionalInterface3) {
        message = wrapInnerMessageAroundOutter( message );

        // WITHOUT overriding functionalInterface3 implementation

        functionalInterface3.printMessage( message );
    }

    public void nonStaticPerformPrintMessageOverrideFunctionalInterface3Implementation( String message, FunctionalInterface3 functionalInterface3) {
        message = wrapInnerMessageAroundOutter( message );

        // OVERRIDING functionalInterface3 implementation
        functionalInterface3 = ( String messageForImplementationScope ) -> {
            final String lambdaFunctionName = "\"Override\" functionalInterface3 = ( String messageForImplementationScope ) -> {...};";
            final String lambdaLocation = FunctionalInterface1.class.getName() + " >>> " + lambdaFunctionName;
            utils.PrintSystem.outBegin( lambdaLocation );

            System.out.println( "Overridden (inner) implementation() " + messageForImplementationScope );

            utils.PrintSystem.outEnd( lambdaLocation );
        };

        functionalInterface3.printMessage( message );
    }
}
