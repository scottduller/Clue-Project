package clue.model.board;

import clue.model.card.RoomType;
import clue.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is used for the main data of the grid and stores where all
 * objects are on the board and where players can move and where they can not move
 */
public class Grid {
    private static final int V_SIZE = 25;
    private static final int H_SIZE = 24;
    private static final Room rooms = new Room();
    private final Coordinate[] nullSpots = {new Coordinate(0, 8), new Coordinate(0, 15), new Coordinate(6, 23), new Coordinate(8, 23),
            new Coordinate(16, 23), new Coordinate(4, 0), new Coordinate(11, 0), new Coordinate(17, 0),
            new Coordinate(23, 6), new Coordinate(24, 6), new Coordinate(24, 7), new Coordinate(24, 8),
            new Coordinate(23, 17), new Coordinate(24, 17), new Coordinate(24, 16), new Coordinate(24, 15)};
    private final Coordinate[] playerSpots = {new Coordinate(15, 16), new Coordinate(15, 17), new Coordinate(15, 18), new Coordinate(6, 6),
            new Coordinate(10, 6), new Coordinate(19, 5), new Coordinate(23, 8), new Coordinate(23, 9),
            new Coordinate(24, 9), new Coordinate(23, 15), new Coordinate(23, 14), new Coordinate(24, 14)};
    private final Tile[][] grid = new Tile[V_SIZE][H_SIZE];
    private final Coordinate[] startSpots = {new Coordinate(5, 0), new Coordinate(18, 0),
            new Coordinate(24, 9), new Coordinate(24, 14),
            new Coordinate(0, 16), new Coordinate(7, 23)};
    private int currentRow = 0;
    private int currentCol = 0;
    private Player[] players;
    private int playerNum;

    /**
     * The constructor is used to initialise the board before the game is started.
     * it assigns each position in a 2d array with a tile object describing what can be done with that tile and where that tile is
     * weapons are all assigned a room to start in
     * players are assigned to starting spots
     * @param players   an array of Player objects
     * @param playerNum the number of players in the players array
     * @see Player
     * @see Tile
     * @see clue.model.card.WeaponType
     */
    public Grid(Player[] players, int playerNum) {
        this.players = players;
        this.playerNum = playerNum;
        for (int row = 0; row < V_SIZE; row++) {
            for (int col = 0; col < H_SIZE; col++) {
                grid[row][col] = new Tile(row, col, false);

                for (RoomType r : RoomType.values()) {
                    if (rooms.getRooms().containsKey(r)) {
                        if (col >= rooms.getRoomTileCoord(r, 0).getCol() && col <= rooms.getRoomTileCoord(r, 1).getCol() &&
                                row >= rooms.getRoomTileCoord(r, 0).getRow() && row <= rooms.getRoomTileCoord(r, 1).getRow()) {
                            Tile t = new Tile(row, col, true);
                            t.setRoomType(r);
                            grid[row][col] = t;
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
        for (int i = 0; i < playerNum; i++) {
            Room.addRoomWeapon();
        }
        this.initPlayerLoc();
    }

    public static int getvSize() {
        return V_SIZE;
    }

    public static int gethSize() {
        return H_SIZE;
    }

    /**
     * This method is used to check whether a player can move to a certain tile
     * if they can the teleport method is called to move the player
     * @param player        the player to be moved
     * @param coordinate    the position for the player to be moved to
     * @param moves         the amount of moves the player can make
     * @return if player can move to a tile and is teleported return true; else false
     * @see Player
     * @see Coordinate
     */
    public boolean move(Player player, Coordinate coordinate, int moves) {
        return playable(coordinate) && distance(moves, player.getCoordinate(), coordinate) && teleport(player, coordinate);
    }

    /**
     * This method is used to move a player to a location without constraints of the tiles location
     * @param player        player to be teleported
     * @param coordinate    position to be teleported to
     * @return if player is teleported true; otherwise false
     * @see Player
     * @see Coordinate
     */
    public boolean teleport(Player player, Coordinate coordinate) {
            this.getTile(player.getCoordinate()).setPlayer(null);
            player.setCoordinate(coordinate);
            return this.getTile(coordinate).setPlayer(player);
    }

    public ArrayList<Coordinate> movePositions(int moves, int row, int col) {
        ArrayList<Coordinate> moveCoordinates = new ArrayList<>();
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

    /**
     * This method calculates the manhattan distance between two coordinates and compares it with the amount of moves possible
     * @param moves number of moves
     * @param init  initial coordinate
     * @param fin   final coordinate
     * @return true if moves >= manhattan distance; otherwise false
     * @see Coordinate
     */
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

    /**
     * This method checks a certain card to see if this is a movable tile
     * @param coordinate    the coordinate to check
     * @return
     * @see Coordinate
     */
    public boolean playable(Coordinate coordinate) {
        return coordinate.getCol() >= 0 && coordinate.getCol() < H_SIZE &&
                coordinate.getRow() >= 0 && coordinate.getRow() < V_SIZE &&
                !this.getGrid()[coordinate.getRow()][coordinate.getCol()].isNullTile() &&
                !this.getGrid()[coordinate.getRow()][coordinate.getCol()].hasPlayer();
    }

    /**
     * This method initialises the players of the game to start location
     * @see Player
     * @see Coordinate
     */
    public void initPlayerLoc() {
        ArrayList<Coordinate> starts = new ArrayList<>();
        for (Coordinate c : startSpots) {
            starts.add(c);
        }
        Collections.shuffle(starts);
        int roomPTR = 0;
        for (Player p : players) {
            if (p.getPlaying()) {
                Coordinate c = starts.remove(0);
                p.setCoordinate(c);
                getGrid()[c.getRow()][c.getCol()].setPlayer(p);
            } else {
                Coordinate[] coordinates  = Room.getRoomPositions().get(RoomType.values()[roomPTR]);
                Coordinate roomCoordinate = null;
                for (int i = 0; i < coordinates.length; i++) {
                    if (!getTile(coordinates[i]).hasPlayer()) {
                        roomCoordinate = coordinates[i];
                        break;
                    }
                }
                p.setCoordinate(roomCoordinate);
                getGrid()[roomCoordinate.getRow()][roomCoordinate.getCol()].setPlayer(p);
                roomPTR++;
            }
        }

    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
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

    public clue.model.board.Room getRooms() {
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
