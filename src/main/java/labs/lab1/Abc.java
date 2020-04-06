package labs.lab1;

import java.io.PrintStream;

public class Abc {
    // Hiding System variable
    private static MySystem System = new MySystem(null);

    public Abc(PrintStream printStream) {
        System = new MySystem(printStream);
    }

    public Abc clone() {
        System.out.println("abc.clone()");
        return new Abc(System.out);
    }
}