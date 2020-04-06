package java8.func.lambda.example1;

public class LambdaExpressionExample1 {
    public static void main() {
        final String functionName = "main()";
        final String location = LambdaExpressionExample1.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        FunctionalInterface functionalInterface = (int x) -> {
            final String lambdaFunctionName = "FunctionalInterface functionalInterface = (int x) -> {...};";
            final String lambdaLocation = LambdaExpressionExample1.class.getName() + " >>> " + lambdaFunctionName;
            utils.PrintSystem.outBegin( lambdaLocation );

            System.out.println( 2*x );

            utils.PrintSystem.outEnd( lambdaLocation );
        };

        functionalInterface.onlyAbstractFunction( 5 );
        functionalInterface.onlyAbstractFunction( -15 );
        functionalInterface.onlyAbstractFunction( 25 );
        functionalInterface.defaultFunction();

        utils.PrintSystem.outEnd( location );
    }
}
