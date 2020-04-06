package java8.func.lambda.example1;

public interface FunctionalInterface {
    void onlyAbstractFunction( int x );

    default void defaultFunction() {
        final String functionName = "defaultFunction()";
        final String location = LambdaExpressionExample1.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        System.out.println("FunctionalInterface fi;" + "\n\r" + "fi.defaultFunction();");

        utils.PrintSystem.outEnd( location );
    }
}
