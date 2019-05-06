package clue.model;

import clue.model.board.Coordinate;
import clue.model.board.Grid;
import clue.model.card.*;
import clue.model.player.Player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ClueText {
    private static Grid grid;
    private final Cards cards = new Cards();
    private final CardType[] murdererCards = {cards.getCharacterCards().get(0), cards.getWeaponCards().get(0), cards.getRoomCards().get(0)};
    private int numPlayer;
    private Queue<Player> players;
    private Player curPlayer;
    private int curRow, curCol;
    private int curRoll;

    public ClueText(int numPlayer, Player[] players) {

        cards.removeCharacterCard((CharacterCard) murdererCards[0]);
        cards.removeWeaponCard((Weapon) murdererCards[1]);
        cards.removeRoomCard((Room) murdererCards[2]);
        cards.shuffle();
        while (!cards.getCards().isEmpty()) {
            for (Player p : players) {
                p.addCard(cards.getCards().poll());
            }
        }
        execute(new Command(CommandWord.NEW, numPlayer, players));
    }

    private void execute(Command c) {
        switch (c.getCommand()) {
            case NEW:
                grid = new Grid(c.getPlayers(), c.getNumPlayer());
                this.setNumPlayer(c.getNumPlayer());
                this.setPlayers(c.getPlayers());
                for (Player p : this.players) {
                    if (p.getCharacter().equals(CharacterCard.MISS_SCARLET)) {
                        this.curPlayer = p;
                    }
                }
                if (this.curPlayer == null) {
                    this.curPlayer = players.peek();
                }
                break;
            case ROLL:
                Random rand = new Random();
                this.curRoll = rand.nextInt(6) + 1;
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
            case TURN:
                this.players.offer(this.players.poll());
                this.curPlayer = this.players.peek();
            case QUIT:
                break;
            default:
                System.out.println(c);
        }
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

    private Queue<Player> getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = new LinkedList<>();
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
        }
    }
}