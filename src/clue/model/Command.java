package clue.model;

import clue.model.board.Coordinate;
import clue.model.board.Door;
import clue.model.player.Player;

/**
 * Command that has many constructors that emulate different commands and initialises the data needed for such command
 */
public class Command {

    private CommandWord command;
    private clue.model.board.Coordinate coordinate;
    private Player[] players;
    private Player curPlayer = null;
    private Door curDoor = null;
    private int numPlayer = 0;
    private String message;

    // default
    public Command(CommandWord command, String message) {
        this.command = command;
        this.message = message;
    }

    // roll/turn
    public Command(CommandWord command) {
        this.command = command;
    }

    // move/teleport player
    public Command(CommandWord command, Player player, int row, int col) {
        super();
        this.command = command;
        this.curPlayer = player;
        this.coordinate = new Coordinate(row, col);
    }

    public Command(CommandWord command, Door door) {
        super();
        this.command = command;
        this.curDoor = door;
    }

    // create game
    public Command(CommandWord command, int numPlayer, Player[] players) {
        super();
        this.command = command;
        this.numPlayer = numPlayer;
        this.players = players;
    }

    @Override
    public String toString() {
        return "Command " + this.getCommand() + ", row: " + this.getRow() + ", column: " + this.getCol();
    }

    CommandWord getCommand() {
        return command;
    }

    public void setCommand(CommandWord command) {
        this.command = command;
    }

    int getRow() {
        return coordinate.getRow();
    }

    int getCol() {
        return coordinate.getCol();
    }


    Player getCurPlayer() {
        return curPlayer;
    }

    public void setCurPlayer(Player curPlayer) {
        this.curPlayer = curPlayer;
    }

    int getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
