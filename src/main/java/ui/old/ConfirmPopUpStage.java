package ui.old;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmPopUpStage {
    public final static int WIDTH = 350;
    public final static int HEIGHT = 200;

    public static Boolean answer = null;

    public static Boolean display(String title, String message) {
        return display(title, message, WIDTH, HEIGHT);
    }

    public static Boolean display(String title, String message, int width, int height) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // block actions on other windows
        stage.setTitle(title);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(actionEvent -> {
            answer = true;
            stage.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(actionEvent -> {
            answer = false;
            stage.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, yesButton, noButton);

        Scene scene = new Scene(vBox, WIDTH, HEIGHT);
        stage.setScene(scene);

        stage.showAndWait();  // waits on close

        return answer;
    }
}