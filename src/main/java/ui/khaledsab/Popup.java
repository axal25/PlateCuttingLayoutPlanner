package ui.khaledsab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;

@Setter
@Getter
public class Popup {
    private PopupController popupController;
    private Parent parent;
    private Stage stage;
    private Scene scene;

    public Popup() {
        this.popupController = new PopupController();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("Popup.fxml");
        fxmlLoader.setLocation(url);
        fxmlLoader.setController(this.popupController);
        try {
            this.parent = fxmlLoader.load();

            this.scene = new Scene(this.parent);

            this.stage = new Stage();
            this.popupController.setStage(this.stage);
            this.stage.initModality(Modality.WINDOW_MODAL);
            this.stage.setScene(this.scene);

            System.out.println("this.stage.showAndWait();");
            this.stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "There was an error trying to load the popup fxml file.").show();
        }
    }
}
