package ui.old;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpStage {
    public final static int WIDTH = 350;
    public final static int HEIGHT = 200;

    public static void display(String title, String message) {
        display(title, message, WIDTH, HEIGHT);
    }

    public static void display(String title, String message, int width, int height) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // block actions on other windows
        stage.setTitle(title);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(actionEvent -> stage.close());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        stage.setScene(scene);

        stage.showAndWait();  // waits on close
    }
}
