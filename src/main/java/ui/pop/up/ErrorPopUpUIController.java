package ui.pop.up;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
public class ErrorPopUpUIController implements Initializable {
    private ErrorPopUpUI errorPopUpUI = null;

    @FXML public JFXTextArea messageTextArea;

    public ErrorPopUpUIController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void handleOkButtonClick() {
        this.errorPopUpUI.getStage().close();
    }

    public void setMessageLabelText() {
        this.messageTextArea.setText(this.errorPopUpUI.getMessage());
    }
}
