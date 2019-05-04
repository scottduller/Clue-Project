package clue.view;

import clue.model.ClueText;
import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.board.Tile;
import clue.model.card.CharacterCard;
import clue.model.player.Computer;
import clue.model.player.Player;
import clue.view.board.ViewPlayer;
import clue.view.board.ViewTile;
import clue.view.navigation.NavigationController;
import clue.view.navigation.PaneSelector;
import clue.view.navigation.RootController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Clue extends Application {

    private static ClueText clue;
    private static Grid grid;
    private static Stage primaryStage;
    private static Pane rootLayout;
    @FXML
    Button b1;
    private Player[] players;
    private int playerPTR = 0;
    private ClueText clueText;
    //Start Menu
    @FXML
    private Button menuStartButton;
    @FXML
    private Button menuQuitButton;
    @FXML
    private Button menuSettingsButton;
    //Settings Menu
    @FXML
    private ToggleButton settingsMenuSound;
    @FXML
    private ToggleButton settingsMenuFullscreen;
    @FXML
    private Button settingsMenuBack;
    //Player Select
    @FXML
    private SplitPane playerSelectSplit;
    @FXML
    private TextField playerNo;
    @FXML
    private Button playerNoConfirm;
    //Player Select Editable Section
    @FXML
    private VBox editableSection;
    @FXML
    private TextField playerName;
    @FXML
    private ComboBox<String> characterSelect;
    @FXML
    private CheckBox isComputer;
    @FXML
    private Button addPlayer;
    @FXML
    private Button startGame;
    //Player Select Nicknames
    @FXML
    private Label scarlettLabel;
    @FXML
    private Label mustardLabel;
    @FXML
    private Label peacockLabel;
    @FXML
    private Label plumLabel;
    @FXML
    private Label greenLabel;

    //Board
    @FXML
    private Label whiteLabel;
    @FXML
    private GridPane gridView;

    //Test
    @FXML
    private StackPane boardStackPane;

    public static void fullscreen() {
        if (!primaryStage.isFullScreen()) {
            letterbox(primaryStage.getScene(), rootLayout);
            primaryStage.setFullScreen(true);
        } else {
            letterbox(primaryStage.getScene(), rootLayout);
            primaryStage.setFullScreen(false);
        }
    }

    private static void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);

    }

    private void main(String[] args) {
        launch(args);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    private Pane getRootLayout() {
        return rootLayout;
    }

    @Override
    public void start(Stage primaryStage) {
        Clue.primaryStage = primaryStage;
        Clue.primaryStage.setTitle("Clue!");
        Clue.primaryStage.getIcons().add(new Image("clue/view/image/ClueLogo.png"));

        try {
            FXMLLoader loader = new FXMLLoader();

            rootLayout = loader.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(PaneSelector.ROOT.getPath())));

            RootController rootController = loader.getController();

            NavigationController.setRootController(rootController);
            NavigationController.loadPane(PaneSelector.MENU);

            Scene scene = new Scene(rootLayout, 1280, 720);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Menu Handling

    @FXML
    private void handleMenuQuitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
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

    //Settings Menu

    @FXML
    private void handleSettingsMenuFullscreen() {
        Clue.fullscreen();
    }

    @FXML
    private void handleSettingsMenuSound() {
        //TODO When sound is added add a mute for all sound.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void handleSettingsMenuBack() {
        NavigationController.loadPane(PaneSelector.MENU);
    }

    //Player Select

    @FXML
    private void handlePlayerNoSelect() {

        if (playerNo.getCharacters().toString().matches("[2-6]")) {
            players = new Player[Integer.parseInt(playerNo.getText())];
            editableSection.setVisible(true);
            characterSelect.getItems().setAll("Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green", "Mrs. White", "Mrs. Peacock");
        } else {
            playerNo.setText("");
            playerNo.setPromptText(playerNo.getPromptText() + "!!!");
        }
    }

    @FXML
    private void handlePlayerNoReset() {
        editableSection.setVisible(false);
        players = null;
        playerPTR = 0;
        scarlettLabel.setText("");
        greenLabel.setText("");
        whiteLabel.setText("");
        plumLabel.setText("");
        peacockLabel.setText("");
        mustardLabel.setText("");
        characterSelect.getItems().setAll("Colonel Mustard", "Miss Scarlet", "Professor Plum", "Mr. Green", "Mrs. White", "Mrs. Peacock");
    }

    @FXML
    private void handleAddPlayer() {
        if (playerPTR < players.length && playerName.getText() != null && characterSelect.getValue() != null) {
            Player p;
            if (isComputer.isSelected()) {
                p = new Computer(playerName.getText(), characterSelect.getValue());
            } else {
                p = new Player(playerName.getText(), characterSelect.getValue());
            }
            int index = -1;
            for (int i = 0; i < playerPTR; i++) {
                if (playerPTR != 0 && players[i].equals(p)) {
                    playerPTR--;
                    index = i;
                }
            }

            if (index != -1) {
                players[index] = p;
            } else {
                players[playerPTR] = p;
            }

            switch (characterSelect.getSelectionModel().getSelectedItem()) {
                case "Miss Scarlet":
                    scarlettLabel.setText(playerName.getText());
                    break;
                case "Mr. Green":
                    greenLabel.setText(playerName.getText());
                    break;
                case "Mrs. White":
                    whiteLabel.setText(playerName.getText());
                    break;
                case "Mrs. Peacock":
                    peacockLabel.setText(playerName.getText());
                    break;
                case "Professor Plum":
                    plumLabel.setText(playerName.getText());
                    break;
                case "Colonel Mustard":
                    mustardLabel.setText(playerName.getText());
                    break;
            }
            playerPTR++;
            if (playerPTR >= players.length) {
                addPlayer.setVisible(false);
                startGame.setVisible(true);
            }
        } else {
            if (playerName.getText() == null) {
                playerName.setPromptText(playerName.getPromptText() + "!!!");
            }
            if (characterSelect.getValue() == null) {
                characterSelect.setPromptText(characterSelect.getPromptText() + "!!!");
            }

        }
        playerName.setText("");
        characterSelect.getSelectionModel().clearSelection();
    }

    @FXML
    public void handlePlayerStartButton() {
        NavigationController.loadPane(PaneSelector.GAME);
        clue = new ClueText(players.length, players);
        grid = clue.getGrid();
    }

    @FXML
    public void handleGrid() {
        for (int row = 0; row < Grid.getvSize(); row++) {
            for (int col = 0; col < Grid.gethSize(); col++) {
                Tile tile = clue.getGrid().getTile(new Coordinate(row, col));
                print(clue.getGrid().getTile(new Coordinate(row, col)));
                StackPane viewTile = new ViewTile(tile, new Coordinate(row, col));
                if (tile.hasPlayer()) {
                    Circle playerView = new ViewPlayer(tile.getPlayer());
                    playerView.setOnMouseClicked(e -> {
                        //TODO:Implement moving
                    });
                    viewTile.getChildren().add(playerView);
                }
                gridView.add(viewTile, col, row);
            }
        }
    }

    @FXML
    public void handleTest() {
        players = new Player[]{
                new Player("1", CharacterCard.MISS_SCARLET.toString()), new Player("2", CharacterCard.MRS_PEACOCK.toString()),
                new Player("3", CharacterCard.COLONEL_MUSTARD.toString()), new Player("4", CharacterCard.MRS_WHITE.toString()),
                new Player("4", CharacterCard.MR_GREEN.toString()), new Player("6", CharacterCard.PROFESSOR_PLUM.toString())
        };
        handlePlayerStartButton();
    }

    public void print(Object s) {
        System.out.println(s);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}