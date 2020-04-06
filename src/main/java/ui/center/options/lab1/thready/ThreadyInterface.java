package ui.center.options.lab1.thready;

import javafx.application.Platform;
import labs.lab1.Exercise1;
import ui.center.options.lab1.Lab1UIFileShortController;
import ui.pop.up.ErrorPopUpUI;
import utils.lab1.FileOps;
import utils.lab1.exceptions.FileOpenException;
import utils.lab1.exceptions.GetFileSizeException;
import java.io.PrintStream;

public interface ThreadyInterface {
    public ThreadyReferenceCollection getThreadyReferenceCollection();

    default public void executeFileShortening() throws Exception {
        if (Platform.isFxApplicationThread()) throw new IllegalThreadStateException("Runnable can be run only on separate thread from UI Thread.");
        getThreadyReferenceCollection().getProgressBar().setVisible(true);
        Thread.sleep(getThreadyReferenceCollection().getFakeLoadingDelay());
        Long sizeToShortenTheFileTo = Lab1UIFileShortController.getAndValidateSizeToShortenFileToTextField(getThreadyReferenceCollection().getSizeToShortenFileToTextField());
        PrintStream out = new PrintStream(getThreadyReferenceCollection().getByteArrayOutputStream());
        Exercise1 exercise1 = new Exercise1(out);
        exercise1.shortenFileCrimes(sizeToShortenTheFileTo);
    }

    default public void handleSuccess() {
        if(!Platform.isFxApplicationThread()) throw new IllegalThreadStateException("Runnable can be run only on UI Thread.");
        try {
            getThreadyReferenceCollection().getCurrentFileSizeTextField().setText(String.valueOf(FileOps.getFileSizeBytes(getThreadyReferenceCollection().getExistingModifiableFullFilePath())));
            getThreadyReferenceCollection().getOutputTextArea().setText(getThreadyReferenceCollection().getByteArrayOutputStream().toString());
            getThreadyReferenceCollection().getProgressBar().setVisible(false);
        } catch (FileOpenException | GetFileSizeException exception) {
            handleExceptions(exception);
        }
    }

    default public void handleExceptions(final Exception exception) {
        if(!Platform.isFxApplicationThread()) throw new IllegalThreadStateException("Runnable can be run only on UI Thread.");
        if(getThreadyReferenceCollection().getByteArrayOutputStream() != null) {
            System.out.println(getThreadyReferenceCollection().getByteArrayOutputStream().toString());
            getThreadyReferenceCollection().getOutputTextArea().setText(getThreadyReferenceCollection().getByteArrayOutputStream().toString());
        }
        exception.printStackTrace();
        getThreadyReferenceCollection().getCurrentFileSizeTextField().setText("! ERROR !");
        ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", exception.getMessage());
        getThreadyReferenceCollection().getProgressBar().setVisible(false);
    }
}
