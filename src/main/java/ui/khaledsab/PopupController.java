package ui.khaledsab;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

public class PopupController implements Initializable {
	@FXML private TextField usernameTF;
	@FXML private PasswordField passwordPF;
	@FXML private Button connectBtn;
	@Setter private Stage stage = null;
	private HashMap<String, Object> result = new HashMap<String, Object>();

	public PopupController() {
		System.out.println("new PopupController() 1");
		checkInitializedFields();
		System.out.println("new PopupController() 2");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("initialize 1");
		checkInitializedFields();
		System.out.println("initialize 2");
		connectBtn.setOnAction((event)-> handleConnectBtnEventAction());
	}

	public HashMap<String, Object> getResult() {
		return this.result;
	}

	/**
	 * setting the stage of this view
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void handleConnectBtnEventAction() {
		result.clear();
		result.put("username", usernameTF.getText());
		result.put("password", passwordPF.getText());
		if(stage!=null) stage.close();
	}

	private void checkInitializedFields() {
		isFieldInitialized("usernameTF", usernameTF);
		isFieldInitialized("passwordPF", passwordPF);
		isFieldInitialized("connectBtn", connectBtn);
		isFieldInitialized("stage", stage);
	}

	private void isFieldInitialized(String fieldName, Object object) {
		if(object == null) System.out.println("[NULL] \"" + fieldName + "\" node == null");
		else System.out.println("[NOT NULL] \"" + fieldName + "\" node != null");
	}
}
