package clue.view.viewController;

import clue.view.Clue;
import clue.view.navigation.NavigationController;
import clue.view.navigation.PaneSelector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * A controller class for the start menu where you can choose to play the game exit or go to the settings menu.
 */
public class StartMenuController {
    @FXML
    private void handleMenuQuitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Clue.getPrimaryStage());
        alert.setHeaderText("Quit");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                alert.close();
            }
        } else {
            throw new NullPointerException("Response button does not exist!");
        }
    }

    @FXML
    private void handleMenuStartButton() {
        NavigationController.loadPane(PaneSelector.PLAYER_SELECT);
    }

    @FXML
    private void handleMenuSettingsButton() {
        NavigationController.loadPane(PaneSelector.SETTINGS);
    }

    public void print(Object s) {
        System.out.println(s);
    }
}
