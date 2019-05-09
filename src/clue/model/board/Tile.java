package clue.model.board;

import clue.model.card.RoomType;
import clue.model.player.Player;

/**
 * This class represents the tiles of the board that players can move and cant move to
 */
public class Tile {
    private final Coordinate coordinate;
    private boolean isDoor = false;
    private boolean isSecret = false;
    private boolean hasPlayer = false;
    private boolean nullTile = false;
    private boolean isStart = false;

    private RoomType roomType;
    private clue.model.board.Door door;
    private Secret secret;
    private Player player;

    /**
     * Each constructor is used to initialise each tile differently
     * @param row
     * @param col
     * @param isDoor
     * @param isSecret
     * @param isStart
     * @param roomType
     */
    Tile(int row, int col, boolean isDoor, boolean isSecret, boolean isStart, RoomType roomType) {
        this.coordinate = new Coordinate(row, col);
        this.isDoor = isDoor;
        this.roomType = roomType;
        this.hasPlayer = false;
        this.player = null;
        this.isSecret = isSecret;
    }

    //Normal tile
    Tile(int row, int col, Boolean nullTile) {
        this.coordinate = new Coordinate(row, col);
        this.nullTile = nullTile;
    }

    //Secret Tunnel tile
    Tile(int row, int col, RoomType roomType, Secret secret) {
        this.coordinate = new Coordinate(row, col);
        this.roomType = roomType;
        this.isSecret = true;
        this.secret = secret;
        this.nullTile = true;
    }

    //Door tile
    Tile(int row, int col, RoomType roomType, Door door) {
        this.coordinate = new Coordinate(row, col);
        this.isDoor = true;
        this.roomType = roomType;
        this.door = door;
    }

    public boolean setPlayer(Player player) {
        if (player == null) {
            this.player = null;
            hasPlayer = false;
            return true;
        } else if (this.hasPlayer) {
            return false;
        } else {
            player.setCoordinate(getCoordinate());
            this.player = player;
            this.hasPlayer = true;
            return true;
        }
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
    }

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Secret getSecret() {
        return secret;
    }

    public void setSecret(Secret secret) {
        this.secret = secret;
    }

    public Door getDoor() {
        return door;
    }

    void setStart(boolean start) {
        isStart = start;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public boolean isNullTile() {
        return nullTile;
    }

    public void setNullTile(boolean nullTile) {
        this.nullTile = nullTile;
    }

    private boolean isSecret() {
        return isSecret;
    }

    @Override
    public String toString() {
        String output = "";
        if (hasPlayer()) {
            output += "P";
        } else if (isSecret()) {
            output += "T";
        } else if (isDoor()) {
            output += "D";
        } else if (isNullTile()) {
            output += "-";
        } else {
            output += "#";
        }
        return output;
    }
}
