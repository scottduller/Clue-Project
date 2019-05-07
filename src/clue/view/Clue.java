package clue.view;

import clue.model.ClueText;
import clue.model.Command;
import clue.model.CommandWord;
import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.board.Tile;
import clue.model.card.Cards;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Clue extends Application {

    private static ClueText clue;
    private static Grid grid;
    private static Stage primaryStage;
    private static Pane rootLayout;
    private static Player[] players = new Player[6];
    private ArrayList<Coordinate> curMoves;
    @FXML
    Button b1;
    private Cards cards = new Cards();
    private int playerNum = 0;


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
    @FXML
    private GridPane characterDetectiveNotes;
    @FXML
    private GridPane roomsDetectiveNotes;
    @FXML
    private GridPane weaponsDetectiveNotes;
    private HashMap<Player, Boolean[]> roomCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> weaponCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> characterCardState = new HashMap<>();

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
        Clue.primaryStage.getIcons().add(new Image("clue/view/image/menu/ClueLogo.png"));

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
            playerNum = Integer.parseInt(playerNo.getText());
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
        players = new Player[6];
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
        for (int i = 0; i < players.length; i++) {
            if (i < playerNum && playerName.getText() != null && characterSelect.getValue() != null) {
                Player p;
                if (isComputer.isSelected()) {
                    p = new Computer(playerName.getText(), characterSelect.getValue(), true);
                } else {
                    p = new Player(playerName.getText(), characterSelect.getValue(), true);
                }

                for (int j = 0; j < i; j++) {
                    if (players[j].equals(p)) {
                        i--;
                        players[j] = p;
                        break;
                    }
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
                if (i >= playerNum) {
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
                Tile tile = grid.getTile(new Coordinate(row, col));
                //print(grid.getTile(new Coordinate(row, col)));
                StackPane viewTile = new ViewTile(tile, new Coordinate(row, col));
                viewTile.setOnMouseClicked(e -> {
                    print("Initial call" + viewTile);
                });
                int r = row, c = 0;
                if (tile.hasPlayer()) {
                    Circle playerView = new ViewPlayer(tile.getPlayer());
                    viewTile.getChildren().add(playerView);
                }
                gridView.add(viewTile, col, row);
            }
        }

        for (Player p : players) {
            Boolean[] view = new Boolean[9];
            Arrays.fill(view, true);
            roomCardState.put(p, view);
            view = new Boolean[6];
            Arrays.fill(view, true);
            characterCardState.put(p, view);
            view = new Boolean[6];
            Arrays.fill(view, true);
            weaponCardState.put(p, view);

            int PTR = 0;
            for (Node n : weaponsDetectiveNotes.getChildren()) {
                if (n instanceof ImageView) {
                    int i = PTR;
                    n.setOnMouseClicked(e -> {
                        if (n.getEffect() == null) {
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setBrightness(-0.75);
                            n.setEffect(colorAdjust);
                            detectiveNotesData(0, i, false);
                        } else {
                            n.setEffect(null);
                            detectiveNotesData(0, i, true);
                        }

                    });
                    PTR++;
                }
            }
            PTR = 0;
            for (Node n : characterDetectiveNotes.getChildren()) {
                if (n instanceof ImageView) {
                    int i = PTR;
                    n.setOnMouseClicked(e -> {
                        if (n.getEffect() == null) {
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setBrightness(-0.75);
                            n.setEffect(colorAdjust);
                            detectiveNotesData(1, i, false);
                        } else {
                            n.setEffect(null);
                            detectiveNotesData(1, i, true);
                        }
                    });
                    PTR++;
                }
            }
            PTR = 0;
            for (Node n : roomsDetectiveNotes.getChildren()) {
                if (n instanceof ImageView) {
                    int i = PTR;
                    n.setOnMouseClicked(e -> {
                        if (n.getEffect() == null) {
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setBrightness(-0.75);
                            n.setEffect(colorAdjust);
                            detectiveNotesData(2, i, false);
                        } else {
                            n.setEffect(null);
                            detectiveNotesData(2, i, true);
                        }
                    });
                    PTR++;
                }
            }
        }
    }

    public void detectiveNotesData(int type, int index, boolean bool) {
        switch (type) {
            case 0:
                Boolean[] states = weaponCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                for (int i = 0; i < states.length; i++) {
                    print(states[i]);
                }
                weaponCardState.put(clue.getCurPlayer(), states);
                break;
            case 1:
                states = characterCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                for (int i = 0; i < states.length; i++) {
                    print(states[i]);
                }
                characterCardState.put(clue.getCurPlayer(), states);
                break;
            case 3:
                states = roomCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                for (int i = 0; i < states.length; i++) {
                    print(states[i]);
                }
                roomCardState.put(clue.getCurPlayer(), states);
                break;
        }
    }

    public void handleDiceThrow() {
        clue.execute(new Command(CommandWord.ROLL));
        curMoves = clue.getMoves();
        ArrayList<ViewTile> coords = new ArrayList<>();
        for (Coordinate c : curMoves) {
            for (Node node : gridView.getChildren()) {
                if (node instanceof ViewTile && gridView.getRowIndex(node) == c.getRow() && gridView.getColumnIndex(node) == c.getCol()) {
                    print(node);
                    node.setOnMouseEntered(e -> {
                        print("Dice Roll");
                    });
                    break;
                }
            }
        }
        for (ViewTile v:coords) {
            print(v);
        }
    }

    @FXML
    public void handleTest() {
        players = new Player[]{
                new Player("1", CharacterCard.MISS_SCARLET.toString(), true), new Player("2", CharacterCard.MRS_PEACOCK.toString(), true),
                new Player("3", CharacterCard.COLONEL_MUSTARD.toString(), true), new Player("4", CharacterCard.MRS_WHITE.toString(), false),
                new Player("4", CharacterCard.MR_GREEN.toString(), false), new Player("6", CharacterCard.PROFESSOR_PLUM.toString(), false)
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