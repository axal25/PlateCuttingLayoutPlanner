package ui.center.options.lab1;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import labs.lab1.Introduction;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
public class Lab1UIIntroController implements Initializable {
    @FXML private ScrollPane scrollPane;
    @FXML private TextArea sourceTextArea1, outputTextArea1, sourceTextArea2, outputTextArea2, sourceTextArea3, outputTextArea3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFields();
    }

    private void initializeFields() {
        initializeOutputFields();
        initializeSourceFields();
    }

    private void initializeOutputFields() {
        Task<ByteArrayOutputStream[]> initializeOutputFieldsTask = new Task<ByteArrayOutputStream[]>() {
            @Override
            protected ByteArrayOutputStream[] call() throws Exception {
                final int length = 3;
                ByteArrayOutputStream[] byteArrayOutputStreams = new ByteArrayOutputStream[length];
                PrintStream[] printStreams = new PrintStream[length];
                Introduction[] introductions = new Introduction[length];
                for (int i = 0; i < length; i++) {
                    byteArrayOutputStreams[i] = new ByteArrayOutputStream();
                    printStreams[i] = new PrintStream(byteArrayOutputStreams[i]);
                    introductions[i] = new Introduction(printStreams[i]);
                    /**
                     * Inside Introduction there is a variable: private static MySystem System
                     * It is hiding inside the scope of the class System.out viable needed to use System.out.println
                     * Because MySystem System inside Introduction is static we need to run xDemonstration
                     * one by one, just after Introduction variable construction
                     * if we did that outside of the loop introductions[0].System, introductions[1].System
                     * would be equal to introductions[2].System
                     * and so every message from introductions[0] and introductions[1]
                     * would be written to out[2] just like introductions[2]
                     * and not like we intended - respectively to out[0] and out[1]
                     */
                    if(i == 0) introductions[0].firstDemonstration();
                    else if(i == 1) introductions[1].secondDemonstration();
                    else if(i == 2) introductions[2].thirdDemonstration();
                    else throw new UnsupportedOperationException("Unsupported demonstration in Introduction - introductions[" + i + "] - where i: " + i + ".");
                }

                return byteArrayOutputStreams;
            }
        };

        initializeOutputFieldsTask.setOnSucceeded(workerStateEvent -> {
            this.outputTextArea1.setText(initializeOutputFieldsTask.getValue()[0].toString());
            this.outputTextArea2.setText(initializeOutputFieldsTask.getValue()[1].toString());
            this.outputTextArea3.setText(initializeOutputFieldsTask.getValue()[2].toString());
        });

        initializeOutputFieldsTask.setOnFailed(workerStateEvent -> {
            initializeOutputFieldsTask.getException().printStackTrace();
            this.outputTextArea1.setText(initializeOutputFieldsTask.getException().getStackTrace().toString());
            this.outputTextArea2.setText(initializeOutputFieldsTask.getException().getStackTrace().toString());
            this.outputTextArea3.setText(initializeOutputFieldsTask.getException().getStackTrace().toString());
        });

        Thread initializeOutputFieldsTaskThread = new Thread(initializeOutputFieldsTask);
        initializeOutputFieldsTaskThread.start();
    }

    private void initializeSourceFields() {
        initializeSourceField(this.sourceTextArea1, 0);
        initializeSourceField(this.sourceTextArea2, 1);
        initializeSourceField(this.sourceTextArea3, 2);
    }

    private void initializeSourceField(TextArea textArea, int index) {
        Task<String> initializeSourceFieldTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                return Introduction.DISPLAY_TEXT[index];
            }
        };

        initializeSourceFieldTask.setOnSucceeded(workerStateEvent -> {
            textArea.setText(initializeSourceFieldTask.getValue());
        });

        initializeSourceFieldTask.setOnFailed(workerStateEvent -> {
            initializeSourceFieldTask.getException().printStackTrace();
            textArea.setText(initializeSourceFieldTask.getException().getStackTrace().toString());
        });

        Thread initializeSourceFieldTaskThread = new Thread(initializeSourceFieldTask);
        initializeSourceFieldTaskThread.start();
    }
}
