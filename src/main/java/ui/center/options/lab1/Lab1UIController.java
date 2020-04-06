package ui.center.options.lab1;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import ui.center.options.AbstractCenterUIController;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class Lab1UIController extends AbstractCenterUIController {

    @FXML private Node centerNode; // just for injection from Lab1UI_Intro_Load.fxml or other Laboratory1UI_... .fxml file
    private ObjectProperty<Node> centerNodeProperty = new SimpleObjectProperty<>();

    public Lab1UIController() { super.setCenterNodeProperty(this.centerNodeProperty); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.centerNodeProperty.set(this.centerNode);
    }
}
