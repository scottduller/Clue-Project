package clue.view.viewController;

import clue.model.ClueText;
import clue.model.Command;
import clue.model.CommandWord;
import clue.model.board.*;
import clue.model.card.*;
import clue.model.player.Player;
import clue.view.Clue;
import clue.view.board.ViewCard;
import clue.view.board.ViewPlayer;
import clue.view.board.ViewTile;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is a controller class for the board fxml file and controls the containers and the controls of the board and how the game is played
 */
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

    @FXML
    public void initialize() {
        initBoard();
    }

    public void handleDiceThrow() {
        diceThrowButton.setDisable(true);
        if (grid.getTile(clue.getCurPlayer().getCoordinate()).isNullTile()) {
            RoomType roomType = grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType();
            Coordinate doorCoordinate = Room.getDOORS().get(roomType)[0].getCoordinate();
            clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), doorCoordinate.getRow(), doorCoordinate.getCol()));
            updateBoard();
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
                            diceThrowButton.setDisable(false);
                            setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "It is your turn to roll the dice!");
                        } else {
                            handleDoorEntered(Room.getSECRETS().containsKey(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType()));
                        }
                        updateBoard();
                    });
                    break;
                }
            }
        }
    }

    private void handleDoorEntered(boolean hasSecret) {
        Coordinate[] coordinates = Room.getRoomPositions().get(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType());
        Coordinate roomCoordinate = null;
        for (int i = 0; i < coordinates.length; i++) {
            if (!grid.getTile(coordinates[i]).hasPlayer()) {
                roomCoordinate = coordinates[i];
                break;
            }
        }
        if (roomCoordinate == null) {
            throw new IllegalStateException("Too Many Players in RoomType: " + grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType().toString());
        }
        clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), roomCoordinate.getRow(), roomCoordinate.getCol()));
        updateBoard();
        VBox roomPrompt = new VBox();
        roomPrompt.setAlignment(Pos.CENTER);
        roomPrompt.setMaxSize(350, 0);
        roomPrompt.setPrefWidth(350);
        roomPrompt.setMinWidth(350);
        roomPrompt.setStyle("-fx-background-color: #92b992; -fx-border-color: white; -fx-border-width: 5");
        Button suggestion = new Button("Suggestion");
        Button accusation = new Button("Accusation");
        Button trapdoor = new Button("Trapdoor");
        suggestion.setMaxWidth(350);
        accusation.setMaxWidth(350);
        trapdoor.setMaxWidth(350);
        suggestion.setOnAction(e -> {
            Label character = new Label("Choose A Character:");
            Label weapon = new Label("Choose A Weapon:");
            GridPane characterGrid = new GridPane();
            GridPane weaponGrid = new GridPane();
            CardType[] suggestions = new CardType[3];
            suggestions[2] = grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType();
            Button suggest = new Button("Suggest");

            characterGrid.setStyle("-fx-border-width: 5; -fx-border-color: white");
            characterGrid.setAlignment(Pos.CENTER);
            weaponGrid.setStyle("-fx-border-width: 5; -fx-border-color: white");
            weaponGrid.setAlignment(Pos.CENTER);

            ViewCard mustardCard = new ViewCard(CharacterType.COLONEL_MUSTARD);
            ViewCard scarletCard = new ViewCard(CharacterType.MISS_SCARLET);
            ViewCard peacockCard = new ViewCard(CharacterType.MRS_PEACOCK);
            ViewCard whiteCard = new ViewCard(CharacterType.MRS_WHITE);
            ViewCard plumCard = new ViewCard(CharacterType.PROFESSOR_PLUM);
            ViewCard greenCard = new ViewCard(CharacterType.MR_GREEN);
            ViewCard candleCard = new ViewCard(WeaponType.CANDLESTICK);
            ViewCard daggerCard = new ViewCard(WeaponType.DAGGER);
            ViewCard leadCard = new ViewCard(WeaponType.LEAD_PIPE);
            ViewCard revolverCard = new ViewCard(WeaponType.REVOLVER);
            ViewCard ropeCard = new ViewCard(WeaponType.ROPE);
            ViewCard spannerCard = new ViewCard(WeaponType.SPANNER);

            mustardCard.setOnMouseClicked(event -> {
                if (mustardCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    mustardCard.setEffect(colorAdjust);
                    suggestions[0] = mustardCard.getCardType();
                } else {
                    mustardCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            scarletCard.setOnMouseClicked(event -> {
                if (scarletCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    scarletCard.setEffect(colorAdjust);
                    suggestions[0] = scarletCard.getCardType();
                } else {
                    scarletCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            peacockCard.setOnMouseClicked(event -> {
                if (peacockCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    peacockCard.setEffect(colorAdjust);
                    suggestions[0] = peacockCard.getCardType();
                } else {
                    peacockCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            whiteCard.setOnMouseClicked(event -> {
                if (whiteCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    whiteCard.setEffect(colorAdjust);
                    suggestions[0] = whiteCard.getCardType();
                } else {
                    whiteCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            plumCard.setOnMouseClicked(event -> {
                if (plumCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    plumCard.setEffect(colorAdjust);
                    suggestions[0] = plumCard.getCardType();
                } else {
                    plumCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            greenCard.setOnMouseClicked(event -> {
                if (greenCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    greenCard.setEffect(colorAdjust);
                    suggestions[0] = greenCard.getCardType();
                } else {
                    greenCard.setEffect(null);
                    suggestions[0] = null;
                }
            });
            candleCard.setOnMouseClicked(event -> {
                if (candleCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    candleCard.setEffect(colorAdjust);
                    suggestions[1] = candleCard.getCardType();
                } else {
                    candleCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            daggerCard.setOnMouseClicked(event -> {
                if (daggerCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    daggerCard.setEffect(colorAdjust);
                    suggestions[1] = daggerCard.getCardType();
                } else {
                    daggerCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            leadCard.setOnMouseClicked(event -> {
                if (leadCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    leadCard.setEffect(colorAdjust);
                    suggestions[1] = leadCard.getCardType();
                } else {
                    leadCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            revolverCard.setOnMouseClicked(event -> {
                if (revolverCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    revolverCard.setEffect(colorAdjust);
                    suggestions[1] = revolverCard.getCardType();
                } else {
                    revolverCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            ropeCard.setOnMouseClicked(event -> {
                if (ropeCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    ropeCard.setEffect(colorAdjust);
                    suggestions[1] = ropeCard.getCardType();
                } else {
                    ropeCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            spannerCard.setOnMouseClicked(event -> {
                if (spannerCard.getEffect() == null) {
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.75);
                    spannerCard.setEffect(colorAdjust);
                    suggestions[1] = spannerCard.getCardType();
                } else {
                    spannerCard.setEffect(null);
                    suggestions[1] = null;
                }
            });
            characterGrid.add(mustardCard, 0, 0);
            characterGrid.add(scarletCard, 1, 0);
            characterGrid.add(peacockCard, 2, 0);
            characterGrid.add(whiteCard, 0, 1);
            characterGrid.add(plumCard, 1, 1);
            characterGrid.add(greenCard, 2, 1);
            weaponGrid.add(candleCard, 0, 0);
            weaponGrid.add(daggerCard, 1, 0);
            weaponGrid.add(leadCard, 2, 0);
            weaponGrid.add(revolverCard, 0, 1);
            weaponGrid.add(ropeCard, 1, 1);
            weaponGrid.add(spannerCard, 2, 1);

            suggest.setOnAction(event -> {
                Label cardFound = new Label("Cross this card off your Detective Notes:");
                Label cardNotFound = new Label("No player has those cards MAKE AN ACCUSATION!");
                ViewCard card = null;
                Button endTurn = new Button("End Turn");

                Coordinate c = getRoomPos(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType());
                Player p = null;
                for (Player player: clue.getPlayers()) {
                    if (player.getCharacter() == suggestions[0]){
                        p = player;
                        break;
                    }
                }
                clue.execute(new Command(CommandWord.TELEPORT, p, c.getRow(), c.getCol()));

                endTurn.setOnAction(event1 -> {
                    boardStackPane.getChildren().remove(roomPrompt);
                    clue.execute(new Command(CommandWord.TURN));
                    diceThrowButton.setDisable(false);
                    setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "It is your turn to roll the dice!");
                    updateBoard();
                });
                for (Player player : clue.getPlayers()) {
                    if (player.getCardHand().contains(suggestions[0])) {
                        card = new ViewCard(suggestions[0]);
                        break;
                    } else if (player.getCardHand().contains(suggestions[1])) {
                        card = new ViewCard(suggestions[1]);
                        break;
                    } else if (player.getCardHand().contains(suggestions[2])) {
                        card = new ViewCard(suggestions[2]);
                        break;
                    }
                }
                if (card == null) {
                    roomPrompt.getChildren().setAll(cardNotFound, endTurn);
                } else {
                    roomPrompt.getChildren().setAll(cardFound,card,endTurn);
                }
            });

            roomPrompt.getChildren().setAll(character, characterGrid, weapon, weaponGrid, suggest);
        });
        trapdoor.setOnAction(e -> {
            Secret secret = Room.getSECRETS().get(grid.getTile(clue.getCurPlayer().getCoordinate()).getRoomType());
            Coordinate c = getRoomPos(secret.getDestination());
            clue.execute(new Command(CommandWord.TELEPORT, clue.getCurPlayer(), c.getRow(), c.getCol()));
            updateBoard();
            boardStackPane.getChildren().remove(roomPrompt);
            clue.execute(new Command(CommandWord.TURN));
            setGameDialog(clue.getCurPlayer().getName() + System.lineSeparator() + "It is your turn to roll the dice!");
                diceThrowButton.setDisable(false);

        });
        roomPrompt.getChildren().addAll(suggestion, accusation);
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

    private Coordinate getRoomPos(RoomType room) {
        Coordinate[] c = Room.getRoomPositions().get(room);
        Coordinate pos = null;
        for (Coordinate co : c) {
            if (!grid.getTile(co).hasPlayer()) {
                pos = co;
                break;
            }
        }
        return pos;
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
        ArrayList<ViewCard> viewCards = new ArrayList<>();
        for (CardType c : initCurPlayerHand) {
            ViewCard card = new ViewCard(c);
            viewCards.add(card);
        }
        playerHand.getChildren().setAll(viewCards);
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
                weaponCardState.put(clue.getCurPlayer(), states);
                break;
            case 1:
                states = characterCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                characterCardState.put(clue.getCurPlayer(), states);
                break;
            case 3:
                states = roomCardState.remove(clue.getCurPlayer());
                states[index] = bool;
                roomCardState.put(clue.getCurPlayer(), states);
                break;
        }
    }

    private void print(Object s) {
        System.out.println(s);
    }
}
