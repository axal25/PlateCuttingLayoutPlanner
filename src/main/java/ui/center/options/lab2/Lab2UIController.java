package ui.center.options.lab2;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import ui.center.options.AbstractCenterUIController;

import java.net.URL;
import java.util.ResourceBundle;

public class Lab2UIController extends AbstractCenterUIController {
    public VBox centerNode;
    public ObjectProperty<Node> centerNodeProperty = new SimpleObjectProperty<>();

    public Lab2UIController() { super.setCenterNodeProperty(this.centerNodeProperty); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { this.centerNodeProperty.set(this.centerNode); }
}
