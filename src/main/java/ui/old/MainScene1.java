package ui.old;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainScene1 implements CustomScene {
    public final static String SWITCH_TO_SCENE_2_BUTTON_TEXT = "Switch to scene 2";
    public final static String SWITCH_TO_SCENE_3_BUTTON_TEXT = "Switch to scene 3";
    public final static String SWITCH_TO_SCENE_4_BUTTON_TEXT = "Switch to scene 4";
    public final static String SCENE_LABEL_TEXT = "Scene 1";
    public final static String POP_UP_WINDOW_BUTTON_TEXT = "Open pop-up window";
    public final static String CONFIRM_WINDOW_BUTTON_TEXT = "Confirm pop-up window";
    public final static int SCENE_WIDTH = 800;
    public final static int SCENE_HEIGHT = 600;

    private Scene scene = null;
    private VBox vBox = null;
    private Button switchToScene2Button, switchToScene3Button, switchToScene4Button, popUpWindowButton, confirmPopUpWindowButton = null;
    private Label sceneLabel = null;

    public MainScene1(MainUIOld mainUIOld) {
        this(SCENE_WIDTH, SCENE_HEIGHT, mainUIOld);
    }

    public MainScene1(int sceneWidth, int sceneHeight, MainUIOld mainUIOld) {
        switchToScene2Button = new Button();
        switchToScene2Button.setText(SWITCH_TO_SCENE_2_BUTTON_TEXT);
        switchToScene2Button.setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getMainScene2().getScene()));

        switchToScene3Button = new Button();
        switchToScene3Button.setText(SWITCH_TO_SCENE_3_BUTTON_TEXT);
        switchToScene3Button.setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getBorderPaneScene().getScene()));

        switchToScene4Button = new Button();
        switchToScene4Button.setText(SWITCH_TO_SCENE_4_BUTTON_TEXT);
        switchToScene4Button.setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getGridPaneScene().getScene()));
        
        popUpWindowButton = new Button();
        popUpWindowButton.setText(POP_UP_WINDOW_BUTTON_TEXT);
        popUpWindowButton.setOnAction(actionEvent -> PopUpStage.display("Pop-up window", "You have to close this window before continuing..."));

        confirmPopUpWindowButton = new Button();
        confirmPopUpWindowButton.setText(CONFIRM_WINDOW_BUTTON_TEXT);
        confirmPopUpWindowButton.setOnAction(actionEvent -> {
            Boolean answer = ConfirmPopUpStage.display("Confirm pop-up window", "You have to either confirm or cancel before continuing...");
            sceneLabel.setText(SCENE_LABEL_TEXT + " | answer from confirm-pop-up-window: " + answer);
        });

        sceneLabel = new Label();
        sceneLabel.setText(SCENE_LABEL_TEXT);
        
        vBox = new VBox(20);
        vBox.getChildren().addAll(switchToScene2Button, switchToScene3Button, switchToScene4Button, sceneLabel, popUpWindowButton, confirmPopUpWindowButton);
        
        scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    @Override
    public void initEventHandlers() {
        // TODO Auto-generated method stub

    }
}