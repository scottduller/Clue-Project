package clue.view.viewController;

import clue.model.ClueText;
import clue.model.Command;
import clue.model.CommandWord;
import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.board.Tile;
import clue.model.player.Player;
import clue.view.Clue;
import clue.view.board.ViewPlayer;
import clue.view.board.ViewTile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

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
    private HashMap<Player, Boolean[]> roomCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> weaponCardState = new HashMap<>();
    private HashMap<Player, Boolean[]> characterCardState = new HashMap<>();

    @FXML
    private StackPane boardStackPane;

    @FXML
    public void initialize() {
        initBoard();
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

    public void handleDiceThrow() {
        clue.execute(new Command(CommandWord.ROLL));
        curMoves = clue.getMoves();
        for (Coordinate c : curMoves) {
            for (Node node : gridView.getChildren()) {
                if (node instanceof ViewTile && GridPane.getRowIndex(node) == c.getRow() && GridPane.getColumnIndex(node) == c.getCol()) {
                    node.setStyle("-fx-background-color: green; -fx-opacity: 0.5;");
                    node.setOnMouseClicked(e -> {
                        clue.execute(new Command(CommandWord.MOVE, clue.getCurPlayer(), c.getRow(), c.getCol()));

                        updateBoard();
                    });
                    break;
                }
            }
        }
    }

    private void print(Object s) {
        System.out.println(s);
    }
}
