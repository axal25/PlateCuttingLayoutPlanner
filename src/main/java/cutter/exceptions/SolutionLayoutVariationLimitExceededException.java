package cutter.exceptions;

import sheet.Layout;

public class SolutionLayoutVariationLimitExceededException extends Exception {
    public SolutionLayoutVariationLimitExceededException(int newLayoutNumber, Layout[] preparedLayouts) {
        this(genMsg(newLayoutNumber, preparedLayouts));
    }

    public SolutionLayoutVariationLimitExceededException(String msg) {
        this(msg, null);
    }

    public SolutionLayoutVariationLimitExceededException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static String genMsg(int newLayoutNumber, Layout[] preparedLayouts) {
        return String.format(
                "%s%s%s",
                String.format("Solution wants to generate additional Layout (LayoutVariation) over the allowed limit.\r\n"),
                String.format("Solution wants to generate Layout number: %d (starting from 0).\r\n", newLayoutNumber),
                String.format("Layout amount limit: %d (starting from 1).", preparedLayouts.length)
        );
    }
}
