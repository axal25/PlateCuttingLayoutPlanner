package ui.center.options.lab1;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import labs.lab1.Exercise1;
import lombok.Getter;
import lombok.Setter;
import ui.center.options.lab1.thready.*;
import ui.pop.up.ErrorPopUpUI;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class Lab1UIFileShortController implements Initializable {
    public static final Long DEFAULT_SIZE_TO_SHORTEN_THE_FILE_TO = 1024L * 1024L;
    public static final Long FAKE_LOADING_DELAY = 500L;
    public static final String EXISTING_MODIFIABLE_FULL_FILE_PATH = Exercise1.EXISTING_NOT_PACKAGED_FULL_FILE_PATH;

    @FXML private HBox hBoxAroundProgressBar;
    @FXML private JFXProgressBar progressBar;
    @FXML private JFXTextField sizeToShortenFileToTextField;
    @FXML private JFXTextField currentFileSizeTextField;
    @FXML private JFXTextArea outputTextArea;
    @FXML private JFXTextArea sourceTextArea;
    @FXML private JFXButton shortenButton;
    @FXML private JFXComboBox threadyComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sizeToShortenFileToTextField.textProperty().addListener((obsVal, oldText, newText) -> handleChangeIn_SizeToShortenFileToTextField_Or_threadyComboBox());
        this.threadyComboBox.getSelectionModel().selectedIndexProperty().addListener((obsVal, oldIndex, newIndex) -> handleChangeIn_SizeToShortenFileToTextField_Or_threadyComboBox());
        this.sizeToShortenFileToTextField.setText(String.valueOf(DEFAULT_SIZE_TO_SHORTEN_THE_FILE_TO));
        this.sourceTextArea.setText(Exercise1.DISPLAY_TEXT[0]);
        this.hBoxAroundProgressBar.managedProperty().bind(this.progressBar.visibleProperty());
        this.progressBar.setVisible(false);
    }

    public void handleShortenButtonClick() {
        ThreadyReferenceCollection threadyReferenceCollection = new ThreadyReferenceCollection(
                this.progressBar,
                this.sizeToShortenFileToTextField,
                this.currentFileSizeTextField,
                this.outputTextArea,
                Lab1UIFileShortController.FAKE_LOADING_DELAY,
                Lab1UIFileShortController.EXISTING_MODIFIABLE_FULL_FILE_PATH
        );
        ThreadyOption threadyOption = getAndValidateSelectedThreadyOption();
        ThreadyInterface shortenFileCrimeThready = getAndValidateThready(threadyOption, threadyReferenceCollection);
        Thread thread = new Thread((java.lang.Runnable) shortenFileCrimeThready);
        thread.start();
    }

    public void handleChangeIn_SizeToShortenFileToTextField_Or_threadyComboBox() {
        try {
            getAndValidateSizeToShortenFileToTextField(this.sizeToShortenFileToTextField);
            validateSelectedThreadyComboBoxText();
            this.shortenButton.setDisable(false);
        } catch (Exception e) {
            this.shortenButton.setDisable(true);
        }
    }

    public static Long getAndValidateSizeToShortenFileToTextField(JFXTextField sizeToShortenFileToTextField) throws Exception {
        Double sizeToShortenFileToDouble = Double.parseDouble(sizeToShortenFileToTextField.getText());
        Long sizeToShortenTheFileToLong = sizeToShortenFileToDouble.longValue();
        if(sizeToShortenTheFileToLong < 0) throw new Exception("Size to shorten the file to can't be lower than 0.");
        return sizeToShortenTheFileToLong;
    }

    private void validateSelectedThreadyComboBoxText() throws Exception {
        if(this.threadyComboBox.getSelectionModel().isEmpty()) throw new Exception("Thready Interface implementation option must be chosen before running.");
    }

    private String getAndValidateSelectedThreadyComboBoxText() {
        try {
            validateSelectedThreadyComboBoxText();
            return ((Label) this.threadyComboBox.getSelectionModel().getSelectedItem()).getText();
        } catch (Exception e) {
            e.printStackTrace();
            this.currentFileSizeTextField.setText("! ERROR !");
            ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", e.getMessage());
            return null;
        }
    }

    private ThreadyOption getAndValidateSelectedThreadyOption() {
        try {
            ThreadyOption threadyOption = ThreadyOptionFactory.getThreadyOption(getAndValidateSelectedThreadyComboBoxText());
            return threadyOption;
        } catch (Exception e) {
            e.printStackTrace();
            this.currentFileSizeTextField.setText("! ERROR !");
            ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", e.getMessage());
            return null;
        }
    }

    public ThreadyInterface getAndValidateThready(ThreadyOption threadyOption, ThreadyReferenceCollection threadyReferenceCollection) {
        try {
            ThreadyInterface threadyInterface = ThreadyFactory.getThready(threadyOption, threadyReferenceCollection);
            return threadyInterface;
        } catch (Exception e) {
            e.printStackTrace();
            threadyReferenceCollection.getCurrentFileSizeTextField().setText("! ERROR !");
            ErrorPopUpUI errorPopUpUI = new ErrorPopUpUI("Error during shortening of file", e.getMessage());
            threadyReferenceCollection.getProgressBar().setVisible(false);
            return null;
        }
    }
}
