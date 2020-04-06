package recruitment.mayeryn.exercise3;

import recruitment.mayeryn.exercise3.part1.Part1;
import recruitment.mayeryn.exercise3.part2.Part2;

public class Exercise3 {
    public static void main() {
        final String functionName = "main()";
        final String location = Exercise3.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        System.out.println("Polymorphism and Inheritance");
        Part1.main();
        Part2.main();

        utils.PrintSystem.outEnd( location );
    }
}
