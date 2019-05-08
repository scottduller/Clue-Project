package clue.view.navigation;

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
