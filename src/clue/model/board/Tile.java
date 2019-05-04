package clue.model.board;

import clue.model.card.Room;
import clue.model.player.Player;

public class Tile {
    private final Coordinate coordinate;
    private boolean isDoor = false;
    private boolean isSecret = false;
    private boolean hasPlayer = false;
    private boolean nullTile = false;
    private boolean isStart = false;

    private clue.model.card.Room room;
    private clue.model.board.Door door;
    private Secret secret;
    private Player player;


    Tile(int row, int col, boolean isDoor, boolean isSecret, boolean isStart, clue.model.card.Room room) {
        this.coordinate = new Coordinate(row, col);
        this.isDoor = isDoor;
        this.room = room;
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
    Tile(int row, int col, Room room, Secret secret) {
        this.coordinate = new Coordinate(row, col);
        this.room = room;
        this.isSecret = true;
        this.secret = secret;
        this.nullTile = true;
    }

    //Door tile
    Tile(int row, int col, Room room, Door door) {
        this.coordinate = new Coordinate(row, col);
        this.isDoor = true;
        this.room = room;
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

    public Door getDoor() {
        return door;
    }

    void setStart(boolean start) {
        isStart = start;
    }

    public clue.model.card.Room getRoom() {
        return room;
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
