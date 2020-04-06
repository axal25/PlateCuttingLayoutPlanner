package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.pop.up.ConfirmPopUpUI;

import java.net.URL;

public class MainUI extends Application {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 600;
    public static final String STAGE_TITLE = "Java Laboratories";
    public static final String VIEW_FILE_NAME = "MainUI.fxml";
    public static final String VIEW_FILE_PACKAGE = "ui";

    private Stage stage = null;
    private Scene scene = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAME);
        Parent parent = FXMLLoader.load(url);

        this.scene = new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT);

        this.stage = stage;
        this.stage.setTitle(STAGE_TITLE);
        this.stage.setScene(scene);
        this.stage.setOnCloseRequest(actionEvent -> {
            actionEvent.consume();
            handleCloseMainStage();
        });
        this.stage.show();
    }

    private void handleCloseMainStage() {
        ConfirmPopUpUI confirmPopUpUI = new ConfirmPopUpUI("Close program confirmation window", "Do you really want to close the program?");
        Boolean answer = confirmPopUpUI.getAnswer();
        if(answer != null && answer) stage.close();
    }
}
