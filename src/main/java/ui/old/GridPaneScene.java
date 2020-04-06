package ui.old;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
public class GridPaneScene implements CustomScene {
    public final static String SCENE_LABEL_TEXT = "GridPaneScene";
    public final static String USERNAME_LABEL_TEXT = "Username: ";
    public final static String PASSWORD_LABEL_TEXT = "Password: ";
    public final static String ADD_LABEL_TEXT = "Addition: ";
    public final static String ADD_COMP_1_LABEL_TEXT = "Component 1: ";
    public final static String ADD_COMP_2_LABEL_TEXT = "Component 2: ";
    public final static String ADD_SUM_LABEL_TEXT = "Sum: ";
    public final static String ADD_LISTEN_SUM_LABEL_TEXT = "Listener sum: ";
    public final static String PROPS_VALUE_DESC_LABEL_TEXT = "Value to be applied to property: ";
    public final static String PROPS_OBJECT_DESC_LABEL_TEXT = "Whole object with properties: ";
    public final static String SWITCH_TO_SCENE_X_BUTTON_TEXT = "Scene";
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
    public final static String ADD_BUTTON_TEXT = "Execute addition";
    public final static String LOGIN_BUTTON_TEXT = "Login";
    public final static String DEFAULT_USERNAME = "DEFAULT_USERNAME";
    public final static String addComp1TextFieldPrompt = "Addition component 1";
    public final static String addComp2TextFieldPrompt = "Addition component 2";
    public final static String addSumTextFieldPrompt = "Addition sum";
    public final static String addListenSumTextFieldPrompt = "Addition sum listening on change of 2 fields";
    public final static int AMOUNT_OF_SCENES = MainUIOld.AMOUNT_OF_SCENES;
    public final static int SCENE_WIDTH = 1920;
    public final static int SCENE_HEIGHT = 600;
    public final static int PADDING = 10;
    public final static int VGAP = 8;
    public final static int HGAP = 10;

    private Scene scene = null;
    private GridPane gridPane = null;
    private Label usernameLabel, passwordLabel, sceneLabel, addLabel, addComp1Label, addComp2Label, addSumLabel, addListenSumLabel, propsValueDescLabel, propsObjectDescLabel = null;
    private JFXRippler addRippler = null;
    private Button[] buttons, switchToSceneXButtons = null;
    private JFXButton loginButton, addButton = null;
    private TextField usernameTextField, passwordTextField, addComp1TextField, addComp2TextField, addSumTextField, addListenSumTextField, propsValueTextFiled = null;
    private CustomObjectWithProperties customObjectWithProperties = null;
    private TextArea propsObjectTextArea = null;

    private MainUIOld mainUIOld = null;

    public GridPaneScene(MainUIOld mainUIOld) {
        this(SCENE_WIDTH, SCENE_HEIGHT, mainUIOld);
    }

    public GridPaneScene(int sceneWidth, int sceneHeight, MainUIOld mainUIOld) {
        setMainUIOld(mainUIOld);

        sceneLabel = new Label(SCENE_LABEL_TEXT);
        usernameLabel = new Label(USERNAME_LABEL_TEXT);
        passwordLabel = new Label(PASSWORD_LABEL_TEXT);

        addLabel = new Label(ADD_LABEL_TEXT);
        addRippler = new JFXRippler(addLabel);
        addRippler.getStyleClass().add("jfx-rippler2");
        addComp1Label = new Label(ADD_COMP_1_LABEL_TEXT);
        addComp2Label = new Label(ADD_COMP_2_LABEL_TEXT);
        addSumLabel = new Label(ADD_SUM_LABEL_TEXT);
        addListenSumLabel = new Label(ADD_LISTEN_SUM_LABEL_TEXT);

        usernameTextField = new TextField(DEFAULT_USERNAME);
        passwordTextField = new TextField();
        passwordTextField.setPromptText("Please enter your password here...");

        addComp1TextField = new TextField();
        addComp2TextField = new TextField();
        addSumTextField = new TextField();
        addListenSumTextField = new TextField();
        addComp1TextField.setPromptText(addComp1TextFieldPrompt);
        addComp2TextField.setPromptText(addComp2TextFieldPrompt);
        addSumTextField.setPromptText(addSumTextFieldPrompt);
        addListenSumTextField.setPromptText(addListenSumTextFieldPrompt);

        loginButton = new JFXButton(LOGIN_BUTTON_TEXT);
        // loginButton.getStyleClass().add("button-raised");
        loginButton.setId("bigger-button");

        addButton = new JFXButton(ADD_BUTTON_TEXT);
        // addButton.getStyleClass().add("button-raised");
        addButton.setId("bigger-button");

        switchToSceneXButtons = new Button[AMOUNT_OF_SCENES-1];
        for (int i = 0; i < switchToSceneXButtons.length; i++) {
            switchToSceneXButtons[i] = new Button(SWITCH_TO_SCENE_X_BUTTON_TEXT + " " + (i+1));
            switchToSceneXButtons[i].getStyleClass().add("button-raised");
        }

        buttons = new Button[BUTTON_TEXTS.length];
        for (int i = 0; i < BUTTON_TEXTS.length && i < buttons.length; i++) {
            buttons[i] = new Button(BUTTON_TEXTS[i]);
        }

        propsValueDescLabel = new Label(PROPS_VALUE_DESC_LABEL_TEXT);
        propsObjectDescLabel = new Label(PROPS_OBJECT_DESC_LABEL_TEXT);
        propsValueTextFiled = new TextField();
        propsObjectTextArea = new TextArea();

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        gridPane.setVgap(VGAP);
        gridPane.setHgap(HGAP);

        GridPane.setConstraints(sceneLabel, 1, 0);
        GridPane.setConstraints(usernameLabel, 0, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);

        GridPane.setConstraints(usernameTextField, 2, 1);
        GridPane.setConstraints(passwordTextField, 2, 2);

        GridPane.setConstraints(loginButton, 2, 3);

        GridPane.setConstraints(addRippler, 0, 5);
        GridPane.setConstraints(addComp1Label, 0, 6);
        GridPane.setConstraints(addComp1TextField, 1, 6);
        GridPane.setConstraints(addComp2Label, 2, 6);
        GridPane.setConstraints(addComp2TextField, 3, 6);
        GridPane.setConstraints(addButton, 4, 6);
        GridPane.setConstraints(addSumLabel, 5, 6);
        GridPane.setConstraints(addSumTextField, 6, 6);
        GridPane.setConstraints(addListenSumTextField, 6, 7);

        GridPane.setConstraints(propsValueDescLabel, 0, 9);
        GridPane.setConstraints(propsValueTextFiled, 2, 9);
        GridPane.setConstraints(propsObjectDescLabel, 0, 10);
        GridPane.setConstraints(propsObjectTextArea, 2, 10);

        gridPane.getChildren().addAll(
            sceneLabel, usernameLabel, passwordLabel, addComp1Label, addComp2Label, addSumLabel, propsValueDescLabel, propsObjectDescLabel,
            addRippler,
            usernameTextField, passwordTextField, addComp1TextField, addComp2TextField, addSumTextField, addListenSumTextField, propsValueTextFiled,
            loginButton, addButton,
            propsObjectTextArea
        );

        for (int i = 0; i < switchToSceneXButtons.length; i++) {
            GridPane.setConstraints(switchToSceneXButtons[i], i, 11);
            gridPane.getChildren().add(switchToSceneXButtons[i]);
        }

        for (int i = 0; i < buttons.length; i++) {
            GridPane.setConstraints(buttons[i], i, 13);
            gridPane.getChildren().add(buttons[i]);
        }

        customObjectWithProperties = new CustomObjectWithProperties();
        
        propsObjectTextArea.setText(customObjectWithProperties.toStringCombined());

        scene = new Scene(gridPane, sceneWidth, sceneHeight);
        scene.getStylesheets().add(MainUIOld.getStyleForApplication());
    }

    public void initEventHandlers() {
        initSwitchToSceneXButtonsEventHandlers();
        initAddButtonEventHandlers();
        initAddListenSumTextFieldEventHandlers();
        initCustomObjectWithPropertiesEventHandlers();
    }

    private void initSwitchToSceneXButtonsEventHandlers() {
        for (int i = 0; i < switchToSceneXButtons.length; i++) {
            switchToSceneXButtons[i].setOnAction(actionEvent -> {
                int customSceneToBeSwitchedToBySceneSwitchButtonIndex = -1;
                for (int j = 0; j < switchToSceneXButtons.length; j++) {
                    if(switchToSceneXButtons[j] == actionEvent.getSource()) customSceneToBeSwitchedToBySceneSwitchButtonIndex = j;
                }
                if(customSceneToBeSwitchedToBySceneSwitchButtonIndex == -1) throw new UnsupportedOperationException("Could not find customSceneToBeSwitchedToBySceneSwitchButtonIndex. BECAUSE: Could not find source button inside switchToSceneXButtons.");
                mainUIOld.getMainStage().setScene(
                    mainUIOld.getCustomScenes()[customSceneToBeSwitchedToBySceneSwitchButtonIndex].getScene()
                );
            });
        }
    }

    private void initAddButtonEventHandlers() {
        addButton.setOnAction(actionEvent -> {
            validateNumber(addComp1TextField, "Component 1");
            validateNumber(addComp2TextField, "Component 2");
            executeAddition(addSumTextField);
        });
    }

    private void initAddListenSumTextFieldEventHandlers() {
        addComp1TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            executeAddition(addListenSumTextField);
        });
        addComp2TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            executeAddition(addListenSumTextField);
        });
    }

    private void initCustomObjectWithPropertiesEventHandlers() {
        propsValueTextFiled.textProperty().addListener((observable, oldValue, newValue) -> {
            customObjectWithProperties.setFirstName(propsValueTextFiled.getText());
        });
        customObjectWithProperties.firstNameProperty().addListener((observable, oldValue, newValue) -> {
            propsObjectTextArea.setText(customObjectWithProperties.toStringCombined());
        });
    }

    private void executeAddition(TextField target) {
        try {
            Float sum = Float.parseFloat(addComp1TextField.getText()) + Float.parseFloat(addComp2TextField.getText());
            target.setText(Float.toString(sum));
        } catch(Exception e) {}    
    }

    private void validateNumber(TextField textField, String textFieldName) {
        if(!isNumber(textField)) new BadInputPopUp("User input into TextField \"" + textFieldName + "\" is not a number. Your input: \"" + textField.getText() + "\".");
    }

    private boolean isNumber(TextField textField) {
        try {
            Float.parseFloat(textField.getText());
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}