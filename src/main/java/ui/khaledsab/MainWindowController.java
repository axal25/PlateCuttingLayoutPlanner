package ui.khaledsab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
	@FXML private Button popItBtn;
	@FXML private Label resultLbl;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		resultLbl.setText("Lets get something in here");
		popItBtn.setOnAction((event) -> handleActionEventOnPopItBtn());
	}

	private void handleActionEventOnPopItBtn() {
		Popup popup = new Popup();
		HashMap<String, Object> resultMap = popup.getPopupController().getResult();
		resultLbl.setText("I've got this (username: " + resultMap.get("username")
			+ " /Password: " + resultMap.get("password") + ")");
	}
}
