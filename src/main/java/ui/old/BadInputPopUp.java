package ui.old;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BadInputPopUp {
    public final static String TITLE_TEXT = "Bad Input";
    public final static String MESSAGE_DESC_LABEL_TEXT = "Message: ";
    public final static String INSTRUCTIONS_LABEL_TEXT = "Confirm to continue.";
    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;

    private Stage stage = null;
    private Scene scene = null;
    private GridPane gridPane = null;
    private Label messageDescLabel, messageLabel, instructionsLabel = null;
    private JFXRippler messageDescRippler, messageRippler, instructionsRippler = null;
    private JFXButton confirmButton = null;

    public BadInputPopUp(String message) {
        this(message, WIDTH, HEIGHT);
    }

    public BadInputPopUp(String message, int width, int height) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // block actions on other windows
        stage.setTitle(TITLE_TEXT);

        messageDescLabel = new Label(MESSAGE_DESC_LABEL_TEXT);
        messageLabel = new Label(message);
        instructionsLabel = new Label(INSTRUCTIONS_LABEL_TEXT);

        messageDescRippler = new JFXRippler(messageDescLabel);
        messageRippler = new JFXRippler(messageLabel);
        instructionsRippler = new JFXRippler(instructionsLabel);

        confirmButton = new JFXButton("OK");
        confirmButton.getStyleClass().add("button-raised");
        confirmButton.setOnAction(actionEvent -> {
            stage.close();
        });

        gridPane = new GridPane();
        GridPane.setConstraints(messageDescRippler, 0, 0);
        GridPane.setConstraints(messageRippler, 1, 1);
        GridPane.setConstraints(instructionsRippler, 1, 2);
        GridPane.setConstraints(confirmButton, 1, 3);
        gridPane.getChildren().addAll(messageDescRippler, messageRippler, instructionsRippler, confirmButton);

        scene = new Scene(gridPane, WIDTH, HEIGHT);
        scene.getStylesheets().add(MainUIOld.getStyleForApplication());

        stage.setScene(scene);
        stage.showAndWait();  // waits on close
    }
}