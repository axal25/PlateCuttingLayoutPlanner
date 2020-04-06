package parser;

import cutter.Solution;

public class OutputComposer {
    public static String getOutputString(Solution solution) {
        return (solution == null) ? "solution == null" : solution.toString(0);
    }
}
