package clue.view.navigation;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class NavigationController {

    private static RootController rootController;

    public static void setRootController(RootController rootController) {
        NavigationController.rootController = rootController;
    }

    public static void loadPane(PaneSelector pane) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getClassLoader().getResource(pane.getPath()));
            rootController.setChild(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
