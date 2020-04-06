package ui.old;

import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainUIOld extends Application {
    public final static String MAIN_STAGE_WINDOW = "Java Laboratories by Jacek Oleś";
    public final static int AMOUNT_OF_SCENES = 4;
    public final static String javaVersion = System.getProperty("java.version");
    public final static String javafxVersion = System.getProperty("javafx.version");
    public final static String pathToStyleFile = "/css/main.css";

    private Stage mainStage = null;
    private MainScene1 mainScene1 = null;
    private MainScene2 mainScene2 = null;
    private BorderPaneScene borderPaneScene = null;
    private GridPaneScene gridPaneScene = null;
    private CustomScene[] customScenes = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainScene1 = new MainScene1(this);
        mainScene2 = new MainScene2(this);
        borderPaneScene = new BorderPaneScene(this);
        gridPaneScene = new GridPaneScene(this);

        customScenes = new CustomScene[]{mainScene1, mainScene2, borderPaneScene, gridPaneScene};
        gridPaneScene.initEventHandlers();

        mainStage.setScene(mainScene1.getScene());
        mainStage.setTitle(MAIN_STAGE_WINDOW);
        mainStage.setOnCloseRequest(actionEvent -> {
            actionEvent.consume();
            closeMainStage();
        });
        mainStage.show();
    }

    private void closeMainStage() {
        Boolean answer = ConfirmPopUpStage.display("Before closing confirm pop-up window", "Do you really want to close the program?");
        if(answer) mainStage.close();
    }

    public static String getStyleForApplication() {
        URL styleUrl = MainUIOld.class.getResource(pathToStyleFile);
        String style = null;
        if(styleUrl!=null) {
            style = styleUrl.toExternalForm();
            return style;
        }
        else {
            System.err.println("Could not retrieve resource (style .css file for application) at path: " + pathToStyleFile);
            return null;
        }
    }
}