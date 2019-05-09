package clue.view.viewController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * A controller of the root fxml file and is used as a container for all other fxml files to placed in
 */
public class RootController {
    @FXML
    private StackPane childHolder;

    public void setChild(Node node) {
        childHolder.getChildren().setAll(node);
    }
}
