package ui.center.options.lambda.exp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import ui.center.options.AbstractCenterUIController;

import java.net.URL;
import java.util.ResourceBundle;

public class LambdaExpUIController extends AbstractCenterUIController {
    public VBox centerNode;
    public ObjectProperty<Node> centerNodeProperty = new SimpleObjectProperty<>();

    public LambdaExpUIController() { super.setCenterNodeProperty(this.centerNodeProperty); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { this.centerNodeProperty.set(this.centerNode); }
}
