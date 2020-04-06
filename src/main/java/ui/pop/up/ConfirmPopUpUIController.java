package ui.pop.up;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
@Getter
public class ConfirmPopUpUIController implements Initializable {
    private ConfirmPopUpUI confirmPopUpUI = null;

    @FXML public Label messageLabel;

    public ConfirmPopUpUIController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void handleConfirmButtonClick() {
        this.confirmPopUpUI.setAnswer(true);
        this.confirmPopUpUI.getStage().close();
    }

    public void handleCancelButtonClick() {
        this.confirmPopUpUI.setAnswer(false);
        this.confirmPopUpUI.getStage().close();
    }

    public void setMessageLabelText() {
        this.messageLabel.setText(this.confirmPopUpUI.getMessage());
    }
}
