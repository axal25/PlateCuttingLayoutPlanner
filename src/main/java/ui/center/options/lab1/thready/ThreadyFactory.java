package ui.center.options.lab1.thready;

import ui.pop.up.ErrorPopUpUI;

public class ThreadyFactory {
    public static ThreadyInterface getThready(ThreadyOption threadyOption, ThreadyReferenceCollection threadyReferenceCollection) throws Exception {
        if(threadyOption == null) throw new NullPointerException("Parameter ThreadyOption threadyOption == null");
        switch (threadyOption) {
            case RUNNABLE:
                return new Runnable(threadyReferenceCollection);

            case TASK:
                return new Task(threadyReferenceCollection);

            default:
                throw new Exception("Unsupported CenterOption: " + threadyOption);
        }
    }
}
