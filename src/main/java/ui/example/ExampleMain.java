package ui.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@Setter
@Getter
public class ExampleMain extends Application {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 600;
    public static final String VIEW_FILE_NAME = "ExampleMain.fxml";
    public static final String VIEW_FILE_PACKAGE = "ui" + File.separator + "example";
    public static final String PATH_TO_RESOURCE_FOLDER = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    private Scene scene = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getViewFileURL(VIEW_FILE_PACKAGE, VIEW_FILE_NAME);
        Parent parent = FXMLLoader.load(url);
        primaryStage.setTitle("Example main.AppMain");
        scene = new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT);
//        scene.getStylesheets().add(MainUIOld.getStyleForApplication());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * View file (.fxml) needs to be in "src/main/resources/..." location under folder corresponding to Model class
     * (.java) package location from "src/main/java/..." to be able to get URL using:
     * URL url = ClassName.class.getResource(viewFileName);
     *
     * The second option is to get URL using absolute path via:
     * URL url = new File(viewFileFullPath).toURI().toURL();
     **/
    public static URL getViewFileURL(String packageName, String fileName) throws MalformedURLException {
        final String viewFileFullPath = PATH_TO_RESOURCE_FOLDER + File.separator + packageName + File.separator + fileName;
        URL url = ExampleMain.class.getResource(fileName);
        if(url == null) url = new File(viewFileFullPath).toURI().toURL();
        return url;
    }
}
