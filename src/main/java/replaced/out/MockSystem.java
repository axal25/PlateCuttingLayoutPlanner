package replaced.out;

import java.io.PrintStream;

public class MockSystem {
    public static final PrintStream err = java.lang.System.err;
    public static PrintStream out = null;

    public MockSystem(PrintStream out) {
        MockSystem.out = out;
    }

    public static String getProperty(String propertyName) {
        return java.lang.System.getProperty(propertyName);
    }
}
