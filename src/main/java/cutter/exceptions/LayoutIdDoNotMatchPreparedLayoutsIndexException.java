package cutter.exceptions;

public class LayoutIdDoNotMatchPreparedLayoutsIndexException extends Exception {
    public LayoutIdDoNotMatchPreparedLayoutsIndexException(int newLayoutId, int preparedLayoutsIndex) {
        this(genMsg(newLayoutId, preparedLayoutsIndex));
    }

    public LayoutIdDoNotMatchPreparedLayoutsIndexException(String msg) {
        this(msg, null);
    }

    public LayoutIdDoNotMatchPreparedLayoutsIndexException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static String genMsg(int newLayoutIndex, int preparedLayoutsIndex) {
        return String.format(
                "%s%s%s",
                String.format("New prepared layout id do not match prepared layout array index.\r\n"),
                String.format("New prepared layout id: %d (starting from 0).\r\n", newLayoutIndex),
                String.format("Prepared layout's Index: %d (starting from 1).", preparedLayoutsIndex)
        );
    }
}
