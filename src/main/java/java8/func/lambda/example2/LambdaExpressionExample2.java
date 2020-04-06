package java8.func.lambda.example2;

import java.util.ArrayList;

public class LambdaExpressionExample2 {
    public static int integerArrayListSize = 10;

    public static void main() {
        final String functionName = "main()";
        final String location = LambdaExpressionExample2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        ArrayList<Integer> arrayList = getIntegerArrayList( integerArrayListSize );
        printIntegerArrayList( arrayList );
        example1( arrayList );
        example2( arrayList );

        utils.PrintSystem.outEnd( location );
    }

    public static void example1( ArrayList<Integer> arrayList ) {
        final String functionName = "example1( int arraySize )";
        final String location = LambdaExpressionExample2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        arrayList.forEach( arrayCell -> System.out.print(arrayCell + " ") );
        System.out.println();

        utils.PrintSystem.outEnd( location );
    }

    public static void example2( ArrayList<Integer> arrayList ) {
        final String functionName = "example2( int arraySize )";
        final String location = LambdaExpressionExample2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        arrayList.forEach( arrayCell -> { if(arrayCell%2 == 0) System.out.print(arrayCell + " "); } );
        System.out.println();

        utils.PrintSystem.outEnd( location );
    }

    public static ArrayList getIntegerArrayList(int arraySize ) {
        final String functionName = "@NotNull getArrayList(int arraySize )";
        final String location = LambdaExpressionExample2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < arraySize; i++) {
            arrayList.add( i+10 );
        }

        utils.PrintSystem.outEnd( location );
        return arrayList;
    }

    public static void printIntegerArrayList(ArrayList<Integer> arrayList ) {
        final String functionName = "printIntegerArrayList(@NotNull ArrayList<Integer> arrayList )";
        final String location = LambdaExpressionExample2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        System.out.print("ArrayList<Integer> arrayList = [ ");
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print( arrayList.get(i) );
            if( i < arrayList.size()-1 ) System.out.print(", ");
        }
        System.out.println(" ]");

        utils.PrintSystem.outEnd( location );
    }
}
