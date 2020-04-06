package ui.center.options;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCenterUIController implements Initializable {
    private ObjectProperty<Node> centerNodeProperty;
}
