package parser.exceptions;

import cutter.Solution;

public class NullSolutionException extends Exception {
    public NullSolutionException(Solution solution) {
        super(generateMessage(solution), new Throwable(generateMessage(solution)));
    }

    public static String generateMessage(Solution solution) {
        return String.format("Solution generated is null. %s", (solution == null) ? "Solution == null" : solution.toString(0));
    }
}
