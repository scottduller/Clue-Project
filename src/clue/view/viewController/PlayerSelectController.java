package clue.view.viewController;

import clue.model.ClueText;
import clue.model.card.CharacterCard;
import clue.model.player.Computer;
import clue.model.player.Player;
import clue.view.Clue;
import clue.view.navigation.NavigationController;
import clue.view.navigation.PaneSelector;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class PlayerSelectController {
    private static Player[] players = new Player[6];
    @FXML
    Button b1;
    private int playerNum = 0;
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
    @FXML
    private Label whiteLabel;

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
        Clue.setClue(new ClueText(players.length, players));
        Clue.setGrid(Clue.getClue().getGrid());
        NavigationController.loadPane(PaneSelector.GAME);
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
}
