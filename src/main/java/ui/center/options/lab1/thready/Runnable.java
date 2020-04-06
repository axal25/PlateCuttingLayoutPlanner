package ui.center.options.lab1.thready;

import javafx.application.Platform;
import lombok.Getter;
import ui.center.options.lab1.Lab1UIFileShortController;

@Getter
public class Runnable implements ThreadyInterface, java.lang.Runnable {
    private final ThreadyReferenceCollection threadyReferenceCollection;
    private RunnableLater runnableLater;
    private Exception exception;

    private Runnable() {
        throw new UnsupportedOperationException("Not for use. This is Runnable specially tailored for use in " + Lab1UIFileShortController.class.getName());
    }

    public Runnable(ThreadyReferenceCollection threadyReferenceCollection) {
        this.threadyReferenceCollection = threadyReferenceCollection;
        this.exception = null;
        this.runnableLater = null;
    }

    @Override
    public void run() {
        try {
            this.executeFileShortening();
        } catch (Exception e) {
            this.exception = e;
        }
        this.runnableLater = new RunnableLater(this);
        Platform.runLater(this.runnableLater);
    }

    public class RunnableLater implements java.lang.Runnable {
        final private Runnable owner;

        public RunnableLater(Runnable owner) {
            this.owner = owner;
        }

        @Override
        public void run() {
            if (this.owner.exception == null) {
                try {
                    this.owner.handleSuccess();
                } catch (Exception exception) {
                    this.owner.handleExceptions(exception);
                }
            } else {
                this.owner.handleExceptions(this.owner.exception);
            }
        }
    }
}
