package clue.view.navigation;

/**
 * This is an enum class that contains the different paths for the fxml files to be used by the NavigationController
 */
public enum PaneSelector {
    ROOT("clue/view/fxml/navigation/RootLayout.fxml"),
    MENU("clue/view/fxml/startMenu/MenuLayout.fxml"),
    GAME("clue/view/fxml/mainBoard/BoardLayout.fxml"),
    SETTINGS("clue/view/fxml/startMenu/SettingsMenuLayout.fxml"),
    PLAYER_SELECT("clue/view/fxml/startMenu/PlayerSelectLayout.fxml");

    private final String PATH;

    PaneSelector(String PATH) {
        this.PATH = PATH;
    }

    public String getPath() {
        return PATH;
    }
}
