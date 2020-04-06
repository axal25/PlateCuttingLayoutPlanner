package ui.center.options.lab1.thready;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;

@Getter
public class ThreadyReferenceCollection {
    private final JFXProgressBar progressBar;
    private final JFXTextField sizeToShortenFileToTextField;
    private final JFXTextField currentFileSizeTextField;
    private final JFXTextArea outputTextArea;
    private final Long fakeLoadingDelay;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final String existingModifiableFullFilePath;

    public ThreadyReferenceCollection(JFXProgressBar progressBar, JFXTextField sizeToShortenFileToTextField, JFXTextField currentFileSizeTextField, JFXTextArea outputTextArea, Long fakeLoadingDelay, String existingModifiableFullFilePath) {
        this.progressBar = progressBar;
        this.sizeToShortenFileToTextField = sizeToShortenFileToTextField;
        this.currentFileSizeTextField = currentFileSizeTextField;
        this.outputTextArea = outputTextArea;
        this.fakeLoadingDelay = fakeLoadingDelay;
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.existingModifiableFullFilePath = existingModifiableFullFilePath;
    }
}
