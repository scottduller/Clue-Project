package clue.model;

import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.player.Player;

public class ClueText {
    private static Grid grid;
    private int numPlayer;
    private Player[] players;
    private Player curPlayer;
    private int curRow, curCol;
    private int curRoll;

    public ClueText(int numPlayer, Player[] players) {
        execute(new Command(CommandWord.NEW, numPlayer, players));
    }

    public static void main(String[] args) {

    }

    private void execute(Command c) {
        switch (c.getCommand()) {
            case NEW:
                grid = new Grid(c.getPlayers());
                this.setNumPlayer(c.getNumPlayer());
                this.setPlayers(c.getPlayers());
                break;
            case ROLL:
                this.curRoll = this.getGrid().roll();
            case TELEPORT:
                this.setCurPlayer(c.getCurPlayer());
                this.setCurRow(c.getRow());
                this.setCurCol(c.getCol());
                this.getGrid().teleport(this.getCurPlayer(), new Coordinate(getCurRow(), getCurCol()));
                break;
            case MOVE:
                this.setCurPlayer(c.getCurPlayer());
                this.setCurRow(c.getRow());
                this.setCurCol(c.getCol());
                this.getGrid().move(this.getCurPlayer(), new Coordinate(getCurRow(), getCurCol()), this.curRoll);
                break;
            case ENTER:
                break;
            case QUIT:
                break;
            default:
                System.out.println(c);
        }
    }

    private Player getCurPlayer() {
        return curPlayer;
    }

    private void setCurPlayer(Player curPlayer) {
        this.curPlayer = curPlayer;
    }

    private int getCurRow() {
        return curRow;
    }

    private void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    private int getCurCol() {
        return curCol;
    }

    private void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    private void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    private Player[] getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = players;
    }
}