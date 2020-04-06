package ui.old;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorderPaneScene implements CustomScene {
    public final static String SCENE_LABEL_TEXT = "BorderPaneScene";
    public final static String SCENE_BUTTON_TEXT = "Scene";
    public final static String[] BUTTON_TEXTS = {
        "File",
        "Edit",
        "Selection",
        "View",
        "Go",
        "Run",
        "Terminal",
        "Help"
    };
    public final static int SCENE_WIDTH = 800;
    public final static int SCENE_HEIGHT = 300;

    private Scene scene = null;
    private BorderPane borderPane = null;

    private HBox topHBox = null;
    private VBox leftVBox = null;
    private VBox rightVBox = null;
    private HBox botHBox = null;

    private VBox centerVBox = null;

    private Button[] topButtons = null;
    private Button[] leftButtons = null;
    private Button[] rightButtons = null;
    private Button[] botButtons = null;

    private Button[] sceneButtons = null;
    private Label sceneLabel = null;

    public BorderPaneScene(MainUIOld mainUIOld) {
        this(SCENE_WIDTH, SCENE_HEIGHT, mainUIOld);
    }

    public BorderPaneScene(int sceneWidth, int sceneHeight, MainUIOld mainUIOld) {
        topHBox = new HBox();
        topButtons = new Button[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length && i < topButtons.length; i++) {
            topButtons[i] = new Button("Top " + BUTTON_TEXTS[i]);
            topHBox.getChildren().add(topButtons[i]);
        }

        leftVBox = new VBox();
        leftButtons = new Button[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length && i < leftButtons.length; i++) {
            leftButtons[i] = new Button("Left " + BUTTON_TEXTS[i]);
            leftVBox.getChildren().add(leftButtons[i]);
        }

        rightVBox = new VBox();
        rightButtons = new Button[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length && i < rightButtons.length; i++) {
            rightButtons[i] = new Button("Right " + BUTTON_TEXTS[i]);
            rightVBox.getChildren().add(rightButtons[i]);
        }

        botHBox = new HBox();
        botButtons = new Button[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length && i < botButtons.length; i++) {
            botButtons[i] = new Button("Bot " + BUTTON_TEXTS[i]);
            botHBox.getChildren().add(botButtons[i]);
        }

        centerVBox = new VBox();
        sceneLabel = new Label(SCENE_LABEL_TEXT);
        centerVBox.getChildren().add(sceneLabel);
        sceneButtons = new Button[2];
        for (int i = 0; i < sceneButtons.length; i++) {
            sceneButtons[i] = new Button(SCENE_BUTTON_TEXT + " " + (i+1));
            centerVBox.getChildren().add(sceneButtons[i]);
        }
        sceneButtons[0].setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getMainScene1().getScene()));
        sceneButtons[1].setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getMainScene2().getScene()));
        
        String style = "-fx-background-color: #0fffff;-fx-border-color: #ff0000;";
        
        sceneLabel.setStyle(style);
        sceneLabel.setStyle(style);

        botButtons[0].setStyle(style);
        botButtons[0].setStyle(style);

        botHBox.setStyle(style);
        botHBox.setStyle(style);

        centerVBox.setStyle(style);
        centerVBox.setStyle(style);

        // public class Cell extends Pane .getView().setStroke(Color.web("#018a23"));
        // public class Cell extends Pane .getView().setFill(Color.web("#018a23"));
        // public class Cell extends Pane .getStyleClass().add("ancestor");

        // centerVBox.getStylesheets().add("style1/button-styles.css");

        borderPane = new BorderPane();
        borderPane.setTop(topHBox);
        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightVBox);
        borderPane.setBottom(botHBox);
        borderPane.setCenter(centerVBox);

        scene = new Scene(borderPane, sceneWidth, sceneHeight);
    }

    @Override
    public void initEventHandlers() {
        // TODO Auto-generated method stub

    }
}