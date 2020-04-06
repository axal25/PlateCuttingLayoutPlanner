package ui.khaledsab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage stage;
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		this.scene = new Scene(parent);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
}
