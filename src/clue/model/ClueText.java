package clue.model;

import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.card.*;
import clue.model.player.Player;

import java.util.*;

public class ClueText {
    private static Grid grid;
    private final Cards cards = new Cards();
    private final CardType[] murdererCards = {cards.removeCharacterCard((CharacterCard) cards.getCharacterCards().get(0)),
            cards.removeWeaponCard((Weapon) cards.getWeaponCards().get(0)),
            cards.removeRoomCard((Room) cards.getRoomCards().get(0))};
    private int numPlayer;
    private Queue<Player> players;
    private Player curPlayer;
    private int curRow, curCol;
    private int curRoll;
    private ArrayList<Coordinate> moves;

    public ClueText(int numPlayer, Player[] players) {
        cards.shuffle();
        while (!cards.getCards().isEmpty()) {
            for (Player p : players) {
                p.addCard(cards.getCards().poll());
            }
        }
        execute(new Command(CommandWord.NEW, numPlayer, players));
    }

    public void execute(Command c) {
        switch (c.getCommand()) {
            case NEW:
                grid = new Grid(c.getPlayers(), c.getNumPlayer());
                this.setNumPlayer(c.getNumPlayer());
                this.setPlayers(c.getPlayers());

                for (Player p : this.players) {
                    if (p.getPlaying() && p.getCharacter().equals(CharacterCard.MISS_SCARLET)) {
                        this.curPlayer = p;
                        break;
                    }
                }
                this.setCurCol(this.curPlayer.getCol());
                this.setCurRow(this.curPlayer.getRow());
                break;
            case ROLL:
                Random rand = new Random();
                this.curRoll = rand.nextInt(6) + 1;
                this.moves = grid.movePositions(this.curRoll, curRow, curCol);
                break;
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
            case TURN:
                this.players.offer(this.players.poll());
                this.curPlayer = this.players.peek();
                if(!this.getCurPlayer().getPlaying()) {
                    execute(new Command(CommandWord.TURN));
                    break;
                }
                this.curCol = curPlayer.getCol();
                this.curRow = curPlayer.getRow();
                break;
            case QUIT:
                break;
            default:
                System.out.println(c);
        }
    }


    public static void setGrid(Grid grid) {
        ClueText.grid = grid;
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    public void setCurRoll(int curRoll) {
        this.curRoll = curRoll;
    }

    public ArrayList<Coordinate> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Coordinate> moves) {
        this.moves = moves;
    }

    public int getCurRoll() {
        return curRoll;
    }

    public Cards getCards() {
        return cards;
    }

    public CardType[] getMurdererCards() {
        return murdererCards;
    }

    public Player getCurPlayer() {
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

    public Queue<Player> getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = new LinkedList<>();
        Collections.addAll(this.players, players);
    }
}