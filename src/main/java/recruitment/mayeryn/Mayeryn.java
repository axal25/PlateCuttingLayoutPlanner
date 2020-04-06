package recruitment.mayeryn;

import recruitment.mayeryn.exercise1.Exercise1;
import recruitment.mayeryn.exercise2.Exercise2;
import recruitment.mayeryn.exercise3.Exercise3;

public class Mayeryn {
    public static void main() {
        final String functionName = "main()";
        final String location = Mayeryn.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        Exercise1.main();
        Exercise2.main();
        Exercise3.main();

        utils.PrintSystem.outEnd( location );
    }
}
