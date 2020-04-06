package labs.lab1;

import java.io.PrintStream;

public class MySystem {
    public static final PrintStream err = System.err;
    public PrintStream out;
    public MySystem(PrintStream out) { this.out = out; }

    public static String getProperty(String propertyName) {
        return System.getProperty(propertyName);
    }
}
