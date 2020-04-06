package ui.old;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainScene2 implements CustomScene {
    public final static String SWITCH_TO_SCENE_1_BUTTON_TEXT = "Switch to scene 1";
    public final static String SCENE_2_LABEL_TEXT = "Scene 2";
    public final static int SCENE_WIDTH = 1200;
    public final static int SCENE_HEIGHT = 800;

    private Scene scene = null;
    private VBox vBox = null;
    private Button switchToScenebutton = null;
    private Label sceneLabel = null;
    
    public MainScene2(MainUIOld mainUIOld) {
        this(SCENE_WIDTH, SCENE_HEIGHT, mainUIOld);
    }

    public MainScene2(int sceneWidth, int sceneHeight, MainUIOld mainUIOld) {
        switchToScenebutton = new Button();
        switchToScenebutton.setText(SWITCH_TO_SCENE_1_BUTTON_TEXT);
        switchToScenebutton.setOnAction(actionEvent -> mainUIOld.getMainStage().setScene(mainUIOld.getMainScene1().getScene()));

        sceneLabel = new Label();
        sceneLabel.setText(SCENE_2_LABEL_TEXT);

        vBox = new VBox(20);
        vBox.getChildren().addAll(switchToScenebutton, sceneLabel);

        scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
    }

    @Override
    public void initEventHandlers() {
        // TODO Auto-generated method stub

    }
}