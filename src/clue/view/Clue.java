package clue.view;

import clue.model.ClueText;
import clue.model.board.Grid;
import clue.model.card.Cards;
import clue.view.navigation.NavigationController;
import clue.view.navigation.PaneSelector;
import clue.view.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is the main GUI controller for the grid that controls the main stage and initialises the controllers
 */
public class Clue extends Application {

    private static ClueText clue;
    private static Grid grid;
    private static Stage primaryStage;
    private static Pane rootLayout;
    private static final BoardController boardController = new BoardController();
    private static final PlayerSelectController playerSelectController = new PlayerSelectController();
    private static final SettingsMenuController settingsMenuController = new SettingsMenuController();
    private static final StartMenuController startMenuController = new StartMenuController();

    private Cards cards = new Cards();

    private void main(String[] args) {
        launch(args);
    }

    public static ClueText getClue() {
        return clue;
    }

    public static void setClue(ClueText clue) {
        Clue.clue = clue;
    }

    public static Grid getGrid() {
        return grid;
    }

    public static void setGrid(Grid grid) {
        Clue.grid = grid;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Clue.primaryStage = primaryStage;
    }

    public static Pane getRootLayout() {
        return rootLayout;
    }

    public static void setRootLayout(Pane rootLayout) {
        Clue.rootLayout = rootLayout;
    }

    @Override
    public void start(Stage primaryStage) {
        Clue.primaryStage = primaryStage;
        Clue.primaryStage.setTitle("Clue!");
        Clue.primaryStage.getIcons().add(new Image("clue/view/image/menu/ClueLogo.png"));

        try {
            FXMLLoader loader = new FXMLLoader();

            rootLayout = loader.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(PaneSelector.ROOT.getPath())));

            RootController rootController = loader.getController();

            NavigationController.setRootController(rootController);
            NavigationController.loadPane(PaneSelector.MENU);

            Scene scene = new Scene(rootLayout, 1280, 720);
            primaryStage.sizeToScene();
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(Object s) {
        System.out.println(s);
    }

    public Cards getCards() {
        return cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }
}