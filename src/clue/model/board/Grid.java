package clue.model.board;

import clue.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    private static final int V_SIZE = 25;
    private static final int H_SIZE = 24;
    private final Coordinate[] nullSpots = new Coordinate[]{new Coordinate(0, 8), new Coordinate(0, 15), new Coordinate(6, 23), new Coordinate(8, 23),
            new Coordinate(16, 23), new Coordinate(4, 0), new Coordinate(11, 0), new Coordinate(17, 0),
            new Coordinate(23, 6), new Coordinate(24, 6), new Coordinate(24, 7), new Coordinate(24, 8),
            new Coordinate(23, 17), new Coordinate(24, 17), new Coordinate(24, 16), new Coordinate(24, 15)};
    private final Coordinate[] playerSpots = new Coordinate[]{new Coordinate(15, 16), new Coordinate(15, 17), new Coordinate(15, 18), new Coordinate(6, 6),
            new Coordinate(10, 6), new Coordinate(19, 5), new Coordinate(23, 8), new Coordinate(23, 9),
            new Coordinate(24, 9), new Coordinate(23, 15), new Coordinate(23, 14), new Coordinate(24, 14)};
    private final Coordinate[] startSpots = new Coordinate[]{new Coordinate(5, 0), new Coordinate(18, 0),
            new Coordinate(24, 9), new Coordinate(24, 14),
            new Coordinate(0, 16), new Coordinate(7, 23)};
    private final Tile[][] grid = new Tile[V_SIZE][H_SIZE];
    private final Room rooms = new Room();
    private int currentRow = 0;
    private int currentCol = 0;
    private int currentRoll;
    private Player[] players;


    public Grid(Player[] players) {
        this.players = players;
        for (int row = 0; row < V_SIZE; row++) {
            for (int col = 0; col < H_SIZE; col++) {
                grid[row][col] = new Tile(row, col, false);

                for (clue.model.card.Room r : clue.model.card.Room.values()) {
                    if (rooms.getRooms().containsKey(r)) {
                        if (col >= rooms.getRoomTileCoord(r, 0).getCol() && col <= rooms.getRoomTileCoord(r, 1).getCol() &&
                                row >= rooms.getRoomTileCoord(r, 0).getRow() && row <= rooms.getRoomTileCoord(r, 1).getRow()) {
                            grid[row][col] = new Tile(row, col, true);
                        }
                    }
                    if (rooms.getDoors().containsKey(r)) {
                        for (Door i : rooms.getDoors().get(r)) {
                            if (i.getCoordinate().getCol() == col && i.getCoordinate().getRow() == row) {
                                grid[row][col] = new Tile(row, col, r, i);
                            }
                        }
                    }
                    if (rooms.getSecret().containsKey(r)) {
                        if (rooms.getSecretLocation(r).getCol() == col && rooms.getSecretLocation(r).getRow() == row) {
                            grid[row][col] = new Tile(row, col, r, rooms.getSecretObj(r));
                        }
                    }
                }

                for (Coordinate i : nullSpots) {
                    if (i.getCol() == col && i.getRow() == row) {
                        grid[row][col] = new Tile(row, col, true);
                    }
                }

                for (Coordinate i : playerSpots) {
                    if (i.getCol() == col && i.getRow() == row) {
                        grid[row][col] = new Tile(row, col, false);
                    }
                }

                for (Coordinate i : startSpots) {
                    if (i.getCol() == col && i.getRow() == row) {
                        grid[row][col].setStart(true);
                    }
                }
            }
        }
        this.initPlayerLoc(players);
    }

    public static int getvSize() {
        return V_SIZE;
    }

    public static int gethSize() {
        return H_SIZE;
    }

    public boolean move(Player player, Coordinate coordinate, int moves) {
        return distance(moves, player.getCoordinate(), coordinate) && teleport(player, coordinate);
    }

    public boolean teleport(Player player, Coordinate coordinate) {
        if (playable(coordinate)) {
            this.getTile(player.getCoordinate()).setPlayer(null);
            return this.getTile(coordinate).setPlayer(player);
        }
        return false;
    }

    public int roll() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public ArrayList<Coordinate> movePositions(int moves, int row, int col) {
        ArrayList<Coordinate> moveCoordinates = new ArrayList<>();
        int PTR = 0;
        for (int i = row - moves; i <= row + moves; i++) {
            for (int j = col - moves; j <= col + moves; j++) {
                Coordinate init = new Coordinate(row, col);
                Coordinate fin = new Coordinate(i, j);
                if (playable(fin) && distance(moves, init, fin)) {
                    moveCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        return moveCoordinates;
    }

    public boolean distance(int moves, Coordinate init, Coordinate fin) {
        if (this.getGrid()[fin.getRow()][fin.getCol()].isDoor()) {
            Door.DoorDirection doorDirection = this.getGrid()[fin.getRow()][fin.getCol()].getDoor().getEntryDirection();
            Coordinate newCoordinate = null;
            switch (doorDirection) {
                case UP:
                    newCoordinate = new Coordinate(fin.getRow() - 1, fin.getCol());
                    break;
                case DOWN:
                    newCoordinate = new Coordinate(fin.getRow() + 1, fin.getCol());
                    break;
                case LEFT:
                    newCoordinate = new Coordinate(fin.getRow(), fin.getCol() - 1);
                    break;
                case RIGHT:
                    newCoordinate = new Coordinate(fin.getRow(), fin.getCol() + 1);
                    break;
            }
            return distance(moves - 1, init, newCoordinate);
        }
        return moves >= Math.abs(fin.getRow() - init.getRow()) + Math.abs(fin.getCol() - init.getCol());
    }

    public boolean playable(Coordinate coordinate) {
        return coordinate.getCol() >= 0 && coordinate.getCol() < H_SIZE &&
                coordinate.getRow() >= 0 && coordinate.getRow() < V_SIZE &&
                !this.getGrid()[coordinate.getRow()][coordinate.getCol()].isNullTile() &&
                !this.getGrid()[coordinate.getRow()][coordinate.getCol()].hasPlayer();
    }

    public void initPlayerLoc(Player[] player) {
        ArrayList<Coordinate> starts = new ArrayList<>();
        for (Coordinate c : startSpots) {
            starts.add(c);
        }
        Collections.shuffle(starts);

        for (Player p : player) {
            Coordinate c = starts.remove(0);
            p.setCoordinate(c);
            getGrid()[c.getRow()][c.getCol()].setPlayer(p);
            players = player;
        }

    }

    public Tile getTile(Coordinate coordinate) {
        return getGrid()[coordinate.getRow()][coordinate.getCol()];
    }

    private Tile[][] getGrid() {
        return grid;
    }

    private Coordinate[] getPlayerSpots() {
        return playerSpots;
    }

    public Coordinate[] getNullSpots() {
        return nullSpots;
    }

    public Coordinate[] getStartSpots() {
        return startSpots;
    }

    public Room getRooms() {
        return rooms;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    private int getCurrentRoll() {
        return currentRoll;
    }

    public void setCurrentRoll(int currentRoll) {
        this.currentRoll = currentRoll;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int ptr1 = 0;
        for (int row = 0; row < V_SIZE; row++) {
            int ptr = 0;
            for (int col = 0; col < H_SIZE; col++) {
                output.append(this.getGrid()[row][col].toString());
                ptr++;
            }
            output.append(System.lineSeparator());
            ptr1++;
        }
        return output.toString();
    }
}
