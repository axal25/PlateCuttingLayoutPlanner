package ui.pop.up;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ui.FxmlResourceUrlFetcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Getter
@Setter
public class ConfirmPopUpUI {
    public final static int WIDTH = 400;
    public final static int HEIGHT = 200;
    public static final String VIEW_FILE_NAME = "ConfirmPopUpUI.fxml";
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "pop" + File.separator + "up";

    private ConfirmPopUpUIController confirmPopUpUIController = null;
    private String title = null;
    private String message = null;
    private Stage stage = null;
    private Scene scene = null;
    private Parent parent = null;

    private Boolean answer = null;

    public ConfirmPopUpUI(String title, String message) {
        this(title, message, WIDTH, HEIGHT);
    }

    public ConfirmPopUpUI(String title, String message, int width, int height) {
        this.title = title;
        this.message = message;
        createWindow(width, height);
    }

    private void createWindow(int width, int height) {
        try {
            initController();
            this.scene = new Scene(this.parent, width, height);
            this.stage = new Stage();
            this.stage.initModality(Modality.APPLICATION_MODAL); // blocks actions on other windows
            this.stage.setTitle(this.title);
            this.stage.setScene(this.scene);
            this.confirmPopUpUIController.setMessageLabelText();
            this.stage.showAndWait();  // waits on close
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void initController() throws IOException {
        URL url = FxmlResourceUrlFetcher.getViewFileUrl(VIEW_FILE_PACKAGE, VIEW_FILE_NAME);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        this.parent = fxmlLoader.load();
        this.confirmPopUpUIController = fxmlLoader.getController();
        this.confirmPopUpUIController.setConfirmPopUpUI(this);
        this.confirmPopUpUIController.setMessageLabelText();
    }
}