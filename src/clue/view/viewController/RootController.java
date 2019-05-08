package clue.view.viewController;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class RootController {
    @FXML
    private StackPane childHolder;

    public void setChild(Node node) {
        childHolder.getChildren().setAll(node);
    }
}
