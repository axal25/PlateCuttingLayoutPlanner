package ui.center.options.lab1.thready;

public class ThreadyOptionFactory {
    public static final String[] STRING_CODES = {
            "Task",
            "Runnable",
    };

    public static ThreadyOption getThreadyOption(String stringCode) throws Exception {
        if(ThreadyOptionFactory.STRING_CODES[0].compareTo(stringCode) == 0) return ThreadyOption.RUNNABLE;
        else if(ThreadyOptionFactory.STRING_CODES[1].compareTo(stringCode) == 0) return ThreadyOption.TASK;
        else throw new Exception("Unsupported String: \"" + stringCode + "\"");
    }
}
