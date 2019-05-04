package clue.view.navigation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class RootController {
    @FXML
    private StackPane childHolder;

    void setChild(Node node) {
        childHolder.getChildren().setAll(node);
    }
}
