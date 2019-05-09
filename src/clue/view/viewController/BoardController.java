package clue.view.viewController;

import clue.model.ClueText;
import clue.model.Command;
import clue.model.CommandWord;
import clue.model.board.*;
import clue.model.card.CardType;
import clue.model.player.Player;
import clue.view.Clue;
import clue.view.board.CardView;
import clue.view.board.ViewPlayer;
import clue.view.board.ViewTile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BoardController {
    private ClueText clue = Clue.getClue();
    private Grid grid = Clue.getGrid();
    private ArrayList<Coordinate> curMoves;

    @FXML
    private GridPane gridView;
    @FXML
    private GridPane characterDetectiveNotes;
    @FXML
    private GridPane roomsDetectiveNotes;
    @FXML
    private GridPane weaponsDetectiveNotes;
    @FXML
    private ImageView diceFaceView;
    @FXML
    private HBox playerHand;
    @FXML
    private Label gameDialog;
    @FXML
    private Button diceThrowButton;
    @FXML
    private StackPane boardStackPane;

    private HashMap<Player, Boolean[]> roomCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> weaponCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> characterCardState = new HashMap<>();
    private ArrayList<CardType> initCurPlayerHand = new ArrayList<>();
    private ArrayList<CardType> hoverCurPlayerHand = new ArrayList<>();

    @FXML
    public void initialize() {
        initBoard();
    }

    public void handleDiceThrow() {
        if (grid.playable(clue.getCurPlayer().getCoordinate())){
            clue.model.card.Room room = grid.getTile(clue.getCurPlayer().getCoordinate()).getRoom();
            Coordinate doorCoordinate = Room.getDOORS().get(room)[0].getCoordinate();
            clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), doorCoordinate.getRow(), doorCoordinate.getCol()));
        }
        clue.execute(new Command(CommandWord.ROLL));
        switch (clue.getCurRoll()) {
            case 1:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice1.png"));
                break;
            case 2:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice2.png"));
                break;
            case 3:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice3.png"));
                break;
            case 4:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice4.png"));
                break;
            case 5:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice5.png"));
                break;
            case 6:
                diceFaceView.setImage(new Image("clue/view/image/board/dice/dice6.png"));
                break;

        }
        curMoves = clue.getMoves();
        setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "Click a green tile to move there.");
        movePlayerView();
    }

    private void movePlayerView() {
        for (Coordinate c : curMoves) {
            for (Node node : gridView.getChildren()) {
                if (node instanceof ViewTile && GridPane.getRowIndex(node) == c.getRow() && GridPane.getColumnIndex(node) == c.getCol()) {
                    node.setStyle("-fx-background-color: green; -fx-opacity: 0.5;");
                    node.setOnMouseClicked(e -> {
                        clue.execute(new Command(CommandWord.MOVE, clue.getCurPlayer(), c.getRow(), c.getCol()));
                        if (!grid.getTile(clue.getCurPlayer().getCoordinate()).isDoor()) {
                            clue.execute(new Command(CommandWord.TURN));
                            setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "It is your turn to roll the dice!");
                        } else {
                            handleDoorEntered(Room.getSECRETS().containsKey(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoom()));
                        }
                        updateBoard();
                    });
                    break;
                }
            }
        }
    }

    private void handleDoorEntered(boolean hasSecret) {
        Coordinate[] coordinates  = Room.getRoomPositions().get(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoom());
        Coordinate roomCoordinate = null;
        for (int i = 0; i < coordinates.length; i++) {
            if (!grid.getTile(coordinates[i]).hasPlayer()) {
                roomCoordinate = coordinates[i];
                break;
            }
        }
        if (roomCoordinate == null) {
            throw new IllegalStateException("Too Many Players in Room: " + grid.getTile(clue.getCurPlayer().getCoordinate()).getRoom().toString());
        }
        clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), roomCoordinate.getRow(), roomCoordinate.getCol()));
        diceThrowButton.setDisable(true);
        updateBoard();
        VBox roomPrompt = new VBox();
        roomPrompt.setMaxSize(350,350);
        roomPrompt.setPrefSize(350,350);
        roomPrompt.setStyle("-fx-border-color: white; -fx-border-width: 5");
        Button suggestion = new Button("Suggestion");
        Button accusation = new Button("Accusation");
        Button trapdoor = new Button("Trapdoor");

        //suggestion.setOnAction();
        //accusation.setOnAction();
        trapdoor.setOnAction(e -> {
            //TODO 1: Figure out why this is null
            Secret secret = Room.getSECRETS().get(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoom());
            clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), secret.getDestination().getRow(), secret.getDestination().getCol()));
            clue.execute(new Command(CommandWord.TURN));
        });
        if (hasSecret) {
            gameDialog.setText(clue.getCurPlayer().getName() + System.lineSeparator() +
                    "Choose Either:" + System.lineSeparator() +
                    "1) Make A Suggestion." + System.lineSeparator() +
                    "2) Make An Accusation." + System.lineSeparator() +
                    "3) Use The Trapdoor.");
            roomPrompt.getChildren().add(trapdoor);

        } else {
            gameDialog.setText(clue.getCurPlayer().getName() + System.lineSeparator() +
                    "Choose Either:" + System.lineSeparator() +
                    "1) Make A Suggestion." + System.lineSeparator() +
                    "2) Make An Accusation.");
        }
        boardStackPane.getChildren().add(roomPrompt);

    }

    private void initBoard() {
        for (int row = 0; row < Grid.getvSize(); row++) {
            for (int col = 0; col < Grid.gethSize(); col++) {
                Tile tile = Clue.getGrid().getTile(new Coordinate(row, col));
                StackPane viewTile = new ViewTile(tile, new Coordinate(row, col));
                if (tile.hasPlayer()) {
                    Circle playerView = new ViewPlayer(tile.getPlayer());
                    viewTile.getChildren().add(playerView);
                }
                gridView.add(viewTile, col, row);
            }
        }

        for (Player p : clue.getPlayers()) {
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
        initCardHand();
        setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "It is your turn to roll the dice!");
    }

    private void setGameDialog(String msg) {
        gameDialog.setText(msg);
    }

    private void initCardHand() {
        initCurPlayerHand.clear();
        initCurPlayerHand.addAll(clue.getCurPlayer().getCardHand());
        ArrayList<CardView> cardViews = new ArrayList<>();
        for (CardType c : initCurPlayerHand) {
            CardView card = new CardView(c);
            cardViews.add(card);
        }
        playerHand.getChildren().setAll(cardViews);
    }

    private void updateBoard() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : gridView.getChildren()) {
            if (node instanceof ViewTile) {
                nodes.add(node);
            }
        }
        for (Node node : nodes) {
            gridView.getChildren().remove(node);
        }
        for (int row = 0; row < Grid.getvSize(); row++) {
            for (int col = 0; col < Grid.gethSize(); col++) {
                Tile tile = Clue.getGrid().getTile(new Coordinate(row, col));
                StackPane viewTile = new ViewTile(tile, new Coordinate(row, col));
                if (tile.hasPlayer()) {
                    Circle playerView = new ViewPlayer(tile.getPlayer());
                    viewTile.getChildren().add(playerView);
                }
                gridView.add(viewTile, col, row);
            }
        }
        for (Node n : weaponsDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (n.getEffect() != null) {
                    n.setEffect(null);
                }
            }
        }
        for (Node n : characterDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (n.getEffect() != null) {
                    n.setEffect(null);
                }
            }
        }
        for (Node n : roomsDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (n.getEffect() != null) {
                    n.setEffect(null);
                }
            }
        }
        int PTR = 0;
        Boolean[] bools = weaponCardState.get(clue.getCurPlayer());
        for (Node n : weaponsDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (!bools[PTR]) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    n.setEffect(colorAdjust);
                }
                PTR++;
            }
        }
        PTR = 0;
        bools = characterCardState.get(clue.getCurPlayer());
        for (Node n : characterDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (!bools[PTR]) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    n.setEffect(colorAdjust);
                }
                PTR++;
            }
        }
        PTR = 0;
        bools = roomCardState.get(clue.getCurPlayer());
        for (Node n : roomsDetectiveNotes.getChildren()) {
            if (n instanceof ImageView) {
                if (!bools[PTR]) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    n.setEffect(colorAdjust);
                }
                PTR++;
            }
        }
        initCardHand();
    }

    private void detectiveNotesData(int type, int index, boolean bool) {
        switch (type) {
            case 0:
                Boolean[] states = weaponCardState.remove(Clue.getClue().getCurPlayer());
                states[index] = bool;
                for (Boolean state : states) {
                    print(state);
                }
                weaponCardState.put(clue.getCurPlayer(), states);
                break;
            case 1:
                states = characterCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                for (Boolean state : states) {
                    print(state);
                }
                characterCardState.put(clue.getCurPlayer(), states);
                break;
            case 3:
                states = roomCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                for (Boolean state : states) {
                    print(state);
                }
                roomCardState.put(clue.getCurPlayer(), states);
                break;
        }
    }

    private void print(Object s) {
        System.out.println(s);
    }
}
