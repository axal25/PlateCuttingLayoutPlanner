package ui.center.options.lab1.thready;

import lombok.Getter;
import ui.center.options.lab1.Lab1UIFileShortController;

import java.io.ByteArrayOutputStream;

@Getter
public class Task extends javafx.concurrent.Task<ByteArrayOutputStream> implements ThreadyInterface {
    private final ThreadyReferenceCollection threadyReferenceCollection;

    private Task() {
        throw new UnsupportedOperationException("Not for use. This is Task<ByteArrayOutputStream> specially tailored for use in " + Lab1UIFileShortController.class.getName());
    }

    public Task(ThreadyReferenceCollection threadyReferenceCollection) {
        this.threadyReferenceCollection = threadyReferenceCollection;

        this.setOnSucceeded(workerStateEvent -> {
            handleSuccess();
        });

        this.setOnFailed(workerStateEvent -> {
            handleExceptions((Exception) this.getException());
        });
    }

    @Override
    public ByteArrayOutputStream call() throws Exception {
        this.executeFileShortening();
        return this.threadyReferenceCollection.getByteArrayOutputStream();
    }
}
