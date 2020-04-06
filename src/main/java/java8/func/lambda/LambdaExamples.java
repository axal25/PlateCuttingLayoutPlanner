package java8.func.lambda;

import java8.func.lambda.example1.LambdaExpressionExample1;
import java8.func.lambda.example2.LambdaExpressionExample2;
import java8.func.lambda.example3.LambdaExpressionExample3;

public class LambdaExamples {
    /**
     * Lambda expression - expresses instances of FUNCTIONAL INTERFACES
     *  - implement ONLY ONE abstract function - implement functional interfaces
     *
     * Functional interface - interface with 1 abstract method - example: java.lang.Runnable
     *
     * Functionality of LAMBDA EX:
     *  - treat functionality as method argument / treat code as data
     *  - creating function not belonging to any class
     *  - LAMBDA EX can be passed as if it was an object, and executed on demand
     */
    public static void main() {
        final String functionName = "main()";
        final String location = LambdaExamples.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        LambdaExpressionExample1.main();
        LambdaExpressionExample2.main();
        LambdaExpressionExample3.main();

        utils.PrintSystem.outEnd( location );
    }
}
